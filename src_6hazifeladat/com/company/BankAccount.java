package com.company;

import static java.lang.Math.abs;

public class BankAccount{

    private int balance;

    private static Object monitor = new Object();

    /**
     *
     * @param balance összeg értéket átadjuk konstruktorba. ha negatív hibát dob és catch-el.
     */
    public BankAccount(int balance){

        try {
            if(balance <0) {
                throw new Exception();
            } else {
                this.balance =balance;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Balance cannot be constructed, as the given number is negative value: " +balance );
        }

    }


    /**
     *
     * @return balance összeg értéke
     */
    public int getBalance() {
        return balance;

    }

    /**
     *
     * @param x 'x' értéket levon a balance bol ha tud, ha nem Exception()-t dob és catch-el. monitorral használhato, szálbiztosságból
     */
    public void BankAccountMaintenance(int x) {
        synchronized (monitor) {
            try {
                if (x > balance) {
                    throw new Exception();
                } else {
                    balance -= x;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Bank Account Maintenance cannot be paid. "
                        + "\n" +"Balance: "+ balance
                        + "\n" +"Value of payment: " + x);
            }
        System.out.println("Utalás történt. Utalás összege: " + x+"\n");


        }

    }

    /**
     *
     * @param x érétkű összeget levon a balancebol ha tud ha nem Exception() dob és catchel
     * @return igaz hamis értk, mely bisszajelzés a BankTransfer osztálnyak. Igaz ha letudott vonni, hamis ha nem
     * monitorral működik szálbiztosságból
     */
    public boolean subtract(int x) {
        synchronized(monitor) {
            try {
                if(x > balance || x<0) {
                    throw new Exception();
                } else {
                    balance -=x;
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Money cannot be transfered, the balance on the account is too small: "+ balance);
            }
            return false;
        }

    }

    /**
     *
     * @param x x értéket hozzáad a balance hoz ha x>0, ha nem Excptio()-t dob és catch-el.
     *          monitorral elérhető szálbiztosságból
     */
    public void set(int x){
        synchronized (monitor){
            try {
                if(x <=0) {
                    throw new Exception();
                } else {
                    balance+=x;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Balance cannot be set, as the given number is zero or negative value: " +x );
            }

        }

    }

}
