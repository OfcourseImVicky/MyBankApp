package training.task.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class TrainingTest {
	Calculator calculator;

	@BeforeAll
	public void setUpBeforeAllOnce() {
		calculator = new Calculator();
	}

//	@BeforeEach
//	public void setUpBeforeEach() {
//		calculator = new Calculator();
//	}

	@Test
	@Order(1)
	@DisplayName("Add Method")
	public void testSum() {
		assertEquals(6, calculator.sum(3, 3));
	}

	@Test
	@Order(2)
	@DisplayName("Sub Method")
	public void testSub() {
		assertEquals(0, calculator.sub(3, 3));
	}

	@Test
	@Order(3)
	@DisplayName("Multiply Method")
	public void testMul() {
		assertEquals(9, calculator.mul(3, 3));
	}

	@Test
	@Order(4)
	@DisplayName("Division Method")
	public void testDiv() {
		assertEquals(1, calculator.div(3, 3));
	}

	@Test
	@Order(5)
	@EnabledOnOs(OS.MAC)
	@DisplayName("Exception Method")
	public void exceptionMethod() {
		assertThrows(ArithmeticException.class, () -> calculator.div(2, 0));
	}

	@ParameterizedTest(name = "Parameterized Test Method")
	@ValueSource(strings = { "Vicky", "Ram", "Akhil" })
	public void parameterisedMethod(String input) {
		System.out.println("Parameter : " + input);
	}

	@Nested
	class NestedClass {
		@Test
		public void emptyMethod() {
		}
	}
}
