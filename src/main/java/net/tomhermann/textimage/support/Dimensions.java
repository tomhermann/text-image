package net.tomhermann.textimage.support;

public class Dimensions {
	private int height;
	private int width;

	public static Dimensions of(int width, int height) {
		return new Dimensions(width, height);
	}
	
	public Dimensions(int width, int height) {
		this.height = height;
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append(width).append("x").append(height).toString(); 
	}
}
