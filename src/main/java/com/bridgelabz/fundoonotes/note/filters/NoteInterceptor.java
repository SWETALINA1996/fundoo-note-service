/*package com.bridgelabz.fundoonotes.note.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bridgelabz.fundoonotes.note.utility.JWTtokenProvider;



public class NoteInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(NoteInterceptor.class);

	 @Override
	 public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
             Object handler) throws Exception {
		 
		JWTtokenProvider tokenProvider = new JWTtokenProvider();
		 String tokenId= request.getHeader("token");
		 String userId=tokenProvider.parseJWT(tokenId);
		 request.setAttribute("token", userId);
		 System.out.println("UserId:"+userId);
		 logger.info("Before handling the request"+request.getRequestURI());
		 
		return true;

}
	
}
*/