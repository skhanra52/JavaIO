package com.skhanra52.managingFileOper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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

        Path oldPath = oldFile.toPath();
        Path newPath = newFile.toPath();
        try{
            Files.move(oldPath, newPath);
            System.out.println("Path renamed Successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /*
         Change the path we're moving to, adding a subfolder as part of the path.
         While creating "newPath1" we have to remember that the Files.move() does not create sub directory. So initially
         when we run the Files.move() with "newPath1" holds the Path.of("files/students-activity.json") it actually
         will throw a NoSuchFileException. So we have to make sure the directory/subdirectories are present before
         invoking move with subdirectory. It throws NoSucFileException exception.
         For this we have to check and move the files which can be done using
         Files.createDirectories(newPath1.getParent());

         */

        Path oldPath1 = Path.of("students.json");
        Path newPath1 = Path.of("files/students-activity.json");
        try{
            Files.createDirectories(newPath1.getParent()); // creates  the directory if not exist.
            Files.move(oldPath1,newPath1);
            System.out.println("Path renamed Successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getFile(){
        System.out.println("cwd: "+ new File("").getAbsolutePath());
        String cwd = new File("").getAbsolutePath();

        return new File("students.json");
    }
}
