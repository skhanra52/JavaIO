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
    The NIO.2 introduced Path interface and file system types, and some helper classes such as
        -> Files,
        -> Paths,
        -> FileSystem
    these do make some common functionality for working with operating system file systems much easier as well
    as much efficient, delegating work to native system.


 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.Map;

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


        useFile("files/testing2.csv"); // It read the existing file from the directory. It is taking file name as param.
        usePath("pathFile.txt"); // It has created the pathFile.txt in the first run. It actually takes path as param.
        /*
          In the above, useFile() method takes the "file name" as an argument and uses the old File class after
          instantiate the File object. This is the older version of checking whether the file exist or not, creating
          files etc.
          In the other hand, we also have path, usePath() method takes the path as an argument and directly uses the
          File static methods which are all takes path as an argument and perform all the common functions.

          Functionality in Common:
          --------------------------------------------------------------------------------------------------------------
          Functionality                 |  File instance methods           | File static methods, with path argument
                                        |  File class                      | Files class (File ending with s)
          --------------------------------------------------------------------------------------------------------------
          1. create file                | createNewFile()                  | createFile(Path p)
          2. delete directory of  file  | delete()                         | File.delete(Path p) / deleteIfExist(Path p)
          3. check path type            | isDirectory() / isFile()         | isDirectory(Path p) / isRegularFile(Path p)
          4. get byte size of the file  | length()                         | size(Path p)
          5. List directory contents    | listFiles                        | list(Path p)
          6. create directory or        | mkdir() or mkdirs()              | createDirectory(Path p)
                directories             |                                  | or createDirectories(Path p)
          7. Rename                     | renameTo(File destination)       | move(Path src, Path destination)
          etc...

          Note: If we need to support legacy code or if we need to be backward compatible to a version of JDK that's
                older than JDK7, we can still rely on "File" class.
                If we are starting new, then probably we can use the "Files"(its Files, ending with s) class and path
                instance, otherwise, known as the nio2 ways of doing things.

           => NIO.2 file operations have been improved:
           -------------------------------------------------------------------------------------------------------------
           The NIO.2 type includes the support for:
            -> Asynchronous file I/O operations.
            -> File locking, including more regular locking. This means, instead of locking entire file a region of it
                can be locked.
            -> File metadata retrieval.
            -> Symbolic link manipulation.
            -> File system notifications. This means changes occurring on a path can be made watchable to register
                services.

            => NIO.2 file operations perform better:
            ------------------------------------------------------------------------------------------------------------
             -> NIO.2 type are non-blocking, meaning asynchronous access to resources by multiple threads is supported.
             -> They manage memory more efficiently, reading and writing directly to and from memory in to buffers,
                through something called a FileChannel.
             -> We can also read from or write to multiple buffers in a single operation.
         */

        System.out.println("--------------------------------------------------");
        printPathInfo(path);
        // Initially the "files/testing3.csv" not present and the folder(directory) "files" and file "testing3.csv"
        // will be created using "Files.createDirectory()" method from "Files" class.
//        Path path2 = Path.of("files/testing3.csv");
//        losStatement(path2);
        // In the below path the entire directory is not present in the file structure. So directory has to get created
        // first and then the file will get created.
        System.out.println("----------Testing path for testing4.txt--------------------");
        Path path3 = Path.of("testDirectory/testFile/testing4.txt");
        printPathInfo(path3);
        losStatement(path3);
        System.out.println("--------OS Path related information, varies as per OS-----------------------");
        extraInfo(path3);

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

    // used older File type
    private static void useFile(String filename){
        File file = new File(filename);
        boolean fileExist = file.exists();

        System.out.printf("File '%s' %s %n", filename, fileExist? "exists.":"does not exist.");

        if(fileExist){
            System.out.println("Deleting the file: "+filename);
            fileExist = !file.delete();
        }

        if(!fileExist){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Something went wrong.");
            }
            System.out.println("Created file: "+filename);
            if(file.canWrite()){
                System.out.println("Would write to file here..");
            }
        }
    }

    private static void usePath(String filename){
        Path path = Path.of(filename);
        boolean fileExist = Files.exists(path);

        System.out.printf("File '%s' %s %n", filename, fileExist? "exists.":"does not exist.");

        if(fileExist){
            System.out.println("Deleting the file: "+filename);
            try {
               Files.delete(path);
                fileExist = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(!fileExist){
            try {
                Files.createFile(path);
                System.out.println("Created file: "+filename);
                if(Files.isWritable(path)){
                    Files.writeString(path, """
                            I am writing some text here.
                            This is just the sample.
                            """);
            }
                System.out.println("I am read too");
                System.out.println("-----------------------------");
                Files.readAllLines(path).forEach(System.out::println);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Describe various methods of path available to read the path, filename, directory, absolute path etc.
     * @param path file path.
     */
    private static void printPathInfo(Path path){
        System.out.println("Path: "+path);
        System.out.println("fileName: "+path.getFileName());
        System.out.println("Parent: "+path.getParent());
        Path absolutePath = path.toAbsolutePath();
        System.out.println("Absolute path: "+absolutePath);
        System.out.println("Absolute path root: "+absolutePath.getRoot());
        System.out.println("Root: "+path.getRoot());
        System.out.println("isAbsolute: "+path.isAbsolute());

        // Starting from root iterating over the path to get the subFolders in it.
        // printing the root separately as it does not include in absolute path.
         System.out.println(absolutePath.getRoot());
//         int i = 1;
//         var it = path.toAbsolutePath().iterator();
//         while (it.hasNext()){
//             System.out.println(".".repeat(i++)+" "+it.next());
//         }

        // Without iterating through the path using .iterate(), we can use the path method available to use to loop
        // through directory tree.
        int pathParts = absolutePath.getNameCount();
        // System.out.println(pathParts);
        for(int j=0; j< pathParts; j++){
            // using ".getName()" we get more flexibility while iterating through the file tree.
            System.out.println(".".repeat(j+1) + " "+absolutePath.getName(j)); // Gives directory name each time
        }
        System.out.println("--------------------------------------------------");
    }

    /**
     *  Creates the directory if not preset, also create and write to file directly.
     * @param path File path passed in to method.
     */
    private static void losStatement(Path path){
        try{
            Path parent = path.getParent();
            if(!Files.exists(parent)){
                // Files.createDirectory(parent); // creates single directory, ex: "files/testing3.csv", see in main method
                // below line of code creates the entire folders(directories)which are not exist along with file testing4.csv.
                Files.createDirectories(parent);
            }
            // creating and writing in the file using single line. First argument is path, second argument is the string
            // that will be printed. This also takes 3rd argument which is a variable length option types.
            // Here, we are using two options StandardOpenOption.CREATE, StandardOpenOption.APPEND
            Files.writeString(path, Instant.now() +": Hello File world\n",
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Provides OS specific attributes information about the file like lastAccessTime, lastModifiedTime etc.
     * @param path of the file.
     */
    private static void extraInfo(Path path){
        try{
            Map<String, Object> attr = Files.readAttributes(path,"*");
            attr.entrySet().forEach(System.out::println);
            System.out.println(Files.probeContentType(path)); // Gives the content type of the file, ex: test/plain
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}