package com.company;

public class Practise extends Subject{
    protected String group;

    /**
     *
     * @param name super-be értéket kap ősnek emgfelelő konstruktor szerint
     * @param credit super-be értéket kap ősnek emgfelelő konstruktor szerint
     * @param room super-be értéket kap ősnek emgfelelő konstruktor szerint
     * @param group csoport 'String group'-ban tároljuk
     */
    public Practise(String name, int credit,String room, String group) {
        super(name,credit,room);
        this.group = group;
    }


    /**
     *
     * @return print
     */
    public String toString(){
        return super.toString() +"\n"+ "Group:  "+ group;

    }

}
