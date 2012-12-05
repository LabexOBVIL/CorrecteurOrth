package CorrecteurOrthV2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.ListModel;
import javax.swing.plaf.SliderUI;

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
	
	static ListeMots corrigerUnMot(String motACorriger) throws IOException{
		
		 String chaine = "$"+motACorriger.toLowerCase()+"$";
		 
		 ListeMots listeMotsCandidat = null;
		 TriGramme[] TabTrigramme = new TriGramme[chaine.length()-2]; // tableau permetant de sotcké l'objet Trigramme
		 
		// parcourt mot et création des tri-gramme 
		 for (int j = 0; j < chaine.length()-2; j++) {
			 String nomTri_gramme = (chaine.substring(j, j+3));
			
			 //On récupère le tri_grame dans la tables hashing code "tableTriGramme"
			 TriGramme tri_gramme = tableTriGramme.getTriGramme(nomTri_gramme); 
			 TabTrigramme[j] = tri_gramme;// On stock le Trigramme afin de remette a zero le nombre d'appartion de tous les Mots par la suite 
			 
			 ListeMots listeMotsContentLeTri = null;// on declare une lise pour récupére les mots contenant le tri-gramme
			 //On vérifie que le tri_gramme existe bien
			 if (tri_gramme != null){
				 listeMotsContentLeTri = tri_gramme.getListeMots();
			 }	 
			 
			while (listeMotsContentLeTri != null){
				
				Mots leMot = listeMotsContentLeTri.tete();
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
		 if (listeMotsCandidat != null){
			 listeMotsCandidat =  QuickSortListe.quick_sort(listeMotsCandidat);
		 }
		 
		 //On remet a zero le nombre d'appartition de tous les mots contenant les tri-gramme
		 ListeMots listeMotsContentLeTri = null;
		 for (int i = 0; i < TabTrigramme.length; i++) {
			
			 if (TabTrigramme[i] != null){
				 listeMotsContentLeTri = TabTrigramme[i].getListeMots();
			 }
			
			 while (listeMotsContentLeTri != null){
					Mots leMot = listeMotsContentLeTri.tete();
					leMot.setNbApparition(0);
					listeMotsContentLeTri = (ListeMots) listeMotsContentLeTri.queue();
			}	
		
		 }//fin de mise a jour de tous les tri-grames 
		 
	
		 listeMotsCandidat = ListeMots.couper(listeMotsCandidat, 10);// On récupere les 10 premiers candidats
		 return listeMotsCandidat;
		 
	}//corrigerUnMot()
	

	public static void CoorigerUnText(FileReader fichierACorriger) throws IOException{
		
		Double tempsDep = (double) System.currentTimeMillis ();
		contruireTables(); // construction des tables
		Double tempFin = (double) System.currentTimeMillis();
		
		System.out.println("Construction des tables fini : "+ ( tempFin- tempsDep)/1000);
		Scanner scan = new Scanner(fichierACorriger);// lecture du fichier a corriger
		
		int nbMotNonCorrige = 0;
		int nbMotCorrectionPropose = 0;
		int nbMotCorrige = 0;
		
		Double tempsDepCorrectionFichier = (double) System.currentTimeMillis ();
		
		while (scan.hasNextLine())// tant qu'il y'a une chaine de caractère à lire
		{
			String[] motLine = scan.nextLine().split(" ⇒ ");
			
			String motACorriger = motLine[0];
//			System.out.println(motACorriger);
			ListeMots l = null;
			String bonneCorrection = motLine[1].toLowerCase();
			
			tempsDep = (double) System.currentTimeMillis (); // temps début correction mot
			if (!tableDeMot.isContent(motACorriger)){

				l = corrigerUnMot(motACorriger);
				if (l != null){
//					System.out.println("Le mot a corrige : "+motACorriger+ " Le mot trouve : "+l.tete().getNom()+ " Le mot correct : "+ bonneCorrection);
					 // Si le premiere correction est la bonne
					if (l.tete().getNom().equals(bonneCorrection)){ 
						nbMotCorrige ++;
					}	
					// Si non on ecris dans le fichier les proposition de correction pour le fichier
					 if (l.estDans(bonneCorrection))
						nbMotCorrectionPropose++;
					 
					 else 
							 nbMotNonCorrige ++;
						 
				}	
				else{
					nbMotNonCorrige ++;
				}
			}// fin correction
			tempFin = (double) System.currentTimeMillis();
			System.out.println(motACorriger+" : " + (tempFin - tempsDep)/1000);
		}
		Double tempsFinCorrectionFichier = (double) System.currentTimeMillis ();
		
		System.out.println(nbMotCorrige+ " bonnes corrections");
		System.out.println("Nombre de mot correction propose : "+ nbMotCorrectionPropose);
		System.out.println("Nombre de mot non corrige : "+ nbMotNonCorrige);
		
		System.out.println("temps pour corriger les 269 mots : "+ (tempsFinCorrectionFichier - tempsDepCorrectionFichier)/1000 );
	
	}
}
