package com.company;

import java.util.Arrays;
import java.lang.Math;


public class Haromszog extends Sokszogek implements KeruletTeruletSzamithato{

    private float[] oldalak;

    /**
     * Megnézzük az oldalakra, hogy teljesül-e a háromszög-egyenlőtlenség, igaz/hamis értékkel térünk vissza.
     * Ha nem ltezik háromszög, akkor true.
     *
     * @return true/false
     */
    public boolean check() {
        float sum =0;

        for (int j = 0; j < oldalak.length; j++) {
            sum += oldalak[j];

        }

        for (int i = 0; i < oldalak.length; i++) {
            if (2 * (oldalak[i]) >= sum) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param oldalak oldalak float[]
     * @throws ComputeException Eception t dobunk és elkapjuk, ha nem jó oldalkat adunk meg(negatív, nulla, túl nagy)
     */
    public Haromszog(float[] oldalak) throws ComputeException {
        try {
            for (float v : oldalak) {
                if (v <= 0) {
                    throw new ComputeException(v + " is negative or zero, polygon cannot be built.");
                }
            }

            this.oldalak = oldalak;
        } catch (ComputeException ignored) {

        }

    }

    /**
     *
     * @param a oldal
     * @param b oldal
     * @param c oldal
     *
     *          Konstruktor 3 számmal
     */
    public Haromszog(float a, float b, float c) {
        try {
            if (a <= 0) {
                    throw new ComputeException(a + " is negative or zero, polygon cannot be built.");
            } else if (b <= 0) {
                throw new ComputeException(b + " is negative or zero, polygon cannot be built.");
            } else if (c<= 0) {
                throw new ComputeException(c + " is negative or zero, polygon cannot be built.");
            }
            float[] temp = new float[3];
            temp[0] =a;
            temp[1] =b;
            temp[2] =c;
            this.oldalak = temp;
        } catch (ComputeException ignored) {
            //this.oldalak = new float[]{0, 0, 0};
        }


    }

    /**
     *
     * @return float érétkben interface-nek megfelelően visszaadja a KEruletet, ha kiszámítható
     */
    @Override
    public float computeKerulet() {
        int sum =0;
        try {
            if(check()) {
                throw new ComputeExceptionTriangle("A given side is too long compared to other sides");
            }
            for (int i = 0; i < oldalak.length; i++) {
                sum += oldalak[i];
            }

        } catch (ComputeException ignored) {

        }

        return sum;
    }

    /**
     *
     * @return float érétkben interface-nek megfelelően visszaadja a Területet, ha kiszámítható, Exception t dob és catch-el ha nem.
     * @throws ComputeException
     */
    @Override
    public float computeTerulet() throws ComputeException {
        float s =0;

            if(check()) {
                throw new ComputeExceptionTriangle("A given side is too long compared to other sides");
            }
            s  = (oldalak[0]+oldalak[1]+oldalak[2])/2;

        return (float) Math.sqrt(s*(s-oldalak[0])*(s-oldalak[1])*(s-oldalak[2]));

    }

    /**
     *
     * @returnkiiratja az osztály adatait, ha hibát talál, catch-el.
     */
    public String toString() {

        String temp = "[";

            for (int i = 0; i < oldalak.length; i++) {
                temp += oldalak[i];
                temp +=" ";
            }
            temp += "]";
        try {
            return  temp + "\n" + "Kerület: "+ this.computeKerulet() + "\n" + "Terület: " + this.computeTerulet();
        } catch (ComputeException e) {
            e.printStackTrace();
            System.err.println("Nem számolható terület, kerület, nem megfelelőek az oldalak: ");
        }


        //visszaadja az array-t a nem megfelelő számokkal
        return temp +" hibás";
    }

}
