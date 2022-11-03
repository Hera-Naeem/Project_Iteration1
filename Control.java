package com.diy.control;

import com.diy.hardware.DoItYourselfStation;
import com.jimmyselectronics.Item;
import com.jimmyselectronics.opeechee.Card;

public class Control {
	
	
	
	
	
	public void addItem(int choice, Item item, DoItYourselfStation station) {
		if(choice == 1) 
			addItemScan(item, station);
		if(choice == 2) 
			addItemText();
		if(choice == 3) 
			addItemBrowse();
		else System.out.printf("Invalid choice");
	}
	
	void addItemScan(Item item, DoItYourselfStation station) {
		
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
