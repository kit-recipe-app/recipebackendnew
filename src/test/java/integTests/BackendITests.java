/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package integTests;

import edu.kit.recipe.recipebackend.RecipebackendApplication;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = RecipebackendApplication.class)
@AutoConfigureMockMvc
@Transactional
class BackendITests {

	String bennyOauth2Token =
			"eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20va2l0LXJlY2lwZS1hcHAiLCJhdWQiOiJraXQtcmVjaXBlLWFwcCIsImF1dGhfdGltZSI6MTY3OTc0MDY3NiwidXNlcl9pZCI6IkFaQnFtcXcza0FoUW1EMEZKRmNtTE5BNGFVaTEiLCJzdWIiOiJBWkJxbXF3M2tBaFFtRDBGSkZjbUxOQTRhVWkxIiwiaWF0IjoxNjc5NzQwNjc2LCJleHAiOjI2Nzk3NDQyNzYsImVtYWlsIjoiZ3JlZW5kcjRnb25AcHJvdG9ubWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJncmVlbmRyNGdvbkBwcm90b25tYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.BB30zwWyj-eGdmZYweW_a7fevFZhSiEWlqR_b4njAK0NIkzKaFJ4QbnlXMb_3VkyjC2ccgkuwhIPWnOixjz62-CpWoe7R6IjgF1JDAWQoDPYsIbDSSmOcf9KGQb59w4nkh9INjooD_OJu7DSQodfJBzWCKXFM3s5pUtAQYjS_3z8lOBe0GB3XFQu2_95Gv3QbEMh-bbrIUbwkFskyGppNiu9l0Da-TaqNSe4UOAU30Mlzt1lVd6SBqN2V5nShZWUmnuXh1HSb34VsT9ksby170_4uHRhBwGHb2hC8xNfgzsPIxJkcS_LE-V9_AvSlKlB9arKfk1ttZWrpqFnDNzGPg";

	@Autowired
	MockMvc mvc;


	void setUpAll() throws Exception {
		setUpUnits();
		setUpTags();
		setUpIngredients();
		setUpRecipes();
	}

	void setUpUnits() throws Exception {
		this.mvc.perform(post("/api/v1/units").with(bearerToken(this.bennyOauth2Token))
						.contentType("application/json")
						.content("{\"name\":\"g\"}"))
				.andExpect(status().isOk());
	}

	void setUpTags() throws Exception {
		this.mvc.perform(post("/api/v1/tags").with(bearerToken(this.bennyOauth2Token))
						.contentType("application/json")
						.content("{\"name\":\"Omnivor\"}"))
				.andExpect(status().isOk());
		this.mvc.perform(post("/api/v1/tags").with(bearerToken(this.bennyOauth2Token))
						.contentType("application/json")
						.content("{\"name\":\"Vegetarisch\"}"))
				.andExpect(status().isOk());
		this.mvc.perform(post("/api/v1/tags").with(bearerToken(this.bennyOauth2Token))
						.contentType("application/json")
						.content("{\"name\":\"Vegan\"}"))
				.andExpect(status().isOk());
	}

	void setUpIngredients() throws Exception {
		this.mvc.perform(post("/api/v1/ingredients").with(bearerToken(this.bennyOauth2Token))
						.contentType("application/json")
						.content("{\"name\":\"Hackfleisch\",\"tag\":{\"name\":\"Omnivor\"}}"))
				.andExpect(status().isOk());

		this.mvc.perform(post("/api/v1/ingredients").with(bearerToken(this.bennyOauth2Token))
						.contentType("application/json")
						.content("{\"name\":\"Zwiebel(n)\",\"tag\":{\"name\":\"Vegan\"}}"))
				.andExpect(status().isOk());
	}

