package net.tomhermann.textimage.ascii;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import net.tomhermann.textimage.colors.ColorToCharacterConverter;
import net.tomhermann.textimage.support.Dimensions;

public class AsciiImageProcessor {
	private final ColorToCharacterConverter colorToCharacter;
	private final ImagePreprocessor imagePreprocessor;

	public AsciiImageProcessor(ImagePreprocessor imagePreprocessor, ColorToCharacterConverter colorToCharacter) {
		this.imagePreprocessor = imagePreprocessor;
		this.colorToCharacter = colorToCharacter;
	}
	
	public List<String> toAscii(BufferedImage originalImg, Dimensions resizedImageDimensions) {
        List<String> output = new ArrayList<>(resizedImageDimensions.height());
		BufferedImage preprocessedImage = imagePreprocessor.preprocess(originalImg, resizedImageDimensions);

        for (int y = 0; y < resizedImageDimensions.height(); y++) {
			StringBuilder sb = new StringBuilder();
			for (int x = 0; x < resizedImageDimensions.width(); x++) {
				sb.append(colorToCharacter.convert(new Color(preprocessedImage.getRGB(x, y))));
			}
			output.add(sb.toString());
		}
		
		return output;
	}
}
