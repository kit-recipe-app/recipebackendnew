package edu.kit.recipe.recipebackend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@ExtendWith(SpringExtension.class)
@WebMvcTest(BaseController.class)
class BaseControllerTest {
    private static final String TEST_EMAIL = "test@test.com";

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser
    void checkTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/test")).andReturn();
        assertEquals("Hello World!", result.getResponse().getContentAsString());
    }

    @Test
    void checkAlive() throws Exception {
        MvcResult result = mockMvc.perform(get("/")
                        .with(jwt().jwt(jwt -> jwt.claim("email", TEST_EMAIL))))
                .andReturn();
        assertEquals("Hello " + TEST_EMAIL + "!", result.getResponse().getContentAsString());
    }
}