	void setUpRecipes() throws Exception {
		this.mvc.perform(post("/api/v1/recipes").with(bearerToken(this.bennyOauth2Token))
						.contentType("application/json")
						.content("""
                                {
                                    "name": "RezeptName",
                                    "description": "RezeptBeschreibung",
                                    "difficulty": "mittel",
                                    "durationInMin": 10,
                                    "calories": 100,
                                    "isPublic": true,
                                    "cookingInstructions": [
                                        {
                                            "instruction": "hihihi"
                                        },
                                        {
                                            "instruction": "Das ist ein zweiter step"
                                        }
                                    ],
                                    "ingredients": [
                                        {
                                            "ingredient": {
                                                "name": "Hackfleisch"
                                            },
                                            "amount": {
                                                "amount": 2.0,
                                                "unit": "g"
                                            }
                                        },
                                        {
                                            "ingredient": {
                                                "name": "Zwiebel(n)"
                                            },
                                            "amount": {
                                                "amount": 3.0,
                                                "unit": "g"
                                            }
                                        }
                                    ]
                                }

                                """))
				.andExpect(status().isOk());

	}


	@Test
	void performWhenValidBearerTokenThenAllows() throws Exception {
		this.mvc.perform(get("/").with(bearerToken(this.bennyOauth2Token)))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello greendr4gon@protonmail.com!")));
	}


	@Test
	void testUserRecipes() throws Exception {
		setUpAll();
		MvcResult mvcResult = this.mvc.perform(get("/api/v1/user/recipes").with(bearerToken(this.bennyOauth2Token)))
				.andExpect(status().isOk())
				.andReturn();
		JSONArray jsonObject = new JSONArray(mvcResult.getResponse().getContentAsString());
		assert jsonObject.length() == 1;
		assert jsonObject.getJSONObject(0).getString("name").equals("RezeptName");

		MvcResult mvcResult1 = this.mvc.perform(get("/api/v1/recipes").with(bearerToken(this.bennyOauth2Token)))
				.andExpect(status().isOk())
				.andReturn();
		JSONArray jsonObject1 = new JSONArray(mvcResult1.getResponse().getContentAsString());
		assert jsonObject1.length() == 1;
		assert jsonObject1.getJSONObject(0).getString("name").equals("RezeptName");
		assert jsonObject1.getJSONObject(0).getString("description").equals("RezeptBeschreibung");
		assert jsonObject1.getJSONObject(0).getString("difficulty").equals("mittel");
		assert jsonObject1.getJSONObject(0).getInt("durationInMin") == 10;
		assert jsonObject1.getJSONObject(0).getInt("calories") == 100;
		assert jsonObject1.getJSONObject(0).getJSONArray("cookingInstructions").length() == 2;
	}


	@Test
	void getUserInfo() throws Exception {

		MvcResult mvcResult = this.mvc.perform(get("/api/v1/user").with(bearerToken(this.bennyOauth2Token)))
				.andExpect(status().isOk())
				.andReturn();
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "{\"name\":\"\",\"email\":\"greendr4gon@protonmail.com\"}", false);

		this.mvc.perform(put("/api/v1/user/name").with(bearerToken(this.bennyOauth2Token))
						.contentType("application/json")
						.content("{\"name\":\"Benny\"}"))
				.andExpect(status().isOk());

		MvcResult mvcResult1 = this.mvc.perform(get("/api/v1/user").with(bearerToken(this.bennyOauth2Token)))
				.andExpect(status().isOk())
				.andReturn();
		String content1 = mvcResult1.getResponse().getContentAsString();
		assertEquals(content1, "{\"name\":\"Benny\",\"email\":\"greendr4gon@protonmail.com\"}", false);
	}

	@Test
	void addInvalidTags() throws Exception {
		this.mvc.perform(post("/api/v1/tags").with(bearerToken(this.bennyOauth2Token))
						.contentType("application/json")
						.content("{\"name\":\"v1\"}"))
				.andExpect(status().isBadRequest());
	}







	private static BearerTokenRequestPostProcessor bearerToken(String token) {
		return new BearerTokenRequestPostProcessor(token);
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
