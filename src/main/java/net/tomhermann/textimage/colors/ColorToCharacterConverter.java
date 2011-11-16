package net.tomhermann.textimage.colors;

import java.awt.Color;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ColorToCharacterConverter {
	private static final Map<Color, Character> COLOR_TO_CHAR;
	static {
		Map<Color, Character> temp = new HashMap<Color, Character>();
		temp.put(Color.BLACK, '#');
		temp.put(Color.decode("#222000"), '#');
		temp.put(Color.decode("#444000"), 'X');
		temp.put(Color.decode("#666000"), '~');
		temp.put(Color.decode("#888000"), '-');
		temp.put(Color.decode("#AAA000"), ',');
		temp.put(Color.decode("#EEE000"), '.');
		temp.put(Color.WHITE, ' ');
		COLOR_TO_CHAR = Collections.unmodifiableMap(temp);
	}
	private final ColorDistanceCalculator colorDistanceCalculator;

	public ColorToCharacterConverter(ColorDistanceCalculator colorDistanceCalculator) {
		this.colorDistanceCalculator = colorDistanceCalculator;
	}
	
	public Character convert(Color color) {
		if (COLOR_TO_CHAR.containsKey(color)) {
			return COLOR_TO_CHAR.get(color);
		}
		return COLOR_TO_CHAR.get(colorDistanceCalculator.findNearestSupportedColor(color, COLOR_TO_CHAR.keySet()));
	}

}
