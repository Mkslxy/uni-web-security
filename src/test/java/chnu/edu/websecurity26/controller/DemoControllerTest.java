package chnu.edu.websecurity26.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void publicHelloShouldBeAccessibleWithoutAuth() throws Exception {
        mockMvc.perform(get("/api/public/hello"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("success"))
            .andExpect(jsonPath("$.framework").value("Spring Boot 4.1"));
    }

    @Test
    void indexHtmlShouldBeAccessibleWithoutAuth() throws Exception {
        mockMvc.perform(get("/index.html"))
            .andExpect(status().isOk());
    }

    @Test
    void secureEndpointShouldReturnUnauthorizedWithoutCredentials() throws Exception {
        mockMvc.perform(get("/api/secure/user"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void secureEndpointShouldBeAccessibleWithUserRole() throws Exception {
        mockMvc.perform(get("/api/secure/user").with(httpBasic("user", "user123")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("user"));
    }

    @Test
    void adminEndpointShouldReturnForbiddenForOrdinaryUser() throws Exception {
        mockMvc.perform(get("/api/admin/dashboard").with(httpBasic("user", "user123")))
            .andExpect(status().isForbidden());
    }

    @Test
    void adminEndpointShouldBeAccessibleForAdminUser() throws Exception {
        mockMvc.perform(get("/api/admin/dashboard").with(httpBasic("admin", "admin123")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.adminUser").value("admin"));
    }
}
