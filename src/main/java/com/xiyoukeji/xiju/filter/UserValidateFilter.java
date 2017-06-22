package com.xiyoukeji.xiju.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.Utils;

public class UserValidateFilter implements Filter {

	Logger logger =Logger.getLogger(UserValidateFilter.class);

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		doFilter((HttpServletRequest) request, (HttpServletResponse) response,
				filterChain);

	}

	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		JSONObject jv = new JSONObject();
		logger.info("jsessionId="+request.getSession().getId());
		Integer userId = (Integer) request.getSession().getAttribute(
				Const.LOGIN_USER_ID);
		if (userId == null) {
			jv.put("code", Const.NEED_LOGIN);
			Utils.writeBack(request, response, jv);
		} else {
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
