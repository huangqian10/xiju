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

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.AdminUser;
import com.xiyoukeji.xiju.service.PowerInfoService;

public class AdminFilter implements Filter {

	private PowerInfoService powerInfoService;

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
		AdminUser user = (AdminUser) request.getSession().getAttribute(
				Const.LOGIN_ADMIN);
		if (user == null) {
			jv.put("code", Const.NEED_LOGIN);
		} else {
			System.out.println("===========" + request.getRequestURI());
			System.out.println("==========" + user.getUserName());
			System.err.println(powerInfoService.isAuthUrl(request.getRequestURI(),
					user.getPowerId()));
			if (user.getStatus().equals(AdminUser.USEFUL_STATUS)
					&& powerInfoService.isAuthUrl(request.getRequestURI(),
							user.getPowerId())) {
				filterChain.doFilter(request, response);
			} else {
				jv.put("code", Const.POWER_LIMIT);
			}
		}
		Utils.writeBack(request, response, jv);
	}

	@Override
	public void init(FilterConfig cfg) throws ServletException {
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(cfg.getServletContext());
		powerInfoService = ctx.getBean(PowerInfoService.class);
	}

}
