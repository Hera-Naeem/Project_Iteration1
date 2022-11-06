package com.diy.software.test;

import com.diy.hardware.BarcodedProduct;
import com.diy.hardware.DoItYourselfStation;
import com.diy.hardware.external.CardIssuer;
import com.diy.hardware.external.ProductDatabases;
import com.diy.software.Cart;
import com.jimmyselectronics.necchi.Barcode;
import com.jimmyselectronics.necchi.BarcodedItem;
import com.jimmyselectronics.necchi.Numeral;
import com.jimmyselectronics.opeechee.Card;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PayAddItemTest {
    private BarcodedItem item;
    private BarcodedProduct product;
    private Barcode barcode;
    private DoItYourselfStation station;
    private Cart cart;
    private List<BarcodedProduct> addedItems;
    private Card card;
    private Card.CardData data;
    private CardIssuer issuer;
    private Calendar calendar;

    @Before
    public void setup(){
        station = new DoItYourselfStation();

        barcode = new Barcode(new Numeral[] { Numeral.one, Numeral.two, Numeral.three, Numeral.four });
        item = new BarcodedItem(barcode, 1.00);
        product = new BarcodedProduct(barcode, "item", 5, 1.00);
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, product);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 4);

        card = new Card ("credit", "123456789", "John Doe", "123", "1234", true, true);
        issuer = new CardIssuer("", 500);
        issuer.addCardData(card.number, card.cardholder, calendar, card.cvv, 1000.0);

        station.plugIn();
        station.turnOn();

        addedItems = new ArrayList<>();
        cart = new Cart(station, addedItems, 0.00);

        station.scanner.scan(item);
    }

    @After
    public void teardown(){
        station = null;
        cart = null;
    }

    @Test
    public void testTryAddingDuringTransaction() throws IOException {
        station.cardReader.insert(card, "1234");
        station.scanner.scan(item);

        List<BarcodedProduct> addedItems = new ArrayList<>();
        addedItems.add(product);

        assertEquals(addedItems, cart.getItemsAdded());
    }

    @Test
    public void testAmountOwedCorrect() throws IOException {
        station.cardReader.insert(card, "1234");
        station.scanner.scan(item);

        assertEquals(5.0, cart.getAmountOwed(), 0.1);
    }

    @Test
    public void testAddAfterCardRemove() throws IOException {
        station.cardReader.insert(card, "1234");
        station.cardReader.remove();
        station.scanner.scan(item);

        List<BarcodedProduct> addedItems = new ArrayList<>();
        addedItems.add(product);
        addedItems.add(product);

        assertEquals(10.0, cart.getAmountOwed(), 0.1);
        assertEquals(addedItems, cart.getItemsAdded());
    }

    @Test
    public void testClearedCartAfterPayment() throws IOException {
        // Attempt Payment
        station.cardReader.insert(card, "1234");

        List<BarcodedProduct> addedItems = new ArrayList<>();

        assertEquals(addedItems, cart.getItemsAdded());

    }

    @Test
    public void testZeroAmountOwedAfterPayment() throws IOException {
        // Attempt Payment
        station.cardReader.insert(card, "1234");

        assertEquals(0.0, cart.getAmountOwed(), 0.1);
    }

}
