/** Binary tree implementation based on a linked structure. */

import java.util.*;
import java.io.*;

public class BinaryTree<E> {
    protected BTNode<E> root; // reference to the root
    protected int size; // number of nodes

    /** Creates an empty binary tree. */
    public BinaryTree() {
        root = null;
        size = 0;
    }
    /** Returns the number of nodes in the tree. */
    public int size() {
        return size;
    }
    /** Returns whether a node is internal. */
    public boolean isInternal(BTNode<E> v) {
        return (hasLeft(v) || hasRight(v));
    }
    /** Returns whether a node is root. */
    public boolean isRoot(BTNode<E> v) {
        return (v == root());
    }
    /** Returns whether a node has a left child. */
    public boolean hasLeft(BTNode<E> v) {
        return (v.getLeft() != null);
    }
    /** Returns the root of the tree. */
    public BTNode<E> root() {
        if (root == null) {
            System.err.println("The tree is empty");
            System.exit(1);
        }
        return root;
    }
    /** Returns the left child of a node. */
    public BTNode<E> left(BTNode<E> v) {
        BTNode<E> leftPos = v.getLeft();
        if (leftPos == null) {
            System.err.println("No left child");
            System.exit(1);
        }
        return leftPos;
    }
    /** Returns the right child of a node. */
    public BTNode<E> right(BTNode<E> v) {
        BTNode<E> rightPos = v.getRight();
        if (rightPos == null) {
            System.err.println("No right child");
            System.exit(1);
        }
        return rightPos;
    }
    /** Returns whether a node has a right child. */
    public boolean hasRight(BTNode<E> v) {
        return (v.getRight() != null);
    }

    /** Returns the parent of a node. */
    public BTNode<E> parent(BTNode<E> v) {
        BTNode<E> parentPos = v.getParent();
        if (parentPos == null) {
            System.err.println("No parent");
            System.exit(1);
        }
        return parentPos;
    }
    /** Returns an iterable collection of the children of a node. */
    public Iterable<BTNode<E>> children(BTNode<E> v) {
        LinkedList<BTNode<E>> children = new LinkedList<BTNode<E>>();
        if (hasLeft(v))
            children.addLast(left(v));
        if (hasRight(v))
            children.addLast(right(v));
        return children;
    }
    /** Returns an iterable collection of the tree nodes. */
    public Iterable<BTNode<E>> positions() {
        LinkedList<BTNode<E>> positions = new LinkedList<BTNode<E>>();
        if (size != 0)
            preorderNodes(root(), positions); // assign positions in preorder
        return positions;
    }
    /** Returns an iterator of the lements stored at the nodes. */
    public Iterator<E> iterator() {
        Iterable<BTNode<E>> positions = positions();
        LinkedList<E> elements = new LinkedList<E>();
        for (BTNode<E> pos: positions)
            elements.addLast(pos.element());
        return elements.iterator(); // An iterator of elements
    }

    /** Adds a root node to an empty tree. */
    public BTNode<E> addRoot(E e) {
        if (size != 0) {
            System.err.println("The tree already has a root!");
            System.exit(1);
        }
        size = 1;
        root = new BTNode<E>(e,null,null,null);
        return root;
    }
    /** Inserts a left child at a given node. */
    public BTNode<E> insertLeft(BTNode<E> v, E e) {
        BTNode<E> leftPos = v.getLeft();
        if (leftPos != null) {
            System.err.println("Node already has a left child");
            System.exit(1);
        }
        BTNode<E> tmp = new BTNode<E>(e, v, null, null);
        v.setLeft(tmp);
        size++;
        return tmp;
    }
    /** Inserts a left child at a given node. */
    public BTNode<E> insertRight(BTNode<E> v, E e) {
        BTNode<E> rightPos = v.getRight();
        if (rightPos != null) {
            System.err.println("Node already has a left child");
            System.exit(1);
        }
        BTNode<E> tmp = new BTNode<E>(e, v, null, null);
        v.setRight(tmp);
        size++;
        return tmp;
    }

    /** Exercise 3 Solution */
    public void preorderNodes(BTNode<E> v, LinkedList<BTNode<E>> positions) {
        positions.addLast(v);

        if (hasLeft(v))
           preorderNodes(left(v), positions);
        if (hasRight(v))
            preorderNodes(right(v), positions);
    }

