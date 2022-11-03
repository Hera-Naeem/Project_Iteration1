package com.diy.control;

import com.jimmyselectronics.Item;
import com.jimmyselectronics.opeechee.Card;

public class Control {

	public void addItem(int choice, Item item) {
		if(choice == 1) addItemScan();
		if(choice == 2) addItemText();
		if(choice == 3) addItemBrowse();
		else System.out.printf("Invalid choice");
	}
	
	void addItemScan() {
		
	}
	
	void addItemText() {}
	void addItemBrowse() {}
	
	public void pay(int choice, Card card) {
		if(choice == 1) payCredit();
		if(choice == 2) payCash();
		if(choice == 2) payCrypto();
		else System.out.printf("Invalid choice");
	}
	
	void payCredit() {
		
	}
	
	void payCash() {}
	void payCrypto() {}
}
