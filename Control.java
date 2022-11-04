package com.diy.control;

import com.diy.hardware.BarcodedProduct;

//import java.io.IOException;

import com.diy.hardware.DoItYourselfStation;
import com.diy.hardware.external.ProductDatabases;
import com.diy.simulation.Customer;
import com.jimmyselectronics.Item;
import com.jimmyselectronics.necchi.Barcode;
import com.jimmyselectronics.necchi.BarcodedItem;
import com.jimmyselectronics.necchi.Numeral;
import com.jimmyselectronics.opeechee.Card;

import ca.ucalgary.seng300.simulation.NullPointerSimulationException;

public class Control {
	
	public static void main () {
		
		// Creating a database with a barcoded item: an apple :)
		Barcode myBarcode = new Barcode(new Numeral[] { Numeral.one, Numeral.two, Numeral.three, Numeral.four }); // 1234
		BarcodedProduct apple = new BarcodedProduct(myBarcode, "apple", 2, 100); 
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(myBarcode, apple);
		
		
		BarcodedItem appleItem = new BarcodedItem(myBarcode, 100);
		// instantiate listener and station
		Customer testCustomer = new Customer();
		testCustomer.shoppingCart.add(appleItem);
		ControlBarcodeScannerListener ourScannerListener = new ControlBarcodeScannerListener(); 
		DoItYourselfStation ourStation = new DoItYourselfStation();
		// register our listener to our scanner 
		ourStation.scanner.register(ourScannerListener);
		
		testCustomer.useStation(ourStation);
		testCustomer.selectNextItem();
		testCustomer.scanItem();
		Item ourItem;		
		
		
	}
	
	public void addItem(int choice, Item item, DoItYourselfStation station) {
		if(choice == 1) 
			addItemScan((BarcodedItem) item, station);
		if(choice == 2) 
			addItemText();
		if(choice == 3) 
			addItemBrowse();
		else System.out.printf("Invalid choice"); // default choice choice be addItemScan

		// actually adding the item to the bagging area
		station.baggingArea.add(item);
		
			

	}
	

	/*
	* addItemScan() is the method we are fully developing
	* @param item and station, same as in addItem()
	* 
	* @throws NullPointerSimulationException
	*             if the item or station are null
	*/
	
	void addItemScan(BarcodedItem item, DoItYourselfStation station) {
		if(item == null)
			throw new NullPointerSimulationException("item");
		if(station == null)
			throw new NullPointerSimulationException("station");
		
		station.plugIn();
		station.turnOn();
		
		// Continue trying to scan the item until it works
		boolean successfulScan = false;
		while (successfulScan == false) {
			System.out.println("scan failed please try again");
			station.scanner.scan(item);
		}	
		
		
		System.out.println("Please place your item in the bagging area");
		
				
	}
	
	
	
	
	
	void addItemText() {}
	void addItemBrowse() {}
	
	public void pay(int choice, Card card, DoItYourselfStation station) {
		if(choice == 1) payCredit(card, station);
		if(choice == 2) payCash();
		if(choice == 3) payCrypto();
		else System.out.printf("Invalid choice");
	}
	
	void payCredit(Card card, DoItYourselfStation station) {
		
	}
	
	void payCash() {}
	void payCrypto() {}
}
