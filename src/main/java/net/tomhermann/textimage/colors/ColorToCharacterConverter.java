package net.tomhermann.textimage.colors;

import java.awt.Color;
import java.util.Map;
import java.util.Optional;

public class ColorToCharacterConverter {
    private static final Map<Color, Character> COLOR_TO_CHAR = Map.of(
        Color.BLACK, '#',
        Color.decode("#222000"), '#',
        Color.decode("#444000"), 'X',
        Color.decode("#666000"), '~',
        Color.decode("#888000"), '-',
        Color.decode("#AAA000"), ',',
        Color.decode("#EEE000"), '.',
        Color.WHITE, ' '
    );

    private final ColorDistanceCalculator colorDistanceCalculator;

    public ColorToCharacterConverter(ColorDistanceCalculator colorDistanceCalculator) {
        this.colorDistanceCalculator = colorDistanceCalculator;
    }

    public Character convert(Color color) {
        return Optional.of(color)
            .map(COLOR_TO_CHAR::get)
            .orElseGet(() -> nearestSupportedColor(color));
    }

    private Character nearestSupportedColor(Color color) {
        var nearestSupportedColor = colorDistanceCalculator.findNearestSupportedColor(color, COLOR_TO_CHAR.keySet());
        return COLOR_TO_CHAR.get(nearestSupportedColor);
    }
}
