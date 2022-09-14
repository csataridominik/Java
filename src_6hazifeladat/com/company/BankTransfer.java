package com.company;

public class BankTransfer extends Thread{

    private BankAccount B1, B2;
    private int transfer;

    private static Object monitor = new Object();


    /**
     *
     * @param B1 első számla, erre utalunk
     * @param B2 erről utalunk B1-re
     * @param transfer az összeg amit utalni fogunk.
     */
    public BankTransfer(BankAccount B1, BankAccount B2, int transfer){
        this.B1 = B1;
        this.B2 = B2;
        this.transfer = transfer;
        //mindig első inputra a másodikról. (B1+x , B2-x,x)

    }


    /**
     * Thread run() fuction-ja override; Thread.sleep()-el késleltetést szimulálunk, és végrehajtjuk a tranzakciókat, amennyiben helyesek a számlák értékei.
     * Eredményt, műveletet kiírjuk.
     */
    @Override
    public void run() {

            synchronized (monitor) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                if(B2.subtract(transfer)) {
                    B1.set(transfer);


                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        System.out.println("utaló bankszámla: " +B2.getBalance() + "\n"+
                "fogadó bankszámla: " + B1.getBalance()+"\n");

            //}


        //}
    }
}
