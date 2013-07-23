package com.tomtom.amelinium.chartservice.state;

import com.tomtom.amelinium.chartservice.model.ChartModel;
import com.tomtom.amelinium.chartservice.model.DoubleTable;
import com.tomtom.amelinium.chartservice.model.IntTable;

/**
 * Stores state of chart building state machine.
 * 
 * @author Natasza Nowak
 */
public class State {
	
	private Step step=Step.BUILDING_INTRO;
	private IntTable currentTableInt = null;
	private DoubleTable currentTableDouble = null;
	private ChartModel chartModel = new ChartModel();

	public Step getStep() {
		return step;
	}

	public void setStep(Step step) {
		this.step = step;
	}

	public IntTable getCurrentIntTable() {
		return currentTableInt;
	}

	public void setCurrentTableInt(IntTable currentTable) {
		this.currentTableInt = currentTable;
	}

	public ChartModel getChartModel() {
		return chartModel;
	}

	public void setChartModel(ChartModel model) {
		this.chartModel = model;
	}

	public DoubleTable getCurrentTableDouble() {
		return currentTableDouble;
	}

	public void setCurrentTableDouble(DoubleTable currentTableDouble) {
		this.currentTableDouble = currentTableDouble;
	}
	
}
