package paystation.domain;

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

    Map <Integer, Integer> record = new HashMap<>();

    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        switch (coinValue) {
            case 5:
                record.add(5,1);
                break;
            case 10:
                record.add(10,1);
                break;
            case 25:
                record.add(25,1);
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
        reset();
        return r;
    }

    @Override
    public void cancel() {
        Map <Integer, Integer> record = new HashMap<>();  //key = coin type, quantity returned


    }

    public void empty(){
        int temp = lastPayment;
        lastPayment = 0;
        insertedSoFar = 0; //set total to 0
        return temp; //return last call
    }
    
    private void reset() {
        timeBought = insertedSoFar = 0;
    }

}