    /** Exercise 4 Solution */
    public String parentheticRepresentation(BTNode<E> v) {
        String s = v.element().toString();
        if (isInternal(v)) {
            boolean firstTime = true;
            s+= "(";
            for (BTNode<E> w: children(v)) {
                if (firstTime) {
                    s += parentheticRepresentation(w); // the first child
                    firstTime = false;
                }
                else s += ", " + parentheticRepresentation(w); // subsequent child
            }
            s += ")"; // close parenthesis
        }
        return s;
    }
    
    /* Writes out to file the Lowest Common Ancestor in GraphViz Format. */
    public void printGraphViz(BinaryTree<E> T, BTNode<E> v, BTNode<E> w)
	{
        Scanner in = new Scanner(System.in);
		System.out.print("Enter a File Name to be written to: ");
		try
		{
			String filename = in.nextLine();
			File file = new File(filename);
			PrintWriter out = new PrintWriter(file);
			
			out.println("graph task3 { ");
		
			BTNode<E> LCA = T.getLCA(T, v, w);
	        String lca = LCA.element().toString();
        	out.println(lca + " [color=green];");
        	
			String letterV = v.element().toString();
			out.println(letterV + " [color=red];");
			
			String letterW = w.element().toString();
			out.println(letterW + " [color=red];");
			out.println();
			
			printChild(T.root(), out);
			
			out.println("}");
			System.out.println("File Written.");
			out.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File could not be opened");
		}
		
    }
    
    /** Recursively print to file output, the children from given node*/
    public void printChild(BTNode<E> v, PrintWriter out) {
        
        String vS = (String) v.element().toString();
    	
        if (hasLeft(v)) {
        	out.print(vS + " -- ");
            printChild(left(v), out);
        }
        if (hasRight(v)) {
        	out.print(vS + " -- ");
            printChild(right(v), out);
        }
        if ( !(hasRight(v)) && !(hasLeft(v)) ) {
        	out.println(vS + ";");
        }
    }
    
    /* Finds the lowest common ancestor of two nodes of a tree using
       the height from root node.
    */
    public BTNode<E> getLCA(BinaryTree<E> T, BTNode<E> v, BTNode<E> w)
    {
    
    	BTNode<E> vP = v.getParent();
    	BTNode<E> wP = w.getParent();
    	
    	// Cases Where the root is given, or if given nodes already directly related.
    	if (isRoot(v) || isRoot(w)) {
    		//System.out.println("Nodes chosen include root.");
    		return T.root();
    	}
    	
    	else if ( isRoot(vP) || isRoot(wP) ) {
    		//System.out.println("Nodes are direct descendants of root.");
    		return T.root();
    	}
    	
    	else if ( vP == wP) {
    		//System.out.println("Nodes are children of same father.");
    		return vP;
    	}
    	
    	// Calculate Height from root.
    	int vHeight = countUntilRoot(v, 0);
    	int wHeight = countUntilRoot(w, 0);
    	
    	// If heights are different
    	if (num(vHeight, wHeight)) {
    		v = levelNode(v, vHeight-wHeight);
    		return v.getParent();
    	}
    	
    	else if (num(wHeight, vHeight)) {
    		w = levelNode(w, wHeight-vHeight);
    		return w.getParent();
    	}
    	
    	// Only other possibility is if heights are same, 
    	else
    		return getLCA(T, vP, wP);
    }

	// Calculate height from a node to root, recursively.
	public int countUntilRoot(BTNode<E> n, int i)
	{
		BTNode<E> nP = n.getParent();
		
		if (isRoot(nP)) { 
			return i; 
		}
		
		else { 
			return countUntilRoot(nP, i+1); 
		}
		
	}
	
	// Level a node to by i distance from root, recursively.
	public BTNode<E> levelNode(BTNode<E> n, int i)
	{
		if (i == 0)
			return n;
		else
			return levelNode(n.getParent(), i-1);
	}
	
	// Find a bigger number out of two.
	public boolean num(int a, int b)
	{
		if (a > b)
			return true;
		else
			return false;
	}
}

