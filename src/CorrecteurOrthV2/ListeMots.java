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
			if (this.queue() != null)
				return this.queue().estDans(x);
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
			System.out.println("["+tete().getNom()+"] ("+tete().getDistanceLenvenshtein()+")");
		}	
		
	}
	
	public Mots getMots (String str){
		
		if (queue() == null || tete().egal(str))
			return tete();
		else {
			return ((ListeMots) queue()).getMots(str);
		}
			
	}

	static public String getListeEnString(ListeMots l, int nbCandidat) {
		
		int i = 0;
		String chaine = "";
		while (l != null && ++i <= nbCandidat){
			if (i < nbCandidat)
				chaine += l.tete().getNom()+", ";
			else 
				chaine += l.tete().getNom();
			
			l = (ListeMots) l.queue();
		}
		return chaine;
	}

	public static ListeMots couper(ListeMots listeMotsCandidat, int nbCandidat) {
	
		int i = 0;
		ListeMots l = null;
		while (listeMotsCandidat != null && ++i <= nbCandidat){
			
			if( l == null){
				l = new ListeMots(listeMotsCandidat.tete(), null);
			}	
			else {
				l.ajouterEnFinListe(listeMotsCandidat.tete());
			}
			
//			l.affiche();
			listeMotsCandidat = (ListeMots) listeMotsCandidat.queue();
		}
		return l;
	}

	public void ajouterEnFinListe(Mots m) {
		
		if (this.queue() == null){
			this.setQueue(new ListeMots(m, null));
		}	
		else{
			((ListeMots) this.queue()).ajouterEnFinListe(m);
		}
			
	}//	ajouterEnFinListe()
}
