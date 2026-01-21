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

    Why this is important?----------------------------------
     Because it is "Depth First", the "Files.walkFileTree()" method provides a mechanism to accumulate information
     about all the children, up to the parent.
     -> Java provides entry points in the walk to execute operations through a "FileVisitor" interface.
     -> This stubs out method we can implement at certain event in the walk.
        These events are:
        -> Before visiting the directory.
        -> After visiting the directory.
        -> When visiting the file.
        -> A failure to visit the file.
     ---------------------------------------------------------------------------------------------
     FileVisitor Interface and the SimpleFileVisitor class signature given below:
     <<interface>>
     FileVisitor<T>
       postVisitDirectory(T dir, IOException exc)
       preVisitDirectory(T dir, BasicFileAttributes attrs)
       visitFile(T file, BasicFileAttributes attrs)
       visitFileFailed(T file, IOException exc)

    Similarly, we have SimpleFileVisitor class.
    class SimpleFileVisitor
      postVisitDirectory(T dir, IOException exc)
      preVisitDirectory(T dir, BasicFileAttributes attrs)
      visitFile(T file, BasicFileAttributes attrs)
      visitFileFailed(T file, IOException exc)

    Return type for both:
    <<Enumeration>>
    FileVisitResult
      CONTINUE
      SKIP_SIBLINGS
      SKIP_SUBTREE
      TERMINATE
 */

public class FileWalker {

}
