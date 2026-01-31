package com.skhanra52;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            // Files.readString() also usages readAllBytes() under the hood.
            System.out.println("Same can be done by using readString():-----------");
            System.out.println(Files.readString(path));

            /*
             We will parse the fixedWidth.txt to get distinct values out of certain column, using readAllLines(), this
             method returns an array of String, So we will see a solution without using Stream.
             */
            Pattern p = Pattern.compile("(.{15})(.{3})(.{12})(.{8})(.{2}).*");
            Set<String> values = new TreeSet<>();
            Files.readAllLines(path).forEach(s -> {
                if(!s.startsWith("Name")){
                    Matcher m = p.matcher(s);
                    if(m.matches()){
                        values.add(m.group(3).trim());
                    }
                }
            });
            System.out.println(values);

            // Another way of doing the same which we have done above, here we are using Files.lines() which returns
            // a Stream of String.
            try(Stream<String> stringStream = Files.lines(path)){
                String[] result = stringStream.skip(1)
                        .map(p::matcher)
                        .filter(Matcher::matches)
                        .map(m -> m.group(3).trim())
                        .distinct()
                        .sorted()
                        .toArray(String[]::new);
                System.out.println(Arrays.toString(result));

            }

            // We are going to count the employee department wise. Collector.groupBy() will give us a Map with department
            // name as key and employee count as values.
            try(Stream<String> stringStream = Files.lines(path)) {
                var empCount = stringStream.skip(1)
                        .map(p::matcher)
                        .filter(Matcher::matches)
                        .collect(Collectors.groupingBy(m -> m.group(3).trim(), Collectors.counting()));
                empCount.entrySet().forEach(System.out::println);
                System.out.println(empCount);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
