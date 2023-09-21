import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Scanner;

public class multithreadingDriver {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        CircularInputBuffer input = new CircularInputBuffer();
        CircularOutputBuffer output = new CircularOutputBuffer();

        Scanner userid = new Scanner(System.in);

        System.out.println("Welcome to the multithreaded root finder!");
        System.out.println("enter 1 for 30 roots\n2 for 3000 roots solved.\n0 to exit the program");
        int choice = userid.nextInt();


        if(choice == 1){

            Master master = new Master(input, output, 1);

            executorService.execute(master);


            for(int i = 0; i < 10; i++) {
                executorService.execute(new Slaves(input, output, 1));
            } // creates 10 threads

            executorService.shutdown();
        }

        if(choice == 2){
            Master master = new Master(input, output, 2);

            executorService.execute(master);


            for(int i = 0; i < 10; i++) {
                executorService.execute(new Slaves(input, output, 2));
            } // creates 10 threads

            executorService.shutdown();
        }

        System.out.println("Thanks for using the root generator!");

    }
}
