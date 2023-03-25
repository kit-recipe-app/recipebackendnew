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

package org.springframework.boot.env;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.env.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;

/**
 * Adds value for mockwebserver.url property.
 *
 * @author Rob Winch
 */
public class MockWebServerPropertySource extends PropertySource<MockWebServer> implements DisposableBean {

	private static final MockResponse JWKS_RESPONSE = response(
			"{ \"keys\": [ { \"kty\": \"RSA\", \"e\": \"AQAB\", \"n\": \"AKzrn1fIxdHnDcCiCCxUdzGMcx+UHiq2zjTsWC6l/3f0zxKQb+rKV8VTuLWjcrV4+Isfn5SzEbuL9UHR/w+zqmu7kyzD2kDoPe5/NzE2Nlg2yd6KcsnF+56JnpC13O/SDIvVDfAq93hM3g1mokdy+7+o3X8rwnHW8q1B2DZPXI6EqYMW68WwrPdDArfr6v3rWQKPNrmxurubVnfCS4iv/dyeIjFlYvQ82kToXxziEKi4mP8a6/F23GJFzuqg7mFPPNpTHD0heovUqRIxER4QgKHKHLPlwGAtGELmEr5vSOzppy9UPjQXx7vQWpe6plNINRDCzHJIC/LSIVapKQGzYrc=\" } ] }",
			200);


	private static final MockResponse NOT_FOUND_RESPONSE = response(
			"{ \"message\" : \"This mock authorization server responds to just one request: GET /.well-known/jwks.json.\" }",
			404);

	/**
	 * Name of the random {@link PropertySource}.
	 */
	public static final String MOCK_WEB_SERVER_PROPERTY_SOURCE_NAME = "mockwebserver";

	private static final String NAME = "mockwebserver.url";

	private static final Log logger = LogFactory.getLog(MockWebServerPropertySource.class);

	private boolean started;

	public MockWebServerPropertySource() {
		super(MOCK_WEB_SERVER_PROPERTY_SOURCE_NAME, new MockWebServer());
	}

	@Override
	public Object getProperty(String name) {
		if (!name.equals(NAME)) {
			return null;
		}
		if (logger.isTraceEnabled()) {
			logger.trace("Looking up the url for '" + name + "'");
		}
		return getUrl();
	}

	@Override
	public void destroy() throws Exception {
		getSource().shutdown();
	}

	/**
	 * Gets the URL
	 * @return the url with the dynamic port
	 */
	private String getUrl() {
		MockWebServer mockWebServer = getSource();
		if (!this.started) {
			initializeMockWebServer(mockWebServer);
		}
		String url = mockWebServer.url("").url().toExternalForm();
		return url.substring(0, url.length() - 1);
	}

	private void initializeMockWebServer(MockWebServer mockWebServer) {
		Dispatcher dispatcher = new Dispatcher() {
			@NotNull
			@Override
			public MockResponse dispatch(RecordedRequest request) {
				if ("/.well-known/jwks.json".equals(request.getPath())) {
					return JWKS_RESPONSE;
				}
				return NOT_FOUND_RESPONSE;
			}
		};

		mockWebServer.setDispatcher(dispatcher);
		try {
			mockWebServer.start();
			this.started = true;
		}
		catch (IOException ex) {
			throw new RuntimeException("Could not start " + mockWebServer, ex);
		}
	}

	private static MockResponse response(String body, int status) {
		return new MockResponse().setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.setResponseCode(status).setBody(body);
	}

}
