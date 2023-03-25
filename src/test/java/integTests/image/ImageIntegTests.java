package integTests.image;

import edu.kit.recipe.recipebackend.RecipebackendApplication;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = RecipebackendApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ImageIntegTests {

    String bennyOauth2Token =
			"eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20va2l0LXJlY2lwZS1hcHAiLCJhdWQiOiJraXQtcmVjaXBlLWFwcCIsImF1dGhfdGltZSI6MTY3OTc0MDY3NiwidXNlcl9pZCI6IkFaQnFtcXcza0FoUW1EMEZKRmNtTE5BNGFVaTEiLCJzdWIiOiJBWkJxbXF3M2tBaFFtRDBGSkZjbUxOQTRhVWkxIiwiaWF0IjoxNjc5NzQwNjc2LCJleHAiOjI2Nzk3NDQyNzYsImVtYWlsIjoiZ3JlZW5kcjRnb25AcHJvdG9ubWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJncmVlbmRyNGdvbkBwcm90b25tYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.BB30zwWyj-eGdmZYweW_a7fevFZhSiEWlqR_b4njAK0NIkzKaFJ4QbnlXMb_3VkyjC2ccgkuwhIPWnOixjz62-CpWoe7R6IjgF1JDAWQoDPYsIbDSSmOcf9KGQb59w4nkh9INjooD_OJu7DSQodfJBzWCKXFM3s5pUtAQYjS_3z8lOBe0GB3XFQu2_95Gv3QbEMh-bbrIUbwkFskyGppNiu9l0Da-TaqNSe4UOAU30Mlzt1lVd6SBqN2V5nShZWUmnuXh1HSb34VsT9ksby170_4uHRhBwGHb2hC8xNfgzsPIxJkcS_LE-V9_AvSlKlB9arKfk1ttZWrpqFnDNzGPg";

	@Autowired
    MockMvc mvc;


    @Test
    void getAllTestTag() throws Exception {
		MvcResult mvcResult = mvc.perform(get("/api/v1/images").with(bearerToken(bennyOauth2Token)))
				.andExpect(status().isOk())
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		assert content.equals("[]");
	}

     private static ImageIntegTests.BearerTokenRequestPostProcessor bearerToken(String token) {
		return new ImageIntegTests.BearerTokenRequestPostProcessor(token);
	}

	private record BearerTokenRequestPostProcessor(String token) implements RequestPostProcessor {

		@NotNull
		@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				request.addHeader("Authorization", "Bearer " + this.token);
				return request;
			}
		}
}
