package com.teamc2.travellingsalesbee.gui.view.settings;

import com.teamc2.travellingsalesbee.gui.view.map.PanelMap;

import javax.swing.*;

public class SettingsSliders {

	private final PanelMap panelMap;
	private final PanelSettings panelSettings;
	private double animationSpeed = 10;
	private JSlider speedSlider;
	private JSlider noOfRunsSlider;
	private JLabel lblRunsOfType;
	private JLabel lblNoOfRuns;
	private JLabel lblAnimationSpeed;
	private JLabel lblSpeed;

	public SettingsSliders(PanelSettings panelSettings, PanelMap panelMap) {
		this.panelMap = panelMap;
		this.panelSettings = panelSettings;

		createRunCountSlider();
		createSpeedSlider();
	}

	public void createRunCountSlider() {
		noOfRunsSlider = new JSlider();
		noOfRunsSlider.setValue(panelSettings.getNoOfRunsValue());
		noOfRunsSlider.setOpaque(false);
		lblRunsOfType = new JLabel("Experiment Runs: ");
		lblNoOfRuns = new JLabel("" + panelSettings.getNoOfRunsValue());

		noOfRunsSlider.addChangeListener(arg0 -> {
			panelSettings.setNoOfRunsValue(noOfRunsSlider.getValue());
			lblNoOfRuns.setText(("" + panelSettings.getNoOfRunsValue()));
		});
	}

	public void createSpeedSlider() {
		speedSlider = new JSlider();
		speedSlider.setMaximum(20);
		speedSlider.setValue((int) animationSpeed);
		speedSlider.setOpaque(false);
		lblAnimationSpeed = new JLabel("Animation Speed: ");
		lblSpeed = new JLabel("" + animationSpeed / 10);

		speedSlider.addChangeListener(arg0 -> {
			animationSpeed = speedSlider.getValue() / 10;
			panelMap.getAnimation().setSpeed(animationSpeed);
			lblSpeed.setText(("" + animationSpeed));
		});
	}

	public void updateSliderDetails() {
		switch (panelSettings.getType()) {
			case BEE:
				lblRunsOfType.setText("Experimental Runs:");
				break;
			case ANT:
				lblRunsOfType.setText("Pheromone Runs:");
				break;
			case NEARESTNEIGHBOUR:
				break;
			case TWOOPT:
				lblRunsOfType.setText("Swap Runs:");
				break;
		}
	}

	public JSlider getSpeedSlider() {
		return speedSlider;
	}

	public JSlider getNoOfRunsSlider() {
		return noOfRunsSlider;
	}

	public JLabel getLblRunsOfType() {
		return lblRunsOfType;
	}

	public JLabel getLblNoOfRuns() {
		return lblNoOfRuns;
	}

	public JLabel getLblAnimationSpeed() {
		return lblAnimationSpeed;
	}

	public JLabel getLblSpeed() {
		return lblSpeed;
	}
}
