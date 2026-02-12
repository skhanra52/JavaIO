package com.skhanra52.readinFiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * This is the summary of File read action. We can refer for quick revision.
 */

public class ReadingFilesSummary {

    public static void main(String[] args) {

        /*
         Here is the older way of reading files (ie IO way)
         Here .read() returns the integer, -1 refers to end of the char. It's read the character by character.
         */
        try(FileReader reader = new FileReader("files/fixedWidth.txt")){
            int charCount;
            while((charCount = reader.read()) != -1){ // .read() returns the integer, -1 refers to end of the char
                System.out.print((char)charCount);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         We can read more than one character at a time, and avoid the cast, by passing a character array to the
         read method.
         */
        try(FileReader reader = new FileReader("files/fileReading.txt")){
            char[] block = new char[1000];
            int data;
            while((data = reader.read(block)) != -1){
                String content = new String(block, 0, data);
                System.out.printf("-----> [%d chars] %s%n", data, content);
            }

        }catch(IOException e){
            e.printStackTrace();
        }

        /*
         Here reader returning each line in each iteration. It reads the file content line by line.
         */
        try(BufferedReader reader = new BufferedReader(new FileReader("files/fixedWidth.txt"))){
            String line;
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         In the below code, we are using reader.lines() which is returning a Stream<String>,
         Unlike, Files.lines() method, it does not open the file on its own, rather it does work
         on an already opened file and just converts  the reader to Stream.
         */
        try(BufferedReader reader = new BufferedReader(new FileReader("files/fixedWidth.txt"))){
            reader.lines().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ----------------------- NIO way of reading file which is the newer way---------------------------------------
        System.out.println("--The newer way of reading file using Files.lines()-------");
        /*
         In the below code we are using Files.lines() method which is available from java.nio.file package.
         It does open the file and read the lines, it returns a Stream<String>.
         It also read content line by line meaning, each line of the file become one String,
         and provide efficient control on encoding mechanism.
         */
        try(Stream<String> lines = Files.lines(Path.of("files/fixedWidth.txt"))){
            lines.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
