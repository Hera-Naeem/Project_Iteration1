package com.diy.control;

import com.jimmyselectronics.AbstractDevice;
import com.jimmyselectronics.AbstractDeviceListener;
import com.jimmyselectronics.virgilio.ElectronicScale;
import com.jimmyselectronics.virgilio.ElectronicScaleListener;

public class ControlElectronicScaleListener implements ElectronicScaleListener{

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {}

	@Override
	public void turnedOn(AbstractDevice<? extends AbstractDeviceListener> device) {}

	@Override
	public void turnedOff(AbstractDevice<? extends AbstractDeviceListener> device) {}
	
	/*
	 * Here lies our trouble in sending the necessary information for Control
	 */
	@Override
	public void weightChanged(ElectronicScale scale, double weightInGrams) {
		//used to signal Control that the weightChanged
	}

	@Override
	public void overload(ElectronicScale scale) {
		//used to signal Control that excessive weight has been placed on the given scale
	}

	@Override
	public void outOfOverload(ElectronicScale scale) {
		//used to signal Control that the former excessive weight has been removed from the indicated scale, and it is again able to measure weight.
	}

}
