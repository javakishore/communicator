package com.company.crm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.company.crm.model.User;

public class AuthenticationFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		String uri = req.getRequestURI();
		System.out.println("AuthenticationFilter >> URI:[" + uri + "] Session:[" + session + "]");

		User user = null;

		if (session != null) {
			user = (User) session.getAttribute("user");
		}
		if (uri.indexOf("/resources/") > 0) {
			
			chain.doFilter(request, response);
			
		} else if (uri.indexOf("/images/") > 0) {
			
			chain.doFilter(request, response);
			
		} else if (uri.indexOf("/js/") > 0) {
			
			chain.doFilter(request, response);
			
		} else if (user == null && !uri.contains("login") && !uri.equals("/crmCmsAdmin/")) {
			
			req.setAttribute("invalidUser", "Session expired or Unauthorized access. Please login to the application");
			req.getRequestDispatcher("/").forward(req, resp);
		}
		if (!resp.isCommitted()) {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig config) throws ServletException {
	}

}
