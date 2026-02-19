package com.skhanra52.managingFileOper;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.stream.Stream;

public class ManagingFile {

    public static void main(String[] args) {

//        System.out.println("This section is just for experiment");
//        File file = getFile();
//        System.out.println("Checking file path:"+file.getAbsolutePath());
//        try {
//            List<String> fileContent = Files.readAllLines(Path.of("students.json"));
//            System.out.println(Files.readAllLines(Path.of("students.json")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println();

        //-------------------------------------------------------------------
        File oldFile = new File("students.json");
        File newFile = new File("students-activity.json");
//        if(oldFile.exists()){
//            oldFile.renameTo(newFile);
//            System.out.println("File renamed successfully");
//        }else {
//            System.out.println("file does not exist");
//        }

        /*
         Here, in the above code we see there is a warning for renameTo() as we are ignoring the result of it which is
         a bad implementation. Actually we are not capturing whether the renaming failed.
         -> As we know the java.io classes does not throw any exception, instead, they simply returns boolean,
            and we don't have good window to analyze what went wrong if we do get false result.
         -> We are going to use the "Path" and the Files class.
            The "toPath()" actually take an existing file instance and convert it into an NIO2 path instance.
            The Path instance has a similar method "toFile()", so we can work with both IO and NIO.2 classes
            by converting between them.
            --> Files class does not have method to rename file, so here "move()" has been used.
         */

//        Path oldPath = oldFile.toPath();
//        Path newPath = newFile.toPath();
//        try{
//            Files.move(oldPath, newPath);
//            System.out.println("Path renamed Successfully");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        /*
         Change the path we're moving to, adding a subfolder as part of the path.
         While creating "newPath1" we have to remember that the Files.move() does not create sub directory. So initially
         when we run the Files.move() with "newPath1" holds the Path.of("files/students-activity.json") it actually
         will throw a NoSuchFileException. So we have to make sure the directory/subdirectories are present before
         invoking move with subdirectory. It throws NoSucFileException exception.

         For this we have to check and move the files which can be done using "getParent()" method.
         Files.createDirectories(newPath1.getParent());

         getParent() -> preserves the root, meaning if path is absolute
                        Path newPath1 = Path.of("/Users/suman/files/student-activity.json");
                      it returns "/Users/suman/files/"
         subPath()   -> newPath1.subPath(0, newPath1.getNameCount() - 1)
                      it returns "Users/suman/files/", root "/" is lost which makes it as relative path.
                      In this case pathNameCount() returns 4
                      so newPath1.subPath(0,4-1);

                      However, the biggest issue with subPath() is, if the Path has only one element,
                      Path newPath1 = Path.of("student-activity.json");
                      newPath1.subPath(0, newPath1.getNameCount() - 1); // newPath1.subPath(0, 1 - 1);
                      newPath.subPath1(0,0); // it throws IllegalArgumentException;
         */

//        Path oldPath1 = Path.of("students.json");
//        Path newPath1 = Path.of("files/students-activity.json");
//        try{
//            Files.createDirectories(newPath1.getParent()); // creates  the directory if not exist.
//            Files.move(oldPath1,newPath1);
//            System.out.println("Path renamed Successfully");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        // Using subPath
//        try{
//            Files.createDirectories(newPath1.subPath(0,newPath1.getNameCount() - 1)); // creates  the directory if not exist.
//            Files.move(oldPath1,newPath1);
//            System.out.println("Path renamed Successfully");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        /*
         Using Files.move() rename directories.
         */

        Path fileDir = Path.of("files");
        Path resourcesDir = Path.of("resources");
//        try{
//            Files.move(fileDir,resourcesDir, StandardCopyOption.REPLACE_EXISTING); // replacing existing if exist
//            System.out.println("Directory has been renamed");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        /* ---------------------------------------------------------------------------------------------------------
         Example of Copy and delete files from the file system.
         */

        // Copying the file to another directory (Shallow copy where the directory contents are not copied)
//        try{
//            Files.copy(fileDir,resourcesDir); // creating shallow copy of the fileDir.
//            System.out.println("Directory has been copied");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        // Copying the file to another directory (Deep copy where the directory contents are copied)
//        try{
//            deleteResource(resourcesDir);
//            resourceCopy(fileDir,resourcesDir); // creating shallow copy of the fileDir.
//            System.out.println("Directory has been copied to" + resourcesDir);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        /*
         Explore a method on both the Reader and the InputStream interfaces, called "transferTo".
         This method was added in InputStream in JDK9, and the Reader interface in JDK10.
         reader.transferTo() actually creating student-backup.json and copying the file's contents to it,
         which we can do it using "Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING)". We can prefer
         to use Files.copy() when we are working within the file system to take the advantage of file system provider.
         -> We can use reader.transferTo() when we are dealing with very large file, especially if a file is being
            copied across different network drives.
         */
        // Setup BufferReader for the student-activity.json which is under sub folder of files.

//        try(BufferedReader reader = new BufferedReader(new FileReader("files/Data/students-activity.json"));
//            PrintWriter writer = new PrintWriter("student-backup.json")){
//            // it's actually creating student-backup.json and copying the file's contents to it, which we can do it
//            // using Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING)
//            reader.transferTo(writer);
//        }catch (IOException e){
//            throw new RuntimeException(e);
//        }

        /*
         Another example of transferTo() method.
         We are using java.net package to make a request to website to get the json response
         */

        String urlString = "https://api.census.gov/data/2019/pep/charagegroup?get=NAME,POP&for=state:*";
        URI uri = URI.create(urlString); // URL coming from the java.net
        try(var urlInputStream = uri.toURL().openStream()){
            urlInputStream.transferTo(System.out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Path jsonPath = Path.of("files/data/USPopulationByState.txt");
        try(InputStreamReader reader = new InputStreamReader(uri.toURL().openStream());
        BufferedWriter writer = Files.newBufferedWriter(jsonPath)){
            reader.transferTo(writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public static File getFile(){
//        System.out.println("cwd: "+ new File("").getAbsolutePath());
//        String cwd = new File("").getAbsolutePath();
//
//        return new File("students.json");
//    }

    public static void resourceCopy(Path source, Path target) throws IOException{
        Files.copy(source,target, StandardCopyOption.REPLACE_EXISTING); // to avoid DirectoryNotEmptyException
        if(Files.isDirectory(source)){
           try(Stream<Path> children =  Files.list(source)){
               children.toList().forEach(sourcePath -> {
                   try {
                       // target.resolve(sourcePath.getFileName()) concatenating file name from source to target
                       resourceCopy(sourcePath, target.resolve(sourcePath.getFileName()));
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }
               });
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
        }
    }

    public static void deleteResource(Path target) throws IOException{
        if(Files.isDirectory(target)){
            try(Stream<Path> path = Files.list(target)){
                path.toList().forEach(path1 -> {
                    try {
                        deleteResource(path1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
        Files.deleteIfExists(target);
    }

    public static void recurseDelete(Path target) throws IOException{
        System.out.println(target.toAbsolutePath());
        Path absolutePath = target.toAbsolutePath();
        if(Files.isDirectory(target)){
           try(Stream<Path> files = Files.list(target)) { // navigating path through Files.List()
                    files.forEach(p -> {
                   try {
                       recurseDelete(p); // recursively calling recurseDelete()
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }
               });
           }
        }
        Files.deleteIfExists(target);
    }

    /**
     * Correct Version (Professional Way)
     * @param target
     * @throws IOException
     */
    public static void recurseDeleteWalk(Path target) throws IOException{
        try(Stream<Path> walk = Files.walk(target)){ // Navigating path through Files.walk()
            walk.sorted(Comparator.reverseOrder()) // deleting child first
                .forEach(p -> {
                    try{
                        Files.delete(p);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
