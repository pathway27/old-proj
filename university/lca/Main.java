import java.util.*;

public class Main {
    public static void main(String[] args) {

        BinaryTree<String> T = new BinaryTree<String>();

        BTNode<String> root = T.addRoot("A");
        	BTNode<String> nodeI = T.insertRight(root, "I");
        	
        	BTNode<String> nodeB = T.insertLeft(root, "B");
        		
        		BTNode<String> nodeC = T.insertLeft(nodeB, "C");
					BTNode<String> nodeD = T.insertLeft(nodeC, "D");
					BTNode<String> nodeE = T.insertRight(nodeC, "E");
        
				BTNode<String> nodeF = T.insertRight(nodeB, "F");
 					BTNode<String> nodeH = T.insertLeft(nodeF, "H");
					BTNode<String> nodeG = T.insertRight(nodeF, "G");
        	
		 
        String nodeCS = nodeC.element().toString();
        String nodeHS = nodeH.element().toString();
        
        BTNode ans = T.getLCA(T, nodeC, nodeH);
        String ans2 = ans.element().toString();
        
        System.out.println("The Lowest Common Ancestor of " + nodeCS + " and " + nodeHS + ":");
        System.out.println(ans2);
        
        T.printGraphViz(T, nodeC, nodeH);    }
}
