package chnu.edu.websecurity26.controller;

/*
  @author   oleksii
  @project  web-security26
  @class    ItemControllerTest
  @version  1.0.0 
  @since    15.09.2026
*/

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String ADMIN_AUTH = "Basic YWRtaW46YWRtaW4=";
    private static final String USER_AUTH = "Basic dXNlcjp1c2Vy";
    private static final String SUPERADMIN_AUTH = "Basic c3VwZXJhZG1pbjpzdXBlcmFkbWlu";

    @Test
    void shouldGetAllItemsAsUserAndAdmin() throws Exception {
        mockMvc.perform(get("/api/v1/items")
                        .header("Authorization", USER_AUTH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());

        mockMvc.perform(get("/api/v1/items")
                        .header("Authorization", ADMIN_AUTH))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetItemByIdAsUser() throws Exception {
        mockMvc.perform(get("/api/v1/items/1")
                        .header("Authorization", USER_AUTH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("name1"));
    }

    @Test
    void shouldCreateNewItemAsAdmin() throws Exception {
        String newItemJson = """
                {
                    "id": "100",
                    "name": "name100",
                    "description": "description100"
                }
                """;

        mockMvc.perform(post("/api/v1/items")
                        .header("Authorization", ADMIN_AUTH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newItemJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("100"))
                .andExpect(jsonPath("$.name").value("name100"));
    }

    @Test
    void shouldDenyCreateItemForUser() throws Exception {
        String newItemJson = """
                {
                    "id": "200",
                    "name": "name200",
                    "description": "description200"
                }
                """;

        mockMvc.perform(post("/api/v1/items")
                        .header("Authorization", USER_AUTH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newItemJson))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldUpdateItemAsAdmin() throws Exception {
        String updateJson = """
                {
                    "id": "2",
                    "name": "name2-updated",
                    "description": "description2-updated"
                }
                """;

        mockMvc.perform(put("/api/v1/items")
                        .header("Authorization", ADMIN_AUTH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.name").value("name2-updated"));
    }

    @Test
    void shouldDenyDeleteItemForUser() throws Exception {
        mockMvc.perform(delete("/api/v1/items/1")
                        .header("Authorization", USER_AUTH))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldDeleteItemAsAdmin() throws Exception {
        mockMvc.perform(delete("/api/v1/items/3")
                        .header("Authorization", ADMIN_AUTH))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/items/3")
                        .header("Authorization", ADMIN_AUTH))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void shouldVerifyAdminEndpointAccess() throws Exception {
        mockMvc.perform(get("/api/admin/hello")
                        .header("Authorization", ADMIN_AUTH))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, admin!"));

        mockMvc.perform(get("/api/admin/hello")
                        .header("Authorization", USER_AUTH))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldVerifySuperAdminEndpointAccess() throws Exception {
        mockMvc.perform(get("/api/superadmin/hello")
                        .header("Authorization", SUPERADMIN_AUTH))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, superadmin!"));

        mockMvc.perform(get("/api/superadmin/hello")
                        .header("Authorization", ADMIN_AUTH))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/superadmin/hello")
                        .header("Authorization", USER_AUTH))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnUnauthorizedWhenNoAuth() throws Exception {
        mockMvc.perform(get("/api/v1/items"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/v1/items/1"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/admin/hello"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/superadmin/hello"))
                .andExpect(status().isUnauthorized());
    }
}
