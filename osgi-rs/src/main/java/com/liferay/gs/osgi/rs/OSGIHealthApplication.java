package com.liferay.gs.osgi.rs;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

import org.apache.felix.service.command.CommandProcessor;
import org.apache.felix.service.command.CommandSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author andrewbetts
 */
@ApplicationPath("/osgi-health")
@Component(immediate = true, service = Application.class)
public class OSGIHealthApplication extends Application {

	public Set<Object> getSingletons() {
		return Collections.singleton(this);
	}

	@GET
	@Path("/bundle/{query}")
	@Produces("application/json")
	public String check(@PathParam("query") String query) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		String status = "problem";

		if (_checkOSGIStatus(jsonObject, _getCommand(query))) {
			status = "ok";
		}

		jsonObject.put("status", status);

		return jsonObject.toString();
	}

	private String _getCommand(String query) {
		if (query == null || query.isEmpty()) {
			return "lb";
		}

		return "lb | grep " + query.replace(';', ' ').replace('|', ' ');
	}

	private boolean _checkOSGIStatus(JSONObject jsonObject, String command) {
		String errorMessage;

		try (UnsyncByteArrayOutputStream outputUBAOS = new UnsyncByteArrayOutputStream();
			 UnsyncByteArrayOutputStream errorUBAOS = new UnsyncByteArrayOutputStream();
			 PrintStream outputPrintStream = new PrintStream(outputUBAOS);
			 PrintStream errorPrintStream = new PrintStream(errorUBAOS)) {

			CommandSession commandSession =
				_commandProcessor.createSession(
					_emptyInputStream, outputPrintStream, errorPrintStream);

			commandSession.execute(command);

			outputPrintStream.flush();
			errorPrintStream.flush();

			String output = outputUBAOS.toString();

			String errorContent = errorUBAOS.toString();

			if (Validator.isNull(errorContent)) {
				return _parseOSGIOutput(
					output, jsonObject, commandSession, outputUBAOS,
					errorUBAOS);
			}
			else {
				errorMessage = errorContent;
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("health check failed", e);
			}

			errorMessage = e.getMessage();
		}

		JSONObject errorJSONObject = JSONFactoryUtil.createJSONObject();

		errorJSONObject.put("status", "problem");
		errorJSONObject.put("errorMessage", errorMessage);

		jsonObject.put("OSGI", errorJSONObject);

		return false;
	}

	private boolean _parseOSGIOutput(
			String output, JSONObject jsonObject, CommandSession commandSession,
			UnsyncByteArrayOutputStream outputUBAOS, UnsyncByteArrayOutputStream errorUBAOS)
		throws Exception {

		JSONObject moduleStateJSONObject = JSONFactoryUtil.createJSONObject();
		JSONArray moduleJSONArray = JSONFactoryUtil.createJSONArray();

		moduleStateJSONObject.put("modules", moduleJSONArray);

		jsonObject.put("OSGI", moduleStateJSONObject);

		output = StringUtil.replace(output, "\n", "|");

		String[] tokens = StringUtil.split(output, "|");

		StringBundler errorSB = new StringBundler(tokens.length * 4);

		for(int i = 3; i < tokens.length; i+=4) {
			JSONObject tokenJSONObject = JSONFactoryUtil.createJSONObject();

			String id = StringUtil.trim(tokens[i - 3]);
			String state = StringUtil.trim(tokens[i - 2]);
			String level = StringUtil.trim(tokens[i - 1]);
			String name = StringUtil.trim(tokens[i]);

			if (!StringUtil.equalsIgnoreCase(state, "active") &&
				!StringUtil.equalsIgnoreCase(state, "resolved")) {

				outputUBAOS.reset();
				errorUBAOS.reset();

				commandSession.execute("diag " + id);

				String error = errorUBAOS.toString();

				if (Validator.isNull(error)) {
					JSONArray diagJSONArray =
						 JSONFactoryUtil.createJSONArray();

					String[] diag = StringUtil.split(
						outputUBAOS.toString(), "\n");

					for (String message : diag) {
						diagJSONArray.put(StringUtil.trim(message));
					}

					tokenJSONObject.put("diag", diagJSONArray);
				}
				else {
					tokenJSONObject.put("diagError", error);
				}

				errorSB.append(name);
				errorSB.append("is ");
				errorSB.append(state);
				errorSB.append(", ");
			}

			tokenJSONObject.put("id", id);
			tokenJSONObject.put("state", state);
			tokenJSONObject.put("level", level);
			tokenJSONObject.put("name", name);

			moduleJSONArray.put(tokenJSONObject);
		}

		if (errorSB.index() > 0) {
			errorSB.setIndex(errorSB.index() - 1);

			jsonObject.put("status", "problem");
			jsonObject.put("errorMessage", errorSB.toString());

			return false;
		}

		jsonObject.put("status", "ok");

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OSGIHealthApplication.class);

	private static final InputStream _emptyInputStream =
		new UnsyncByteArrayInputStream(new byte[0]);

	@Reference
	private CommandProcessor _commandProcessor;

}