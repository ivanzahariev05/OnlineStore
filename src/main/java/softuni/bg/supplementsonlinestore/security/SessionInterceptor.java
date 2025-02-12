package softuni.bg.supplementsonlinestore.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import softuni.bg.supplementsonlinestore.user.model.User;
import softuni.bg.supplementsonlinestore.user.service.UserService;

import java.util.Set;
import java.util.UUID;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    private final Set<String> UNAUTHENTICATED_ENDPOINTS = Set.of("/", "/register", "/login");

    private final UserService userService;

    @Autowired
    public SessionInterceptor(UserService userService) {
        this.userService = userService;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String servletPath = request.getServletPath();

        if (UNAUTHENTICATED_ENDPOINTS.contains(servletPath)) {
            return true;
        }
        HttpSession currentSession = request.getSession(false);

        if (currentSession == null) {
            response.sendRedirect("/");
            return false;
        }

        UUID userId = (UUID) currentSession.getAttribute("user_id");
        User user = userService.getUserById(userId);

        if (!user.isActive()){
            currentSession.invalidate();
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
