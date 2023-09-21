// Fig. 23.18: CircularBuffer.java
// Synchronizing access to a shared three-element bounded buffer.

/**
 * the input buffer takes in a triple tuple
 * and creates a circular buffer
 */
public class CircularInputBuffer implements inputBuffer {
   private final triple[] buffer = new triple[300]; // shared buffer
   //in my interpretation using 300 spaces results in much faster times and ability to solve the problem.

   private int occupiedCells = 0; // count number of buffers used
   private int writeIndex = 0; // index of next element to write to
   private int readIndex = 0; // index of next element to read

   /**
    * places a value into the circular buffer
    * @param value - insert tuple
    */
   @Override
   public synchronized void blockingPut(triple value)
           throws InterruptedException {

      // wait until buffer has space available, then write value;
      // while no empty locations, place thread in blocked state

      //System.out.println(value.toString() + "<-- INSERTED TRIPLE");

      while (occupiedCells == buffer.length) {
         //System.out.println("Buffer is full. Master waits.");
         wait(); // wait until a buffer cell is free
      }


      buffer[writeIndex] = value; // set new buffer value

      // update circular write index
      writeIndex = (writeIndex + 1) % buffer.length;

      ++occupiedCells; // one more buffer cell is full

      notifyAll(); // notify threads waiting to read from buffer
   }

   /**
    * reads a value from the circular buffer
    * @return - read value from the buffer
    */
   @Override
   public synchronized triple blockingGet() throws InterruptedException {
      // wait until buffer has data, then read value;
      // while no data to read, place thread in waiting state

      while (occupiedCells == 0) {
         //System.out.println("Buffer is empty. Slave waits.");
         wait(); // wait until a buffer cell is filled
      }


      triple readValue = buffer[readIndex]; // read value from buffer

      // update circular read index
      readIndex = (readIndex + 1) % buffer.length;

      --occupiedCells; // one fewer buffer cells are occupied

      notifyAll(); // notify threads waiting to write to buffer

      //System.out.println(readValue.toString() + "<-- TAKEN OUT OF INSERT");
      return readValue;
   }

   /**
    * @return - the length of the circular buffer
    */
   @Override
   public synchronized int size(){
      return buffer.length;
   }

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
 * furnishing, performance, or use of these programs.                     */