package net.tomhermann.textimage.ascii;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import net.tomhermann.textimage.colors.ColorToCharacterConverter;
import net.tomhermann.textimage.support.Dimensions;

import org.junit.Before;
import org.junit.Test;

public class AsciiImageProcessorTest {
	private AsciiImageProcessor asciiImageProcessor;
	private ImagePreprocessor imagePreprocessor;
	private ColorToCharacterConverter colorToCharacterConverter;
	
	@Before
	public void setup() {
		this.imagePreprocessor = mock(ImagePreprocessor.class);
		this.colorToCharacterConverter = mock(ColorToCharacterConverter.class);
		asciiImageProcessor = new AsciiImageProcessor(imagePreprocessor, colorToCharacterConverter);
	}
	
	@Test
	public void whenGivenImageDimensionsProvideOneRowOfTextPerPixelOfHeight() {
		Dimensions resizedImageDimensions = Dimensions.of(5,5);
		BufferedImage originalImg = mock(BufferedImage.class);
		BufferedImage preprocessedImage = mock(BufferedImage.class);
		when(imagePreprocessor.preprocess(originalImg, resizedImageDimensions)).thenReturn(preprocessedImage);
		when(colorToCharacterConverter.convert(any(Color.class))).thenReturn('!');
		
		List<String> ascii = asciiImageProcessor.toAscii(originalImg, resizedImageDimensions);
		
		assertThat(ascii.size(), is(equalTo(resizedImageDimensions.getHeight())));
	}

	@Test
	public void eachRowIsGeneratedFromConcatenatedCharactersRepresentingHorizontalPixels() {
		Dimensions resizedImageDimensions = Dimensions.of(2, 1);
		BufferedImage originalImg = mock(BufferedImage.class);
		BufferedImage preprocessedImage = mock(BufferedImage.class);
		when(imagePreprocessor.preprocess(originalImg, resizedImageDimensions)).thenReturn(preprocessedImage);
		when(preprocessedImage.getRGB(0, 0)).thenReturn(Color.BLACK.getRGB());
		when(preprocessedImage.getRGB(1, 0)).thenReturn(Color.WHITE.getRGB());
		when(colorToCharacterConverter.convert(Color.BLACK)).thenReturn('@');
		when(colorToCharacterConverter.convert(Color.WHITE)).thenReturn('!');
		
		List<String> ascii = asciiImageProcessor.toAscii(originalImg, resizedImageDimensions);
		
		assertThat(ascii.size(), is(equalTo(resizedImageDimensions.getHeight())));
		assertThat(ascii.get(0), is(equalTo("@!")));
	}
}
