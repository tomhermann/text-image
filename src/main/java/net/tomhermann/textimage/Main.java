package net.tomhermann.textimage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import net.tomhermann.textimage.ascii.AsciiImageProcessor;
import net.tomhermann.textimage.ascii.ImagePreprocessor;
import net.tomhermann.textimage.colors.ColorDistanceCalculator;
import net.tomhermann.textimage.colors.ColorToCharacterConverter;
import net.tomhermann.textimage.imaging.ImageFilter;
import net.tomhermann.textimage.support.Dimensions;

public class Main {

    // I would normally use a library like args4j and provide command line
    // options for the image and dimensions, but for now this demonstrates the
    // program in all its ducky glory.

    public static void main(String... args) throws Exception {
        try (InputStream ducky = Main.class.getResourceAsStream("/Ducky.png")) {
            BufferedImage image = ImageIO.read(ducky);
            ColorToCharacterConverter colorConverter = new ColorToCharacterConverter(new ColorDistanceCalculator());
            ImagePreprocessor imagePreprocessor = new ImagePreprocessor(new ImageFilter());
            AsciiImageProcessor asciiProcessor = new AsciiImageProcessor(imagePreprocessor, colorConverter);
            List<String> asciiLines = asciiProcessor.toAscii(image, Dimensions.of(90, 50));

            for (String line : asciiLines) {
                System.out.println(line);
            }
        }
    }
}
