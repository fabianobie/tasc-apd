package br.uece.comp.tasc.apd.decisionTree;
/**
 * 
 * @author Fabiano Um noh da Arvore de decisao
 */
public class DecisionNoh {
	/**
	 * Campos
	 */
	private String[] instancias;
	private String nomeRamo;
	private String nomeAtributo;
	private double entropia;
	private DecisionNoh[] nohs;
	private Resposta resposta = Resposta.VAZIO;

	public Resposta getResposta() {
		return resposta;
	}

	public void setResposta(Resposta resposta) {
		this.resposta = resposta;
	}

	/**
	 *Constructor
	 */

	public DecisionNoh() {

	}
/**
 * Possui um conjunto de instancias e um nome do ramo de origem
 * @param instancias
 * @param nomeRamo
 */
	public DecisionNoh(String[] instancias, String nomeRamo) {
		this.instancias = instancias;
		this.nomeRamo = nomeRamo;
	}

	/**
	 * Methods and Functions
	 */

	/**
	 * Getters and Setters
	 */

	public String getNomeRamo() {
		return nomeRamo;
	}

	public void setNomeRamo(String nomeRamo) {
		this.nomeRamo = nomeRamo;
	}

	public String[] getInstancias() {
		return instancias;
	}

	public void setInstancias(String[] instancias) {
		this.instancias = instancias;
	}

	public double getEntropia() {
		return entropia;
	}

	public void setEntropia(double entropia) {
		this.entropia = entropia;
	}

	public void setNomeAtributo(String nomeAtributo) {
		this.nomeAtributo = nomeAtributo;
	}

	public String getNomeAtributo() {
		return nomeAtributo;
	}

	public void setNohs(DecisionNoh[] nohs) {
		this.nohs = nohs;
	}

	public DecisionNoh[] getNohs() {
		return nohs;
	}

	public DecisionNoh getNoh(String valor) {
		DecisionNoh result = getNohVazio();
		for (int i = 0; i < nohs.length; i++) {
			if (nohs[i].getNomeRamo().equals(valor)) {
				result = nohs[i];
				break;
			}
		}
		return result;
	}

	public boolean isFolha() {
		return (nohs == null) || (nohs.length == 0);
	}

	/**
	 * verifica se o noh eh vazio para que o algoritmo saiba q chegou em uma
	 * folha
	 * 
	 * @return
	 */
	public DecisionNoh getNohVazio() {
		DecisionNoh noh = new DecisionNoh();
		noh.setNomeAtributo("");
		noh.setNomeRamo("sem resposta");
		return noh;
	}
}
