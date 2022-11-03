package com.diy.control;

import java.io.IOException;
import java.util.Scanner;

import com.diy.hardware.DoItYourselfStation;
import com.jimmyselectronics.Item;
import com.jimmyselectronics.necchi.BarcodedItem;
import com.jimmyselectronics.opeechee.Card;

import ca.ucalgary.seng300.simulation.NullPointerSimulationException;

public class Control {
	
	private double totalDue;
	
	/**
	 * @param choice
	 * 	which of 3 ways will we be adding the item
	 * @param item
	 *            what item are we trying to add
	 * @param station
	 *            at what station is this occurring
	 */
	
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
			station.scanner.scan(item);
		}	
	}
	
	void addItemText() {}
	void addItemBrowse() {}
	
/*-------------------------------------------------------------------------------------------------------
	
	/**
	 * 	which of 3 ways will we be adding the item
	 * @param card
	 *            what card are we using
	 * @param station
	 *            at what station is this occurring
	 * @throws IOException 
	 */
	
	public void pay(Card card, DoItYourselfStation station) throws IOException {
		Scanner input = new Scanner(System.in);
		// Prompts user to pay until totalDue is 0
		// only using System I/O at the moment
		while (totalDue > 0) {
			System.out.printf("Total amount due: %d\n", totalDue);
			System.out.println("1. Pay with Credit");
			System.out.println("2. Pay with Cash");
			System.out.println("3. Pay with Crypto");
			System.out.printf("Choose your payment method: ");
			int choice = input.nextInt();
			if(choice == 1) payCredit(card, station);
			if(choice == 2) payCash();
			if(choice == 3) payCrypto();
			else System.out.printf("Invalid choice");
		}
		input.close();
	}
	
	/*
	* payCredit() is the method we are fully developing
	*/
	void payCredit(Card card, DoItYourselfStation station) throws IOException {
		Scanner input = new Scanner(System.in);
		System.out.printf("Enter your pin: ");
		String pin = input.toString();
		station.cardReader.insert(card, pin);
		input.close();
	}
	
	void payCash() {}
	void payCrypto() {}
}
