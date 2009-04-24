package br.uece.comp.tasc.apd.decisionTree;
/**
 * Proporção: Utilizado no calculo da entropia
 * 
 * @author Fabiano
 * 
 */
public class Proporcao {
	public int pos = 0;
	public int neg = 0;

	public double total() {
		return pos + neg;
	}
}
