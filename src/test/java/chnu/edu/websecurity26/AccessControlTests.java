package chnu.edu.websecurity26;

/*
  @author   oleksii
  @project  web-security26
  @class    AccessControlTests
  @version  1.0.0 
  @since    13.10.2026
*/

import chnu.edu.websecurity26.config.SecurityConfig;
import chnu.edu.websecurity26.controller.AdminController;
import chnu.edu.websecurity26.controller.ItemController;
import chnu.edu.websecurity26.controller.SuperAdminController;
import chnu.edu.websecurity26.jwt.JwtAuthenticationFilter;
import chnu.edu.websecurity26.jwt.JwtService;
import chnu.edu.websecurity26.model.Item;
import chnu.edu.websecurity26.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Access tests per Lecture 8: role-based access control for the
 * admin / user / superadmin accounts declared in {@link SecurityConfig}.
 */
@WebMvcTest(controllers = {ItemController.class, AdminController.class, SuperAdminController.class})
@Import({SecurityConfig.class, JwtAuthenticationFilter.class, JwtService.class})
class AccessControlTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    private static final String ITEM_JSON = """
            {"id": "10", "name": "name10", "description": "description10"}
            """;

    @Test
    void user_canListItems() throws Exception {
        when(itemService.getAllItems()).thenReturn(List.of(new Item("1", "name1", "description1")));

        mockMvc.perform(get("/api/v1/items").with(httpBasic("user", "user")))
                .andExpect(status().isOk());
    }

    @Test
    void superadmin_cannotListItems() throws Exception {
        mockMvc.perform(get("/api/v1/items").with(httpBasic("superadmin", "superadmin")))
                .andExpect(status().isForbidden());
    }

    @Test
    void admin_canReadOneItem() throws Exception {
        when(itemService.getItem("1")).thenReturn(new Item("1", "name1", "description1"));

        mockMvc.perform(get("/api/v1/items/1").with(httpBasic("admin", "admin")))
                .andExpect(status().isOk());
    }

    @Test
    void admin_canCreateItem() throws Exception {
        when(itemService.createItem(any())).thenReturn(new Item("10", "name10", "description10"));

        mockMvc.perform(post("/api/v1/items")
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ITEM_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void user_cannotCreateItem() throws Exception {
        mockMvc.perform(post("/api/v1/items")
                        .with(httpBasic("user", "user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ITEM_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void admin_canUpdateItem() throws Exception {
        when(itemService.updateItem(any())).thenReturn(new Item("10", "name10-updated", "description10-updated"));

        mockMvc.perform(put("/api/v1/items")
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ITEM_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void user_cannotUpdateItem() throws Exception {
        mockMvc.perform(put("/api/v1/items")
                        .with(httpBasic("user", "user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ITEM_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void admin_canDeleteItem() throws Exception {
        mockMvc.perform(delete("/api/v1/items/1")
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isOk());
    }

    @Test
    void user_cannotAccessAdminRoute() throws Exception {
        mockMvc.perform(get("/api/admin/hello").with(httpBasic("user", "user")))
                .andExpect(status().isForbidden());
    }

    @Test
    void superadmin_canAccessSuperadminRoute() throws Exception {
        mockMvc.perform(get("/api/superadmin/hello").with(httpBasic("superadmin", "superadmin")))
                .andExpect(status().isOk());
    }
}
