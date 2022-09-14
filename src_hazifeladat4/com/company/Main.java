package com.company;

public class Main {

    public static void main(String[] args) throws Exception {

            Sokszogek[] sokszogek_ = new Sokszogek[6];

/*
            //kiiratja
            Haromszog temp= new Haromszog(3,4,5);
            sokszogek_[0] =temp;
            System.out.println(sokszogek_[0].toString());
*/


            // 3+1 =4 Hibat dob
            Haromszog temp2= new Haromszog(3,4,1);
            sokszogek_[1] = temp2;
            System.out.println(sokszogek_[1].toString());



            //44 túl nagy, hibát dob
            Haromszog temp3= new Haromszog(3,44,5);
            sokszogek_[2] = temp3;
            System.out.println(sokszogek_[2].toString());

            //lefut
            float[] temp_arr = {1,2,3,4};
            Negyszog temp5 = new Negyszog(temp_arr);
            sokszogek_[3] = temp5;
            System.out.println(sokszogek_[3].toString());


            //negativ értékre hibát dob konsturktorba
            float[] temp_arr_2 = {1,2,3,-4};
            Negyszog temp6 = new Negyszog(temp_arr);
            sokszogek_[4] = temp6;
            System.out.println(sokszogek_[4].toString());


        float[] temp_arr_3 = {1,2,3,700};
        Negyszog temp7 = new Negyszog(temp_arr);
        sokszogek_[5] = temp6;
        System.out.println(sokszogek_[5].toString());


        //kor
            Kor kor = new Kor(3);
            System.out.println(kor.toString());

            // hiba
            try {
                Kor kor2 = new Kor(-1);
                System.out.println(kor2.toString());
            } catch (Exception ignored){

            }


    }
}
