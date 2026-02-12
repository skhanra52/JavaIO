package com.skhanra52.writingFiles.student;

/*
 There are lots of reason why you might want to write data to a file.
 These include:
    -> Storing user data.
    -> Logging application events to a log file.
    -> Storing configuration data.
    -> Exporting data for exchanging information.
    -> Supporting offline Usages in a File Cache.
    -> Generating file products.

  Is writing to a File so different from reading from a file?
    Some of the concepts of writing to the files are naturally similar to those reading from a file.
    We will use similar named classes, but instead of InputStream, we will work with an OutputStream.
    For Example:
        There is a FileWrite class rather than FileRead class and so on.

    Understanding buffer data become more important, as well as managing multiple writes to a single file from
    the different threads.

    There are different ways to open a file for writes.

 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class WriteFileMain {

    public static void main(String[] args) {
        String header = """
                Student Id, Country Code, Enrolled Year, Age, Gender, Experienced, Course code,\
                Engagement Month, Engagement Year, Engagement Type
                """;

        Course jmc = new Course("JMC", "Java MasterClass");
        Course pymc = new Course("PYMC", "Python MasterClass");

        List<Student> students = Stream
                .generate(() -> Student.getRandomStudent(jmc, pymc))
                .limit(5)
                .toList();
        System.out.println(header);
        students.forEach((item) -> item.getEngagementRecords().forEach(System.out::println));

        // Writing data to a file instead of printing to a console
        /*
         Here in the below code we are not using try-with-resource because "Files.writeString" and "Files.write" method
         does open a file and close it as well after writing in to file.

         Default Open Option:
         ----------------------------
         All available options are found on an Enum in the java.nio.file package, called StandardOpenOption.
         The default options for Files.write() method are shown in the table.
         ---------------------------------------------------------------------------------------------------------------
            Option             |           Description
         ---------------------------------------------------------------------------------------------------------------
         CREATE                | This creates a new file if it does not exist.
         TRUNCATE_EXISTING     | If the file already exist, and it opened for write access, then its length is
                               | truncated to 0.
         WRITE                 | The file is opened for write access.

         */

        Path path = Path.of("files/students.csv");

//        try{
//            Files.writeString(path, header); // writing header
//            for(Student student : students){
//                Files.write(path, student.getEngagementRecords(), // if we don't mention option then it will truncate
//                        StandardOpenOption.APPEND); // everytime , here, appending to the file after header.
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }

        // It would be efficient to create a single iterable object and pass that for writing. This is called
        // single Files.write() method.

        try{
            List<String> data = new ArrayList<>();
            data.add(header);
            for(Student student: students){
                data.addAll(student.getEngagementRecords());
            }
            Files.write(path, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /*
         In the above we are using Files.write() to write to file. However, everytime you write it will overwrite the
         previous content. The same can be achieved using "BufferWriter" as well, refer below code.
         */

        try(BufferedWriter writer = Files.newBufferedWriter(Path.of("files/take2.csv"))){
            writer.write(header);
            writer.newLine();
            for (Student student : students){
                for (var record : student.getEngagementRecords()){
                    writer.write(record);
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        try(FileWriter writer = new FileWriter("files/take3.csv")){
            writer.write(header);
            writer.write(System.lineSeparator());
            for (Student student : students){
                for (var record : student.getEngagementRecords()){
                    writer.write(record);
                    writer.write(System.lineSeparator());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try(PrintWriter writer = new PrintWriter("files/take4.csv")){
            writer.write(header);
            for (Student student : students){
                for (var record : student.getEngagementRecords()){
//                    writer.write(record);
                    writer.println(record);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /*
         Difference between various writer classes:
         ---------------------------------------------------------------------------------------------------------------
                        |     Buffering           | Data Format |         Features          | Use cases
         ---------------------------------------------------------------------------------------------------------------
         BufferedWriter | Yes                     | Characters  | Supports line breaks with | Writing large amount of
                        |                         |             | newline methods.          | text to a file.
         ---------------------------------------------------------------------------------------------------------------
         FileWriter     | Yes, but much smaller   | Characters  | No Separate method for lin| Writing small amount of
                        | buffer than BufferWriter|             |-e separator, would need to| text to a file
                        |                         |             | write manually.           |
         ---------------------------------------------------------------------------------------------------------------
         PrintWriter    | No, But often used with | Characters, | Familiar methods, that hav| Writing text to a file,
                        | a bufferWriter          | Numbers,    |-e same behaviour as       | formatting output, and
                        |                         | Objects     | System.out methods        | outputting objects.
         ---------------------------------------------------------------------------------------------------------------
         */

        try(PrintWriter writer = new PrintWriter("files/take4.txt")){
            writer.write(header);
            for (Student student : students){
                for (var record : student.getEngagementRecords()){
                    String[] recordData = record.split(",");
                    writer.printf("%-12d%-14s%2d%12d%3d%-1s".formatted(
                            student.getStudentId(),  // Student Id
                            student.getCountry(),           // Country Code
                            student.getEnrollmentYear(),    // Enrolled year
                            student.getEnrollmentMonth(),   // Enrolled month
                            student.getEnrollmentAge(),     // Enrolled age
                            student.getGender()
                    ));
                    writer.printf("%-1s", (student.hasExperience() ? 'Y' : 'N'));
                    writer.format("%-3s%10.2f%-10s%-4s%-30s",   // alternate to printf
                            recordData[7],                    // Course code
                            student.getPercentComplete(recordData[7]),
                            recordData[8],                    // Engagement Month
                            recordData[9],                    // Engagement Year
                            recordData[10]                   // Engagement Type
                            );
                    writer.println();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
