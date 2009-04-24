package br.uece.comp.tasc.apd.decisionTree;
import java.util.ArrayList;

/**
 * Atributo: Eh a classe que define os diversos valores de atributo
 * 
 * @author Fabiano
 * 
 */
public class Atributo {
	public String name = new String();
	public ArrayList<String> valores = new ArrayList<String>();

	public Atributo(String name, ArrayList valores) {
		this.name = name;
		this.valores = valores;
	}

	public Atributo() {

	}
}
