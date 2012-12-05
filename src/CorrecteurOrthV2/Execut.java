package CorrecteurOrthV2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

public class Execut {

	public static void main(String[] args) throws IOException {
		Correcteur.CoorigerUnText(new FileReader("C:/Users/bouabd/workspace/CorrecteurOrth/materiel/fautes2012.txt"));
	}//main()
			
	
}//Execut.java
