import java.util.Arrays;

/**
 * class to make a two element tuple used for returning the solved roots
 */
public class duo {
    private final double[] array;

    /**
     * constructor to create the two element tuple, called a duo
     * @param a - first root
     * @param b - second root
     */
    duo(double a, double b){
        this.array = new double[]{a, b};
    }

    /**
     * gets the first root of the tuple
     * @return - first root
     */
    public double get0(){
        return array[0];
    }

    /**
     * gets the second root of hte tuple
     * @return - the second root
     */
    public double get1(){
        return array[1];
    }


    /**
     * to String method to overwrite and actually print out the array
     * @return - the array in string form
     */
    public String toString(){
        return Arrays.toString(array);
    }

}
