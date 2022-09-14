package hu.ppke.itk.khf05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException{
        //TODO parancsok beolvasása, értelmezése és a WebShop megfelelő függvényeinek meghívása

        WebShop temp_WebShop = new WebShop();

        while(true) {
            String name = new BufferedReader(
                    new InputStreamReader(System.in)).readLine();


            if (name.equals("")) {
                //System.out.println("Out of Input");
                break;
            }


            /**
             * Parancsok szerint szétválasztja, hibát dob ha nem értelmezhető a commandline
             */
            try {
                    if(name.matches( "L")){
                        temp_WebShop.printOrders();
                    } else if(name.matches("L P")){
                        temp_WebShop.printOrders(true);
                    } else if(name.matches("L N") ){
                        temp_WebShop.printOrders(false);
                    } else if(name.matches("S\s[0-9]+") ){
                        String[] str = name.split(" ");
                        temp_WebShop.shipOrder(Integer.valueOf(str[1]));

                    } else if(name.matches("L\s[0-9]+")){
                        String[] str = name.split(" ");
                        temp_WebShop.printOrder(Integer.valueOf(str[1]));

                    }else if(name.matches("N\s([0-9]+)\s[0-1]")) {
                        String[] str = name.split(" ");


                        if(Integer.valueOf(str[2] )==1) {
                            temp_WebShop.addOrder(Integer.valueOf(str[1]),true);
                        } else {
                            temp_WebShop.addOrder(Integer.valueOf(str[1]),false);
                        }



                        //System.out.println(name);
                    } else if(name.matches("A\s([0-9]+)\s([a-zA-Z]+)\s([0-9]+)\s([0-9]+)")) {
                        String[] str = name.split(" ");
                        temp_WebShop.addItemToOrder(Integer.valueOf(str[1]),str[2], Integer.valueOf(str[3]), Integer.valueOf(str[4]));

                    } else {
                        throw new UnknownOrderException("The given command is incorrect: "+ " \" " +name +" \" ");
                    }


                } catch (UnknownOrderException e) {
                        e.printStackTrace();
                }
            }




    }

}
