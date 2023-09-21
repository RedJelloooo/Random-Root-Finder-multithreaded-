import java.util.Arrays;

/**
 * This is a class to create a 3 element tuple to insert into the input buffer
 */
public class triple {
    private final int[] array; // creates array for the elements

    /**
     * creates a 3 element tuple in the form of an integer array
     * @param a - first number
     * @param b - second number
     * @param c - third number
     */
    triple(int a, int b, int c){
        this.array = new int[]{a, b, c};
    }

    /**
     * gets the first element of the triple tuple
     * @return - a, first element
     */
    public int get0(){
        return array[0];
    }

    /**
     * returns the second element of the triple tuple
     * @return - b, second element
     */
    public int get1(){
        return array[1];
    }

    /**
     * returns the third element of the triple tuple
     * @return - c, the third element
     */
    public int get2(){
        return array[2];
    }

    /**
     * overrwrites the built-in toString to return the array in string form
     * @return - String of the array
     */
    public String toString(){
        return Arrays.toString(array);
    }

}
