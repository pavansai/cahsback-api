package com.store.cashback;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CashbackApiApplicationTests {

	@Test
	@Disabled("Skipping test until database setup is fixed")
	void contextLoads() {
	}

}
