package net.tomhermann.textimage;

import net.tomhermann.textimage.ascii.AsciiImageProcessor;
import net.tomhermann.textimage.ascii.ImagePreprocessor;
import net.tomhermann.textimage.colors.ColorDistanceCalculator;
import net.tomhermann.textimage.colors.ColorToCharacterConverter;
import net.tomhermann.textimage.imaging.ImageFilter;
import net.tomhermann.textimage.support.Dimensions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static java.util.Objects.requireNonNull;

public class Main {
    private final ColorToCharacterConverter colorConverter = new ColorToCharacterConverter(new ColorDistanceCalculator());
    private final ImagePreprocessor imagePreprocessor = new ImagePreprocessor(new ImageFilter());
    private final AsciiImageProcessor asciiProcessor = new AsciiImageProcessor(imagePreprocessor, colorConverter);

    public static void main(String... args) throws IOException {
        new Main().duck();
    }

    public void duck() throws IOException {
        try (InputStream ducky = getClass().getResourceAsStream("/Ducky.png")) {
            BufferedImage image = ImageIO.read(requireNonNull(ducky));

            asciiProcessor.toAscii(image, new Dimensions(90, 50))
                .forEach(System.out::println);
        }
    }
}
