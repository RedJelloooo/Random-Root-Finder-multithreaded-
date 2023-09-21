import java.util.Random;
public class Master implements Runnable {
    private static CircularInputBuffer inputStream = new CircularInputBuffer();
    private static CircularOutputBuffer outputStream = new CircularOutputBuffer(); // references to buffers
    private static int option; // 30 or 3000 roots


    /**
     * Constructor for the master that references teh buffers and
     * how many roots it should solve
     * @param inputStream - input circular buffer
     * @param outputStream - output circular buffer
     * @param option - how many roots should be solved
     */
    public Master(CircularInputBuffer inputStream, CircularOutputBuffer outputStream, int option){
        Master.inputStream = inputStream;
        Master.outputStream = outputStream;
        Master.option = option;
    }

    /**
     * runs the master thread with options of how many roots to solve
     */
    @Override
    public void run(){
        Random randomiser = new Random();
        int a;
        int b;
        int c;

        if(option == 1) {
            for (int x = 0; x < 30; x++) {
                 a = randomiser.nextInt(2000)-1000;
                 b = randomiser.nextInt(2000)-1000;
                 c = randomiser.nextInt(2000)-1000; // creates random integers from -1000 to 1000

                try {

                    triple insertTup = new triple(a, b, c);
                    inputStream.blockingPut(insertTup); // inserts random numbers into input buffer

                    duo printDuo;
                    printDuo = outputStream.blockingGet(); // gets the solved duo tuple

                    System.out.println("SOLVED ROOT #: " + (x+1) + " " + printDuo.toString()); // prints out the root

                    if (x == 29) { // stops the program once it finishes
                        Master.stop();
                        // once last is reached then the master stops
                    }


                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(option == 2) {
            for (int x = 0; x < 3000; x++) {
                 a = randomiser.nextInt(2000)-1000;
                 b = randomiser.nextInt(2000)-1000;
                 c = randomiser.nextInt(2000)-1000; // creates random integers from -1000 to 1000

                try {

                    triple insertTup = new triple(a, b, c);
                    inputStream.blockingPut(insertTup); // inserts the tuple

                    duo printDuo;
                    printDuo = outputStream.blockingGet();

                    if (x == 2999) {
                        System.out.println("Here is how many roots each thread calculated (thread 10 is added to thread 1): ");
                        System.out.println(Slaves.getMap());
                        System.out.println("finished calculating roots for 3000 numbers");
                        Master.stop();
                        // once last is reached then the master stops
                    }


                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        //System.out.println(print.toString());
    }

    /**
     * stops the master from running
     */
    public static void stop(){
        System.exit(0);
    }

}
