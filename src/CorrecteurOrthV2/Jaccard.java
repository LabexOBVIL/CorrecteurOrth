package CorrecteurOrthV2;

import java.util.Vector;

public class Jaccard {

	static Vector<Character> lescharDeLaString(String chaine){
		
		Vector<Character> vChaine = new Vector<Character>();
		//On parourt la chaine de caractère 
		for (int i = 0; i < chaine.length(); i++) {
			//On récupère le caractere courant 
			char c = chaine.charAt(i);
			//Si le caractère n'a pas encore était ajouté 
			if (!vChaine.contains(c)){
				// On l'ajoute 
				vChaine.add((c));
			}
		}
		// et on retourne la chaine sous forme de vecteur de caractere sans doublons
		return vChaine;
	}
	
	static Double indiceJaccard(String chaine1, String chaine2){
		
		Vector<Character> vChaine1 = lescharDeLaString(chaine1);// on supprime les doublons de la chaine1
		Vector<Character> vChaine2 = lescharDeLaString(chaine2);// on supprime les doublons de la chaine2
		
		double nbCharEnCommun = 0;

		// pour tous les caractère du vecteur de chaine1 
		for (Character c1 : vChaine1) {
			//si  le caractère est dans le vecteur de la chaine2 on incremente de 1 le nombre de charactère en commun
			if (vChaine2.contains(c1)) 
				nbCharEnCommun += 1 ;
		}
		
		// pour avoir l'union des chaine on supprimer les doublons des deux chaine de caractere et on prend la taille du vecteur renovoyais par la fonction
		Double nbUnionChar = (double)lescharDeLaString(chaine1+chaine2).size();
		
		// On revoins L'intersection des chaine sur les l'union des chaines
		return (nbCharEnCommun/nbUnionChar);
	}
}
