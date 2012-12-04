package CorrecteurOrthV2;

import javax.swing.text.html.MinimalHTMLWriter;

public class Levenshtein {
	
	
	private static int minimum(int val1, int val2, int val3){
		
			return Math.min(Math.min(val1, val2), val3);
			
	}//minimum()
	
	
	private static int distanceDeLevenshtein (String chaine1, String chaine2){
		
		
		int longueurChaine1 = chaine1.length();
		int longueurChaine2 = chaine2.length();
		
		// pour pouvoir faire la comparaison entre de deux caract�re 
		chaine1 = chaine1.toUpperCase(); 
		chaine2 = chaine2.toUpperCase(); 
		
		// d est un tableau de longueur de chaine1 + 1 rang�es et longueur de chaine2 + 1 colonnes
		int d[][] = new int[longueurChaine1 + 1][longueurChaine2 + 1];
		
		int cout;
		
		for (int i = 0; i < longueurChaine1 + 1 ; i++) 
			d[i][0] = i;

		for (int i = 0; i < longueurChaine2 + 1; i++) 
			d[0][i] = i;
		
		for (int i = 1; i < longueurChaine1 + 1 ; i++) {
			
			for (int j = 1; j < longueurChaine2 + 1; j++) {
				
				
				if (chaine1.charAt(i-1) == chaine2.charAt(j-1)) 
					cout = 0;
				else 
					cout = 1;
				
				d[i][j] = minimum(d[i-1][j] +1, d[i][j-1]+1, d[i-1][j-1] + cout);

			}
		}
		
		return d[longueurChaine1][longueurChaine2];
	}//distanceDeLevenshtein()
	
	
	public static Double d(String chaine1, String chaine2){
		
		Double distanceLevenshtein = (double) distanceDeLevenshtein(chaine1, chaine2);
		Double max = (double) Math.max(chaine1.length(), chaine2.length());
		
		return 1.0 - distanceLevenshtein /max;
		
	}
	
}	
