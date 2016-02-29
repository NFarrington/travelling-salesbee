package com.teamc2.travellingsalesbee.gui.data.cells;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CellFlower extends Cell {

	/**
	 * Create a new flower cell
	 *
	 * @param x X position of cell
	 * @param y Y position of cell
	 */
	public CellFlower(double x, double y) {
		super(x, y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CellType getType() {
		return CellType.FLOWER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BufferedImage getImage() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(this.getClass().getResource("/assets/icons/Flower.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}