package trees;




import java.io.File;
import java.io.PrintWriter;
import java.util.Stack;
import myinterfaces.MyTreesandHashes;

/*
 * The AVL node represents each node of a an avl tree. 
 * It holds the key,value , left ,right child,balance factor of the ndoe along with the descendent 
 * node (cNode) at which the insertion started. 
 */
class AVLNode{
	int key,value;
	AVLNode left,right,cNode;
	int bf;
	//Stack<AVLNode> nodeStack;
	AVLNode(int key,int value){
		this.key = key;
		this.value = value;
		this.left = null;
		this.right = null;
		this.bf = 0;
					
	}
	 public String toString()
	{
		return "Key:"+this.key +" bf:" + this.bf;
	}
	

}
/*
 * AVL Tree 
 */
public class AVL implements MyTreesandHashes<Integer,Integer>{
	private  AVLNode root;
	private  Stack<AVLNode> auxStack;// used for bottom up traversals for rotations
	File outfile;
	PrintWriter pw;
    
    public AVL() throws Exception {root = null;
          auxStack = new Stack<AVLNode>();
          
          
          }
    
   //Returns the value for the matching searchkey if the searchkey is found. Else returns -1.
    public Integer get(Integer searchKey){
    	if(root == null) return -1;
    	else{
    		AVLNode node = root;
    		while(node!=null){
    			if(node.key == searchKey) {return node.value;}
    			else
    			{
    				if(node.key > searchKey)
    					node = node.left;
    				else if(node.key<searchKey)
    					node = node.right;
    			}
    		}
    	}
    	
    	return -1;
    }
  //Inserts key and value pair into the  AVL Tree and returns the value
    public Integer put(Integer key,Integer value){
    	AVLNode newNode = new AVLNode(key,value);
    	if(root == null){//when tree is empty
    		root = newNode;
    	}
    	else
    	{
    		AVLNode node = root;
    		AVLNode prev = node;
    		auxStack.clear();
    		while(node!=null)
    		{
    			auxStack.push(node); //push nodes into stack as we traverse down the tree
    			prev = node;
    			if(node.key>newNode.key){
    				
    				node = node.left;
    			}
    			else if(node.key<newNode.key){
    				
    				node = node.right;
    				
    			}
    		}
    	    if(node == null){
    			if(prev.key>newNode.key){ // once we reach the leaf, start updating balance factors.
    				prev.left = newNode;updateBalanceFactor(newNode);
    			}
    			else if(prev.key<newNode.key){
    				prev.right = newNode;updateBalanceFactor(newNode);
    			}
    			
    		}
    	}
    return value;
    }
  /*Inorder traversal of the AVL Tree from root. The inorder traversal result is written to the file 
    instance wrapped in PrintWriter.*/
    public void inorder(PrintWriter pw) throws Exception
    {
    	inorder(root,pw);
    }
    /*Inorder traversal of the AVL Tree from root. The inorder traversal result is written to the file 
    instance wrapped in PrintWriter.*/
    public void  inorder(AVLNode startNode,PrintWriter pw) throws Exception
    {   
    	if(startNode == null) 
    		return;
    	else
    	{
    		inorder(startNode.left,pw);
    		pw.println(startNode.key+" "+startNode.value);
    		//System.out.println(startNode);
    		inorder(startNode.right,pw);
    	}
    	
    }
  /*Postorder traversal from a startNode. This function is used by the Postorder(PrintWriter pw) 
    function for postorder traversal from root.*/
    public void postOrder(PrintWriter pw) throws Exception
    {
    	postOrder(root,pw);
    }
  /*The function is used to update the balance factor of the avl tree nodes as we move bottom up to 
    correct an imbalance.*/
    public void  postOrder(AVLNode startNode,PrintWriter pw) throws Exception
    {   
    	if(startNode == null) 
    		return;
    	else
    	{
    		postOrder(startNode.left,pw);
    		postOrder(startNode.right,pw);
    		pw.println(startNode.key+" "+startNode.value);
    		
    	}
    	
    }
    /*The function is used for single Rotation (both LR and RL cases based on the balance factor of the 
    child and parent nodes
    newNode is the newly inserted node.*/
    public  void updateBalanceFactor(AVLNode newNode){
    	
    	AVLNode childNode = newNode;
    	AVLNode parentNode;
    	if(auxStack.empty()) System.out.println("Stack empyt");
    	do{
    		parentNode = auxStack.pop();//get the previous node of the current node
    	
    		if(parentNode.left == childNode){
    			parentNode.bf++;//if traversing up from left node, increment balance factor of parent by 1
    			if(parentNode.bf == 2||parentNode.bf == 0) break;//if there is imbalance or if there is no change in height, stop traversing up.
    		}
    		if(parentNode.right == childNode){
    			parentNode.bf--; //if traversing up from right, decrement balance factor
    			if(parentNode.bf == -2||parentNode.bf == 0) break;
    		}
    		if(!auxStack.empty()){
    			
    			childNode = parentNode; //make the current parent node as a child node for next traversal
    			
    		}
         
    	}while(!auxStack.empty());
    	
    	AVLNode aNode = parentNode; //collect aNode as the one 
    	AVLNode bNode = childNode;
    	AVLNode cNode = null;
    	if(aNode.bf==2||aNode.bf==-2){
    	if((aNode.bf == 2 && bNode.bf ==1) || (aNode.bf ==-2 && bNode.bf ==-1))
    	{
    		singleRotation(aNode,bNode);//LL or RR rotaion
    	}
    	else
    	{
    		if(bNode.bf == 1) cNode = bNode.left;
    		else if(bNode.bf == -1) cNode = bNode.right;
    		if(cNode == newNode)
    		{
    			doubleRotationCase1(aNode,bNode,cNode);//LR case 1 or RL case 1
    		}
    		else if(cNode.bf == 1)
    		{
    			doubleRotationCase2(aNode,bNode,cNode);//LR case 2 or RL case 2
    		}
    		else if(cNode.bf == -1)
    		{
    			doubleRotationCase3(aNode,bNode,cNode);//LR case 3 or RL case 3
    		}
    	}
    	}
    }
    
