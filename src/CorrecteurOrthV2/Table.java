package CorrecteurOrthV2;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

public abstract class Table<T> {
	
	protected final int nEntree = 1000000;
	protected Liste<T>[] tableau;
	
	// fonction d'hachage 
	public int hashCode(String chaine){
		
		int hash = 0;
		for (int i = 0; i< chaine.length(); i++)
			hash  = (hash *31 + chaine.charAt(i)) % nEntree;
		return hash;
	}
	
	abstract public void ajouter(T element);
	
	public boolean isContent(String nom){
		int indice = hashCode(nom);
//		System.out.println(indice);
		if (tableau[indice] != null){
//			System.out.println("existe");
			return tableau[indice].estDans(nom);
		}
		else {
//			System.out.println("n'existe pas ");
			return false;
		}
			
		
	}//isContent()

	public void affiche(){
		for (int i = 0; i < tableau.length; i++) {
			if(tableau[i] != null)
				tableau[i].affiche();
		}
	}// affiche
	
}
