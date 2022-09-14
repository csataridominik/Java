package com.company;

public class Kor implements KeruletTeruletSzamithato{
    float Radius;

    /**
     *
     * @param Radius kör sugara
     * @throws ComputeException ha ez a Radius negatív, vagy nulla hibát dobunk.
     */
    public Kor(float Radius) throws ComputeException {

            if(Radius <=0) {
                throw new ComputeException("Radius is zero or negative number, cannot be computed.");
            }


        this.Radius =Radius;
    }

    /**
     *
     * @return kiszámolja a Kerületet
     */

    @Override
    public float computeKerulet()  {
        return (float) (2*Math.PI*this.Radius);
    }

    /**
     *
     * @return kiszámolja a Területet
     */
    @Override
    public float computeTerulet() {
        return (float) (Radius*Radius*Math.PI);
    }

    /**
     *
     * @return kiírja a kör adatait
     */
    public String toString(){
        return "Radius: " + this.Radius + "\n"+ "Terület: " + computeTerulet() + "\n" + "Kerület: " + computeKerulet();
    }
}