  /*The function is used for single Rotation (both LL and RR cases based on the balance factor of the 
    child and parent nodes
    bNode:Node on which the insertion is done
    aNode :parent of bNode
    bNode: node where insertion is done.
    (Function corrects the  imbalance in case where insertion is done on the left of bNode and bNode is 
    left child of aNode. Or if the insertion is done on right of bNode and bNode is right child of aNode.
    */
    	public  void singleRotation(AVLNode aNode,AVLNode bNode)
    {
    	
        if(auxStack.empty()) 
        {
        	root = bNode;
        	
        }
        else{
    	AVLNode nodeBeforeA = auxStack.peek();//keep track of parent of Anode.
    	if(nodeBeforeA.left == aNode)
    	{
    		nodeBeforeA.left = bNode; //take bNode in place of aNode (case when aNode was a left child) 
    	}
    	else if(nodeBeforeA.right == aNode)
    	{
    		nodeBeforeA.right = bNode;//take bNode in place of aNode (case when aNode was a right child)
    	}
        }
    	if(aNode.bf == 2) //LL case
    	{
    		aNode.left = bNode.right;
    		bNode.right = aNode;
    	}
    	else if(aNode.bf == -2) // RR case
    	{
    		
    		aNode.right = bNode.left;
    		bNode.left = aNode;
    	}
    	//make anode and bnode's balance factors 0. Common for both LL and RR cases
    	aNode.bf = 0;
    	bNode.bf = 0;
    	}
    	/*The function is used for single Rotation (both LL and RR cases based on the balance factor of the 
    	child and parent nodes
    	bNode :Node on which the insertion is made (either to left or right) and bNode has no children.
    	aNode :Parent of bNode
    	newNode: the new node that was introduced (and created imbalance on aNode).
    	*/
    public  void doubleRotationCase1(AVLNode aNode,AVLNode bNode,AVLNode newNode)
    {
    	
    	
    	if(auxStack.empty()) {
    		root = newNode;
    	}
        else{
    	AVLNode nodeBeforeA = auxStack.peek();//keep track of node before aNode to figure if anode was to the left or right
    	if(nodeBeforeA.left == aNode)
    	{
    		nodeBeforeA.left = newNode; //replace anode with the newNode (case when anode was a left child)
    	}
    	else if(nodeBeforeA.right == aNode)
    	{
    		nodeBeforeA.right = newNode;//replace anode with newNode(case when anode was a right child)
    	}
        }
    	
    	if(aNode.bf == 2) //LR case 1
    	{
    		//make bNode and aNode the left and right child of the newNode respectively.
    		newNode.left = bNode;
    		newNode.right = aNode;
    		bNode.right = null;//to clear references which were previously present
    		aNode.left = null;
    	}
    	if(aNode.bf == -2)//RL case 1
    	{
    		//make aNode and bNode the left and right child of the newNode repsectively.
    		newNode.left = aNode;
    		newNode.right = bNode;
    		bNode.left = null;
    		aNode.right = null;
    	}
    	//make aNode,bNode and cNode balance factors 0. Common for LR ,RL cases.
    	aNode.bf = 0;
    	bNode.bf = 0;
    	newNode.bf = 0;
    	
    }
  /*The function is used for double rotationcase2 (LR or RL with where the  addition to the left subtree 
    after a LR or RL traversal (left of R in case of LR and left of L in case of RL)
    cNode: Node on which the insertion is done. The insertion is done on the left subtree of cNode in this case
    bNode: parent of Cnode
    aNode: parent of bNode. Anode is the node on which the imbalance (bf = 2 or -2 ) is detected.
    */
    public  void doubleRotationCase2(AVLNode aNode,AVLNode bNode,AVLNode cNode)
    {
    	if(auxStack.empty()) {
    	root = cNode;
    	}
        else{
    	AVLNode nodeBeforeA = auxStack.peek();//keep track of node before aNode
    	if(nodeBeforeA.left == aNode)
    	{
    		nodeBeforeA.left = cNode;// replace aNode with cNode (case when aNode was left child
    	}
    	else if(nodeBeforeA.right == aNode)// replace aNode with cNode (case when aNode was right child
    	{
    		nodeBeforeA.right = cNode;
    	}
        }
    	if(aNode.bf == 2)//LR case
    	{
    	aNode.bf = -1;
    	bNode.bf = 0;
    	cNode.bf = 0;
    	//Cnode is now in the place of Anode. Anode and Bnode would becomes the left and right children of
    	//Cnode. So find right places for the children of Cnode on aNode and Node
    	aNode.left = cNode.right;
    	bNode.right = cNode.left;
    	//cNode is now in the position where aNode was. Make aNode and bNode the left and right children of
    	//cNode
    	cNode.left =bNode;
    	cNode.right = aNode;
    	}
    	else if(aNode.bf == -2)//RL case
    	{
    		aNode.bf = 0;
    		bNode.bf = -1;
    		cNode.bf = 0;
    		//Cnode is now in the place of Anode. Anode and Bnode would becomes the left and right children of
        	//Cnode. So find right places for the children of Cnode on aNode and Node
        	aNode.right = cNode.left;
    		bNode.left = cNode.right;
    		cNode.left = aNode;
    		cNode.right = bNode;
    				
    	}
    }

