package net.tomhermann.textimage.colors;

import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class ColorToCharacterConverterTest {
	private ColorToCharacterConverter converter;
	private ColorDistanceCalculator colorDistanceCalculator;

	@Before
	public void setup() {
		this.colorDistanceCalculator = mock(ColorDistanceCalculator.class);
		this.converter = new ColorToCharacterConverter(colorDistanceCalculator);
	}

	@Test
	public void whenColorHasAnExactMappingUseTheMappedValue() {
		assertThat(converter.convert(BLACK), is(equalTo('#')));
	}

	@Test
	public void whenColorDoesNotHaveAnExactMappingFindNearestSupportedColor() {
		Color myColor = Color.PINK;
		when(colorDistanceCalculator.findNearestSupportedColor(same(myColor), anyColorCollection())).thenReturn(WHITE);
		
		assertThat(converter.convert(myColor), is(equalTo(converter.convert(WHITE))));
	}
	
	@Test
	public void whenColorHasAnExactMappingDoNotCalculateTheClosestColorDistance() {
		verify(colorDistanceCalculator, never()).findNearestSupportedColor(any(Color.class), anyColorCollection());
		assertThat(converter.convert(BLACK), is(equalTo('#')));
	}

	@SuppressWarnings("unchecked")
	private static Collection<Color> anyColorCollection() {
		return anyCollection();
	}
}
