package com.skhanra52;

/*
    Other useful methods of the "Files" class.
    -> "list", "walk" and "find" methods, all which are methods that can be used to get the source for a
        stream pipeline of path element.
 */

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.stream.Stream;

public class FileListingMain {

    public static void main(String[] args) {
        // Created variable called "path" using empty string, which gives current working directory.
        Path path = Path.of("");
        System.out.println("cwd: "+path.toAbsolutePath());

        // Printing full path of current directory. It's same as "ls" command in the macOS/Linux terminal.
        // This method returns a stream of path instances, each path representing either a file or a directory/folder or
        // sub-folder and that going to be an element of stream called "Paths" which has a type "Path".
        //  Stream<Path> paths = Files.list(path); If we use this under the normal try block it would through a warning
        // as "'Stream<Path>' used without 'try'-with-resources statement" that is because the "Files.list(path)" returns
        // an stream, so it required a terminal operation which would close the stream and here we don't have any.
        // So in the below given code we have used try-with-resource because Files.line() returns a stream which will be
        // opened, to avoid manual consumption we are using here try-with-resource.
        System.out.println("-Example of Files.list()--------------------------------------");
        try(Stream<Path> paths = Files.list(path)) {
            // This will print the folders and files in the current directories. However, this is not recursive so we
            // would not see the content of the sub-folders.
            // paths.forEach(System.out::println);

            // Below code list the sub-folders/files available in the current directory
            paths.map(FileListingMain::listDir)
                    .forEach(System.out::println);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        System.out.println("-Example of Files.walk()--------------------------------------");
        /*
         Another method in the "Files" class, similar to the "list()" is "walk()" method.
         If we select the maxDepth 2, then it will display the current directory content and sub-folder content, just
         one level down.
         */
        try(Stream<Path> paths = Files.walk(path, 2)) {
            paths.filter(Files::isRegularFile) // filters only files.
                    .map(FileListingMain::listDir)
                    .forEach(System.out::println);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        System.out.println("Example of Files.find()--------------------------------------");
        /*
         We also have find method in the "Files" class to find the folder/files.
         */
        try(Stream<Path> paths = Files.find(path, 2,
                (item, attr) -> Files.isRegularFile(item))) {
            paths.map(FileListingMain::listDir)
                    .forEach(System.out::println);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        System.out.println("---------------------------------------");
        try(Stream<Path> paths = Files.find(path, 2,
                (item, attr) -> attr.isRegularFile())) {
            paths.map(FileListingMain::listDir)
                    .forEach(System.out::println);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        System.out.println("Search files in all level and size more than 300kb---------------------------------------");
        try(Stream<Path> paths = Files.find(path, Integer.MAX_VALUE,
                (item, attr) -> attr.isRegularFile() && attr.size() > 300)) {
            paths.map(FileListingMain::listDir)
                    .forEach(System.out::println);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        // another example of finding the file name called "testing2.csv"
        System.out.println("Find testing2.csv file---------------------------------------");
        try(Stream<Path> paths = Files.find(path, 2,
                (item, attr) -> item.endsWith("testing2.csv"))) {
            paths
                    .map(FileListingMain::listDir)
                    .forEach(System.out::println);
        }catch (IOException e){
            throw new RuntimeException(e);
        }


         /*
         Directory stream is another Java NIO 2 class. It provides an iterable directories.
         => path.resolve() method takes a sub-folder from where it starts the navigation. In the below code we are
            starting from the ".idea" folder and looking for ".xml" files available in it using glob param.
         */
        path = path.resolve(".idea");
        System.out.println("========Directory Stream using glob=================================");
        try(DirectoryStream<Path> dirs = Files.newDirectoryStream(path, "*.xml")){
            dirs.forEach((d) -> System.out.println(FileListingMain.listDir(d)));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        System.out.println("========Directory Stream using lambda function for filter============");
        try(DirectoryStream<Path> dirs = Files.newDirectoryStream(path,
                p -> p.getFileName().toString().endsWith(".xml")
                        && Files.isRegularFile(p)
                        && Files.size(p) > 1000)){
            dirs.forEach((d) -> System.out.println(FileListingMain.listDir(d)));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Display the path(file/folder) detail, including last modified date. DIR detail etc.
     * @param path provided Path type which includes the path of the directory
     * @return the formated string to display the file/folder detail including modification details.
     */
    private static String listDir(Path path){
        try {
            boolean isDir = Files.isDirectory(path); // checking whether the given name is a file or folder.
            FileTime dateField = Files.getLastModifiedTime(path);
            long size = Files.size(path);
            return "%s %5s %-10s %s".formatted(dateField,size, (isDir ? "<DIR>" : ""), path);
        } catch (IOException e) {
            System.out.println("Whoops! Something went wrong with "+path);
            return path.toString();
        }
    }
}
