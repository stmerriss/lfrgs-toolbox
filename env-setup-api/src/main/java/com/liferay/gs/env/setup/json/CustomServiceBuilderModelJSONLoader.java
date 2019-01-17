package com.liferay.gs.env.setup.json;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.BaseLocalService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Andrew Betts
 */
public class CustomServiceBuilderModelJSONLoader {

	public <M extends BaseModel<M>> M loadModel(
		JSONObject jsonObject, String primKeyPropertyName,
		Map<String, Function<Object, Object>> propertyCustomizers,
		Function<Object, M> modelGenerator, Function<M, M> modelPersiter) {

		Iterator<String> keys = jsonObject.keys();

		Map<String, Object> modelAttributes = new HashMap<>();

		M m = null;

		while (keys.hasNext()) {
			String key = keys.next();

			Object property = jsonObject.get(key);

			Function<Object, Object> propertyCustomizer =
				propertyCustomizers.get(key);

			if (propertyCustomizer != null) {
				property = propertyCustomizer.apply(property);
			}

			if (primKeyPropertyName.equals(key)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"attempting to generate model with primKey = " +
							property);
				}

				m = modelGenerator.apply(property);
			}

			modelAttributes.put(key, property);
		}

		if (m == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("attempting to generate model with primKey = 0");
			}

			m = modelGenerator.apply(0);
		}

		if (m == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("model generator returned null");
			}

			return null;
		}

		m.setModelAttributes(modelAttributes);

		return modelPersiter.apply(m);
	}

	public <M extends BaseModel<M>, S extends BaseLocalService, P> M loadWithLocalService(
			JSONObject jsonObject, String primKeyPropertyName,
			Map<String, Function<Object, Object>> propertyCustomizers,
			Class<M> modelClass, Class<P> primKeyClass, P dummyPrimKey,
			S localService)
		throws NoSuchMethodException {

		String modelName = modelClass.getSimpleName();

		String createMethodName = "create" + modelName;
		String fetchMethodName = "fetch" + modelName;
		String updateMethodName = "update" + modelName;

		Class<?> serviceClass = localService.getClass();

		Method createModelMethod = serviceClass.getMethod(
			createMethodName, primKeyClass);
		Method fetchModelMethod = serviceClass.getMethod(
			fetchMethodName, primKeyClass);
		Method updateModelMethod = serviceClass.getMethod(
			updateMethodName, modelClass);

		Function<Object, M> modelGenerator = (obj) -> {
			try {
				if (primKeyClass.isAssignableFrom(obj.getClass())) {
					P primKey = primKeyClass.cast(obj);

					M m = (M)fetchModelMethod.invoke(localService, primKey);

					if (m != null) {
						return m;
					}
				}
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("unable to invoke fetch method", e);
				}
			}

			try {
				return (M)createModelMethod.invoke(localService, dummyPrimKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("unable to invoke create method", e);
				}

				return null;
			}
		};

		Function<M, M> modelPersister = m -> {
			try {
				return (M)updateModelMethod.invoke(localService, m);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("unable to invoke update method", e);
				}

				return null;
			}
		};

		return loadModel(
			jsonObject, primKeyPropertyName, propertyCustomizers, modelGenerator,
			modelPersister);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomServiceBuilderModelJSONLoader.class);

}