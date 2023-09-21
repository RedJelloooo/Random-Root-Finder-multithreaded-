import java.lang.Runnable;
import java.lang.String;
import java.util.HashMap;

public class Slaves implements Runnable{
    private static inputBuffer inputStream;
    private static outputBuffer outputStream; // references to the buffers
    private static int option; // option for how many roots to solve

    private static final HashMap<Character, Integer> threadTracker = new HashMap<>(10);

    /**
     * creates instance of a slave that solves quadratics
     * with options of 1 or 2 to help the master
     * @param inputStream - input circular buffer
     * @param outputStream - output circular buffer
     * @param option - 30 or 3000 roots
     */
    public Slaves(inputBuffer inputStream, outputBuffer outputStream, int option){
        Slaves.inputStream = inputStream;
        Slaves.outputStream = outputStream;
        Slaves.option = option;
    }

    /**
     * runnable for the slaves to help the master solve either 30 or 3000 roots
     */
    @Override
    public void run() {

        if(option ==1) {
            triple[] arr = new triple[30]; //30 maximum for all possible spaces
            int[] tempArr = {-1, -1, -1}; // dummy numbers

            for (int x = 0; x < inputStream.size(); x++) { // while there are indexes left in the buffer
                try {
                    arr[x] = inputStream.blockingGet(); // gets new array
                    tempArr[0] = arr[x].get0();
                    tempArr[1] = arr[x].get1();
                    tempArr[2] = arr[x].get2();

                    duo math;
                    math = QuadraticFormula(tempArr[0], tempArr[1], tempArr[2]); // solves set of coefficients
                    duo output = new duo(math.get0(), math.get1());

                    outputStream.blockingPut(output); // puts in the output
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        if(option ==2){
            triple[] arr = new triple[3000]; // 3000 for the needed indecision (more than needed)
            int[] tempArr2 = {-1, -1, -1}; // dummy numbers

            for (int x = 0; x < inputStream.size(); x++) { // while there are elements left in the buffer
                try {
                    arr[x] = inputStream.blockingGet();
                    tempArr2[0] = arr[x].get0();
                    tempArr2[1] = arr[x].get1();
                    tempArr2[2] = arr[x].get2(); // gets coefficients

                    duo math;
                    math = QuadraticFormula(tempArr2[0], tempArr2[1], tempArr2[2]); // solves the coefficients
                    duo output = new duo(math.get0(), math.get1());

                    //System.out.println(Thread.currentThread());
                    String currThread = String.valueOf(Thread.currentThread());
                    char useThread = currThread.charAt(21); // gets the current thread its using

                    if(!threadTracker.containsKey(useThread)) {
                        threadTracker.put(useThread, 1); // if the key is new a new key is created
                    }
                    else{
                        //incrementing a map borrowed from user LE GALL Benoit on StackOverflow
                        //credit link: https://stackoverflow.com/questions/81346/most-efficient-way-to-increment-a-map-value-in-java
                        threadTracker.merge(useThread, 1, Integer::sum);
                    }

                    outputStream.blockingPut(output); // puts in the output stream for the master

                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * returns the hashmap with the count of how many roots each thread solved
     */
    public static HashMap getMap(){
        return threadTracker;
    }

    /**
     * this is a method to solve roots using the quadratic formula
     * I'm in numerical methods and I learned that 2c/-b sqrt(gamma)
     * results in a more accurate result because subtraction causes numerical errors
     * @param a - first number or x^2
     * @param b - second number or x
     * @param c - third number or constant
     * @return - duo of solved roots
     */
    public duo QuadraticFormula(int a, int b, int c){
        duo roots;
        double rootOne, rootTwo; // the two roots to be returned

        // this calculates what goes in the square root (gamma).
        // It's called gamma because that's how I learned it in Numerical Methods
        double gamma = b * b - (4 * a * c);

        //if gamma is 0 then both of the roots will be the same
        if(gamma == 0){
            rootOne = (2 * c) / (-b + Math.sqrt(gamma));
            // I learned in Numerical methods if you use 2c/-b+sqrt(gamma)
            // it results in much lower error in quadratic formula
            roots = new duo(rootOne, rootOne);
            return roots;
        }
        //second check if there are two real roots, gamma > 0
        else if(gamma > 0){
            rootOne = (-b + Math.sqrt(gamma)) / (2 * a);
            rootTwo = (2 * c) / (-b + Math.sqrt(gamma));
            roots = new duo(rootOne, rootTwo);
            return roots;
        }
        // gamma < 0 means there are imaginary roots
        else{
            rootOne = (double) -b /(2*a); //real root
            rootTwo = Math.sqrt(-gamma) / (2*a); // imaginary root
            roots = new duo(rootOne, rootTwo);
        }

        return roots;

    }
}
