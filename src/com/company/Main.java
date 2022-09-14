package com.company;


//import javax.security.auth.Subject;



public class Main {

    public static void main(String[] args) throws Exception {

        try {
            Subject Course01 = new Lecture("Math",5,"101",false);
            Course01.addClass(Day.Monday,8,10);
            Course01.addTeacher("Kovacs Peter");
            Course01.addTeacher("Kovacs Ferenc");
            Course01.addTeacher("Kovacs Lajos");
            Course01.addTeacher("Horvath Anett");

            Course01.setLimit(10);
            //hibaüzenet:
            //Course01.addTeacher("Horvath Anett");
            //
            Course01.apply("Student01");
            Course01.apply("Student02");
            Course01.apply("Student03");
            Course01.apply("Student04");
            Course01.apply("Student05");

            //hibaüzenet:
            //Course01.apply("Student05");
            //Course01.leave("Student05");
            //Course01.leave("Student05");
            System.out.println(Course01.toString());
            Subject Course02 = new Practise("Something",3,"001","0A");
            Course02.setLimit(4);
            //Hibaüzenet
            //
            //Course02.addClass(Day.Monday,7,7);
            //
            //Course02.removeTeacher("Remove Reni");


        } catch (Exception e) {
            System.err.println("Error||  " +e);
        }




    }
}
