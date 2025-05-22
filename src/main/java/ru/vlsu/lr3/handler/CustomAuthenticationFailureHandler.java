package ru.vlsu.lr3.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import ru.vlsu.lr3.model.User;
import ru.vlsu.lr3.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserService userService;


    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        String username = request.getParameter("username");
        User user = userService.getUserByUsername(username);

        if (user != null) {
            // Увеличиваем счетчик неудачных попыток
            int attempts = user.getFailedAttempts() != null ? user.getFailedAttempts() : 0;
            user.setFailedAttempts(attempts + 1);
            userService.updateUser(user);

            // Блокировка после 3 попыток
            if (attempts >= 2) {
                user.setAccountNonLocked(false);
                userService.updateUser(user);
                response.sendRedirect("/login?error=blocked");
                return;
            }
        }

        response.sendRedirect("/login?error=true");
    }
}
