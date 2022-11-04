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
		
		
		//  Creating a product database with a barcoded item: an apple, orange banana
		
		Barcode myAppleBarcode = new Barcode(new Numeral[] { Numeral.one, Numeral.two, Numeral.three, Numeral.four }); // 1234
		// constructor is BarcodedProduct(barcode, name, price, weight)
		BarcodedProduct apple = new BarcodedProduct(myAppleBarcode, "apple", 2, 100); 
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(myAppleBarcode, apple); // adding our barcoded product to the database
		
		Barcode myOrangeBarcode = new Barcode(new Numeral[] { Numeral.one, Numeral.one, Numeral.two, Numeral.two }); // 1122
		BarcodedProduct orange = new BarcodedProduct(myOrangeBarcode, "orange", 2, 100); 
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(myOrangeBarcode, orange); // adding our barcoded product to the database
				
		Barcode myBananaBarcode = new Barcode(new Numeral[] { Numeral.two, Numeral.two, Numeral.three, Numeral.three }); // 2233
		BarcodedProduct banana = new BarcodedProduct(myBananaBarcode, "banana", 2, 100); 
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(myBananaBarcode, banana); // adding our barcoded product to the database
		
		
		
		// creating ITEMS of our barcodedProducts apple, orange, banana
		
		BarcodedItem appleItem = new BarcodedItem(myAppleBarcode, 100);  // make a barcoded item out of our barcoded product
		BarcodedItem orangeItem = new BarcodedItem(myOrangeBarcode, 110);  // make a barcoded item out of our barcoded product
		BarcodedItem bananaItem = new BarcodedItem(myBananaBarcode, 120);  // make a barcoded item out of our barcoded product

		// instantiating a customer (testCustomer) and adding the apple, orange, and banana to their cart
		
		Customer testCustomer = new Customer();  
		testCustomer.shoppingCart.add(appleItem);
		testCustomer.shoppingCart.add(orangeItem);
		testCustomer.shoppingCart.add(bananaItem);

		
		//instantiating a station for our use
		DoItYourselfStation ourStation = new DoItYourselfStation();
		
		// instantiate listeners
		ControlBarcodeScannerListener ourScannerListener = new ControlBarcodeScannerListener(null); 
		ControlElectronicScaleListener ourScaleListener = new ControlElectronicScaleListener();
		ControlCardReaderListener ourCardReaderListener = new ControlCardReaderListener();
		
		
		// registering the listeners for each of our stations needs
		ourStation.scanner.register(ourScannerListener);	// for add by scanning
		ourStation.baggingArea.register(ourScaleListener);	// for add by scanning 
		ourStation.cardReader.register(ourCardReaderListener);	// for pay by credit 

		
		//--------------------------------------- I feel pretty confident in the set up up until this point.
		// not sure what is happening right below with the test customer and how that works-catalina
		
		// the test customer will use and scan the specific instances of our station and apple
		testCustomer.useStation(ourStation);
		testCustomer.selectNextItem();
		testCustomer.scanItem();
		
	} // end of main function here
	
	
	
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
		
		boolean contShopping = false;		
		Scanner input = new Scanner(System.in);

		while (!contShopping) {
			System.out.println("How would you like to add your next item?");
			System.out.println("1. Pay with Credit");
			System.out.println("2. Pay with Cash");
			System.out.println("3. Pay with Crypto");
			System.out.printf("Choose your payment method: ");
			int additionChoice = input.nextInt(); // storing customer desired payment method in variable choice
			
			if(additionChoice == 1) 
				addItemScan((BarcodedItem) item, station);
			if(additionChoice == 2) 
				addItemText();
			if(additionChoice == 3) 
				addItemBrowse();
			else System.out.printf("Invalid choice"); // default choice choice be addItemScan
		}
		input.close();

		
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
		//while (/*cardReaderListener say !cardInserted*/) {
			// wait for cardReaderListener to say !cardInserted
		//}
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
