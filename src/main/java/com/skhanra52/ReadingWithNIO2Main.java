package com.skhanra52;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadingWithNIO2Main {

    public static void main(String[] args) {
        /*
         Check what is the default character coding is?
         there are couple if ways to check the same.
         */
        System.out.println(System.getProperties());
        System.out.println(Charset.defaultCharset());

        /*
         Start reading the file using NIO2 classes.
         We have started reading the file using the smallest unit bytes. Ultimately all data is really in bytes, and
         it gets encoded to characters. If it contents text, we can use text based reader.
         */

        Path path = Path.of("files/fixedWidth.txt");
        try{
            System.out.println(new String(Files.readAllBytes(path)));
            System.out.println("-Same can be done by using readString()-----------");
            System.out.println(Files.readString(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
