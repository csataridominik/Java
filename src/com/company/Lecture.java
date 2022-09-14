package com.company;

import java.util.ArrayList;
import java.util.List;

public class Lecture extends Subject{
    boolean online;
    List<String> recommendedLiterature;

    /**
     *
     * @param name super-be értéket kap ősnek emgfelelő konstruktor szerint
     * @param credit super-be értéket kap ősnek emgfelelő konstruktor szerint
     * @param room super-be értéket kap ősnek emgfelelő konstruktor szerint
     * @param online Online vagy élő ben van oktatás; 'online' bool-ban tároljuk
     */
    public Lecture(String name, int credit, String room, boolean online) {
        super(name, credit, room);
        this.online = online;
        recommendedLiterature = new ArrayList<>();
    }

    /**
     *
     * @param literature ajanlott szakirodalom, listához tűzzük
     * @throws Exception ha már benne volt, hibát dobunk
     */
    public void addLiterature(String literature) throws Exception {
        if(recommendedLiterature.contains(literature)){
            recommendedLiterature.remove(literature);
        } else {
            throw new Exception("recommended literature already added: " + literature);
        }
    }

    /**
     *
     * @param literature szakirodalom törlése
     * @throws Exception ha nem volt benne a lsitában, hibát dobunk
     */
    public void deleteLiterature(String literature) throws Exception {
        if(recommendedLiterature.contains(literature)){
            recommendedLiterature.remove(literature);
        } else {
            throw new Exception("Literature is not in the class: " + literature);
        }
    }

    /**
     *
     * @return print
     */
    public String toString() {

            return  super.toString()+ "\n" + "online:" + "\n" + "Recommended Literature: " + recommendedLiterature.toString();
    }

}
