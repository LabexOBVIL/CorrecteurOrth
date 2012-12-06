package CorrecteurOrthV2;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Correcteur {

	static private final String dico = "materiel/dico2012.txt";
	static private TableTriGramme tableTriGramme;
	static private TableMots tableDeMot;
	
	static private void contruireTables() throws IOException{
		
		tableTriGramme = new TableTriGramme(); // creation d'une table hashing code pour les tri-gramme
		tableDeMot = new TableMots();// creation d'une table hashing code pour tous les mots du dico
		
		Scanner scan = new Scanner(new FileReader(dico));// lecture du dictionnaire
		
		while (scan.hasNext())// tant qu'il y'a une chaine de caractère à lire
		{
			String chaine = scan.next();// chaine vaut la prochaine chaine
			chaine = chaine.toLowerCase();// On enregistre le dictionnaire en miniscule pour que la comparaison de chaine ne pose pas de problème
			
			tableDeMot.ajouter(new Mots(chaine));// ajout du mot dans la table des mots
			
			Mots leMot = new Mots(chaine);// On cree un Objet mot afin que tous les tri-gramme pointe vers le même objet
			
			chaine = "$"+chaine+"$";
			// On parcourt la chaine afin de construire les tri-gramme
			for (int j = 0; j < chaine.length()-2; j++) {
				
				String nomTri_gramme = (chaine.substring(j, j+3));
				ListeMots lTempo = null;// liste permetant de sotck� la nouvelle liste apres l'ajout du mots dans le Trigramme
				
				// On verifie si le tri-gramme n'est pas deja dans la table hash code
				if(!tableTriGramme.isContent(nomTri_gramme)){
					// Si c'est le cas on l'ajoute dans la table hash code avec une liste contenant le mots 
					tableTriGramme.ajouter(new TriGramme(nomTri_gramme, new ListeMots(leMot, null)));
				}	
				
				// Sinon on récupère la liste contenant les mots qui contienent ce tri-gramme et on l'ajoute en début de liste
				else {			
					lTempo = tableTriGramme.getTriGramme(nomTri_gramme).getListeMots().ajouterEnTete(leMot);
					tableTriGramme.getTriGramme(nomTri_gramme).setListe(lTempo);// mis ajour de la liste apres ajout
				}	
				
			}// fin de parcourt du mots 
		}
		scan.close();// fin de lecture
	}
	
	static ListeMots corrigerUnMot(String motACorriger) throws IOException{
//		contruireTables();
		 String chaine = "$"+motACorriger.toLowerCase()+"$";// mise en miniscule du mot a corrige 
		 ListeMots listeMotsCandidat = null;// Creation d'une liste de resultat que la fonction renvois
		 TriGramme[] TabTrigramme = new TriGramme[chaine.length()-2]; // tableau permetant de sotcké l'objet Trigramme
		 
		// parcourt mot et création des tri-gramme 
		 for (int j = 0; j < chaine.length()-2; j++) {
			 String nomTri_gramme = (chaine.substring(j, j+3));
			
			 TriGramme tri_gramme = tableTriGramme.getTriGramme(nomTri_gramme); //On récupère le tri_grame dans la tables hashing code "tableTriGramme"
			 TabTrigramme[j] = tri_gramme;// On stock le Trigramme afin de remette a zero le nombre d'appartion de tous les Mots par la suite 
			 
			 ListeMots listeMotsContentLeTri = null;// on declare une lise pour récupére les mots contenant le tri-gramme
			 //On vérifie que le tri_gramme existe bien
			 if (tri_gramme != null){
				 listeMotsContentLeTri = tri_gramme.getListeMots();
			 }	 
			 
			 //On parcourt la liste de mots contenant le tri-gramme
			while (listeMotsContentLeTri != null){
				
				Mots leMot = listeMotsContentLeTri.tete();
				// On incrimente le nombre d'appraition du mot 
				leMot.incrementer();
				
				//Si le nombre d'apparation arrive a 3 c'est qu'il contient au moins 3 tri-gramme donc je profite pour calculer l'indice de jaccard 
				// Cette premier condition est reserve a l'initialisation de la listeMotsCandidat 
				if ((leMot.getNbApparition() == 3) && (Jaccard.indiceJaccard(leMot.getNom(), motACorriger)> 0.2) && listeMotsCandidat == null){
					// On ajoute la distance de leveinshtein
					leMot.setDistanceLenvenshtein(Levenshtein.d(leMot.getNom(), motACorriger));
					listeMotsCandidat = new ListeMots(leMot, null);
				}
				else if ((leMot.getNbApparition() == 3) && (Jaccard.indiceJaccard(leMot.getNom(), motACorriger)> 0.2)){
					// On et ajour la distance de leveinshtein
					leMot.setDistanceLenvenshtein(Levenshtein.d(leMot.getNom(), motACorriger));
					listeMotsCandidat = listeMotsCandidat.ajouterEnTete(leMot);
				}
				
				listeMotsContentLeTri = (ListeMots) listeMotsContentLeTri.queue();// On passe au Mots suivant
				
			}// fin de parcourt de la liste de Mots contenant le tri-gramme
			
		 }// fin de recherche des mots pour tous les tri-gramme 
		 
	
		 //On trie la liste
		 if (listeMotsCandidat != null){
			 listeMotsCandidat =  QuickSortListe.quick_sort(listeMotsCandidat);
		 }
		 
		 //On remet a zero le nombre d'appartition de tous les Mots contenant les tri-gramme de la chaine a corriger
		 ListeMots listeMotsContentLeTri = null;
		 for (int i = 0; i < TabTrigramme.length; i++) {
			
			 if (TabTrigramme[i] != null){
				 listeMotsContentLeTri = TabTrigramme[i].getListeMots();
			 }
			
			 while (listeMotsContentLeTri != null){
					Mots leMot = listeMotsContentLeTri.tete();
					leMot.setNbApparition(0);// mise a 0 le nombre d'apparition
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
		
		int nbMotNonCorrige = 0;// Nombre de mots que on aura pas reussi a corriger
		int nbMotCorrectionPropose = 0;// Nombre de mot qu'on aura trouve dans la liste de candidat
		int nbMotCorrige = 0;// Nombre de mot renvoyais en premier dans liste de candidat
		
		Double tempsDepCorrectionFichier = (double) System.currentTimeMillis ();
		
		while (scan.hasNextLine())
		{
			String[] motLine = scan.nextLine().split(" ⇒ ");
			
			String motACorriger = motLine[0].trim();// On recupere le mot a corriger
//			System.out.println(motACorriger);
			ListeMots l = null;// On cree une liste pour recupere les candidats
			String bonneCorrection = motLine[1].toLowerCase().trim();// On recupere la bonne orthographe du mot a corriger 
			
			tempsDep = (double) System.currentTimeMillis (); // temps début correction mot
			if (!tableDeMot.isContent(motACorriger)){// On verfie que le mot nest pas deja dans la table des mots du dictionnaire 
				
				l = corrigerUnMot(motACorriger);// On recupere une liste de candidats
				if (l != null){
//					l.affiche();
//					System.out.println("Le mot a corrige : "+motACorriger+ " Le mot trouve : "+l.tete().getNom()+ " Le mot correct : "+ bonneCorrection);
					 // Si le premiere candidat est la bonne correction
//					System.out.println(bonneCorrection+"=="+l.tete().getNom());
					if (l.tete().getNom().equals(bonneCorrection)){
//						System.out.println("trouve au deb: "+ motACorriger);
						nbMotCorrectionPropose++;
						nbMotCorrige ++;
					}	
		
					else {
						// Si la bonne correction est dans la liste de candidats
						if (l.estDans(bonneCorrection)){
							nbMotCorrectionPropose++;
						}	
					 //Sinon il y'a pas de correction pour le mot
						else {
							 nbMotNonCorrige ++;
							/* System.out.println();
							 System.out.println("Non corrige : " +motACorriger + " bonne correction : "+bonneCorrection);
							 l.affiche();
							 System.out.println();*/
						 }	
					}	
				}	
				// si la liste de candidat est vide c'est qu'il ya pas de correction
				else{
					nbMotNonCorrige ++;
				}
			}// fin correction
			
			tempFin = (double) System.currentTimeMillis();
//			System.out.println(motACorriger+" : " + (tempFin - tempsDep)/1000);
		}
		Double tempsFinCorrectionFichier = (double) System.currentTimeMillis ();
		
		System.out.println(nbMotCorrige+ " bonnes corrections");
		System.out.println("Nombre de mot correction propose : "+ nbMotCorrectionPropose);
		System.out.println("Nombre de mot non corrige : "+ nbMotNonCorrige);
		
		System.out.println("temps pour corriger les 269 mots : "+ (tempsFinCorrectionFichier - tempsDepCorrectionFichier)/1000 );
	
	}
}
