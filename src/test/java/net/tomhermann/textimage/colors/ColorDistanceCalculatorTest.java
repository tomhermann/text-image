package net.tomhermann.textimage.colors;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.List;

import static java.awt.Color.*;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ColorDistanceCalculatorTest {
	private final ColorDistanceCalculator calculator = new ColorDistanceCalculator();
	
	@Test
	void euclideanDistanceIsSqrtOfTheSumOfAllColorDeltasEachSquared() {
		var euclideanDistance = calculator.euclideanDistance(Color.PINK, Color.GREEN);

		assertThat(euclideanDistance).isCloseTo(319.45, Offset.offset(0.01));
	}

	@Test
	void euclideanDistanceReveresedIsEquivalent() {
		var euclideanDistance = calculator.euclideanDistance(Color.GREEN, Color.PINK);

		assertThat(euclideanDistance).isCloseTo(319.45, Offset.offset(0.01));
	}
	
	@Test
	void nullColorsOnEuclideanRHSAreNotAllowed() {
		//noinspection ConstantConditions
		assertThatThrownBy(() -> calculator.euclideanDistance(Color.PINK, null))
				.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	void nullColorsOnEuclideanLHSAreNotAllowed() {
		//noinspection ConstantConditions
		assertThatThrownBy(() -> calculator.euclideanDistance(null, PINK))
				.isInstanceOf(NullPointerException.class);
	}

	@Test
	void whenGivenSomeColorAndCollectionOfAcceptableColorsFindNearest() {
		var supportedColors = List.of(BLACK, WHITE);

		var closestColor = calculator.findNearestSupportedColor(Color.decode("#111"), supportedColors);

		assertThat(closestColor).isEqualTo(BLACK);
	}

	@Test
	void whenGivenTwoColorsWithSameDistanceChooseFirstFound() {
		var supportedColors = List.of(YELLOW, MAGENTA);

		var closestColor = calculator.findNearestSupportedColor(PINK, supportedColors);

		assertThat(closestColor).isEqualTo(YELLOW);
	}
	
	@Test
	void whenGivenNoSupportedColorsThrowUp() {
		List<Color> supportedColors = emptyList();

		assertThatThrownBy(() -> calculator.findNearestSupportedColor(WHITE, supportedColors))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void whenGivenNullAsSupportedColorCollectionThrowUp() {
		assertThatThrownBy(() -> calculator.findNearestSupportedColor(WHITE, null))
			.isInstanceOf(IllegalArgumentException.class);
	}
}