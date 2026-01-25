package com.skhanra52;


/*
 A Review of IO, the old school way:
 -------------------------------------------------------------------------------------------------------------
 The simple way we can read line from a file using "Files.readAllLines()" method.

 However, We are going to see how reading file operation was happening in the previous versions like Java 1.0.
 These methods somewhat reflect the actual process of reading data from a file, as opposed to a black box method
 like readAllLines.


 */

import java.io.FileReader;
import java.io.IOException;

public class ReadingFilesMain {

    public static void main(String[] args) {
        /*
         The below code where we are using reader.read() method till we get "-1" which is the end of the file.
         This is a tedious way to read file, as well as its very expensive when it comes to Disk Read.
         Disk Read:-----------------------------------
           A Disk read means something is physically or mechanically occurring to the hard disk to read the character
           from the file. This is expensive in terms of time and resource usages. Fortunately, java provides way to
           reduce the number of disk read being done.


         */
        try(FileReader reader = new FileReader("files/fileReading.txt")){
            int data;
            while((data = reader.read()) != -1){
                System.out.println((char)data); // converting unsigned integers to characters.
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
