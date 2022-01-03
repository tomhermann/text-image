package net.tomhermann.textimage.ascii;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;

import net.tomhermann.textimage.imaging.ImageFilter;
import net.tomhermann.textimage.support.Dimensions;

import org.junit.Before;
import org.junit.Test;

public class ImagePreprocessorTest {
	private ImagePreprocessor imageProcessor;
	private ImageFilter imageFilter;
	
	@Before
	public void setup() {
		this.imageFilter = mock(ImageFilter.class);
		this.imageProcessor = new ImagePreprocessor(imageFilter);
	}
	
	@Test
	public void whenImageIsPreprocessedItIsResizedDarkenedAndConvertedToGrayScale() {
        Dimensions dimensions = new Dimensions(100, 100);
		BufferedImage originalImg = mock(BufferedImage.class);
		BufferedImage resizedImg = mock(BufferedImage.class);
		BufferedImage darkenedImg = mock(BufferedImage.class);
		BufferedImage finalImg = mock(BufferedImage.class);
		when(imageFilter.resizeImage(originalImg, dimensions)).thenReturn(resizedImg);
		when(imageFilter.darken(resizedImg, ImagePreprocessor.DARKENING_FACTOR)).thenReturn(darkenedImg);
		when(imageFilter.grayScale(darkenedImg)).thenReturn(finalImg);
		
		BufferedImage result = imageProcessor.preprocess(originalImg, dimensions);
		
		verify(imageFilter).resizeImage(originalImg, dimensions);
		verify(imageFilter).darken(resizedImg, ImagePreprocessor.DARKENING_FACTOR);
		verify(imageFilter).grayScale(darkenedImg);
		assertSame(finalImg, result);
	}
}
