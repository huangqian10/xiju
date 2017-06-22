package com.xiyoukeji.xiju.core.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class Utils {

	public static void writeBack(HttpServletRequest request,
			HttpServletResponse response, JSONObject jv) {
		try {
			String callback = request.getParameter("callback");
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(Utils.getJsonResult(callback, jv));
		} catch (Exception ex) {
		}
	}

	public static String getJsonResult(String callback, JSONObject json) {
		if (StringUtils.isEmpty(callback)) {
			return json.toJSONString();
		}
		return cleanKeywords(callback) + "(" + json.toJSONString() + ")";
	}

	public static void writeBack(HttpServletRequest request,
			HttpServletResponse response, String str) {
		try {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(str);
			response.getWriter().flush();
		} catch (Exception ex) {
		}
	}

	public static String cleanKeywords(String value) {
		if (value == null)
			return null;

		value = value.replaceAll("(?i)eval\\((.*)\\)", "");
		value = value.replaceAll(
				"[\\\"\\\'][\\s]*(?i)javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("(?i)script", "");
		value = value.replaceAll("%0a", "").replaceAll("%0d", "");
		value = value.replaceAll("&", "&amp;").replaceAll("#", "&#35;")
				.replaceAll("\\\\", "&#92;");
		value = value.replaceAll("'", "&apos;").replaceAll("\"", "&quot;");
		value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		value = value.replaceAll("(?i)\\\\x3c", "&lt;").replaceAll(
				"(?i)\\\\x3e", "&gt;");
		return value;
	}
}
