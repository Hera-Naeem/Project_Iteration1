package com.diy.control;
import com.jimmyselectronics.AbstractDevice;
import com.jimmyselectronics.AbstractDeviceListener;
import com.jimmyselectronics.necchi.*;

public class ControlBarcodeScannerListener implements BarcodeScannerListener {



	@Override
	public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {}

	@Override
	public void turnedOn(AbstractDevice<? extends AbstractDeviceListener> device) {}

	@Override
	public void turnedOff(AbstractDevice<? extends AbstractDeviceListener> device) {}

}
