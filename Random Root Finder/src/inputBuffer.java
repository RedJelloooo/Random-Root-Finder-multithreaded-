/**
 * Borrowed Buffer class from Chapter 23 in book
 */

// Fig. 23.9: Buffer.java
// Buffer interface specifies methods called by Producer and Consumer.
public interface inputBuffer {
   // place int value into Buffer
   void blockingPut(triple value) throws InterruptedException;

   // return int value from Buffer
   triple blockingGet() throws InterruptedException;

   int size();
}


/*
 * (C) Copyright 1992-2015 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * the best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/