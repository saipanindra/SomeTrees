package trees;

import java.io.*;
import java.util.Queue;
import java.util.LinkedList;
import myinterfaces.MyTreesandHashes;
/*
 * Each Pair holds a key and a value
 */
class Pair
{
	int key;
	int value;
	Pair(int k,int v){
		key = k;
		value = v;
	}

	public String toString()
	{
		return "Key: " + key + " Value : " + value;
	}
}

/*
 * Each BTree Node holds a collection of pairs, number of pairs at any given time and a flag which
 * indicates if the BTreeNode is a leaf.
 */
class BTreeNode
{
	int n;
	BTreeNode c[];
	Pair pair[];
	boolean leaf;
	BTreeNode(int m){
		n = 0;
		c = new BTreeNode[m];

		pair = new Pair[m];
		for(int i =0 ;i<m;i++){
			pair[i] = new Pair(-1,-1);
		}

		leaf = true;// the newly created node is a leaf.
	}
	public String toString(){
		String res="";
		for(int i=0;i<n;i++){
			res = res + pair[i].key +"\n";
		}
		return res;
	}
}

/*
 * The BTree class provides the put, get ,inorder and post order traversal opertaions on a BTree
 */
public class BTree implements MyTreesandHashes<Integer,Integer>{
	private BTreeNode root;
	private int m;
	private int t;
	public BTree(int m){
		root = new BTreeNode(m);
		this.m = m;
		t = (m%2==0 ? m/2: (m+1)/2); // t is the minimal number of nodes that an in an internal node shoudl possess

	}

