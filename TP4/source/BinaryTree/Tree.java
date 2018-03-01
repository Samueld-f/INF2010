package BinaryTree;

public class Tree<AnyType> {

	private Node<AnyType> root = null; // Racine de l'arbre

	public void insert (AnyType elem) {
		Node<AnyType> newElem = new Node<AnyType>(elem);
		root = insert(root, newElem);
	}

	private Node<AnyType> insert (Node<AnyType> root, Node<AnyType> newelement) {
		// A compléter

	}

	public int getHauteur () {
		return this.getHauteur(root);
	}

	private int getHauteur(Node<AnyType> tree) {
		// A compléter 

	}

	public String printTreePreOrder() {
		return "{" + this.printPreOrder(root) + "}";

	}

	private String printPreOrder(Node root) {
		String results = "";

		// A compléter
		
	}

	private class Node<AnyType> {
		AnyType val; // Valeur du noeud
		Node right; // fils droite
		Node left; // fils gauche

		public Node (AnyType val) {
			this.val = val;
			right = null;
			left = null;
		}

	}


}

