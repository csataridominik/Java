package hu.ppke.itk.khf05;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class WebshopOrder {
    //Az egy rendeléshez tartozó adatokat és termékeket(OrderItem) tárolja
    private int orderId;
    private int priority;
    private boolean prepaid;

    //SortedMap<String,OrderItem> order;
    List<OrderItem> order;

    Integer sum = 0;
    //TODO adattagok meghatározása, adatstruktúra kiválasztása,
    //TODO comparable/comparator

    /**
     *
     * @param priority prioritás
     * @param orderId ID
     * @param prepaid false/true ki van e fizetve,
     *                konstruktor
     */
    public WebshopOrder(Integer priority, Integer orderId, boolean prepaid){
        this.priority = priority;
        this.orderId = orderId;
        this.prepaid = prepaid;

        //SortedMap<String,OrderItem> order =new TreeMap<String, OrderItem>();

        List<OrderItem> temp= new LinkedList<OrderItem>();
        this.order = temp;


    }

    /**
     *
     * @param itemName OrderItem neve
     * @param unitPrice OrderItem unitprice a
     * @param amount OrderItem menniysége
     */
    public void addItem(String itemName, Integer unitPrice, Integer amount){
        OrderItem temp = new OrderItem(itemName,unitPrice,amount);

        order.add(temp);
        order = order.stream().sorted((o1,o2) -> o1.getItemname().compareTo(o2.getItemname())).collect(Collectors.toList());
        // order = order.stream().sorted(Comparator.comparing(o -> o.Itemname)).collect(Collectors.toList()); ez is jo

        order.forEach(e -> System.out.println(e.getItemname()));

    }

    /**
     * kiirat
     */
    public void printOrder(){
        System.out.println("Order data(priority: "+priority+") ID: " +
                orderId+" "+ "prepaid: " +prepaid+ "\n");
        order.forEach( e -> System.out.println(e.toString()));

        //TODO rendelés adatainak,valamint a termékek adatainak kiírása a console-ra
    }
    public Integer getTotal(){

        order.forEach( e-> {
            sum += e.getAmount() * e.getUnitPrice();
        });
        Integer temp = sum;
        sum =0;
        return temp;
    }

    public boolean getPrepaid(){
        return prepaid;
    }


    public int getPriority() {
        return priority;
    }

    public int getOrderId() {
        return orderId;
    }
}
