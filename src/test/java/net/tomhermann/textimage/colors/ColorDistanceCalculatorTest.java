package net.tomhermann.textimage.colors;

import static java.awt.Color.*;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

public class ColorDistanceCalculatorTest {
	private final ColorDistanceCalculator calculator = new ColorDistanceCalculator();
	
	@Test
	public void euclideanDistanceIsSqrtOfTheSumOfAllColorDeltasEachSquared() {
		double euclideanDistance = calculator.euclideanDistance(Color.PINK, Color.GREEN);
		assertThat(euclideanDistance, is(closeTo(319.45, 0.01)));
	}

	@Test
	public void euclideanDistanceReveresedIsEquivalent() {
		double euclideanDistance = calculator.euclideanDistance(Color.GREEN, Color.PINK);
		assertThat(euclideanDistance, is(closeTo(319.45, 0.01)));
	}
	
	@Test(expected=NullPointerException.class)
	public void nullColorsOnEuclideanRHSAreNotAllowed() {
		calculator.euclideanDistance(Color.PINK, null);
	}
	
	@Test(expected=NullPointerException.class)
	public void nullColorsOnEuclideanLHSAreNotAllowed() {
		calculator.euclideanDistance(null, Color.PINK);
	}

	@Test
	public void whenGivenSomeColorAndCollectionOfAcceptableColorsFindNearest() {
		Collection<Color> supportedColors = Arrays.asList(BLACK, WHITE);
		Color closestColor = calculator.findNearestSupportedColor(Color.decode("#111"), supportedColors);
		assertThat(closestColor, is(equalTo(BLACK)));
	}

	@Test
	public void whenGivenTwoColorsWithSameDistanceChooseFirstFound() {
		Collection<Color> supportedColors = Arrays.asList(YELLOW, MAGENTA);
		Color closestColor = calculator.findNearestSupportedColor(PINK, supportedColors);
		assertThat(closestColor, is(equalTo(YELLOW)));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void whenGivenNoSupportedColorsThrowUp() {
		calculator.findNearestSupportedColor(WHITE, Collections.emptyList());
	}

	@Test(expected=IllegalArgumentException.class)
	public void whenGivenNullAsSupportedColorCollectionThrowUp() {
		calculator.findNearestSupportedColor(WHITE, null);
	}
	
}