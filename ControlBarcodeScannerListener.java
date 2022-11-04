package com.diy.control;
import com.jimmyselectronics.AbstractDevice;
import com.jimmyselectronics.AbstractDeviceListener;
import com.jimmyselectronics.necchi.*;

public class ControlBarcodeScannerListener implements BarcodeScannerListener {


	/*
	 * Here lies our trouble in sending the necessary information for Control
	 */
	@Override
	public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
		//used to signal Control the barcode that was scanned
		// check SampleBarcodeScannerListener from his code
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
