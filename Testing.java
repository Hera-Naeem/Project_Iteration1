package com.diy.software.test;

import com.diy.hardware.BarcodedProduct;
import com.diy.hardware.DoItYourselfStation;
import com.diy.hardware.external.ProductDatabases;
import com.diy.software.Cart;
import com.jimmyselectronics.necchi.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AddItemTest {

    private BarcodedItem item;
    private BarcodedProduct product;
    private Barcode barcode;
    private DoItYourselfStation station;
    private Cart cart;
    private List<BarcodedProduct> addedItems;



    @Before
    public void setup(){
        station = new DoItYourselfStation();

        barcode = new Barcode(new Numeral[] { Numeral.one, Numeral.two, Numeral.three, Numeral.four });
        item = new BarcodedItem(barcode, 1.00);
        product = new BarcodedProduct(barcode, "item", 5, 1.00);
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, product);

        station.plugIn();
        station.turnOn();

        addedItems = new ArrayList<>();
        cart = new Cart(station, addedItems, 0.00);
    }

    @After
    public void teardown(){
        station = null;
        cart = null;
    }


    @Test
    public void testBarcodedItemToCart(){
        List<BarcodedProduct> expected = new ArrayList<>();
        expected.add(product);

        station.scanner.scan(item);

        assertEquals(expected, cart.itemsAdded);
    }
    
    @Test
	public void addBarcodedItemTest() {
		System.out.println("In test");
		System.out.println("\nItem scanned");
		systemControl.station.scanner.scan(item);
		System.out.println("\nItem Added to bagging area");
		systemControl.station.baggingArea.add(item);
	}
    
    @Test
	public void getBillTotalTest() {
		systemControl.station.scanner.scan(item);
		systemControl.station.baggingArea.add(item);
        assertTrue(systemControl.getBillTotal());
        
    }

}
