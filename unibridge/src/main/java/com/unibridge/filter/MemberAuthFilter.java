package com.unibridge.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.app.member.dao.MemberDAO;
import com.unibridge.app.member.dto.MemberDTO;

public class MemberAuthFilter extends HttpFilter implements Filter {
	MemberDAO membeDAO = new MemberDAO();
	
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public MemberAuthFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
//			HttpServletRequest  httpRequest  = (HttpServletRequest ) request;
//			HttpServletResponse httpResponse = (HttpServletResponse) response;
//			
//			String userId = extractUserId(request, response);
//			MemberDTO member = membeDAO.internalSelectMemberForFilterByID(userId);
//			if (member == null) {
//				httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유저 정보를 찾을 수 없습니다.");
//				return;
//			}
//			
//			String memberRole = member.getMemberType().toUpperCase();
//			String memberStatus = member.getMemberState();
//			if (!"계정 활성화".equals(memberStatus)) {
//				httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "계정 정보가 유효하지 않습니다.");
//				return;
//			}
//			
//			String requiredMemberRole = this.extractUserRole(httpRequest.getRequestURI()).toUpperCase();
//			if (memberRole != requiredMemberRole) {
//				httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "해당 페이지에 접근할 권한이 없습니다.");
//				return;
//			}
		} catch (Exception e) {} 
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	private String extractUserId(ServletRequest request, ServletResponse response) {
		String userId = request.getParameter("userId");
		if (userId == null) {
			userId = request.getParameter("memberId");
		}
		
		return userId;
	}
	
	private String extractUserRole(String requestUri) {
		if (requestUri.contains("/mentor")) {
			return "mentor";
		}
		
		if (requestUri.contains("/mentee")) {
			return "mentee";
		}
		
		return "";
	}
}
