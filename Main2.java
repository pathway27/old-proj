import java.util.*;
import java.lang.*;

public class Main2 {
    /** Prints a pseudorandom permution of the integers 0 through N. */
    public static int[] permute(int N, int seed) {
        Random rng = new Random(seed);
        int[] a = new int[N];

        // insert integers 0..N-1
        for (int i = 0; i < N; i++)
            a[i] = i;

        // shuffle
        for (int i = 0; i < N; i++) {
            //int r = (int) (Math.random() * (i+1)); // int between 0 and i
            int r = (int) (rng.nextDouble() * (i+1)); // int between 0 and i
            int swap = a[r];
            a[r] = a[i];
            a[i] = swap;
        }

        // record permutation
        int[] rnd = new int[N];
        for (int i = 0; i < N; i++) {
            rnd[i] = a[i];
        }
        return rnd;
    }

    /**
     * Inserts a new element as a node in a binary tree. 
     * The position is given by the 0-1 string path start from the root and
     * follow the path (0 means go left, 1 go right) if string path is only one
     * character, create and add the new node as a child of the current one
     * empty string means replace node v with the new one. (v is lost, subtrees
     * of v are preserved as subtrees of new one). 
     * @param T
     * @param e
     * @param v
     * @param path
     */
    public static <E> void insertBTNodeThere(BinaryTree<E> T, E e, BTNode<E> v, String path) {
	if (v == null) throw new RuntimeException("Wrong position: ran out of tree trying to insert node"+e.toString());
	if (path.length() == 0) {
            /* replace current node - retain subtrees*/
            BTNode<E> newOne = new BTNode<E>(e,v.getParent(),v.getLeft(),v.getRight());
            if (v.getParent().getLeft() != null && v.getParent().getLeft().equals(v) ) {
                v.getParent().setLeft(newOne);
            } else {
                v.getParent().setRight(newOne);
            }
	} 
	else {
            int direction = Integer.parseInt(path.substring(0, 1));
            if (path.length() == 1) {
                /* found the position insert the element as a left or right child */
                if (direction == 0) {
                    v.setLeft(new BTNode<E>(e,v,null,null));
                } else {
                    v.setRight(new BTNode<E>(e,v,null,null));
                }
                return;
            }
            String restPath = path.substring(1);
            if (direction == 0) {
                insertBTNodeThere(T,e,v.getLeft(),restPath);
            } else {
                insertBTNodeThere(T,e,v.getRight(),restPath);
            }
	}
    }

    public static <E> BTNode<E> findBTNode(BinaryTree<E> T, BTNode<E> v, String path) {
        int direction = Integer.parseInt(path.substring(0, 1));
        if (path.length() == 1) {
            /* return the node */
            if (direction == 0)
                return v.getLeft();
            else
                return v.getRight();
        }

        /* traverse the tree recursively */
        String restPath = path.substring(1);
        if (direction == 0)
            return findBTNode(T, v.getLeft(), restPath);
        else
            return findBTNode(T, v.getRight(), restPath);
    }

    public static void main(String[] args) {
        /**********************************************************
         **********************************************************
         ** REPLACE the seed value with the last 4 digits
         ** of YOUR unikey.
         ** For example, the seed for the unikey 'vnik5287' is 5287.
         **/

        int seed = 6603;

        /**********************************************************
         **********************************************************/

        int depth = 8; // tree depth
        int curr_index = 0;

        /* Create an empty tree that stores integers. */
        BinaryTree<Integer> T = new BinaryTree<Integer>();
        BTNode<Integer> root = T.addRoot(1000);

        /* permute the max number of nodes in the tree */
        int[] permutedList = permute((int)(Math.pow(2, depth)-1), seed);

        /* constructing the random tree */
        for (int i = 1; i < depth; ++i) {
            for(int j = 0; j < Math.pow(2, i); ++j) {
                String bin = Integer.toBinaryString(j);
                if (bin.length() != depth) {
                    /* construct a string of 0s for padding */
                    String prefix = "";
                    for (int k = 0; k < i-bin.length(); ++k)
                        prefix += "0";

                    insertBTNodeThere(T, permutedList[curr_index], root, prefix+bin);
                }
                else {
                    insertBTNodeThere(T, permutedList[curr_index], root, bin);
                }
                curr_index++;
            }
        }

        Random rng = new Random(seed);
        
        String path = (Integer.toBinaryString(rng.nextInt())).substring(0, depth-1);
        BTNode<Integer> v1 = findBTNode(T, root, path);

        path = (Integer.toBinaryString(rng.nextInt())).substring(0, depth-3);
        BTNode<Integer> w1 = findBTNode(T, root, path);

        path = (Integer.toBinaryString(rng.nextInt())).substring(0, depth-2);
        BTNode<Integer> v2 = findBTNode(T, root, path);

        path = (Integer.toBinaryString(rng.nextInt())).substring(0, depth-2);
        BTNode<Integer> w2 = findBTNode(T, root, path);

        path = (Integer.toBinaryString(rng.nextInt())).substring(0, depth-1);
        BTNode<Integer> v3 = findBTNode(T, root, path);

        path = (Integer.toBinaryString(rng.nextInt())).substring(0, depth-3);
        BTNode<Integer> w3 = findBTNode(T, root, path);

        /**
         * ADD YOUR FUNCTION for computing LCA (INFO1905) 
         * or reflections (INFO1105) below!
         **/
        
		String w2S = w3.element().toString();
        String v2S = v3.element().toString();
        
        BTNode ans = T.getLCA(T, w3, v3);
        String ans2 = ans.element().toString();
        
        System.out.println("The Lowest Common Ancestor of " + w2S + " and " + v2S + ":");
        System.out.println(ans2);
        
        T.printGraphViz(T, w3, v3);
    }
}