	/*
	 * inserts a Pair with key , value into the BTree.
	 */
	int insert(Pair p){

		BTreeNode r = root;
		int limit = (m%2==0)?(2*t-1):(2*t-2);
		if(r.n == limit){ //if the maximum allowed number of pairs is reached within a root node.
			BTreeNode s = new BTreeNode(m);
			root = s;
			s.leaf = false;
			s.n = 0;
			s.c[0] = r;
			BTreeSplitChild(s,0,r);    // call split child on the 0th child(r) of the root(here)
			BTreeInsertNonFull(s,p);   // After split, insert the new Pair into the split node.
		}
		else{
			// if not full, insert in a non full node
			BTreeInsertNonFull(r,p);
		}
		return p.value;
	}
	/*
	 * The function inserts a key value pair in the file. Modeled after put method in TreeMap class	
	 */
	public Integer put(Integer key,Integer value){
		int v = insert(new Pair(key,value));
		return v;
	}
	/*
	 * Function for inserting into a non full node.
	 */
	void BTreeInsertNonFull(BTreeNode x,Pair p){
		int i = x.n;

		/*
		 * if the node is a leaf, realign the pairs to accomodate he new pair.
		 */
		if(x.leaf){

			while(i>=1 && p.key < x.pair[i-1].key){

				x.pair[i]  = x.pair[i-1];
				i--;

			}
			x.pair[i] = p;
			x.n++;

		}
		/*
		 * If the node is not a leaf, search of a pointer which points to the appropriate node from among
		 * child pointers
		 */
		else{
			while(i>=1 && p.key < x.pair[i-1].key){
				i--;
			}
			i++;
			int limit2 = (m%2==0)?(2*t-1):(2*t-2);
			if(x.c[i-1].n == limit2){
				BTreeSplitChild(x,i-1,x.c[i-1]);// If the node reached after searching is a full node, split it.
				if( p.key > x.pair[i-1].key)
					i++;
			}

			//After split, insert the pair into the split node
			BTreeInsertNonFull(x.c[i-1],p);
		}


	}
	/*
	 * Function to split a node y which is ith child of x.
	 */
	void BTreeSplitChild(BTreeNode x,int i,BTreeNode y){
		BTreeNode z = new BTreeNode(m); // create a new node to accomodate the element to the right of the median.
		z.leaf = y.leaf;
		//The number of elements to the right of the median value depends on the order of the tree.
		if(m%2!=0) z.n = t-2;  
		else z.n = t - 1;
		//Move the pairs to the right of median into the new created node z.
		for(int  j = 1 ; j <= t-1;j++ ){
			if(y.pair[j+t-1].key == -1) break;
			z.pair[j-1] = y.pair[j+t-1];
		}

		//if the node being split is not a child node, also move it's child pointers to the 
		//newly created node z
		
		if(!y.leaf){

			int max = (m%2==0)? t : t-1;
			for(int j = 1 ; j<=max ;j++){
				z.c[j-1] = y.c[j+t-1];
			}
		}


        //Now that the pairs and children from the node to be split are moved, it's size can 
		//be reduced.
		y.n = t-1;

        //realign child pointers in parent node so as to accomodate the new node z that is created
		for(int j = x.n+1;j>=i+1;j--){
			x.c[j] = x.c[j-1];
		}
		// move new node with the pairs to the right of the median and the corresponding child pointers,
		// to the parent node x.
		x.c[i+1] = z;
		
		if(x.n!=0){
			for(int j = x.n;j>i;j--){
				x.pair[j]= x.pair[j-1];
			}


			x.pair[i] = y.pair[t-1];
		}
		else
		{
			x.pair[0] = y.pair[t-1];
		}
		x.n++;

	}
  /*
   * The search function starts with a BTreeNode and searches for the key.
   * If key is found, Pair(key,value) is returned . Else a null is returned.
   */
	public Pair search(BTreeNode startNode,int key){

		int i =0;
		while(i< startNode.n && key>startNode.pair[i].key )
		{
			i= i + 1;
		}
		if(i<startNode.n && key == startNode.pair[i].key)
			return startNode.pair[i];
		else if(startNode.leaf) return null;
		else 
			return search(startNode.c[i],key);

	}
  /*
   * Uses the search function to search the Tree starting from the root.
   */
	public Integer get(Integer key){
		Pair p = search(root,key);
		if(p == null){
			return -1;
		}
		return p.value;
	}
	/*
	 * Uses sortedOrder(startNode) function to traverse the tree in sorted order starting from root. 
	 */
	public void sortedOrder(PrintWriter pw){
		sortedOrder(root,pw);
	}
	/*
	 * Traverses the tree in sorted order from a startNode 
	 * and prints the result to a file instance wrapped in printWriter interface.
	 */
	public void sortedOrder(BTreeNode startNode,PrintWriter pw){
		if(startNode.leaf){
			for(int i=0;i<startNode.n;i++)
			{
				pw.println(startNode.pair[i].key+ " "+ startNode.pair[i].value);
				
			}
			return;
		}
		int j = 0;
		while(j<startNode.n)
		{
			if(startNode.c[j]!=null)
				sortedOrder(startNode.c[j],pw);
			pw.println(startNode.pair[j].key+ " "+ startNode.pair[j].value);
			j++;	
		}
		if(startNode.c[j]!=null)
			sortedOrder(startNode.c[j],pw);

	}
	/*
	 * Traverses the tree in level order from the root. 
	 * 
	 */
	public void levelOrder(PrintWriter pw){
		levelOrder(root,pw);
	}
	/*
	 * Traverses the tree in level order from a startNode 
	 * and prints the result to a file instance wrapped in printWriter interface.
	 */
	public void levelOrder(BTreeNode startNode,PrintWriter pw){
		Queue<BTreeNode> q = new LinkedList<BTreeNode>();
		BTreeNode bt = startNode;
		q.offer(bt);//enquing the startNode
		while(!q.isEmpty())
		{
			BTreeNode node = q.remove();//Deque the node and print the pairs in the node
			for(int i = 0 ;i<node.n;i++){
				pw.println(node.pair[i].key+ " "+node.pair[i].value);
				
			}
			for(int i=0; i<=node.n;i++){//After the parent node is dequed, enque all the child nodes
				if(node.c[i]!=null)
					q.offer(node.c[i]);
			}
		}

	}




}

