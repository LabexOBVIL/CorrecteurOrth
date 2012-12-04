package CorrecteurOrthV2;

public class QuickSortListe {

	static public ListeMots _quick_sort (ListeMots list, ListeMots end)
	{
		ListeMots pivot, tmp, next, prec, suiv;
		
	    if ( list != end && list.queue() != end )
	    {
	        /* partitionnement (fin liste 'prec' : 'pivot'; fin liste 'suiv' : 'end') */
	        pivot = list;
	        prec = pivot;
	        suiv = end;
	        for ( tmp = (ListeMots) list.queue(); tmp != end; tmp = next )
	        {
	            next = (ListeMots) tmp.queue();
	            if (tmp.tete().getDistanceLenvenshtein() < pivot.tete().getDistanceLenvenshtein()){
	                tmp.setQueue(suiv);
	            	suiv = tmp; /* ajoute la cellule a la liste 'suiv' */
	            }	
	            else{
	                tmp.setQueue(prec);
	                prec = tmp; /* ajoute la cellule a la liste 'prec' */
	            }    
	        }
	        /* appels recursifs */
	        prec = _quick_sort (prec, pivot);
	        suiv = _quick_sort (suiv, end);
	        /* recolle la liste */
	        pivot.setQueue(suiv);
	        return prec;
	    }
	    return list;
	}

	static public ListeMots quick_sort (ListeMots list)
	{
	    return _quick_sort (list, null);
	}

}
