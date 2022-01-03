package net.tomhermann.textimage.imaging;

import net.tomhermann.textimage.support.Dimensions;

import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.RescaleOp;

/***
 * This class provides gives basic image manipulation abilities using Graphics2D 
 * @author Tom Hermann
 *
 * Note - RenderingHints are not used since the quality of the image doesn't matter much for ASCII art.. 
 */
public class ImageFilter {
    private static final ColorSpace CS_GRAY = ColorSpace.getInstance(ColorSpace.CS_GRAY);

    public BufferedImage resizeImage(BufferedImage original, Dimensions dimensions) {
        BufferedImage resizedImage = new BufferedImage(dimensions.width(), dimensions.height(), original.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(original, 0, 0, dimensions.width(), dimensions.height(), null);
        g.dispose();
        return resizedImage;
    }

    public BufferedImage grayScale(BufferedImage image) {
        return new ColorConvertOp(CS_GRAY, null).filter(image, null);
    }

    public BufferedImage darken(BufferedImage image, float darkeningFactor) {
        return new RescaleOp(darkeningFactor, 0, null).filter(image, null);
    }
}
