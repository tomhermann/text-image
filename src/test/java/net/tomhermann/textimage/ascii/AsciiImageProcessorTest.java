package net.tomhermann.textimage.ascii;

import net.tomhermann.textimage.colors.ColorToCharacterConverter;
import net.tomhermann.textimage.support.Dimensions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AsciiImageProcessorTest {
    @Mock
    private ImagePreprocessor imagePreprocessor;
    @Mock
    private ColorToCharacterConverter colorToCharacterConverter;
    @InjectMocks
    private AsciiImageProcessor asciiImageProcessor;

    @Test
    void whenGivenImageDimensionsProvideOneRowOfTextPerPixelOfHeight() {
        var resizedImageDimensions = new Dimensions(5, 5);
        var originalImg = mock(BufferedImage.class);
        var preprocessedImage = mock(BufferedImage.class);
        when(imagePreprocessor.preprocess(originalImg, resizedImageDimensions)).thenReturn(preprocessedImage);
        when(colorToCharacterConverter.convert(any())).thenReturn('!');

        var ascii = asciiImageProcessor.toAscii(originalImg, resizedImageDimensions);

        assertThat(ascii.size()).isEqualTo(resizedImageDimensions.height());
    }

    @Test
    void eachRowIsGeneratedFromConcatenatedCharactersRepresentingHorizontalPixels() {
        var resizedImageDimensions = new Dimensions(2, 1);
        var originalImg = mock(BufferedImage.class);
        var preprocessedImage = mock(BufferedImage.class);
        when(imagePreprocessor.preprocess(originalImg, resizedImageDimensions)).thenReturn(preprocessedImage);
        when(preprocessedImage.getRGB(0, 0)).thenReturn(Color.BLACK.getRGB());
        when(preprocessedImage.getRGB(1, 0)).thenReturn(Color.WHITE.getRGB());
        when(colorToCharacterConverter.convert(Color.BLACK)).thenReturn('@');
        when(colorToCharacterConverter.convert(Color.WHITE)).thenReturn('!');

        List<String> ascii = asciiImageProcessor.toAscii(originalImg, resizedImageDimensions);

        assertThat(ascii.size()).isEqualTo(resizedImageDimensions.height());
        assertThat(ascii.get(0)).isEqualTo("@!");
    }
}
