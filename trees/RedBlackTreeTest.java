package trees;
import java.io.*;
public class RedBlackTreeTest {
public static void main(String[] args) throws Exception{
	RedBlackTree<Integer,Integer> rt = new RedBlackTree<Integer,Integer>();
	System.out.println(rt.put(2, 3));
	System.out.println(rt.put(3, 4));
	//rt.inorder(new PrintWriter(new File("abc.out")));
}
}
