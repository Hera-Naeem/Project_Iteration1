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

	
	/*
	 * Here lies our trouble in sending the necessary information for Control
	 */
	@Override
	public void cardInserted(CardReader reader) {
		//used to signal Control that the card is inserted
	}

	@Override
	public void cardRemoved(CardReader reader) {
		//used to signal Control that the card is removed
	}

	@Override
	public void cardDataRead(CardReader reader, CardData data) {
		//used to signal Control that the card data was read
	}

}
