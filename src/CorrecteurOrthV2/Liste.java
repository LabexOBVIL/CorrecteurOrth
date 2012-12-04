package CorrecteurOrthV2;

public abstract class Liste<T> {

	private T contenu;
	private Liste<T> suivant;

	
	public Liste(T contenu, Liste<T> suivant) {
		this.contenu = contenu;
		this.suivant = suivant;

	}

	public T tete() {
		return this.contenu;
	}
	
	public Liste<T> queue() {
		return this.suivant;
	}
	
	
	public void setQueue(Liste<T> l){
		/*if (this.queue() == null)
			this.suivant = l;
		else 
			this.queue().setQueue(l);
		*/	
		suivant = l;

	}
	
	
	public abstract Liste<T> ajouterEnTete(T contenu);

	
	public abstract boolean estDans(String x);
	
	
	public abstract void affiche ();
	
	/*
	public static void main(String[] args) {
		
		Liste<String> l = new Liste<String>("0", null);
		
		for (int i = 1; i < 15; i++) {
			Liste<String> l2 = l.ajouterEnTete(""+i);
			l= l2;
		}
		
		l.affiche();
		
	}
	*/
}
