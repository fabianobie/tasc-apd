package br.uece.comp.tasc.apd.decisionTree;
/**
 * Classe Util: utilizada para calculos matematicos auxilares na geração da
 * arvore de decisao:
 * --Entropia
 * --Ganho
 * --Somatorio da Entropia
 * 
 * @author Fabiano Tavares
 * 
 */
public class DecisionUtil {
	/**
	 * 
	 * @param instancias
	 * @return
	 */
	public static double entropy(String[][] instancias) {
		Proporcao prop = getProp(instancias);
		return entropy(prop.pos, prop.neg);
	}

	/**
	 * 
	 * @param positivo
	 * @param negativo
	 * @return
	 */
	public static double entropy(int positivo, int negativo) {
		double res;
		if (positivo == 0 || negativo == 0) {
			res = 0;
		} else {
			double total = (double) (positivo + negativo);
			double pos = (positivo / total);
			double neg = (negativo / total);
			res = -pos * log2(pos) - (neg) * log2(neg);
		}
		return res;
	}

	/**
	 * 
	 * @param dados
	 * @return
	 */
	public static Proporcao getProp(String[][] dados) {
		Proporcao prop = new Proporcao();
		int numLin = dados.length;
		int numCol = dados[0].length;

		for (int i = 0; i < numLin; i++) {
			if (dados[i][numCol - 1].equals("true")) {
				prop.pos++;
			} else if (dados[i][numCol - 1].equals("false")) {
				prop.neg++;
			}
		}
		return prop;
	}

	/**
	 * 
	 * @param S
	 * @param A
	 * @return
	 */
	public static double gain(String[][] S, Atributo A) {
		double gain;
		gain = entropy(S) - sumEntropy(S, A);
		return gain;
	}

	/**
	 * 
	 * @param S
	 * @param atributo
	 * @return
	 */
	private static double sumEntropy(String[][] S, Atributo atributo) {
		double eCount = 0;
		Proporcao prop = new Proporcao();
		int numCol = S[0].length;
		int numLin = S.length;

		for (int i = 1; i < numCol - 1; i++) {
			if (S[0][i].equals(atributo.name)) {
				for (String tipoAtributo : atributo.valores) {
					prop.pos = 0;
					prop.neg = 0;
					for (int j = 1; j < numLin; j++) {
						if (S[j][i].equals(tipoAtributo)) {
							if (S[j][numCol - 1].equals("true")) {
								prop.pos++;
							} else if (S[j][numCol - 1].equals("false")) {
								prop.neg++;
							}
						}
					}

					eCount += ((double) prop.total() / (double) (numLin - 1))
							* entropy(prop.pos, prop.neg);
				}
			}
		}

		return eCount;
	}

	/**
	 * 
	 * @param num
	 * @return
	 */
	private static double log2(double num) {
		return Math.log(num) / Math.log(2);
	}

	/**
	 * 
	 * @param linhas
	 * @param matriz
	 * @return
	 */
	public static String[][] getInstacias(String[] linhas, String[][] matriz) {
		String[][] res = new String[linhas.length][matriz[0].length];
		int count = 0;
		for (int j = 0; j < linhas.length; j++) {
			for (int i = 0; i < matriz.length; i++) {
				if (linhas[j].equals(matriz[i][0])) {
					res[count] = matriz[i];
					count++;
				}
			}
		}
		return res;
	}

	/**
	 * 
	 * @param matriz
	 * @return
	 */
	public static String[] setInstancias(String[][] matriz) {
		String[] res = new String[matriz.length];

		for (int i = 0; i < matriz.length; i++) {
			res[i] = matriz[i][0];

		}
		return res;
	}

	/**
	 * Seleciona o Maximo dos Ganhos dos Atributos
	 * 
	 * @param entropias
	 * @return
	 */
	public static double selectMax(double[] entropias) {
		double max = -9999;
		for (double d : entropias) {
			if (d > max) {
				max = d;
			} else {
				continue;
			}
		}
		return max;
	}
}
