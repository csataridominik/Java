package hu.ppke.itk.java.first;

/**
 * @author      Dominik CSATARI
 * @version     1.8
 * @since       1.8
 */

class matrix {

    /**
     * n az n*n-es mátrix mérete
     * matrix_values tárolja mátrixba a konstruktorral átadott méretben értékeket.
     */
    private int n;
    private int[][] matrix_values;

    /**
     * @param matrix_values_ mátrix tipussal meghívott konstruktor, átadja az értékeket, ha négyzetes a matrix_values_
     * @return -1-t ad vissza, ha hibás a az argumentumban megadott int[][]
     */
    public matrix(int[][] matrix_values_){

        for (int i = 0; i <matrix_values_.length; i++) {
            if(matrix_values_[i].length != matrix_values_.length) {
                System.out.println("invalid size given to constructor");
                java.lang.System.exit(-1);
            }
        }

        n=matrix_values_.length;
        matrix_values = matrix_values_;


    }

    /**
     *
     * @param initialized matrix típusú class bemásolja az értékeit
     */
    public matrix(matrix initialized){
        if(initialized.get_n() < this.get_n()) {
            System.out.println("invalid size");
            java.lang.System.exit(-1);
        } else {
            this.n =initialized.get_n();
            this.matrix_values =getMatrix_values();
        }


    }

    /**
     *
     * @return private mezős n érték elérése public metódussal
     */
    public int get_n(){
        return n;
    }


    /**
     *
     * @return private mezős matrix_values érték elérése public metódussal
     */
    public int[][] getMatrix_values(){
        return matrix_values;
    }

    /**
     *
     * @return private mezős matrix_values konrét értéke(x,y) elérése public metódussal
     */
    public int getMatrix_values(int x, int y){
        if(x >=n || y >=n) {
            System.out.println("out of range coords:  " + x+"  :  "+y);
            java.lang.System.exit(-1);
        }
        return matrix_values[x][y];
    }


    /**
     *
     * @param rhs másik matrix típus, melyet hozzáadunk az éppen meghívott metódus mátrixához
     */
    public void add(matrix rhs){
        //this := lhs
        if(rhs.get_n() != this.n){
            System.out.println("not the same size, cannot compile");
            java.lang.System.exit(-1);
        }

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j <this.n; j++) {
                matrix_values[i][j] += rhs.getMatrix_values(i,j);
            }
        }
    }

    /**
     *
     * @param rhs másik matrix típus, melyet összeszorzunk az éppen meghívott metódus mátrixával, ha lehet és ez(rhs) van a jobb oldalt
     */
    public void multiplication(matrix rhs){
        int temp[][] = new int[n][n];

        if(rhs.get_n() != n) {
            System.out.println("not the same size, cannot compile");
            java.lang.System.exit(-1);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                for (int k = 0; k < n; k++) {
                    temp[i][j] += matrix_values[i][k]* rhs.getMatrix_values(k,j);
                }

            }
        }
        matrix_values=temp;
    }

    /**
     *
     * @param x egész számú skalár, melylel megszorozzuk a mátrixunk
     */
    public void scalar_multiplication(int x){
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j <this.n; j++) {
                matrix_values[i][j] *=x;
            }
        }
    }

    /**
     *
     * transzponál
     */
    public void transpose(){
        int temp[][] = new int[n][n];

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j <this.n; j++) {
                temp[i][j] = matrix_values[j][i];
            }
        }
        matrix_values =temp;

    }

    /**
     *
     * print
     */
    public void print(){
        for (int j = 0; j <n; j++) {
            String out= "";
            for (int i = 0; i < n; i++) {
                out+= matrix_values[j][i]+" ";
                //System.out.println(col[j][i]+"\r");
            }
            System.out.println(out+" \n");
        }
    }

}


public class Main {

    public static void main(String[] args) {
        int[][] col = new int[3][3];
        for (int j = 0; j <col.length; j++) {

            int[] line = new int[3];
            for (int i = 0; i < line.length; i++) {
                line[i] =i+1;
            }
            col[j] =line;
        }



        matrix a= new matrix(col);
        System.out.println(a.get_n());
        a.print();
        /*

        //TEST1 TRANSPOSE

        int[][] temp= new int[3][3];
        temp = a.getMatrix_values();
        a.transpose();
        a.transpose();



        a.print();
        a.transpose();
        a.print();

        //add
        matrix b=new matrix(col);
        a.add(b);

        a.scalar_multiplication(-2);
        a.print();
*/
        matrix b=new matrix(col);
        a.multiplication(b);
      //  a.print();

        int[][] c= {{3,3,3},{3,3,3},{3,3,3}};
        matrix sec = new matrix(c);


    }
}
