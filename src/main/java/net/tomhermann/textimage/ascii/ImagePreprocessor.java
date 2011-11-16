package net.tomhermann.textimage.ascii;

import java.awt.image.BufferedImage;

import net.tomhermann.textimage.imaging.ImageFilter;
import net.tomhermann.textimage.support.Dimensions;

public class ImagePreprocessor {
	protected static final float DARKENING_FACTOR = .8f;
	private final ImageFilter imageFilter;

	public ImagePreprocessor(ImageFilter imageFilter) {
		this.imageFilter = imageFilter;
	}
	
	public BufferedImage preprocess(BufferedImage originalImg, Dimensions resizedImageDimensions) {
		BufferedImage resizedImg = imageFilter.resizeImage(originalImg, resizedImageDimensions);
		BufferedImage darkenedImg = imageFilter.darken(resizedImg, DARKENING_FACTOR);
		return imageFilter.grayScale(darkenedImg);
	}
}
