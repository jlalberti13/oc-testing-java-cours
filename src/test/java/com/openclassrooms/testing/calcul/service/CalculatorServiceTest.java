package com.openclassrooms.testing.calcul.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.testing.calcul.domain.Calculator;
import com.openclassrooms.testing.calcul.domain.model.CalculationModel;
import com.openclassrooms.testing.calcul.domain.model.CalculationType;

@ExtendWith(MockitoExtension.class	)
public class CalculatorServiceTest {

	/* calculator remplacé par un mock
	Calculator calculator = new Calculator();
	// Calculator IS CALLED BY CalculatorService
	CalculatorService classUnderTest = new CalculatorServiceImpl(calculator);
	 

	@Test
	public void add_returnsTheSum_ofTwoPositiveNumbers() {
		final int result = classUnderTest.calculate(
				new CalculationModel(CalculationType.ADDITION, 1, 2)).getSolution();
		assertThat(result).isEqualTo(3);
	}

	@Test
	public void add_returnsTheSum_ofTwoNegativeNumbers() {
		final int result = classUnderTest.calculate(
				new CalculationModel(CalculationType.ADDITION, -1, 2))
				.getSolution();

		assertThat(result).isEqualTo(1);
	}

	@Test
	public void add_returnsTheSum_ofZeroAndZero() {
		final int result = classUnderTest.calculate(
				new CalculationModel(CalculationType.ADDITION, 0, 0)).getSolution();
		assertThat(result).isEqualTo(0);
	}
 	 remplacé par */

	@Mock
	Calculator calculator;

	@Mock	
	SolutionFormatter solutionFormatter;

	CalculatorService classUnderTest;
	@BeforeEach
	public void init() {
		classUnderTest = new CalculatorServiceImpl(calculator, solutionFormatter);
	}

	@Test
	public void calculate_shouldUseCalculator_forAddition() {
		// given
		// when(calculator.add(1, 2)).thenReturn(3);
		when(calculator.add(anyInt(), anyInt())).thenReturn(3);

		// when
		final int result = classUnderTest.calculate(
			new CalculationModel(CalculationType.ADDITION, 1, 2)).getSolution();

		// then
		// verify(calculator).add(1, 2);	// vérifie que la méthode add a été appelée
		verify(calculator).add(anyInt(), anyInt());
		// vérifie que la méthode a été appelée une seule fois
		// verify(calculator, times(1)).add(any(Integer.class), any(Integer.class));
		// vérifie que la méthode n'a pas été appelée
		// verify(calculator, never()).sub(any(Integer.class), any(Integer.class));
		assertThat(result).isEqualTo(3);
	}

	@Test
	public void calculate_shouldThrowIllegalArgumentException_forDivisionBy0() {
		// given
		when(calculator.divide(1, 0)).thenThrow(new ArithmeticException());

		// when
		assertThrows(IllegalArgumentException.class, () -> classUnderTest.calculate(
			new CalculationModel(CalculationType.DIVISION, 1, 0)
		));

		// then
		verify(calculator, times(1)).divide(1, 0);
	}

	@Test
	public void calculate_shouldFormatSolution_forAnAddition() {
		// given
		when(calculator.add(10000, 3000)).thenReturn(13000);
		when(solutionFormatter.format(13000)).thenReturn("13 000");

		// when
		final String formattedResult = classUnderTest.calculate(
				new CalculationModel(CalculationType.ADDITION, 10000, 3000)
			).getFormattedSolution();

		// then
		assertThat(formattedResult).isEqualTo("13 000");
	}
}
