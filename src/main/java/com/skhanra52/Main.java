package com.skhanra52;

/*
Input & Output(I/O), working with file in Java.
------------------------------------------------------------------------------------------------------------------------
 Communicating outside the JVM using resources.
 Resources can be files, network connections, database connections, streams, or sockets.
 We use the resources to interact with the file systems, networks and databases to exchange the information.

 Exception Handling:
 -----------------------------------------------------------------------------------------------------------------------
 -> Try, catch, finally etc.
 -> When we are dealing with the external resources, exception handling become much more important.
 -> When we instantiate one of the Java's file access classes, Java will delegate to the operating system to open a file
    from the operating systems file system.
 This then performs some or all of the following steps:
  1. Check if the file exists.
  2. If the file exist, check if the user or process has the proper permissions for the type of access being requested.
  3. If this is true, then file metadata is retrieved, and a file descriptor is allocated. This descriptor is a handle
     to opened file.
  4. An entry made in the file table or file control block table of the operating system to track the opened files.
  5. The file may be locked.
  6. The file may be buffered by the OS, meaning memory is allocated to cache all or part of the file contents, to
     optimize read and write operations.
 Java uses a file handel to communicate with the operating system for file operation it wants to perform.

 Closing an open file resources:------------------------------------
  -> Many of the methods in Java make opening a file look like instantiating any other object. We don't have to call
     open on a file, so it's easy to forget that we have really opened a resource.
  -> Closing the file handle will free up the memory used to store any file related data and allow other processes to
     access the file.
  -> Fortunately, most of the Java classes used to interact with file also implements an AutoClosable interface, which
     makes closing resources seamless.
------------------------------------------------------------------------------------------------------------------------
 IO, NIO, NIO.2
------------------------------------------------------------------------------------------------------------------------
Java has large series of classes, in many packages, to support input/output.

IO: Input/Output, and java.io is the package that contains all the original set of types that supports reading and
    writing data from external resources.
NIO: It was introduced as Non-blocking IO, with the java.nio package in java 1.4, as well few other related packages.
    The communication with resources is facilitated through specific types of channels, and the data stored in container
    called buffers information when exchanged.
NIO.2: Stands for New IO, and is a term that came into being with java 1.7, emphasizing significant improvements to the
    java.nio package, most importantly the java.nio.file package and its type.
    The NIO.2 introduced Path interface and file system types, and some helper classes such as Files, Paths, and
    FileSystem that do make some common functionality for working with operating system file systems much easier as well
    as much efficient, delegating work to native system.


 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args){

        /*
        LOOK in testFile()
        Files.readAllLines(path) method throws an exception called IOException. This is a special kind of exception
        called a Checked Exception. It's a parent class of many common exceptions we'll encounter when working with
        external resources.
        -> Checked Exception represents an anticipated or common problem that might occur. For Example, we can imagine
           that a typo in the file name might be a common mistake, resulting in the system being unable to locate the
           file. It's so common that java has a named exception for that situation, the FileNotFoundException, which is
           a subclass of the IOException.

        How do we handle the checked exception?
        ----------------------------------------
        There are two options,
         1. We can wrap statement that throws a checked exception in a try catch block and then handle the situation in
            the catch block.
         2. Or we can change the method signature, declaring a throws clause and specifying the exception type.

        We can use File class to check if the file exist or not.
        LBYL or EAFP,

        LBYL: "Look Before You Leap", this style of coding involves checking for errors before you perform an operation.
        EAFP: "Easier to Ask Forgiveness than Permission". This assumes an operation will usually succeed, and then
               handles any error that occurs, if they do occur.

        How do we recognize a checked exception?
          -> A checked exception means it will give you an error at the compiled time itself in the IDE.
          -> An unchecked exception is an instance of a RuntimeException or one of its subclasses.
         */

        /*
          -> Many of the types to read and write to files are instantiated using the new keyword.
          -> Underneath the covers, the constructor opens the file resource, and it's important to close the resource
             when we are done with it.
          -> Using "try with resources" is the recommended approach, both to make your code more concise and to avail
             yourself of java's build-in support for automatically closing resources with the try block.
         */

        /*
            Legacy(io):---------------------
            java.io.File;
            java.io.FileReader; -----------> AutoClosable

            -> The File class and FileReader class are part of Java since version 1.
            -> The FileReader class implements the AutoClosable interface through the parent class Reader. This class
               open a file implicitly.
            -> In contrast, when we are creating an instance of a File, we are not actually opening it. Instead, we are
               working with something called file handler, that let us perform OS-like operation.

           File Handle vs File Resource:
           -------------------------------------------------------------
           A File Handle is a reference to a file that is used by the operating system to keep track of the file.
           It's an abstract representation of the file, and it does not contain any of the actual data from the file.

           A File Resource, on the other hand, is the actual data from the file.
           It is stored on desk, and it can be accessed by the operating system and by application.


         */

        File file = getFile();
        System.out.println(file.getAbsolutePath());
        if(!file.exists()){
            System.out.println("Can't run until the file is exist");
            return;
        }
        System.out.println("We are good to go");

        /*
            File System concept:
            --------------------------------------------
            -> A "directory(or Folder)" is a file system container for other directories ot files.
            -> A "path" is either a directory or a filename and may include information about parent directories or folder.
            -> A "root directory" is the top level directory in the file system.
            -> The "current working directory" is the directory where the current process is working in or running from.
            -> An "Absolute path" includes the root(by either starting with / or optionally, C:\ in windows)
            -> A "relative path" defines a path relative to the current working directory, and therefore would not start
               with /, but may optionally start with dot . then a file separator character.
         */

        // using nio2 ------------------------
        Path path = Paths.get("files/testing2.csv");
        System.out.println(file.getAbsolutePath());
        if(!Files.exists(path)){
            System.out.println("2. Can't run until the file is exist");
            return;
        }
        System.out.println("2. We are good to go");


    }

    private static File getFile() {
        System.out.println("Current working directory (cwd): "+new File("").getAbsolutePath());
        // Listing the root directory.
        for(File f : File.listRoots()){
            System.out.println("Root directory: "+f);
        }
//        String fileName = "files/testing.csv";
        String fileName = "testing.csv";
        String fileName2 = "files/testing2.csv";
//        testFile(fileName);
//        testFile2(fileName);
//        testFile2(null);
//        return new File(fileName);
        // moved the testing file from the "Desktop/Java/FileExceptions/files" to "/Users/sumankalyankhanra/Desktop"
//        return new File("/Users/sumankalyankhanra/Desktop", fileName); // provided absolute path to access the file.
        return new File(".", fileName2); // We will get /Users/sumankalyankhanra/Desktop/Java/FileExceptions/./files/testing2.csv
        // This is called redundant name element.
    }

    private static void testFile(String fileName){
//        Path path = Paths.get(fileName);
        FileReader reader = null;
        try{
//            List<String> lines = Files.readAllLines(path);
            reader = new FileReader(fileName);
        }catch (IOException e){
            throw new RuntimeException(e);
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("May be I would log something either way...");
        }
        System.out.println("File exists and able to use the resource");
    }

    // Example of try with resource
    private static void testFile2(String fileName){
        // surround a try with resources block.
       try(FileReader reader = new FileReader(fileName)){
           System.out.println("The file is "+reader);
       } catch (FileNotFoundException e) {
           System.out.println("File '" +fileName + "' does not exist.");
           throw new RuntimeException(e);
       } catch (NullPointerException | IllegalArgumentException e){
           System.out.println("User has added bad data "+ e.getMessage());
       }
       catch (IOException e) {
           throw new RuntimeException(e);
       } catch (Exception e){
           System.out.println("Something unrelated and unexpected happened.");
       }finally {
           System.out.println("May be I would log something else either way..........");
       }
        System.out.println("File exists and able to use the resource");
    }
}