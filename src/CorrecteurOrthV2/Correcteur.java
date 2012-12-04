package CorrecteurOrthV2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.ListModel;

import org.w3c.dom.ls.LSInput;

public class Correcteur {

	static private final String dico = "C:/Users/bouabd/workspace/Devoir2/materiel/dico2012.txt";
	static private TableTriGramme tableTriGramme;
	static private TableMots tableDeMot;
	
	static private void contruireTables() throws IOException{
		
		tableTriGramme = new TableTriGramme(); // creation d'une table hashing code pour les tri-gramme
		tableDeMot = new TableMots();// creation d'une table hashing code pour tous les mots du dico
		
		Scanner scan = new Scanner(new FileReader(dico));// lecture du dictionnaire
		
		while (scan.hasNext())// tant qu'il y'a une chaine de caractère à lire
		{
			String chaine = scan.next();
			chaine = chaine.toLowerCase();
			tableDeMot.ajouter(new Mots(chaine));// ajout du mot dans la table des mots
			Mots leMot = new Mots(chaine);
			chaine = "$"+chaine+"$";
			// parcourt des tri-gramme
			for (int j = 0; j < chaine.length()-2; j++) {
				
				String nomTri_gramme = (chaine.substring(j, j+3));
				
				ListeMots lTempo = null;// liste permetant de sotck� la nouvelle liste apr�s l'ajout du mots dans le Trigramme
				
				// On verifie si le tri-gramme n'est pas deja dans la table hash code
				if(!tableTriGramme.isContent(nomTri_gramme)){
					// Si c'est le cas on l'ajoute dans la table hash code avec une liste contenant le mots 
					tableTriGramme.ajouter(new TriGramme(nomTri_gramme, new ListeMots(leMot, null)));
				}	
				
				// Sinon on récupère la liste contenant les mots qui contienent ce tri-gramme
				else {			
					lTempo = tableTriGramme.getTriGramme(nomTri_gramme).getListeMots().ajouterEnTete(leMot);
					tableTriGramme.getTriGramme(nomTri_gramme).setListe(lTempo);// mis ajour de la liste apr�s ajout
				}	
				
			}// fin de parcourt du mots 
			
		}
		
		scan.close();// fin de lecture
		
//		tableTriGramme.affiche();
		
	}
	
	static void corrigerUnMot(String motACorriger) throws IOException{
		
		 contruireTables(); // construction des tables 
		 
		 String chaine = "$"+motACorriger.toLowerCase()+"$";
		 ListeMots listeMotsCandidat = null;
		 
		// parcourt mot et création des tri-gramme 
		 for (int j = 0; j < chaine.length()-2; j++) {
			 String nomTri_gramme = (chaine.substring(j, j+3));
			 //On récupère tous les mots contenant ce tri_grame dans la tables hashing code "tableTriGramme"
			ListeMots listeMotsContentLeTri = tableTriGramme.getTriGramme(nomTri_gramme).getListeMots();
			
			while (listeMotsContentLeTri != null){
			
				Mots leMot = listeMotsContentLeTri.tete();
//				System.out.println(leMot.getNom());
				// On incrimente nombre d'appraition du mot 
				leMot.incrementer();
				
				if ((leMot.getNbApparition() == 3) && (Jaccard.indiceJaccard(leMot.getNom(), motACorriger)> 0.2) && listeMotsCandidat == null){
					// On et ajour la distance de leveinshtein
					leMot.setDistanceLenvenshtein(Levenshtein.d(leMot.getNom(), motACorriger));
					listeMotsCandidat = new ListeMots(leMot, null);
				}
				else if ((leMot.getNbApparition() == 3) && (Jaccard.indiceJaccard(leMot.getNom(), motACorriger)> 0.2)){
					// On et ajour la distance de leveinshtein
					leMot.setDistanceLenvenshtein(Levenshtein.d(leMot.getNom(), motACorriger));
					listeMotsCandidat = listeMotsCandidat.ajouterEnTete(leMot);
				}
				
				listeMotsContentLeTri = (ListeMots) listeMotsContentLeTri.queue();
				
			}// fin de listeMotsContentLeTri
			
		 }// fin de recherche des mots pour tous les tri-gramme 
		 
	
		 //On trie la liste
		 listeMotsCandidat =  QuickSortListe.quick_sort(listeMotsCandidat);
		 listeMotsCandidat.affiche();
	}//corrigerUnMot()
	
	//	/*
//	*/
	public static void main(String[] args) throws IOException{
//		Correcteur.contruireTables();
		Correcteur.corrigerUnMot("OrdinAteur");
		
//		Double d = Levenshtein.d("odrinateur", "ordinatur");
//		System.out.println(d);
	}
}
