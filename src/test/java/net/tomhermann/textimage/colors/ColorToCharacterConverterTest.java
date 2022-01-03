package net.tomhermann.textimage.colors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.Color;

import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ColorToCharacterConverterTest {
	@Mock
	private ColorDistanceCalculator colorDistanceCalculator;
	@InjectMocks
	private ColorToCharacterConverter converter;

	@Test
	void whenColorHasAnExactMappingUseTheMappedValue() {
		assertThat(converter.convert(BLACK)).isEqualTo('#');
	}

	@Test
	void whenColorDoesNotHaveAnExactMappingFindNearestSupportedColor() {
		var pink = Color.PINK;
		when(colorDistanceCalculator.findNearestSupportedColor(eq(pink), any())).thenReturn(WHITE);

		var actual = converter.convert(pink);

		assertThat(actual).isEqualTo(converter.convert(WHITE));
	}
	
	@Test
	void whenColorHasAnExactMappingDoNotCalculateTheClosestColorDistance() {
		verify(colorDistanceCalculator, never()).findNearestSupportedColor(any(), any());

		assertThat(converter.convert(BLACK)).isEqualTo('#');
	}
}
