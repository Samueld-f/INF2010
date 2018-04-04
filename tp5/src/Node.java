import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author maitr
 */
public class Node {

	public int ordre;
	public Node parent;

	private int valeur;
	private ArrayList<Node> enfants;

	public Node(int valeur) {
		this.valeur = valeur;
		ordre = 0;
		this.parent = null;
		enfants = new ArrayList<Node>();
	}

	public Node(int valeur, Node parent) {
		ordre = 0;
		this.valeur = valeur;
		this.parent = parent;
		enfants = new ArrayList<Node>();
	}

	public int getVal() {
		return valeur;
	}

	public ArrayList<Node> getEnfants() {
		return enfants;
	}

	public void addEnfant(Node enfant) {
		enfants.add(enfant);
	}

	public boolean removeEnfant(Node enfant) {
		if (enfants.contains(enfant)) {
			enfants.remove(enfant);
			return true;
		}
		return false;
	}

	public void removeEnfants(ArrayList<Node> enfants) {
		this.enfants.removeAll(enfants);
	}

	public Node fusion(Node autre) throws DifferentOrderTrees {
		if (this.ordre != autre.ordre)
			throw new DifferentOrderTrees();
		if (this.parent != null || autre.parent != null)
			throw new DifferentOrderTrees("Erreur : on ne peut pas fusionner deux noeuds qui ne sont pas des racines");
		if (this.valeur < autre.valeur) {
			autre.addEnfant(this);
			this.parent = autre;
			autre.ordre = autre.enfants.size();
			return autre;
		}
		else {
			this.addEnfant(autre);
			autre.parent = this;
			ordre = enfants.size();
			return this;
		}
	}

	private void sortEnfants() {
		ArrayList<Node> newEnfants = new ArrayList<Node>();
		while (!enfants.isEmpty()) {
			Node min = enfants.get(0);
			for (Node n : enfants) {
				if (n.ordre < min.ordre)
					min = n;
			}
			newEnfants.add(min);
			enfants.remove(min);
		}
		enfants = newEnfants;
	}
	
	private void moveUp() {
		if (this.parent == null)
			return;
		else {
			Node oldParent = this.parent;
			//Changer le parent de this
			this.parent = this.parent.parent;
			if(this.parent != null) {
				this.parent.addEnfant(this);
				this.parent.removeEnfant(oldParent);
			}
			//Changer oldParent
			oldParent.removeEnfant(this);
			ArrayList<Node> oldEnfants = oldParent.enfants;
			oldParent.enfants = this.enfants;
			oldParent.ordre = oldParent.enfants.size();
			//Changer this
			this.enfants = oldEnfants;
			this.addEnfant(oldParent);
			this.ordre = this.enfants.size();
			//Changer les enfants
			for(Node n: this.enfants)
				n.parent=this;
			for(Node n: oldParent.enfants)
				n.parent = oldParent;
		}
		sortEnfants();
	}

	public ArrayList<Node> delete() {
		while(parent != null)
			moveUp();
		for(Node n : enfants)
			n.parent = null;
		return enfants;
	}

	public Node findValue(int valeur) {
		if (this.valeur == valeur)
			return this;
		else if(this.valeur < valeur)
			return null;
		else {
			for (Node n : enfants) {
				Node retour = n.findValue(valeur);
				if (retour != null)
					return retour;
			}
		}
		return null;
	}

	private Node findMax(ArrayList<Node> listNode) {
		Node max = listNode.get(0);
		for(Node n : listNode) {
			if(n.getVal() > max.getVal())
				max = n;
		}
		return max;
	}
	
	public ArrayList<Integer> getElementsSorted() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(this.getVal());
		ArrayList<Node> listNode = this.enfants;
		while(!listNode.isEmpty()) {
			Node max=findMax(listNode);
			list.add(max.getVal());
			listNode.remove(max);
			listNode.addAll(max.enfants);			
		}
		return list;
	}

	public void print() {
        print("", true);
    }

    private void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "'-- " : "|-- ") + valeur);
        for (int i = 0; i < enfants.size() - 1; i++) {
            enfants.get(i).print(prefix + (isTail ? "    " : "|   "), false);
        }
        if (enfants.size() > 0) {
            enfants.get(enfants.size() - 1)
                    .print(prefix + (isTail ?"    " : "|   "), true);
        }
    }
}
