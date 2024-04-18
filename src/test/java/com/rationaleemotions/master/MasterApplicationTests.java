package com.rationaleemotions.master;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootTest
class MasterApplicationTests {

	@Test
	void contextLoads() {
	}

	@TestConfiguration
	public static class TestConfig {
		@MockBean
		public RestClient restClient;

	}

}
