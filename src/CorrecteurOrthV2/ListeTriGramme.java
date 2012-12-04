package CorrecteurOrthV2;

public class ListeTriGramme extends Liste<TriGramme> {

	public ListeTriGramme(TriGramme contenu, Liste<TriGramme> suivant) {
		super(contenu, suivant);
		// TODO Auto-generated constructor stub
	}
	
	public TriGramme getTriGramme (String str){
		
		if (queue() == null || tete().egal(str))
			return tete();
		else {
			return ((ListeTriGramme) queue()).getTriGramme(str);
		}
			
	}
	
	public ListeTriGramme ajouterEnTete(TriGramme contenu) {
		ListeTriGramme l = new ListeTriGramme(contenu, this);
		return l;
	}

	@Override
	public boolean estDans(String x) {
				
		if (tete().getNom().equals(x))
			return true;
		else {
			if (this.queue() != null)
				return this.queue().estDans(x);
		}	
		
		return false;
	}

	@Override
	public void affiche() {
		if (this.queue() != null){
			System.out.print("["+tete().getNom()+"] ( ");
			tete().getListeMots().affiche();
			System.out.print(" ) -> ");
			this.queue().affiche();
		}
		else {
			System.out.print("["+tete().getNom()+"] ( ");
			tete().getListeMots().affiche();
			System.out.println(" )");	
		}	
	}

}
