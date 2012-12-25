

import java.io.PrintWriter;

import java.io.*;

import java.util.*;

import myinterfaces.MyTreesandHashes;

import treehashes.*;
import trees.*;

public class dictionary {

	public static void main(String[] args)throws Exception{

		if((args[0].equals("-r") && args.length!=3)||(args[0].equals("-u") && args.length!=2)){
			System.out.println("For Random mode : dictionary -r <hashTableSize> <bTreeOrder>\nFor User mode dictionary -u <filename>");
		}
		else{
			if(args[0].equals("-r")){//random mode


				int s = Integer.parseInt(args[1]);
				int order = Integer.parseInt(args[2]);
				System.out.println("In Random Mode");
				List<Integer> lst = new ArrayList<Integer>();
				for(int i=1;i<=1000000;i++) lst.add(i); //adding n elements to the list to be shuffled.
                //initialize search and insert times for each structure to 0.
				double time_avlTree_insert = 0;
				double time_avlTree_search = 0;
				double time_BTree_insert = 0;
				double time_BTree_search = 0;
				double time_RedBlackTree_insert = 0;
				double time_RedBlackTree_search = 0;
				double time_bTreeHash_insert = 0;
				double time_bTreeHash_search = 0;
				double time_avlHash_insert = 0;
				double time_avlHash_search = 0;
				double time_redBlackHash_insert = 0;
				double time_redBlackHash_search = 0;
				BTreeHash bTreeHash = null;
				AVLHash avlTreeHash = null;
				RedBlackHash redBlackHash = null;
				BTree bTree  = null;
				AVL avlTree = null;
				RedBlackTree<Integer,Integer> redBlackTree = null;
				//performing the inserts and searche experiment for 1000000 elements 10 times.
				for(int j=1;j<=10;j++)
				{
					System.out.println("-----------------");
					System.out.println("Iteration " + j);
					System.out.println("-----------------");
					//initializing trees in each iteration
					bTreeHash = new BTreeHash(s,order);
					avlTreeHash = new AVLHash(s);
					redBlackHash = new RedBlackHash(s);
					avlTree = new AVL();
					bTree = new BTree(order);
					redBlackTree = new RedBlackTree<Integer,Integer>();
					
					Collections.shuffle(lst);//list is shuffled for each iteration ( 10 times)
					
					MyTreesandHashes<Integer,Integer> bth = (MyTreesandHashes<Integer,Integer>)bTreeHash;
					MyTreesandHashes<Integer,Integer> ath = (MyTreesandHashes<Integer,Integer>)avlTreeHash;
					MyTreesandHashes<Integer,Integer> rth = (MyTreesandHashes<Integer,Integer>)redBlackHash;
					MyTreesandHashes<Integer,Integer> bt = (MyTreesandHashes<Integer,Integer>)bTree;
					MyTreesandHashes<Integer,Integer> at = (MyTreesandHashes<Integer,Integer>)avlTree;
					MyTreesandHashes<Integer,Integer> rbt = (MyTreesandHashes<Integer,Integer>)redBlackTree;

					//getSearchTime and getInsertTime functions return the time after searching for
					//and inserting 10000 elements from lst parameter for the tree instance that is passed 
                    System.out.println("BTree Hash Insertion started..");
					time_bTreeHash_insert += getInsertTime(bth,lst);
                    System.out.println("BTree Hash Insertion done..");
                    System.out.println("BTree Hash Search started..");
                    time_bTreeHash_search += getSearchTime(bth,lst);
                    System.out.println("BTree Hash Search done..");
                    System.out.println("AVL Hash Insertion started..");
                    time_avlHash_insert += getInsertTime(ath,lst);
                    System.out.println("AVL Hash Insertion done..");
                    System.out.println("AVL Hash Search started..");
                    time_avlHash_search += getSearchTime(ath,lst);
                    System.out.println("AVL Hash Search done..");
                    System.out.println("Red Black Hash Tree Insertion started..");
                    time_redBlackHash_insert += getInsertTime(rth,lst);
                    System.out.println("Red Black Hash Tree Insertion done..");
                    System.out.println("Red Black Hash Tree Search started..");
                    time_redBlackHash_search += getSearchTime(rth,lst);
                    System.out.println("Red Black Hash Tree Search done..");
                    System.out.println("AVL Tree Insertion started..");
                    time_avlTree_insert  += getInsertTime(at,lst);
                    System.out.println("AVL Tree Insertion done..");
                    System.out.println("AVL Tree Search started..");
                    time_avlTree_search += getSearchTime(at,lst);;
                    System.out.println("AVL Search done..");
                    System.out.println("BTree Insertion started..");
                    time_BTree_insert += getInsertTime(bt,lst);
                    System.out.println("BTree Insertion done..");
                    System.out.println("BTree Search started..");
                    time_BTree_search += getSearchTime(bt,lst);
                    System.out.println("BTree Search done..");
                    System.out.println("RedBlack Tree Insertion started..");
					time_RedBlackTree_insert += getInsertTime(rbt,lst);;
					System.out.println("RedBlack Tree Insertion done..");
					System.out.println("RedBlack Tree Search started..");
					time_RedBlackTree_search += getSearchTime(rbt,lst);
					System.out.println("RedBlack Tree Search done..");


				}
				//Reporting average time for insert and search for the trees.
				System.out.println("---------------------------------------");
				System.out.println("Time Report");
				System.out.println("---------------------------------------");
				
				System.out.println("Average AVLTree  insert time:" + (time_avlTree_insert / 10));
				System.out.println("Average AVLTree  search time:" + (time_avlTree_search / 10));
				System.out.println("Average AVLTree Hash insert time:" + (time_avlHash_insert / 10)); 
				System.out.println("Average AVLTree Hash search time:" + (time_avlHash_search / 10));
				System.out.println("\n");
				System.out.println("Average BTree  insert time:" + (time_BTree_insert / 10)); 
				System.out.println("Average BTree  search time:" + (time_BTree_search / 10));
				System.out.println("Average BTree Hash insert time:" + (time_bTreeHash_insert / 10)); 
				System.out.println("Average BTree Hash search time:" + (time_bTreeHash_search / 10));
				System.out.println("\n");
				System.out.println("Average RedBlackTree insert time:" + (time_RedBlackTree_insert / 10));
				System.out.println("Average RedBlackTree search time:" + (time_RedBlackTree_search / 10));
				System.out.println("Average RedBlackTree Hash insert time:" + (time_redBlackHash_insert / 10));
				System.out.println("Average RedBlackTree Hash search time:" + (time_redBlackHash_search / 10));
				
			}
			else if(args[0].equals("-u")){//user mode
				BTreeHash bTreeHash = new BTreeHash(3,3);//passing tree order and hash table size as 3 in user mode
				AVLHash avlTreeHash = new AVLHash(3);//hash table size = 3
				RedBlackHash redBlackHash = new RedBlackHash(3);//hash Table size = 3
				AVL avl = new AVL();
				BTree bTree = new BTree(3);//order = 3 in user mode
				RedBlackTree<Integer,Integer> redBlackTree = new RedBlackTree<Integer,Integer>();
				//RandomListGenerator.writeRandom(1000);
				File f = new File(args[1]);
             
				if(f.exists())
				{
					Scanner input = new Scanner(f);
					int numberOfKeys = input.nextInt();
					System.out.println("Number of keys to be read from file: "+numberOfKeys);
					int count = 0;
					//Reading from the file "inputKeysandValues".The file has  collection of n 
					//key value pairs
					while(count<=numberOfKeys && input.hasNext())//putting values found in file sequence into the trees and hashed trees one by one
					{
						int key = input.nextInt();
						int value = input.nextInt();
						bTreeHash.put(key,value);
						avlTreeHash.put(key, value);
						redBlackHash.put(key, value);
						avl.put(key, value);
						bTree.put(key, value);
						redBlackTree.put(key, value);
						count ++ ;


					}
					input.close();
					System.out.println("Insertion into all Trees and hashTrees done...");

					//Creating file instances for writing for each of the traversals
					PrintWriter pw1 = new PrintWriter(new File("AVL_inorder.out"));
					PrintWriter pw2 = new PrintWriter(new File("AVL_postorder.out"));
					PrintWriter pw3 = new PrintWriter(new File("AVLHash_inorder.out"));
					PrintWriter pw4 = new PrintWriter(new File("BTree_sorted.out"));
					PrintWriter pw5 = new PrintWriter(new File("BTree_level.out"));
					PrintWriter pw6 = new PrintWriter(new File("BTreeHash_level.out"));

					//Writing the inorder post order traversals for the avl , avl hash and redblack hash
					//into the files. (sorted and level order traversals for BTree);

					avl.inorder(pw1); 
					avl.postOrder(pw2);
					avlTreeHash.inorder(pw3);
					bTree.sortedOrder(pw4);
					bTree.levelOrder(pw5);
					bTreeHash.levelOrder(pw6);
					pw1.close();
					pw2.close();
					pw3.close();
					pw4.close();
					pw5.close();
					pw6.close();
					
                    System.out.println("The files\nAVL_inorder.out\nAVL_postorder.out\nAVLHash_inorder.out\nBTree_sorted.out\nBTree_level.out\nBTreeHash_level.out\ncontain the corresponding traverals of the trees and hased trees");


				}
				else
					System.out.println("Input file not found");
			}
		}
	}
	/*
	 * Returns the time of search for all the objects that implement MyTreesandHashes Interface
	 * (for all the trees and hash tree data structures in this case
	 */
	public static long getSearchTime(MyTreesandHashes<Integer,Integer> obj,List<Integer> lst){

		long time_start = System.currentTimeMillis();
		int count = 0;
		for(int elem:lst)
		{
			Integer e = obj.get(elem); //search for the element
			if(e!=-1) count ++;
		}
		long time_end = System.currentTimeMillis();

		System.out.println("Finished search");
		System.out.println("Found " + count + "elements ");
		return time_end-time_start;
	}
	/*
	 * Returns the time of insert after inserting, for all the objects that implement MyTreesandHashes Interface
	 * (for all the trees and hash tree datastructures in this case
	 */
	public static long getInsertTime(MyTreesandHashes<Integer,Integer> obj,List<Integer> lst){
		long time_start = System.currentTimeMillis();
		for(int elem:lst)
		{
			obj.put(elem,2*elem);//put key  = element and value = 2*elem
		}
		long time_end = System.currentTimeMillis();
		System.out.println("Finished insertion");
		return time_end - time_start;

	}



}
