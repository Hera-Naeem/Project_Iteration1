package com.diy.control;
import com.jimmyselectronics.AbstractDevice;
import com.jimmyselectronics.AbstractDeviceListener;
import com.jimmyselectronics.necchi.*;

public class ControlBarcodeScannerListener implements BarcodeScannerListener {


	// additions by catalina friday morning base on profs code ///////////
	
	// Here, we will record the device on which an event occurs.
	public AbstractDevice<? extends AbstractDeviceListener> device = null;
	
	//Here, we will record the barcode that has been scanned.
	public Barcode barcode = null;
	
	//This is the name of this listener
	public String name;

	
	//Basic constructor.
	//@param name- The name to use for this listener.
	public ControlBarcodeScannerListener(String name) {
		this.name = name;
	}
	
////////////////////////////////////////////////////////////////////////////
	
	/*
	 * Here lies our trouble in sending the necessary information for Control
	 */
	
	@Override
	public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
		//used to signal Control the barcode that was scanned
		
		//Copy paste from the profs sampleBarcodeScannerListener class
		this.device = barcodeScanner;
		this.barcode = barcode;
		System.out.println(name + ": A barcode has been scanned: " + barcode.toString());
		
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
