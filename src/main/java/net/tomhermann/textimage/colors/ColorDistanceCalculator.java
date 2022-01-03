package net.tomhermann.textimage.colors;

import java.awt.Color;
import java.util.Collection;

import static java.util.Comparator.comparingDouble;

public class ColorDistanceCalculator {

    /**
     * See <a href="http://www.compuphase.com/cmetric.htm">http://www.compuphase.com/cmetric.htm</a>
     */
    public double euclideanDistance(Color lhs, Color rhs) {
        double redDelta = (double) lhs.getRed() - rhs.getRed();
        double greenDelta = (double) lhs.getGreen() - rhs.getGreen();
        double blueDelta = (double) lhs.getBlue() - rhs.getBlue();
        return Math.sqrt(sq(redDelta) + sq(greenDelta) + sq(blueDelta));
    }

    public Color findNearestSupportedColor(Color color, Collection<Color> supportedColors) {
        if (supportedColors == null || supportedColors.isEmpty()) {
            throw new IllegalArgumentException("Please provide at least one supported color.");
        }

        return supportedColors.stream()
                .min(comparingDouble(current -> euclideanDistance(color, current)))
                .stream()
                .findFirst()
                .orElse(Color.WHITE);
    }

    private static double sq(double number) {
        return Math.pow(number, 2);
    }
}
