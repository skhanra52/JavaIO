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

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello, World!");
    }
}