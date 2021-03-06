package com.teamc2.travellingsalesbee.gui.data.cells;

import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * A generic cell of no type
 *
 * @author Christopher Lane (cml476)
 * @author Todd Waugh Ambridge (txw467)
 */
public abstract class Cell extends Point2D.Double {

	/**
	 * Create a new cell
	 */
	public Cell() {
		//
	}

	/**
	 * Create a new cell
	 *
	 * @param x X position of the cell
	 * @param y Y position of the cell
	 */
	public Cell(double x, double y) {
		super(x, y);
	}

	@Override
	public String toString() {
		return "[(" + (int) x + ", " + (int) y + ") " + getType() + "]";
	}

	/**
	 * Get the type of the cell
	 *
	 * @return Type of the cell
	 */
	public abstract CellType getType();

	/**
	 * Get the cell image
	 *
	 * @return Cell image
	 */
	protected abstract Image getImage(AlgorithmType type);
}