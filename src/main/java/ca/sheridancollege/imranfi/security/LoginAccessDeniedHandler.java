/* * This method handles the access denied exception thrown by Spring Security.
 * It logs the name of the user and the resource they were trying to access,
 * then redirects the user to the "/access" page. */

package ca.sheridancollege.imranfi.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
		System.out.println(auth.getName()
		+ " was trying to access protected resource: "
		+ request.getRequestURI());
		}
		
		response.sendRedirect(request.getContextPath() + "/access");
		
	}

}
