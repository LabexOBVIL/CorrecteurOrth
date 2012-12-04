package CorrecteurOrthV2;


public class TriGramme {
	
	private String nom;
	private ListeMots liste;
	
	public TriGramme(String triGramme, ListeMots liste) {
		this.nom = triGramme;
		this.liste = liste;
	}
	
	public void setListe(ListeMots l){
		liste = l;
	}
	public String getNom() {
		return nom;
	}

	public boolean egal(String str){
		if (str.equals(nom))
			return true;
		else 
			return false;
	}
	
	public ListeMots getListeMots (){
		return liste;
	}

}
