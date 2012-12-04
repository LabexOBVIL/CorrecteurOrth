package CorrecteurOrthV2;

public class ListeMots extends Liste<Mots> {

	public ListeMots(Mots contenu, ListeMots suivant) {
		super(contenu, suivant);
	}

	@Override
	public ListeMots ajouterEnTete(Mots contenu) {
		ListeMots l = new ListeMots(contenu, this);
		return l;
	}

	@Override
	public boolean estDans(String x) {
		
		if (tete().getNom().equals(x))
			return true;
		else {
			if (this.queue() != null && this.queue().estDans(x))
				return true;
		}	
				
		return false;
	}

	@Override
	public void affiche() {
		if (this.queue() != null){
			System.out.println("["+tete().getNom()+"] ("+tete().getDistanceLenvenshtein()+") -> ");
			this.queue().affiche();
		}
		else {
			System.out.println("["+tete().getNom()+"]");
		}	
		
	}
	
	public Mots getMots (String str){
		
		if (queue() == null || tete().egal(str))
			return tete();
		else {
			return ((ListeMots) queue()).getMots(str);
		}
			
	}
	
}
