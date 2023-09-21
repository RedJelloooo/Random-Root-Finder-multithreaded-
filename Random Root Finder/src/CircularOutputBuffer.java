
// Fig. 23.18: CircularBuffer.java
// Synchronizing access to a shared three-element bounded buffer.

/**
 * output buffer that takes in solved roots and outputs them to the master
 */
public class CircularOutputBuffer implements outputBuffer {
   private final duo[] buffer = new duo[3000]; // shared buffer
   // 3000 because it's the most amount of roots it will ever need

   private int occupiedCells = 0; // count number of buffers used
   private int writeIndex = 0; // index of next element to write to
   private int readIndex = 0; // index of next element to read

   /**
    * puts a solved root into the circular buffer in the form of a two element tuple
    * @param value - value to be inserted
    */
   @Override
   public synchronized void blockingPut(duo value)
           throws InterruptedException {

      // wait until buffer has space available, then write value;
      // while no empty locations, place thread in blocked state

      //System.out.println(value.toString() + "<-- INSERTED SOLVED ROOT");

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
    * used for the master to read teh values
    * @return - the read value from the circular buffer
    */
   @Override
   public synchronized duo blockingGet() throws InterruptedException {
      // wait until buffer has data, then read value;
      // while no data to read, place thread in waiting state

      while (occupiedCells == 0) {
         //System.out.println("Buffer is empty. Slave waits.");
         wait(); // wait until a buffer cell is filled
      }


      duo readValue = buffer[readIndex]; // read value from buffer

      // update circular read index
      readIndex = (readIndex + 1) % buffer.length;

      --occupiedCells; // one fewer buffer cells are occupied

      notifyAll(); // notify threads waiting to write to buffer

      //System.out.println(readValue.toString() + "<-- TAKEN OUT OF OUTPUT");
      return readValue;
   }

   /**
    * @return - returns the length of the circular buffer
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