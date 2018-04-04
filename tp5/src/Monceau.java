import java.util.ArrayList;

public class Monceau {
	ArrayList<Node> arbres;

	public Monceau() {
		arbres = new ArrayList<Node>();
	}

	private int ordreMax() {
		int max = -1;
		for (Node n : arbres)
			if (n.ordre > max)
				max = n.ordre;
		return max;
	}
	
	public void fusion(Monceau autre) {
		Node retenue = null;
		int ordreMax = ordreMax();
		if (ordreMax < autre.ordreMax())
			ordreMax = autre.ordreMax();
		for(int j = 0 ; j <= ordreMax+1 ; j++) {
			Node arbre1 = null;
			Node arbre2 = null;
			for(Node n : arbres) {
				if (n.ordre == j)
					arbre1 = n;
			}
			for(Node n : autre.arbres) {
				if (n.ordre == j)
					arbre2 = n;
			}
			try {
				if (arbre1 != null && arbre2 == null && retenue == null);
				else if(arbre1 == null && arbre2 != null && retenue == null)
					arbres.add(arbre2);
				else if(arbre1 == null && arbre2 == null && retenue != null) {
					arbres.add(retenue);
					retenue = null;
				}
				else if(arbre1 != null && arbre2 != null && retenue == null) {
					retenue = arbre1.fusion(arbre2);
					arbres.remove(arbre1);
					autre.arbres.remove(arbre2);
				}
				else if(arbre1 != null && arbre2 == null && retenue != null) {
					retenue = retenue.fusion(arbre1);
					arbres.remove(arbre1);
				}
				else if(arbre1 == null && arbre2 != null && retenue != null) {
					retenue = retenue.fusion(arbre2);
					autre.arbres.remove(arbre2);
				}
				else if(arbre1 != null && arbre2 != null && retenue != null) {
					retenue = retenue.fusion(arbre2);
					autre.arbres.remove(arbre2);
				}
			} catch (DifferentOrderTrees exception) {
				System.out.println(exception);
				return;
			}
		}
		sortArbres();
	}

	public void insert(int val) {
		Node arbre = new Node(val);
		Monceau foret = new Monceau();
		foret.arbres.add(arbre);
		fusion(foret);
	}
	
	public boolean delete(int val) {
			
		boolean supprime = false;
		boolean modifie = false;
		for (Node n : arbres) {
			Node remove = n.findValue(val);
			while (remove != null) {
					ArrayList<Node> enfants = remove.delete();
					Monceau restants = new Monceau();
					for(Node enfant: enfants)
						restants.arbres.add(enfant);
					arbres.remove(n);
					modifie = true;
					fusion(restants);
					supprime = true;
					remove = n.findValue(val);
			}
			if (modifie == true)
				break;		
		}
		if (modifie == true)
			this.delete(val);
		return supprime;
	}

	private void sortArbres() {
		ArrayList<Node> newArbres = new ArrayList<Node>();
		while (!arbres.isEmpty()) {
			Node min = arbres.get(0);
			for (Node n : arbres) {
				if (n.ordre < min.ordre)
					min = n;
			}
			newArbres.add(min);
			arbres.remove(min);
		}
		arbres = newArbres;
	}
	
	public void print() {
		for (Node node : arbres) {
			System.out.println("\n\nordre : " + node.ordre);
			node.print();
		}
	}

}
