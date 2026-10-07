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

    private static final String AUTH_HEADER = "Basic YWRtaW46YWRtaW4=";

    @Test
    void shouldGetAllItems() throws Exception {
        mockMvc.perform(get("/api/v1/items")
                        .header("Authorization", AUTH_HEADER))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldGetItemById() throws Exception {
        mockMvc.perform(get("/api/v1/items/1")
                        .header("Authorization", AUTH_HEADER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("name1"));
    }

    @Test
    void shouldCreateNewItem() throws Exception {
        String newItemJson = """
                {
                    "id": "100",
                    "name": "name100",
                    "description": "description100"
                }
                """;

        mockMvc.perform(post("/api/v1/items")
                        .header("Authorization", AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newItemJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("100"))
                .andExpect(jsonPath("$.name").value("name100"));
    }

    @Test
    void shouldUpdateItem() throws Exception {
        String updateJson = """
                {
                    "id": "2",
                    "name": "name2-updated",
                    "description": "description2-updated"
                }
                """;

        mockMvc.perform(put("/api/v1/items")
                        .header("Authorization", AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.name").value("name2-updated"));
    }

    @Test
    void shouldDeleteItem() throws Exception {
        mockMvc.perform(delete("/api/v1/items/3")
                        .header("Authorization", AUTH_HEADER))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/items/3")
                        .header("Authorization", AUTH_HEADER))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void shouldReturnUnauthorizedWhenNoAuth() throws Exception {
        mockMvc.perform(get("/api/v1/items"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/v1/items/1"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/api/v1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"999\",\"name\":\"unauthorized\",\"description\":\"test\"}"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(put("/api/v1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"name\":\"unauthorized\",\"description\":\"test\"}"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(delete("/api/v1/items/1"))
                .andExpect(status().isUnauthorized());
    }
}
