package com.teamc2.travellingsalesbee.gui.view.map;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.CellType;
import com.teamc2.travellingsalesbee.gui.view.toolbox.CellDraggable;
import javafx.scene.text.Text;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * A class for the visualisation of map elements
 *
 * @author Christopher Lane (cml476)
 * @author Melvyn Mathews (mxm499)
 */
public class PanelMap extends JPanel {

	public final static String name = "PanelMap";
	private final int cellWidth;
	private final int cellHeight;
	private ComponentPath componentPath;
	private PanelAnimalAnimation panelAnimation;
	private PanelOverlyingText panelOverlyingText;
	private Map map;
	private AlgorithmType type;

	/**
	 * Create the map panel
	 *
	 * @param cellWidth  Width of the grid sections
	 * @param cellHeight Height of the grid sections
	 */
	public PanelMap(int cellWidth, int cellHeight) {

		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;

		setName(name);

		// Create the map we're visualising
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		map = new Map();


		ComponentGrid componentGrid = new ComponentGrid(cellWidth, cellHeight);
		add(componentGrid);

		componentPath = new ComponentPath(AlgorithmType.BEE);
		add(componentPath);


		//Initialise and set bounds
		panelAnimation = new PanelAnimalAnimation(screenWidth, screenHeight);
		panelAnimation.setBounds(getX(), getY(), screenWidth, screenHeight);
		add(panelAnimation); //Add to panel map

		panelOverlyingText = new PanelOverlyingText();
		panelOverlyingText.setBounds(getX(), getY(), screenWidth, screenHeight);
		add(panelOverlyingText);

		setComponentZOrder(panelOverlyingText, 2);
		setComponentZOrder(panelAnimation, 1);
		setLayout(null);

	}

	/**
	 * Paint the grass texture onto the map and the grid
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		Point2D center = new Point2D.Float(getWidth() / 2, 0);
		float radius = getWidth() / 2;
		Point2D focus = new Point2D.Float((getWidth() / 2), 1000);
		float[] dist = {0.0f, 0.3f, 1.0f};
		Color[] colors = {new Color(71, 35, 35), Color.WHITE, new Color(71, 35, 35)};
		RadialGradientPaint p = new RadialGradientPaint(center, radius, focus, dist, colors, MultipleGradientPaint.CycleMethod.REFLECT);

		// Find the longest line in the overlying text
		FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
		Text textObj = panelOverlyingText.getTextObject();
		String[] textLines = textObj.getText().split("\n");
		int longestStringLength = 0;
		String longestString = "";

		for (String line : textLines) {
			if (line.length() > longestStringLength) {
				longestStringLength = line.length();
				longestString = line;
			}
		}

		// Set the overlying text location based on map width and text length
		float textWidth = fontLoader.computeStringWidth(longestString, textObj.getFont());
		panelOverlyingText.setTextXandY((int) ((getWidth() / 2) - (textWidth / 2)), 13);

		//GradientPaint redtowhite = new GradientPaint(0, 50, new Color(71, 35, 35), 0, 0, new Color(134, 93, 93));
		//this.setBackground(new Color(71, 35, 35));

		g2.setPaint(p);
		g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 0, 0));
		g2.setPaint(Color.black);

		//Allowing for the correct background to be printed
		try {

			BufferedImage img = null;

			TexturePaint paint;

			switch (type) {
				case BEE:
					img = ImageIO.read(getClass().getResource("/assets/backgrounds/Grass.jpg"));
					break;
				case ANT:
					img = ImageIO.read(getClass().getResource("/assets/backgrounds/Dirt.jpg"));
					break;
				case NEARESTNEIGHBOUR:
					img = ImageIO.read(getClass().getResource("/assets/backgrounds/Parchment.jpg"));
					break;
				case TWOOPT:
					img = ImageIO.read(getClass().getResource("/assets/backgrounds/Sea.jpg"));
					break;
			}

			paint = new TexturePaint(img, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
			g2.setPaint(paint);
			g2.fill(new Rectangle(0, 50, getWidth(), getHeight()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return the height of grid sections
	 *
	 * @return Height of grid sections
	 */
	public int getCellHeight() {
		return cellHeight;
	}

	/**
	 * Return the width of grid sections
	 *
	 * @return Width of grid sections
	 */
	public int getCellWidth() {
		return cellWidth;
	}

	/**
	 * Get the path component
	 *
	 * @return The path component
	 */
	public ComponentPath getPathComponent() {
		return componentPath;
	}

	/**
	 * Get the animal animation panel
	 *
	 * @return The animal animation panel
	 */
	public PanelAnimalAnimation getPanelAnimalAnimation() {
		return panelAnimation;
	}

	/**
	 * Get the overlying text panel
	 *
	 * @return The overlying text panel
	 */
	public PanelOverlyingText getPanelOverlyingText() {
		return panelOverlyingText;
	}

	/**
	 * Get the map data object
	 *
	 * @return The map for the panel
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Get the algorithm type
	 *
	 * @return The algorithm type
	 */
	public AlgorithmType getAlgorithmType() {
		return type;
	}

	/**
	 * @param type The type of algorithm currently being viewed to adjust the cell images on
	 *             the panelMap accordingly
	 */
	public void setAlgorithmType(AlgorithmType type) {
		this.type = type;

		for (Component c : getComponents()) {
			if (c instanceof CellDraggable) {
				((CellDraggable) c).setAlgorithmType(type);
				((CellDraggable) c).setImage(((CellDraggable) c).getType());
			}
		}
	}

	/**
	 * Clear a cell at a given position if it is full
	 *
	 * @param x X position of the cell
	 * @param y Y position of the cell
	 */
	public void clearFullCell(int x, int y) {
		for (Component c : getComponents()) {
			if (c instanceof CellDraggable) {
				if (c.isEnabled() && c.getBounds().x == x && c.getBounds().y == y) {
					remove(c);
					c.setEnabled(false);
				}
			}
		}

	}

	/**
	 * Delete the origin draggable cell
	 */
	public void deleteOldOrigin() {
		for (Component c : getComponents()) {
			if (c instanceof CellDraggable) {
				if (c.isEnabled() && ((CellDraggable) c).getType().equals(CellType.ORIGIN)) {
					remove(c);
					c.setEnabled(false);
				}
			}
		}
	}

	/**
	 * Clear the map
	 */
	public void clear() {
		getPathComponent().setStepNum(-1);
		//getPanelAnimalAnimation().setStepNum(-1);
		for (Component c : getComponents()) {
			if (c instanceof CellDraggable) {
				int x = c.getX();
				int y = c.getY();
				remove(c);
				c.setEnabled(false);
				map.setCell(x, y, CellType.EMPTY);
			}
		}
	}
}
