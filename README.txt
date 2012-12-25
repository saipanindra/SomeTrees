SomeTrees
=========

What do the folders contain?

myinterfaces : There is an interface in this folder. All the trees and treehashtables implement this interface. This ensures that the put and get methods
-------------
that the trees and treehashes follow the standard of the put & get methods of the TreeMap interface

trees: This folder has the source files for AVL, BTree and Red Black tree(I picked the source for this one from OpenJDK source)
------
treehashes : This foldder has the fource files for the above trees frontended with hash table.
-----------

How do you run the program?

One of the files you will find is the dictionary.java

There are two modes in which it operates.
 
1) java dictionary -r <hashtablesize> <bTreeOrder>) 
 // give the hash table size and bTreeOrder and wait to see the comparision of average performance of the three trees(AVL,BTree,RedBlack)
    for the insert and search operations.

2) java dictionary -u <filename> 
//  give the filename of file which has the inputs for the trees to operate on.
// There is a sample file inputKeysandValues you will find .
// Input format is ..
    
    <number of keys(n)>
    <key1> <value1>
    <key2> <value2>
    ..
    <keyn> <valuen>
