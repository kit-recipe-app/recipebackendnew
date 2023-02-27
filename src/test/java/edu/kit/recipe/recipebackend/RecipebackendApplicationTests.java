package edu.kit.recipe.recipebackend;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
class RecipebackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void contextLoads() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isUnauthorized());
    }


    @Test
    void expectHelloWorld() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/").with(jwt()
                        .jwt(jwt -> jwt.claim("email", ""))))
                .andReturn();
        assertEquals("Hello !", mvcResult.getResponse().getContentAsString());
    }

}