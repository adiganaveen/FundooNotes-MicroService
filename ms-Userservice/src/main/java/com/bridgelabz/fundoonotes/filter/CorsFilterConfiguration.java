package com.bridgelabz.fundoonotes.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsFilterConfiguration implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		servletResponse.setHeader("Access-Control-Allow-Origin", "*");
		servletResponse.setHeader("Allow-Control-Allow-Credentials", "true");
		servletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
		servletResponse.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, token");
		servletResponse.setHeader("Access-Control-Allow-Max-Age", "3600");
		if (servletRequest.getMethod().equalsIgnoreCase("options")) {
			chain.doFilter(servletRequest, servletResponse);
		}
	}

	@Override
	public void destroy() {
	}

}
