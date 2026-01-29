package com.skhanra52;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.MatchResult;

// Continuation from ReadingFilesMain...

public class ScannerProjectMain {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(new File("files/fileReading.txt"))) {
//            while(scanner.hasNextLine()){
//                System.out.println(scanner.nextLine());
//            }
            /*
             Using while loop is not that easy as BufferReader.lines()
             using Token method to get a stream of String. Output of below code using tokens() is exactly same as
             above while loop. Each line of text in the file is return on the stream as we used delimiter "$" which is
             meta character for end of line. And we can work on lines of text rather that words if we will be using
             default delimiter as "\p{javaWhitespace}+" which means one or more whitespace and includes new line.
             */
//            System.out.println(scanner.delimiter());
//            scanner.useDelimiter("$");
//            scanner.tokens().forEach(System.out::println);

            // List of words that are 10 character or more. It enables the capability of quick search for
            // matching elements.
            scanner.findAll("[A-Aa-z]{10,}")
                    .map(MatchResult::group)
                    .distinct()
                    .sorted()
                    .forEach(System.out::println);
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }

        // demonstrating search feature here using findAll

        try(Scanner scanner2 = new Scanner(new File("files/fixedWidth.txt"))){
            var result = scanner2.findAll(
                    "(.{15})(.{3})(.{12})(.{8})(.{2}).*")
                    .skip(1)
                    .map(m -> m.group(2).trim()) // Here the number in group is the column index.
                    .distinct()
                    .sorted()
                    .toArray(String[]::new);
            System.out.println(Arrays.toString(result));
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }

    }
}
