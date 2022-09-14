package com.company;

public class Negyszog extends Sokszogek implements KeruletSzamithato{
    float[] oldalak;

    /**
     *
     * @param oldalak oldalak float[]
     * @throws ComputeExceptionRectangle Konstruktor kivételt dob, ha nem értelmezhető számoakt adunk meg(negatív, nulla, túl nagy)
     */
    public Negyszog(float[] oldalak) throws ComputeExceptionRectangle {
        this.oldalak =oldalak;

        if(check()) {
            throw new ComputeExceptionRectangle("One of the sides is too long compared to others, cannot be computed.");
        }
    }

    /**
     *
     * @return Formulának megfelelő értékek, melyek igazoljak, hogy  megadott oldalak képezhetnek négyszöget hamis értékkel térek vissza.
     */
    public boolean check() {
        float sum =0;
        float sum2 =0;

        int max_index =0;

        for (int j = 0; j < oldalak.length; j++) {
            if(oldalak[j]>oldalak[max_index]) {
                max_index = j;
            }

        }

        for (int i = 0; i < oldalak.length; i++) {
            if(max_index == i) {
                continue;
            }

            sum += Math.pow(oldalak[i],2);
            sum2 += Math.pow(oldalak[i],4);
        }


        if(sum > Math.pow(oldalak[max_index],2)/3 && sum2 >= Math.pow(oldalak[max_index],4)/27) {
            return false;
        } else {
            return true;
        }
    }


    /**
     *
     * @return interface implementálás az osztályban, Kerületet számol, ha tud, ha nem kivételt dob és catch-el
     */
    @Override
    public float computeKerulet()  {
        int sum =0;

        try {
            if(check()) {
                throw new ComputeExceptionRectangle("One of the sides is too long compared to others, cannot be computed.");
            } else {
                for (int i = 0; i < oldalak.length; i++) {
                    sum += oldalak[i];
                }

            }

        } catch (ComputeException e){

        }
        return sum;
    }

    /**
     *
     * @return kiírja az soztályt ha tudja, ha nem catch-el.
     */
    public String toString() {

            String temp = "[";
            for (int i = 0; i < oldalak.length; i++) {
                temp += oldalak[i];
                temp +=" ";
            }
            temp += "]";
            return temp  + "\n" + "Kerulet: "+ this.computeKerulet();
    }

}
