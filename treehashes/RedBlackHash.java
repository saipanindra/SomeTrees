package treehashes;

import java.io.PrintWriter;

import trees.RedBlackTree;
import myinterfaces.MyTreesandHashes;
/*
 * Class that provides The RedBlackHashTable of RedBlackTrees and provides operations
 * put,get,inorder and postorder on the RedBlackHashTable
 */
public class RedBlackHash implements MyTreesandHashes <Integer,Integer>{
		 int hashSize;
		 RedBlackTree<Integer,Integer>[] redblackHashTable;
		@SuppressWarnings("unchecked")
		public RedBlackHash(int s) throws Exception
		{
			if(s%2==0) s+=1;
			hashSize = s;
			
			redblackHashTable = new RedBlackTree[hashSize];
			for(int i=0;i<hashSize;i++)
			{
				redblackHashTable[i] = new RedBlackTree<Integer,Integer>();
			}
		}
		/*
		 * The function inserts a key value pair in the file. Modeled after put method in TreeMap class
		 */
		public Integer put(Integer key,Integer value)
		{
			int hashIndex = hashCode(key);
			//System.out.println("Hash Index:" + hashIndex);
			int v = redblackHashTable[hashIndex].put(key, value);
			return v;
		}
		/*
		 * The function searches for a key value and returns the value of key if search is successful else returns a null
		 */
		public Integer get(Integer key)
		{
			int hashIndex = hashCode(key); //(key) mod s as the hashIndex
			int v = redblackHashTable[hashIndex].get(key);
			return v;
		}
		/*
		 * The function returns the hashCode for the key. In this specific case, it returns the key
		 * itself;
		 */
		public int hashCode(int key){
			return key%hashSize;
		}
		/*
		 * This function does a inorder traversal on the Trees in the RedBlackHash table
		 * The inorder traversal result is written out to the file instance that is passed(Wrapped in PrintWriter)
		 */
		public void inorder(PrintWriter pw)throws Exception{ 
			
			for(int i=0;i<hashSize;i++){
			    pw.println("From Tree " + i);
				redblackHashTable[i].inorder(pw);
			}
			
		}

		/*
		 * This function does a post order traversal on the Trees in the RedBlackHash table
		 * The postorder traversal result is written out to the file instance that is passed(Wrapped in PrintWriter)
		 */
		public void postorder(PrintWriter pw) throws Exception{
			for(int i=0;i<hashSize;i++){
				pw.println("From Tree " + i);
				redblackHashTable[i].postorder(pw);
			}
		}
		
	}


