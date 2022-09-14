package hu.ppke.itk.khf05;

public class OrderItem {
    //Egy termék adatait tárolja
    private String Itemname;
    private int UnitPrice;
    private int Amount;
    //TODO adattagok meghatározása, adatstruktúra kiválasztása,
    //TODO comparable/comparator
    public OrderItem(String Itemname,int  UnitPrice, int Amount){
        this.Itemname = Itemname;
        this.UnitPrice = UnitPrice;
        this.Amount = Amount;
    }

    public String getItemname() {
        return Itemname;
    }

    public int getAmount() {
        return Amount;
    }

    public int getUnitPrice() {
        return UnitPrice;
    }


    @Override
    public String toString(){

        return "Itemname: " + Itemname + "\n"+
                "Unitprice: " + UnitPrice + "\n"+
                "Amount: " + Amount + "\n";
    }

}
