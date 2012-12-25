package treehashes;




import java.io.*;

import myinterfaces.MyTreesandHashes;

import trees.BTree;
/*
 * Class that front ends the BTree class with a Hash table
 */
public class BTreeHash implements MyTreesandHashes<Integer,Integer>
{
	 int hashSize;
	 BTree[] bTreeHashTable;
	public BTreeHash(int s,int order) throws Exception
	{
		if(s%2==0) s+=1;
		hashSize = s;
		System.out.println("Hash Table size" + hashSize);
		bTreeHashTable = new BTree[hashSize];
		for(int i=0;i<hashSize;i++)
		{
			bTreeHashTable[i] = new BTree(order);
		}
	}
	/*
	 * Function to Put the key value pair int he tree instance that is at the hashIndex
	 */
	public Integer put(Integer key,Integer value)
	{
		int hashIndex = hashCode(key);
		
		int v = bTreeHashTable[hashIndex].put(key, value);
		return v;
	}
	/*
	 Function to get the value of a search key from the tree instance in the hashtable.
	 The tree instance is obtained by hasing key value hashCode against the hash table.
	 */
	
	public Integer get(Integer key)
	{
		int hashIndex = hashCode(key);
		int v = bTreeHashTable[hashIndex].get(key);
		return v;
	}
	/*
	 * generates the hashCode. In this case.. it's key%hashSize.
	 */
	
	public int hashCode(Integer key){
		return key%hashSize;
	}
	/*
	 * Function for the sorted order traversal of the BTrees in the hash table
	 */
	public void sortedOrder(PrintWriter pw)throws Exception{
		for(int i=0;i<hashSize;i++){
			pw.println("From Tree " + i);
			bTreeHashTable[i].sortedOrder(pw);
		}
	}
	/*
	 * Function for the sorted order traversal of the BTrees in the hash table
	 */
	public void levelOrder(PrintWriter pw) throws Exception{
		for(int i=0;i<hashSize;i++){
			pw.println("From Tree " + i);
			bTreeHashTable[i].levelOrder(pw);
		}
		
	}
	
}


