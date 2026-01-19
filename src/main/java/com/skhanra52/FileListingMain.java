package com.skhanra52;

/*
    Other useful methods of the "Files" class.
    -> "list", "walk" and "find" methods, all which are methods that can be used to get the source for a
        stream pipeline of path element.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.stream.Stream;

public class FileListingMain {

    public static void main(String[] args) {
        // Created variable path using empty string, which gives current working directory.
        Path path = Path.of("");
        // printing full path of current directory. It's same as "ls" command in the macOS terminal.
        // This method returns a stream of path instances, each path representing either a file or a directory/folder or
        // sub-folder and that going to be a stream, typed with "Path" called "Paths".
        System.out.println("cwd: "+path.toAbsolutePath());
        // used try-with-resource because Files.line() returns a stream which will be opened, to avoid manual consumption
        // we are using here try-with-resource.
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
    }

    private static String listDir(Path path){
        try {
            boolean isDir = Files.isDirectory(path);
            FileTime dateField = Files.getLastModifiedTime(path);
            return "%s %-15s %s".formatted(dateField, (isDir ? "<DIR>" : ""), path);
        } catch (IOException e) {
            System.out.println("Whoops! Something went wrong with "+path);
            return path.toString();
        }
    }
}
