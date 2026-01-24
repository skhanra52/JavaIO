package com.skhanra52;

/*
 Walking the File tree:--------------------------------
     -> The "walkFileTree()" method walks the file tree, depth first(as does the walk method).
     -> "Depth First": It means that the code will recursively visit all the child elements of the folder,
        before visiting any other folder's siblings(Folder available in the same level).
        Example: If the current working directory(cwd) has two folders "out", "src".
        If the first folder "out" contains a folder "dev" and the folder "dev" contains a file Walker.class.
        Visually it look like <DIR> out > <DIR> dev > Walker.class.
        And the second folder "src" contains a folder "dec" and the folder "dev" contains a file Walker.class.
        Visually it look like <DIR> src > <DIR> dev > Walker.class.
        -> And if we set "out" in the walkFileTree() method then it will visit <DIR> out and
            all its child folders/file. Then it will go to src folder. Depending on what we are giving as input.
     -> "Breath First": This is the alternative to "Depth First". In this any child node of the particular folder
        are walked after the sibling(same level folders/file) folders/file visited.

Why this is important?----VVI------------------------------
     Because it is "Depth First", the "Files.walkFileTree()" method provides a mechanism to accumulate information
     about all the children, up to the parent.
     -> Java provides "entry points"(Entry point = a method Java calls automatically, like callback function)
        in the walk to execute operations through a "FileVisitor" interface.
     -> This stubs out(default implementations) method we can implement at certain "event" in the walk.
     NOTE:
        (Meaning of above two lines, when Java walk through a directory tree (folders + files),
        it gives us hooks(entry points) where our code can run. We don't control how Java walks the files.
        Instead, java says:
        "Hey, whenever something interesting happens during the walk, I will call your method."
        Those "interesting moments" called events.)

        These events are:                       | (corresponding method of FileVisitor Interfaces )
        -> Before visiting the directory.       | preVisitDirectory(T dir, BasicFileAttributes attrs)
        -> When visiting the file.              | visitFile(T file, BasicFileAttributes attrs)
        -> A failure to visit the file.         | visitFileFailed(T file, IOException exc)
        -> After visiting the directory.        | postVisitDirectory(T dir, IOException exc)

        Java gives us empty stubs (default implementations) of above methods. We can override only what you care about.

 What is FileVisitor? --------------------
    FileVisitor Interface and the SimpleFileVisitor class signature given below:
    <<interface>>
    FileVisitor<T>
        preVisitDirectory(T dir, BasicFileAttributes attrs)
        visitFile(T file, BasicFileAttributes attrs)
        visitFileFailed(T file, IOException exc)
        postVisitDirectory(T dir, IOException exc)

    Decode each event: ----------------------
        1. preVisitDirectory(T dir, BasicFileAttributes attrs)
           Meaning, we are about to go inside the folder. Before getting in do we want to do something first?
           Use cases:
             -> Print directory name.
             -> Skip directory.
             -> Check permissions.
        2. visitFile(T file, BasicFileAttributes attrs)
           Meaning, we found a file, what should we do with it?
           Use cases:
             -> Read/Write/Update/Delete file.
             -> Count files.
             -> Search contents.
        3. visitFileFailed(T file, IOException exc)
           Meaning, we tried to access this file, however, something went wrong.
           Reasons:
             -> No Permission.
             -> File deleted mid-walk.
             -> Locked file.
        4. postVisitDirectory(T dir, IOException exc)
           Meaning, we are done with the directory, and all its files.
           Use Cases:
             -> Cleanup, logging.
             -> Summary after processing folder.


    ------------------------------------------------------------------------------------
    Similarly, we have SimpleFileVisitor class.
    class SimpleFileVisitor
        postVisitDirectory(T dir, IOException exc)
        preVisitDirectory(T dir, BasicFileAttributes attrs)
        visitFile(T file, BasicFileAttributes attrs)
        visitFileFailed(T file, IOException exc)
    ------------------------------------------------------------------------------------
    Return type for both:
    <<Enumeration>>
    FileVisitResult
        CONTINUE
        SKIP_SIBLINGS
        SKIP_SUBTREE
        TERMINATE

 */

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class FileWalkerMain {

    public static void main(String[] args) {

        Path startingPath = Path.of(".");

        FileVisitor<Path> statsVisitor = new StatsVisitor();
        try {
            Files.walkFileTree(startingPath,statsVisitor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private static class StatsVisitor extends SimpleFileVisitor<Path> {

        private Path initialPath = null;
        // values of map represent the cumulative size of the folder.
        private final Map<Path, Long> folderSizes = new LinkedHashMap<>(); // LinkedHashMap to maintain insertion order
        private int initialCount;

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Objects.requireNonNull(file);
            Objects.requireNonNull(attrs);
            folderSizes.merge(file.getParent(), 0L, (o,n) -> o += attrs.size());
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            Objects.requireNonNull(dir);
            Objects.requireNonNull(attrs);
            if(initialPath == null){
                initialPath = dir;
                initialCount = dir.getNameCount();
            } else {
                int relativeLevel = dir.getNameCount() - initialCount;
                if(relativeLevel == 1){
                    folderSizes.clear();
                }
                folderSizes.put(dir, 0L);
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Objects.requireNonNull(dir);
            if(dir.equals(initialPath)){
                return FileVisitResult.TERMINATE;
            }

            int relativeLevel = dir.getNameCount() - initialCount;
            if(relativeLevel == 1){
                folderSizes.forEach((key, value) -> {
                    int level = key.getNameCount() - initialCount  -1;
                    System.out.printf("%s[%s] - %d byte %n", "\t".repeat(level), key.getFileName(), value);
                });
            }else {
                long folderSize = folderSizes.get(dir);
                folderSizes.merge(dir.getParent(), 0L, (o,n) -> o += folderSize);
            }
            return FileVisitResult.CONTINUE;
        }

        /*
         Let say we want to get the total number of bytes of a folder, or the sum of its file sizes, and we want each
         parent's sizes.
         */
    }

}
