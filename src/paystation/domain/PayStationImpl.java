package paystation.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.*;
import java.util.HashMap.*;
/*
    Pat Looft   - tug16392@temple.edu
    Chris Huang - tuf61096@temple.edu
 */

/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
public class PayStationImpl implements PayStation {
    
    private int insertedSoFar;
    private int timeBought;

    private int lastPayment;

    public HashMap<Integer,Integer> record = new HashMap<>();

    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        switch (coinValue) {
            case 5:
                record.put(5,1);
                lastPayment = 5;
                break;
            case 10:
                record.put(10,1);
                lastPayment = 10;
                break;
            case 25:
                record.put(25,1);
                lastPayment = 25;
                break;
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }

        insertedSoFar += coinValue;
        timeBought = insertedSoFar / 5 * 2;
    }

    @Override
    public int readDisplay() {
        return timeBought;
    }

    //money only collected after call to this
    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        lastPayment = timeBought;
        record.clear();
        reset();
        return r;
    }


    /** Cancel the present transaction. Resets the paystation for a
     * new transaction.
     * @return A Map defining the coins returned to the user.
     * The key is the coin type and the associated value is the
     * number of these coins that are returned.
     * The Map object is never null even if no coins are returned.
     * The Map will only contain only keys for coins to be returned.
     * The Map will be cleared after a cancel or buy.
     */

    @Override
    public Map cancel() {
        //key = coin type, value = total quantity
        Map temp = (Map)record.clone();
        insertedSoFar -= lastPayment;
        timeBought = insertedSoFar / 5 * 2;
//        timeBought = 0;
//        insertedSoFar = 0;
        record.clear();
        return temp;
    }

    @Override
    public int empty(){
        int temp = insertedSoFar;
        lastPayment = 0;
        insertedSoFar = 0; //set total to 0
        return temp; //return last call
    }
    
    private void reset() {
        timeBought = insertedSoFar = 0;
    }

}
