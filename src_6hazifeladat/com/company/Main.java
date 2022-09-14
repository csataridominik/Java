package com.company;

public class Main {

    public static void main(String[] args) {

        BankAccount a = new BankAccount(500);
        BankAccount b = new BankAccount(500);

        BankAccount c = new BankAccount(100);
        BankAccount d = new BankAccount(101);


        BankTransfer t = new BankTransfer(a,b,300);
        BankTransfer t2 = new BankTransfer(c,d, 100);
        t.start();
        t2.start();

        //először ez fog lefutni, mert a többiben van Thread.sleep(1000)
        //Itt hibát kell dobnia, mert az első thread még nem futott le, és még nincs 800 a bankszámlán
        a.BankAccountMaintenance(800);

        //Ez még lefut
        d.BankAccountMaintenance(1);

        //Ez hamarabb fog lefutni mint a t2.start(), így ott már hibát fog dobni
        d.BankAccountMaintenance(1);

        //ok
        BankAccount e = new BankAccount(1000);
        BankAccount f = new BankAccount(1000);

        BankTransfer t3 = new BankTransfer(e,f,300);
        t3.start();
        e.BankAccountMaintenance(10);
        f.BankAccountMaintenance(100);



    }
}
