package net.tomhermann.textimage.ascii;

import net.tomhermann.textimage.imaging.ImageFilter;
import net.tomhermann.textimage.support.Dimensions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImagePreprocessorTest {
	@Mock
	private ImageFilter imageFilter;
	@InjectMocks
	private ImagePreprocessor imageProcessor;

	@Test
	void whenImageIsPreprocessedItIsResizedDarkenedAndConvertedToGrayScale() {
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
