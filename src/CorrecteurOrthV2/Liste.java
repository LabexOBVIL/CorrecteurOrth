package CorrecteurOrthV2;

public abstract class Liste<T> {

	private T contenu;
	private Liste<T> suivant;

	public Liste(T contenu, Liste<T> suivant) {
		this.contenu = contenu;
		this.suivant = suivant;
	}

	public T tete() { return this.contenu; }
	
	public Liste<T> queue() { return this.suivant; }
	
	public void setQueue(Liste<T> l){ suivant = l;}

	public abstract Liste<T> ajouterEnTete(T contenu);

	public abstract boolean estDans(String x);
	
	public abstract void affiche ();
	
}