    /*
    The function is used for double rotationcase3(LR or RL with where the  addition to the right 
    subtree  after a LR or RL traversal (right of R in case of LR and right of L in case of RL) 
    cNode: Node on which the insertion is done. The insertion is done on the right subtree of cNode in this case
    bNode: parent of Cnode
    aNode: parent of bNode. Anode is the node on which the imbalance (bf = 2 or -2 ) is detected.
    */
  
    public  void doubleRotationCase3(AVLNode aNode,AVLNode bNode,AVLNode cNode)
    {
    	
    	
    	if(auxStack.empty()) {root = cNode; }
        else{
    	AVLNode nodeBeforeA = auxStack.peek(); //keep track of node before aNode
    	if(nodeBeforeA.left == aNode)
    	{
    		nodeBeforeA.left = cNode; // replace aNode with cNode (case when aNode was left child
    	}
    	else if(nodeBeforeA.right == aNode)
    	{
    		nodeBeforeA.right = cNode;//replace anode with newNode(case when anode was a right child)
    	}
        }
    	
    	if(aNode.bf == 2)//LR Case 3
    	{
    	aNode.bf = 0;
    	bNode.bf = 1;
    	cNode.bf = 0;
    	// CNode now occupies the position where Anode was. and will have bNode and aNode as it's
    	//left and right children respectively.Make left subtree of cNode the right subtree of bNode and make the
    	//right subtree of cNode the left subtree of aNode
    	aNode.left = cNode.right;
    	bNode.right = cNode.left;
    	cNode.left = bNode;
    	cNode.right = aNode;
    	}
    	else if(aNode.bf == -2)//RL Case 3
    	{
    		aNode.bf = 1;
    		bNode.bf = 0;
    		cNode.bf = 0;
    		// CNode now occupies the position where Anode was. and will have aNode and bNode as it's
        	//left and right children respectively.Make left subtree cNode the right subtree of aNode and make the
        	//right subtree of cNode the left subtree of aNode
        	
    		aNode.right = cNode.left;
    		bNode.left = cNode.right;
    		cNode.left = aNode;
    		cNode.right = bNode;
    	}
    }
}








