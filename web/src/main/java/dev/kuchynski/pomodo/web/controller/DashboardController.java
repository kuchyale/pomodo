package dev.kuchynski.pomodo.web.controller;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for dashboard page.
 *
 * @author <a href="mailto:o.kuchynski@gmail.com">Aleh Kuchynski</a>
 * @since 1.0
 */
@AllArgsConstructor
@Controller
public class DashboardController {

    @NonNull
    private final OAuth2AuthorizedClientService authorizedClientService;

    /**
     * Return dashboard page.
     *
     * @param authToken {@link OAuth2AuthenticationToken}
     * @return dashboard page with model data
     */
    @RequestMapping("/dashboard")
    public String authorize(OAuth2AuthenticationToken authToken) {
        Assert.notNull(authToken, "authToken must not be null");

        return "dashboard";
    }
}
