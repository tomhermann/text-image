package net.tomhermann.textimage.colors;

import java.awt.Color;
import java.util.Collection;


public class ColorDistanceCalculator {

	/***
	 * @see http://www.compuphase.com/cmetric.htm
	 */
	public double euclideanDistance(Color lhs, Color rhs) {
		double redDelta = lhs.getRed() - rhs.getRed();
		double greenDelta = lhs.getGreen() - rhs.getGreen();
		double blueDelta = lhs.getBlue() - rhs.getBlue();
		return Math.sqrt(sq(redDelta) + sq(greenDelta) + sq(blueDelta));
	}
	
	public Color findNearestSupportedColor(Color color, Collection<Color> supportedColors) {
		if(supportedColors == null || supportedColors.size() == 0) {
			throw new IllegalArgumentException("Please provide at least one supported color.");
		}
		Color nearestColor = Color.WHITE;
		Double nearestDistance = Double.MAX_VALUE;
		
		for (Color supportedColor : supportedColors) {
			double distance = euclideanDistance(color, supportedColor);
			if(distance < nearestDistance) {
				nearestDistance = distance;
				nearestColor = supportedColor;
			}
		}

		return nearestColor;
	}
	
	private static final double sq(double number) {
		return Math.pow(number, 2);
	}
}
