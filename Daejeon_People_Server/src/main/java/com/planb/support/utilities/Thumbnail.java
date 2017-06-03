package com.planb.support.utilities;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Thumbnail {
	private int blockCount = 6;
	private Color blockColor;
	private int blockSize = 40;
	private int imageSize = blockCount * blockSize;
	
	private BufferedImage image;
	
	private Random random = new Random();
	
	public Thumbnail() {
		setBlockColor(createRandomColor());
	}
	
	public Thumbnail(int blockCount, Color blockColor) {
		setBlockCount(blockCount);
		setBlockColor(blockColor);
	}
	
	// Block count
	public int getBlockCount() {
		return blockCount;
	}

	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
	}
	// Block count
		
	// Block color
	public Color getBlockColor() {
		return blockColor;
	}

	public void setBlockColor(Color blockColor) {
		this.blockColor = blockColor;
	}
	
	public void setBlockColor(int r, int g, int b) {
		setBlockColor(new Color(r, g, b));
	}
	// Block color
	
	// Block size
	public int getBlockSize() {
		return blockSize;
	}
	
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}
	// Block size
	
	public void process() {
		imageSize = blockCount * blockSize;
		// Resolution (imageSize * imageSize)
		
		int rgb = blockColor.getRGB();
		// Color to integer
		
		image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
		// Define image
			
		for(int i = 0; i < (blockCount % 2 == 1 ? blockCount / 2 + 1 : blockCount / 2); i++) {
			for(int j = 0; j < blockCount; j++) {
				// Loop block
					
				boolean block = random.nextBoolean();
				// Generate block
					
				if(block) {
					// Assign block
					image = assignBlock(image, i, j, rgb);
				}
			}
		}
	}
	
	public void saveImage(String filePath) {
		File outputFile = new File(filePath);
		try {
			ImageIO.write(image, "png", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File getImageFile(String filePath) {
		saveImage(filePath);
		File file = new File(filePath);
		
		return file;
	}
	
	private Color createRandomColor() {
		return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
	}
	
	private BufferedImage assignBlock(BufferedImage image, int x, int y, int rgb) {
		for(int i = x * blockSize; i < (x + 1) * blockSize; i++) {
			// Horizontal
			for(int j = y * blockSize; j < (y + 1) * blockSize; j++) {
				// Vertical
				
				image.setRGB(i, j, rgb);
				// RGB
				
				image.setRGB(imageSize - i - 1, j, rgb);
				// Symmetry
			}
		}
		
		return image;
	}
}
