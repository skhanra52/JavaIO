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

        /*
         FileReader has a default buffer size, meaning it reads the certain number of character in the memory space
         called buffer.
         What is a File Buffer:-------------------------------
         -> A file buffer is a computer memory which temporarily holds data while it's being read from the file.
         -> Its primary purpose is to improve the efficiency of the data transfer and processing.
         -> It reduces the number of direct interactions, or disk read, against the actual storage device.
         -> In our example, where we are reading from the "files/fileReading.txt", which has 11 characters. Only one
            of them(the first) was an actual disk read. The subsequent characters are read from the memory, or a buffer
            read. Java's API says that the size of the buffer in the case of FileReader is implementation specific.
            Meaning, it depends on OS and other factor.

          How the FileReader actually works:---------------------
          
         */

        try(FileReader reader = new FileReader("files/fileReading.txt")){
            // we can read more than one character at a time, and avoid the cast,
            // by passing a character array to the read method.
            char[] block = new char[1000];
            int data;
            while((data = reader.read(block)) != -1){
                String content = new String(block, 0, data);
                System.out.printf("-----> [%d chars] %s%n", data, content);
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
