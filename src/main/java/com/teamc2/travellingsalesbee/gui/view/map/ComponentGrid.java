package com.teamc2.travellingsalesbee.gui.view.map;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A class for the grid placed on top of the map area
 *
 * @author Christopher Lane (cml476)
 */
public class ComponentGrid extends JComponent {

	private final int cellWidth;
	private final int cellHeight;
	private ArrayList<Line> lines;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int screenWidth = screenSize.width;
	private int screenHeight = screenSize.height;


	/**
	 * Create a new grid
	 */
	public ComponentGrid(int cellWidth, int cellHeight) {
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;

		setBounds(0, 0, screenWidth, screenHeight);
		addLines();
	}

	/**
	 * Collate all lines for the grid
	 */
	private void addLines() {
		lines = new ArrayList<>();

		// While the widthCount is less than the width of the users screen, draw vertical lines
		for (int i = 0; i <= screenWidth; i += cellWidth) {
			lines.add(new Line(i, 50, i, screenHeight));
		}

		// While the heightCount is less than the height of the users screen, draw horizontal lines
		for (int i = 0; i <= screenHeight; i += cellHeight) {
			lines.add(new Line(0, i + 50, screenWidth, i + 50));
		}
	}

	/**
	 * Draw the lines
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setStroke(new BasicStroke(5)); // Set the line thickness to 5

		// Set the line colour
		Color lineColor = new Color(191, 191, 191, 100);
		g2.setColor(lineColor);

		// Draw all lines
		for (Line line : lines) {
			g2.drawLine(line.x1, line.y1, line.x2, line.y2);
		}
	}

	/**
	 * A class for creating lines
	 */
	private static class Line {
		int x1;
		int y1;
		int x2;
		int y2;

		/**
		 * Create a new line from (x1, y1) to (x2, y2)
		 *
		 * @param x1 x1 position
		 * @param y1 y1 position
		 * @param x2 x2 position
		 * @param y2 y2 position
		 */
		Line(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
	}
}