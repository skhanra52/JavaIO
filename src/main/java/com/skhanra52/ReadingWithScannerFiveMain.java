package com.skhanra52;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.MatchResult;

// Continuation from ReadingFilesMain...

public class ReadingWithScannerFiveMain {

    public static void main(String[] args) {

        /*
         Scanner constructor that takes "File" as source. "File class" is part of IO not NIO 2.
         In the scanner constructor definition (command + click on Scanner), we do see it is taking File as argument,
         and throws a FileNotFoundException.

         Here, is the definition, which has another overloaded constructor and output of
         "new FileInputStream(source).getChannel()" as been converted to "ReadableByteChannel". This is definitely
         using NIO 2 functionality. Even though we think we might be using an IO class like "Files" and "Scanner",
         the underlying functionality is taking advantage of the NIO 2 enhancements.

             public Scanner(File source) throws FileNotFoundException {
                this((ReadableByteChannel)(new FileInputStream(source).getChannel()));
             }
         */
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

        /*
         In the below code we are using "Path.of" to Scanner constructor. Command + click on "Scanner" and we get the
         below definition of scanner constructor. Here, a Path is an argument to the method of a "Files" class.
         If we follow the method trail we will find "ReadableByteChannel" which means its using NIO 2 feature under
         the hood.

              public Scanner(Path source) throws IOException {
                    this(Files.newInputStream(source));
              }

         */
        try(Scanner scanner2 = new Scanner(Path.of("files/fixedWidth.txt"))){
            var result = scanner2.findAll(
                            "(.{15})(.{3})(.{12})(.{8})(.{2}).*")
                    .skip(1)
                    .map(m -> m.group(2).trim()) // Here the number in group is the column index.
                    .distinct()
                    .sorted()
                    .toArray(String[]::new);
            System.out.println(Arrays.toString(result));
        } catch (IOException e) {
            throw new RuntimeException();
        }

        /*
         This time we are passing "FileReader" to the scanner constructor. If we look the "FileReader" constructor code
         we do see below:

             public Scanner(Readable source) {
                this(Objects.requireNonNull(source, "source"), WHITESPACE_PATTERN);
             }

         When we are using "FileReader" the Scanner constructor will use the IO FileReader which is minimal buffering.
         To avoid that we can wrap the "FileReader" under new BufferReader().

         So All the three "File()", "Path.of()" and "FileReader()" provided as an argument to Scanner, it gives the
         same result. So the fact is Scanner will use advantage of NIO 2 irrespectively what we use as a Scanner arg.
         */

        try(Scanner scanner2 = new Scanner(new BufferedReader(
                new FileReader("files/fixedWidth.txt")))){
            var result = scanner2.findAll(
                            "(.{15})(.{3})(.{12})(.{8})(.{2}).*")
                    .skip(1)
                    .map(m -> m.group(2).trim()) // Here the number in group is the column index.
                    .distinct()
                    .sorted()
                    .toArray(String[]::new);
            System.out.println(Arrays.toString(result));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
