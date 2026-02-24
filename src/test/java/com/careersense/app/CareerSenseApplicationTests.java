package com.careersense.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CareerSenseApplicationTests {

	@Test
	void contextLoads() {
        System.out.println("Test");
	}

}
