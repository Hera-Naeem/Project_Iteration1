package com.diy.control;

import com.jimmyselectronics.AbstractDevice;
import com.jimmyselectronics.AbstractDeviceListener;
import com.jimmyselectronics.opeechee.*;
import com.jimmyselectronics.opeechee.Card.CardData;


public class ControlCardReaderListener implements CardReaderListener{

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {}
	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {}
	@Override
	public void turnedOn(AbstractDevice<? extends AbstractDeviceListener> device) {}
	@Override
	public void turnedOff(AbstractDevice<? extends AbstractDeviceListener> device) {}

	@Override
	public void cardInserted(CardReader reader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cardRemoved(CardReader reader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cardDataRead(CardReader reader, CardData data) {
		// TODO Auto-generated method stub
		
	}

}
