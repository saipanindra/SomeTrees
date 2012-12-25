package treehashes;



/*
 * Frontends the AVL Tree with the AVL Hash
 */
import java.io.*;

import myinterfaces.MyTreesandHashes;

import trees.AVL;
public class AVLHash implements MyTreesandHashes<Integer,Integer>
{
	 int hashSize;
	 AVL[] avlHashTable;// A hashtable of avl trees
	public AVLHash(int s) throws Exception
	{
		if(s%2==0) s+=1;
		hashSize = s;
		avlHashTable = new AVL[hashSize];
		for(int i=0;i<hashSize;i++)
		{
			avlHashTable[i] = new AVL();//initializing the table with avl tree entries
		}
	}
	/*
	 * Function to Put the key value pair int he tree instance that is at the hashIndex
	 */
	public Integer put(Integer key,Integer value)
	{
		int hashIndex = hashCode(key);
		//System.out.println("Hash Index:" + hashIndex);
		int v = avlHashTable[hashIndex].put(key, value);
		return v;
	}
	/*
	 Function to get the value of a search key from the tree instance in the hashtable.
	 The tree instance is obtained by hasing key value hashCode against the hash table.
	 */
	public Integer get(Integer key)
	{
		int hashIndex = hashCode(key);
		int v = avlHashTable[hashIndex].get(key);
		return v;
	}
	/*
	 * generates the hashCode. In this case.. it's key%hashSize.
	 */
	public int hashCode(int key){
		return key%hashSize;
	}
	/*
	 * Inorder traversal of a AVLTrees in the hash table
	 */
	public void inorder(PrintWriter pw)throws Exception{ 
		
		for(int i=0;i<hashSize;i++){
		    pw.println("From Tree " + i);
			avlHashTable[i].inorder(pw);
		}
		
	}
	/*
	 * Post order traversal of a AVLHash
	 */
	public void postorder(PrintWriter pw) throws Exception{
		for(int i=0;i<hashSize;i++){
			pw.println("From Tree " + i);
			avlHashTable[i].postOrder(pw);
		}
	}
	
}


