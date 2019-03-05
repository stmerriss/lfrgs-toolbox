package com.lfrgs.tools.api.json;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Andrew Betts
 */
public class JSONUtil {

	public static String getString(JSONObject jsonObject, String key) {
		JSONArray jsonArray = jsonObject.getJSONArray(key);

		if (Objects.isNull(jsonArray)) {
			return jsonObject.getString(key);
		}

		Set<String> valueSet = new TreeSet<>(new StringComparator());

		for (int i = 0; i < jsonArray.length(); i++) {
			valueSet.add(jsonArray.getString(i));
		}

		return StringUtil.merge(valueSet);
	}

}
