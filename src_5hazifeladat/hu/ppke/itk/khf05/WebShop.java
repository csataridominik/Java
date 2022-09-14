package hu.ppke.itk.khf05;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class WebShop {
    //Az összes rendelést(WebShopOrder) tárolja, ami még nem került kiküldésre

    private List<WebshopOrder>   orders;
    //TODO adattagok meghatározása, adatstruktúra kiválasztása,
    //TODO megfelelő függvényekhez amik kivételt dobnak, hozzáírni az UnknownOrderException-t

    /**
     * inkrementáljuk minden új rendelésnél, 0-tól kezd
     */
    private int count;

    /**
     * Konstruktor, inicilaizálja count privát mezőket és orders értékeket
     */
    public WebShop(){
        //SortedMap<Integer,WebshopOrder> orders =new TreeMap<Integer, WebshopOrder>();
        List<WebshopOrder> temp= new LinkedList<WebshopOrder>();
        this.orders = temp;


        count =0;
    }

    /**
     *
     * @param priority
     * @param prepaid megadott paramétereker alapján készít egy WebshopOrder-t
     * @return id-va tér vissza
     */
    public Integer addOrder(Integer priority, boolean prepaid){

        WebshopOrder temp = new WebshopOrder(priority, count,prepaid);
        count+=1;

        orders.add(temp);

        orders =orders.stream().sorted(Comparator.comparingInt(WebshopOrder::getPriority)).collect(Collectors.toList());

        return count;
    }

    /**
     *
     * @param orderId
     * @param itemName
     * @param unitPrice
     * @param amount
     * paraméterekkel létrehoz egy új Item-et melyet a kiválaszott(orderId szerint) hozzáadja a rendeléshez
     */
    public void addItemToOrder(Integer orderId, String itemName, Integer unitPrice, Integer amount){
        final Integer[] indikator = {0};
        orders.stream().forEach(e -> {
            if(e.getOrderId() == orderId) {
                e.addItem(itemName,unitPrice,amount);
                indikator[0] =1;
            }


            if(indikator[0].equals(0)) {
                try {
                    throw new Exception();
                } catch (Exception ex) {
                    System.err.println("OrderId could not be found: " + orderId + "\n");
                }
            }


                });

    }


    /**
     * kiírat
     */
    public void printOrders(){
        orders.stream().forEach(e-> {
            e.printOrder();
            System.out.println("\n");
        });
        //TODO összes rendelés kiírása console-ra
    }

    /**
     *
     * @param id ezt iratja ki
     */
    public void printOrder(Integer id){

        orders.stream().forEach(e-> {
            if(id == e.getOrderId()){
                e.printOrder();
            }

        });
        //TODO megfelelő ID-jú rendelés kiírása console-ra
    }

    /**
     *
     * @param paid boolean nek emgfelelve irat ki
     */
    public void printOrders(boolean paid){
        orders.stream().forEach(e-> {
            if(e.getPrepaid() ==paid) {
                System.out.println("\n");
                e.printOrder();
            }
        });

    }

    /**
     *
     * @param id orders List<Obj></> ot filterrel kiszűri azokra az adatokra melyek != id-val
     */
    public void shipOrder(Integer id){

        /*
            orders =orders.stream().forEach(e -> {
                if(e.getOrderId() == id ) {
                    orders.remove(e);
                    orders =orders.stream().sorted(Comparator.comparingInt(WebshopOrder::getOrderId)).collect(Collectors.toList());

                    indikator[0] =1;
                }
            });*/

        orders =orders.stream()
                .filter(c -> c.getOrderId() != id)
                .collect(Collectors.toList());

    }
}
