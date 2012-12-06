package CorrecteurOrthV2;

public class TableTriGramme extends Table<TriGramme> {


	public TableTriGramme() {
		tableau = new ListeTriGramme [nEntree];
	}
	
	// Recupere un TriGramme dans la ListeTrigramme contenu dans la table 
	public TriGramme getTriGramme(String nomTriGramme){
		int indice = hashCode(nomTriGramme);
		if (tableau[indice] == null)
			return null;
		else
			return ((ListeTriGramme) tableau[indice]).getTriGramme(nomTriGramme);
		
	}
	
	// ajouter un element dans le tableau 
	public void ajouter (TriGramme trigramme){
		
		int indice = hashCode(trigramme.getNom());
		if (tableau[indice] != null){
			ListeTriGramme l = (ListeTriGramme) tableau[indice].ajouterEnTete(trigramme);
			tableau[indice] = l;
		}
		else {
			tableau[indice] = new ListeTriGramme(trigramme, null); 
		}
		
	}

	
	
}
