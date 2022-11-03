package com.diy.control;

import com.diy.hardware.DoItYourselfStation;
import com.jimmyselectronics.Item;
import com.jimmyselectronics.opeechee.Card;

public class Control {
	
	
	
	/**
	 * @param choice
	 * 	which of 3 ways will we be adding the item
	 * @param item
	 *            what item are we trying to add
	 * @param station
	 *            at what station is this occurying
	 */
	
	public void addItem(int choice, Item item, DoItYourselfStation station) {
		if(choice == 1) 
			addItemScan(item, station);
		if(choice == 2) 
			addItemText();
		if(choice == 3) 
			addItemBrowse();
		else System.out.printf("Invalid choice");
	}
	
	/*
	* addItemScan() is the method we are fully developing
	* @param item and station, same as in addItem()
	* 
	* @throws NullPointerSimulationException
	*             if the item or station are null
	*/
	
	void addItemScan(Item item, DoItYourselfStation station) {
		if(item == null)
			throw new NullPointerSimulationException("item");
		if(station == null)
			throw new NullPointerSimulationException("station");
	}
	
	void addItemText() {}
	void addItemBrowse() {}
	
/*-------------------------------------------------------------------------------------------------------
	
	/**
	 * @param choice
	 * 	which of 3 ways will we be adding the item
	 * @param card
	 *            what card are we using
	 * @param station
	 *            at what station is this occurring
	 * @throws IOException 
	 */
	
	public void pay(int choice, Card card, DoItYourselfStation station) throws IOException {
		if(choice == 1) payCredit(card, station);
		if(choice == 2) payCash();
		if(choice == 3) payCrypto();
		else System.out.printf("Invalid choice");
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
