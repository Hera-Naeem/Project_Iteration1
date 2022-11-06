package com.diy.software;

import com.diy.hardware.BarcodedProduct;
import com.diy.hardware.DoItYourselfStation;
import com.diy.hardware.external.CardIssuer;
import com.diy.hardware.external.ProductDatabases;
import com.jimmyselectronics.AbstractDevice;
import com.jimmyselectronics.AbstractDeviceListener;
import com.jimmyselectronics.necchi.Barcode;
import com.jimmyselectronics.necchi.BarcodeScanner;
import com.jimmyselectronics.necchi.BarcodeScannerListener;
import com.jimmyselectronics.opeechee.Card;
import com.jimmyselectronics.opeechee.CardReader;
import com.jimmyselectronics.opeechee.CardReaderListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Cart implements BarcodeScannerListener, CardReaderListener {

    private List<BarcodedProduct> itemsAdded;
    private double amountOwed;
    private DoItYourselfStation station;
    private Boolean isPaying = false;


    public List<BarcodedProduct> getItemsAdded() {
        return itemsAdded;
    }

    public void setItemsAdded(List<BarcodedProduct> itemsAdded) {
        this.itemsAdded = itemsAdded;
    }

    public double getAmountOwed() {
        return amountOwed;
    }

    public void setAmountOwed(double amountOwed) {
        this.amountOwed = amountOwed;
    }

    public void setStation(DoItYourselfStation station) {
        this.station = station;
    }

    public Boolean getIsPaying(){
        return this.isPaying;
    }


    /*
    Constructor for Cart
    station is the do it yourself station associated with the cart
    itemsAdded is a list of all products that have been added the cart
    amountOwed is the total price of all the products in the cart
     */
    public Cart(DoItYourselfStation station, List<BarcodedProduct> itemsAdded, double amountOwed){
        this.station = station;
        this.itemsAdded = itemsAdded;
        this.amountOwed = amountOwed;
        this.station.scanner.register(this);
        this.station.cardReader.register(this);
    }


    public DoItYourselfStation getStation(){
        return this.station;
    }

    @Override
    public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) { }

    @Override
    public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) { }

    @Override
    public void turnedOn(AbstractDevice<? extends AbstractDeviceListener> device) { }

    @Override
    public void turnedOff(AbstractDevice<? extends AbstractDeviceListener> device) { }

    @Override
    public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
        if(!isPaying) {
            itemsAdded.add(ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode));
            amountOwed += ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode).getPrice();
        }
        else{ }   // Payment in progress
    }

    @Override
    public void cardInserted(CardReader reader) {
        isPaying = true;  // Prevents adding new items to the cart

    }

    @Override
    public void cardRemoved(CardReader reader) {
        isPaying = false;   // Allows for adding items to the cart again
    }


    // Can't access an issuer from a card, or the database that holds the cards hold data
    // Therefore, need to make a card issuer and create card data for the card passed in
    // Then we can attempt to do a transaction
    // Probably shouldn't work this way but that's all we can do with the hardware.
    @Override
    public void cardDataRead(CardReader reader, Card.CardData data) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 4);
        CardIssuer issuer = new CardIssuer("", 5);
        issuer.addCardData(data.getNumber(), data.getCardholder(), calendar, "123", 20.0);
        long holdNumber = issuer.authorizeHold(data.getNumber(), amountOwed);
        if(holdNumber != -1) {

            // Clear cart and set amount owed to 0.
            this.setAmountOwed(0.0);
            List<BarcodedProduct> newItemsAdded = new ArrayList<>();
            this.setItemsAdded(newItemsAdded);

            Boolean transaction = issuer.postTransaction(data.getNumber(), holdNumber, amountOwed);
            if(transaction == true){

                Boolean releaseHold = issuer.releaseHold(data.getNumber(), holdNumber);
                if(releaseHold == true){
                    //Transaction complete
                }
                else {} // Hold wasn't released
            }
            else{} // Transaction didn't go through
        }
        else{} // Hold wasn't authorized
    }
}
