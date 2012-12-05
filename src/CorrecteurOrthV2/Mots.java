package CorrecteurOrthV2;

public class Mots {

	private String nom;
	private int nbApparition = 0; // servivra pour savoir combien de tri-gramme contient le mot par rapport au mots à corriger
	private Double distanceLenvenshtein = 0.0; // permettra de trier la liste des mots candidat
	
	public Double getDistanceLenvenshtein() {
		return distanceLenvenshtein;
	}

	public void setDistanceLenvenshtein(Double distanceLenvenshtein) {
		this.distanceLenvenshtein = distanceLenvenshtein;
	}

	public Mots(String nom){
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getNbApparition() {
		return nbApparition;
	}
	
	public void setNbApparition(int nbApparition) {
		this.nbApparition = nbApparition;
	}

	public boolean egal(String str){
		if (str.equals(nom))
			return true;
		else 
			return false;
	}

	public void incrementer() {
		this.nbApparition += 1 ;
		
	}
	
	
}
