package com.openclassrooms.testing;

import static org.assertj.core.api.Assertions.assertThat;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class CalculatorTest {

	private Calculator calculatorUnderTest;
	private static Instant startedAt;

	@BeforeAll
	public static void initStartingtime() {
		System.out.println("Appel avant tous les tests");
		startedAt = Instant.now();
	}

	@AfterAll
	public static void showTestDuration() {
		System.out.println("Appel après tous les tests");
		Instant endedAt = Instant.now();
		long duration = Duration.between(startedAt, endedAt).toMillis();
		System.out.println(MessageFormat.format("Durée des tests : {0} ms", duration));
	}

	@BeforeEach
	public void initCalculator() {
		calculatorUnderTest = new Calculator();
		System.out.println("Appel avant chaque test");
	}

	@AfterEach
	public void undefCalculator() {
		System.out.println("Appel après chaque test");
		calculatorUnderTest = null;
	}

	@Test
	@Tag("operation")
	public void testAddTwoPositiveNumbers() {
		// Arrange
		int a = 2;
		int b = 3;

		// Act
		int somme = calculatorUnderTest.add(a, b);

		// Assert
		// assertEquals(5, somme);
		assertThat(somme).isEqualTo(5);
	}

	@Test
	@Tag("operation")
	public void testMultiplyTwoPositiveNumbers() {
		// Arrange
		int a = 42;
		int b = 11;

		// Act
		int produit = calculatorUnderTest.multiply(a, b);

		// Assert
		// assertEquals(462, produit);
		assertThat(produit).isEqualTo(462);
	}

	@ParameterizedTest(name = "{0} x 0 doit toujours être égal à 0")
	@ValueSource(ints = { 1, 2, 42, 1001, 5248 })
	public void multiplyByZero(int arg) {
		// Arrange - tout est prêt

		// Act - multiplier par zéro
		int actualResult = arg * 0;

		// Assert - vaut toujours zéro
		// assertEquals(0, actualResult);
		assertThat(actualResult).isEqualTo(0);
	}

	@ParameterizedTest(name = "{0} + {1} doit être égal à {2}")
	@CsvSource({ "1,1,2", "2,3,5", "42,57,99" })
	public void addWithCsv(int arg1, int arg2, int expectedResult) {
		// Arrange - tout est prêt

		// Act - multiplier par zéro
		int actualResult = arg1 + arg2;

		// Assert
		// assertEquals(expectedResult, actualResult);
		assertThat(actualResult).isEqualTo(expectedResult);
	}

	@Test
	@Timeout(1)
	public void longCalculation() {
		// Arrange - tout est prêt

		// Act - multiplier par zéro
		calculatorUnderTest.longCalculation();
		;

		// Assert
		// ... rien
	}

	@Test
	public void listDigits() {
		// Given
		int number = 98765;

		// When
		Set<Integer> actualDigits = calculatorUnderTest.digitsSets(number);

		// Then
		// Set<Integer> expectedDigits = Stream.of(9, 8, 7, 6, 5).collect(Collectors.toSet());
		// assertEquals(expectedDigits, actualDigits);
		assertThat(actualDigits).containsExactlyInAnyOrder(9, 8, 7, 6, 5);
	}

	@Test
	public void listDigitsNegative() {
		// Given
		int number = -98765;

		// When
		Set<Integer> actualDigits = calculatorUnderTest.digitsSets(number);

		// Then
		assertThat(actualDigits).containsExactlyInAnyOrder(9, 8, 7, 6, 5);
	}

	@Test
	public void listDigitsZero() {
		// Given
		int number = 0;

		// When
		Set<Integer> actualDigits = calculatorUnderTest.digitsSets(number);

		// Then
		assertThat(actualDigits).containsExactlyInAnyOrder(0);
	}
}

