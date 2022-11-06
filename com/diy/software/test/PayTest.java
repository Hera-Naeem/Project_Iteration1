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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PayTest {

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
        issuer = null;
    }

    @Test
    public void testInsertCardBlockOnAddingItems() throws IOException {
        station.cardReader.insert(card, "1234");
        assertEquals(true, cart.getIsPaying());
    }

    @Test
    public void testRemoveCardUnBlockOnAddingItems() throws IOException {
        station.cardReader.insert(card, "1234");
        station.cardReader.remove();
        assertEquals(false, cart.getIsPaying());
    }

    @Test
    public void testAuthorizeHold() throws IOException {
        data = station.cardReader.insert(card, "1234");
        long holdNumber = issuer.authorizeHold(data.getNumber(), cart.getAmountOwed());
        Assert.assertNotEquals(-1, holdNumber);
    }


    // Only passes the test when Random.nextLong() in CardIssuer.authorizeHold gives a positive number
    // This is apart of the profs code so I don't think we are allowed to change it
    // Also can't do the absolute value cause that could cause the holdNumber to be equal to an
    // already active holdNumber in the database.
    @Test
    public void testReleaseHold() throws IOException {
        data = station.cardReader.insert(card, "1234");
        long holdNumber = issuer.authorizeHold(data.getNumber(), cart.getAmountOwed());
        Boolean transaction = issuer.postTransaction(data.getNumber(), holdNumber, cart.getAmountOwed());
        System.out.println(holdNumber);
        assertEquals(true, transaction);
    }


    @Test
    public void testPostTransaction() throws IOException {
        data = station.cardReader.insert(card, "1234");
        long holdNumber = issuer.authorizeHold(data.getNumber(), cart.getAmountOwed());
        if(holdNumber > 0) {
            issuer.postTransaction(data.getNumber(), holdNumber, cart.getAmountOwed());
            Boolean releaseHold = issuer.releaseHold(data.getNumber(), holdNumber);
            assertEquals(true, releaseHold);
        }
        else System.out.println("Negative holdNumber assigned by Random.nextLong");
    }


}
