package CorrecteurOrthV2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

public class Execut {

	public static void main(String[] args) throws FileNotFoundException {
		
		/* Lecture fichier -> stocké dans un vecteur */
		
		Scanner scan = new Scanner(new FileReader("C:/Users/bouabd/workspace/Devoir2/materiel/dico2012.txt"));
		Vector<String> vDico = new Vector<String>();
		
		while (scan.hasNext()){
			String chaine = scan.next();
			vDico.add(chaine);
			
		}
		scan.close();
		
		
		/* Test levenshtein * /	
		Scanner scan = new Scanner(new FileReader("C:/Users/bouabd/workspace/Devoir2/materiel/dico2012.txt"));
		
		while (scan.hasNext()){
			String chaineCompare = scan.next();
			System.out.println("Levenshtein(baillonette, " + chaineCompare + " ) = " + Levenshtein.distanceDeLevenshtein("baillonette ",chaineCompare));
		}
		
		scan.close();
		
		/* Fin test */
		
				
		/*Construction dictionnaire tri-gramme Et Dictionnaire de mots* /
		
		TableTriGramme tableTriGramme = new TableTriGramme();
		TableMots tableDeMot = new TableMots();
	
		// creation de tous les tri-gramme
	
		for (int i = 0; i < vDico.size(); i++) {
			
			String chaine = vDico.get(i);
			tableDeMot.ajouter(chaine);// ajout du mot dans la table des mots
			
			chaine = "$"+chaine+"$";
		
			// parcourt des tri-gramme
			for (int j = 0; j < chaine.length()-2; j++) {
				
				String nomTri_gramme = (chaine.substring(j, j+3));
				
				ListeMots lTempo = null;// liste permetant de sotck� la nouvelle liste apr�s l'ajout du mots dans le Trigramme
				
				// On verifie si le tri-gramme n'est pas deja dans la table hash code
				if(!tableTriGramme.isContent(nomTri_gramme)){
					// Si c'est le cas on l'ajoute dans la table hash code avec une liste contenant le mots 
					tableTriGramme.ajouter(new TriGramme(nomTri_gramme, new ListeMots(chaine, null)));
				}	
				
				// Sinon on récupère la liste contenant les mots qui contienent ce tri-gramme
				else {			
					lTempo = tableTriGramme.getTriGramme(nomTri_gramme).getListeMots().ajouterEnTete(chaine);
					tableTriGramme.getTriGramme(nomTri_gramme).setListe(lTempo);// mis ajour de la liste apr�s ajout
				}	
				
			}// fin de parcourt du mots 
			
		}//	fin parcourt du dictionnaire
		
			tableTriGramme.affiche();
	
	
	*/
	}//main()
			
	
}//Execut.java
