package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Class típus egy osztályhoz rendelhet időt, konstruktorba megadva, delete() metódusa jelzi, hgoy a kurzusnak van e meghatározott ideje, vagy nincs
 */
class Class {
    protected Day day;
    protected int fromTime;
    protected int toTime;
    protected boolean inUse;

    Class(Day day,int fromTime, int toTime) {
        this.day=day;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.inUse =true;
    }

    public void delete() {
        this.inUse =false;
    }
     public boolean getInUse(){
        return this.inUse;
     }

}

public abstract class Subject {
    protected String name;
    protected String room;
    protected int credit;
    /**
     * Tanárok és Diákok listában
     */
    protected List<String> Teachers;
    protected List<String> Students;
    protected int limit;
    Class classTime;

    /**
     *  Konstruktor, a class csak gyermekeiben példányosítható
     * @param name Kurzus neve
     * @param credit kredit érték
     * @param room szoba
     */
    public Subject(String name, int credit, String room){
        this.name = name;
        this.room = room;
        this.credit = credit;
        Teachers = new ArrayList<>();
        Students = new ArrayList<>();
        this.limit =0;
    }

    /**
     *
     * @param teacherName Ha még nincs megjelölve a tanár a listában, hozzáadjuk
     * @throws Exception Ha már szerepel a tanár neve hibaüzenetet dob, amit catch elünk a main-be
     */
    public void addTeacher(String teacherName) throws Exception {
        if(Teachers.contains(teacherName)) {
            throw new Exception("Teacher already in added: " + teacherName);

        } else {
            Teachers.add(teacherName);
        }
    }

    /**
     *
     * @param teacherName Ezt a nevet megkeresi a tanár listában ha megtalálja törli
     * @throws Exception Ha nem találja teacherName String-et hibaüzenetet dob, catch elünk a main-be
     */
    public void removeTeacher(String teacherName) throws Exception {
        if(Teachers.contains(teacherName)){
            Teachers.remove(teacherName);
        } else {
            throw new Exception("Teacher is not in the class: " + teacherName);

        }
    }

    /**
     *
     * @param Limit beállítható egy maximum létszám
     */
    public void setLimit(int Limit) {
        this.limit = Limit;
    }

    /**
     *
     * @param day nap, enum
     * @param fromTime mikortol van óra
     * @param toTime meddig lehet óra
     * @throws Exception Ha fromTime, toTime nem 8-20 óra között van hibaüzenet dobunk
     */
    public void addClass(Day day, int fromTime, int toTime) throws Exception {
        if(fromTime <8 || fromTime >=20) {
            throw new Exception("Class 'fromTime' cannot be implemented, out of range:" + fromTime);
        } else if(toTime <=8 || toTime >20) {
            throw new Exception("Class 'toTime' cannot be implemented, out of range:" + toTime);
        } else {
            Class temp = new Class(day,fromTime,toTime);
            this.classTime = temp;
        }

    }

    /**
     *
     * @param day melyik időpont szabadul fel
     * @param fromTime mikortol szabadul fel
     * @param toTime melyik időpont szabadul fel
     */
    public void removeClass(Day day, int fromTime, int toTime) {
        classTime.delete();
    }


    /**
     *
     * @param studentName Diákokat tartalmazó listához hozzáadja a diákot ha még nem tartalmazta
     * @throws Exception ha már tartalmazta, hibát dobunk
     */
    public void apply(String studentName) throws Exception {
        if(Students.contains(studentName)) {
            throw new Exception("Student already in class: " + studentName);
        } else {
            if(this.limit != 0 && Students.size() >=limit) {
                throw new Exception("Classes headcount is at limits: " + this.limit);
            } else {
                Students.add(studentName);
            }
        }
    }

    /**
     *
     * @param studentName órát leadó diák
     * @throws Exception Ha már leadta vagy fel se vette, hibát dobunk
     */
    public void leave(String studentName) throws Exception {
        if(Teachers.contains(studentName)){
            Teachers.remove(studentName);
        } else {
            throw new Exception("Student is not in the class: " + studentName);

        }

    }

    /**
     *
     * @return szoba
     */
    public String getRoom() {
        return room;
    }

    /**
     *
     * @return név
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return print
     */
    public String toString() {
        System.out.println("ClassName: " +name + "\n" +
                "Credit: " + credit+ "\n" +
                "Room: " + room + "\n" +
                "Headcount: " + Students.size()+ "/" + limit + "\n"+
                "Students: "+ Students.toString() + "\n"+
                "Teachers: "+ Teachers.toString());


        return  "ClassName: " +name + "\n" +
                "Credit: " + credit+ "\n" +
                "Room: " + room + "\n" +
                "Headcount: " + Students.size()+ "/" + limit + "\n"+
                "Students: "+ Students.toString() + "\n"+
                "Teachers: "+ Teachers.toString();
    }





}
