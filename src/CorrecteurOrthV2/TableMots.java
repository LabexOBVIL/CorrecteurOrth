package CorrecteurOrthV2;

public class TableMots extends Table<Mots> {

	public TableMots() {
		tableau = new ListeMots[nEntree];
	}
	@Override
	public void ajouter(Mots mot) {
		
		int indice = hashCode(mot.getNom());
		if (tableau[indice] != null){
			tableau[indice] = tableau[indice].ajouterEnTete(mot);
		
		}
		else {
			tableau[indice] = new ListeMots(mot, null); 
		
		}
	}//ajouter()	

}
