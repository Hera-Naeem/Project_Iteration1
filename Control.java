package com.diy.control;

import java.io.IOException;
import java.util.Scanner;

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
	
	
	// This main function is used to demo the control software
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
	
	// Total Amount of payment due
	double totalAmountDue;
	
	public void addItem(int choice, Item item, DoItYourselfStation station) {
		/*
		 * The Use Case Scenario:
		 * 1. (Abstract use case) Details about the item to add must be provided to the System.
		 * 2. System: Blocks the self-checkout station from further customer interaction.
		 * 3. System: The expected weight in the Bagging Area is updated.
		 * 4. System: Signals to the Customer I/O to place the item in the Bagging Area.
		 * 5. Bagging Area: Signals the weight change from the added item.
		 * 6. System: Unblocks the station.
		 */
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
		/*
		 * The Use Case Scenario:
		 * 1. Laser Scanner: Detects a barcode and signals this to the System.
		 * 2. System: Blocks the self-checkout station from further customer interaction.
		 * 3. System: Determines the characteristics (weight and cost) of the product associated with the barcode.
		 * 4. System: Updates the expected weight from the Bagging Area.
		 * 5. System: Signals to the Customer I/O to place the scanned item in the Bagging Area.
		 * 6. Bagging Area: Signals to the System that the weight has changed.
		 * 7. System: Unblocks the station.
		 */
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
	
	public void pay(Card card, DoItYourselfStation station) throws IOException {
		/*
		 * The Use Case Scenario:
		 * 1. (Abstract use case) The customer indicates the mode of payment that they want to use.
		 * 2. (Abstract use case) The customer provides payment, signalling this to the System.
		 * 3. System: The amount due is reduced by the payment.
		 * 4. System: Signals to the Customer I/O the remaining amount due.
		 * 5. Customer I/O: Update the amount due.
		 * 6. Customer I/O: Ready for additional customer interaction.
		 */
		
		
		Scanner input = new Scanner(System.in);
		while (totalAmountDue > 0) {
			// (4.)
			System.out.printf("Total amount due: %d\n", totalAmountDue);
			System.out.println("1. Pay with Credit");
			System.out.println("2. Pay with Cash");
			System.out.println("3. Pay with Crypto");
			System.out.printf("Choose your payment method: ");
			// (1.)
			int choice = input.nextInt();
			// (2.), (3.), (5.), (6.)
			if(choice == 1) payCredit(card, station); 
			if(choice == 2) payCash();
			if(choice == 3) payCrypto();
			else System.out.printf("Invalid choice");
		}
		input.close();
	}
	
	void payCredit(Card card, DoItYourselfStation station) {
		/*
		 * The Use Case Scenario:
		 * 1. Customer I/O: Signals the insertion of a credit card and PIN.
		 * 2. System: Validates the PIN against the credit card.
		 * 3. System: Signals to the Bank the details of the credit card and the amount to be charged.
		 * 4. Bank: Signals to the System the hold number against the account of the credit card.
		 * 5. System: Signals to the Bank that the transaction identified with the hold number should be posted, reducing the amount of credit available.
		 * 6. Bank: Signals to the System that the transaction was successful.
		 * 7. Customer I/O: Updates the amount due displayed to the customer.
		 * 8. <Print Receipt extension point>.
		 */
		System.out.println("Please insert your Credit card");
		//(1.)
		while (//cardReaderListener say !cardInserted) {
			// wait for cardReaderListener to say !cardInserted
		}
		//(2.)
		// Already done by card.CardInsertData()
		//(3.)
		// We have to make an Instance of a Bank (most likely the CardIssuer of the card to be used/tested)
		//(4.), (5.), (6.)
		// In the bank instance (CardIssuer), there should be the methods to do this already
		// (7.)
	}
	
	void payCash() {}
	void payCrypto() {}
}
