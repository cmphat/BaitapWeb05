package vn.iotstar;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import vn.iotstar.interceptor.AdminSecurityInterceptor;
import vn.iotstar.model.User;

import static org.junit.jupiter.api.Assertions.*;

public class AdminSecurityInterceptorTest {

    private AdminSecurityInterceptor interceptor;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;

    @BeforeEach
    public void setup() {
        interceptor = new AdminSecurityInterceptor();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
    }

    @Test
    public void testUnauthenticatedAccessRedirectsToLogin() throws Exception {
        request.setSession(session);
        boolean result = interceptor.preHandle(request, response, new Object());

        assertFalse(result);
        assertEquals("/login?error=unauthorized", response.getRedirectedUrl());
    }

    @Test
    public void testRegularUserForbidden() throws Exception {
        User regularUser = new User();
        regularUser.setId(10);
        regularUser.setUsername("user1");
        regularUser.setRoleid(3); // Regular user
        session.setAttribute("account", regularUser);
        request.setSession(session);

        boolean result = interceptor.preHandle(request, response, new Object());

        assertFalse(result);
        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());
    }

    @Test
    public void testAdminUserAllowed() throws Exception {
        User adminUser = new User();
        adminUser.setId(1);
        adminUser.setUsername("admin");
        adminUser.setRoleid(1); // Admin
        session.setAttribute("account", adminUser);
        request.setSession(session);

        boolean result = interceptor.preHandle(request, response, new Object());

        assertTrue(result);
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
    }
}
