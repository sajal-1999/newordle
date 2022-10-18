package com.newordle.newordle;

import com.newordle.newordle.controllers.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NewordleApplicationTests {
	@Test
	void contextLoads() {
		Controller controller = new Controller();
		assertEquals("GreenGreenGreenGreenGreen", controller.newordle("madam"));
		assertEquals("GreenGreenYellowGreyYellow", controller.newordle("mamma"));
	}
}