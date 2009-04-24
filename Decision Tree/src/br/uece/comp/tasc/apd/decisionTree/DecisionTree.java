package br.uece.comp.tasc.apd.decisionTree;
import java.util.ArrayList;

/**
 * 
 * @author Fabiano Tavares
 * @version 1.0
 * 
 *          Arvores de Decisão : Uma árvore de decisão é uma representação de
 *          uma tabela de decisão sob a forma de uma árvore. Tem a mesma
 *          utilidade da tabela de decisão. Trata-se de uma maneira alternativa
 *          de expressar as mesmas regras que são obtidas quando se constrói a
 *          tabela
 * 
 * 
 */

public class DecisionTree {
	private DecisionNoh raiz;
	private AmostraTrain train;

	public DecisionTree(AmostraTrain train) {
		this.train = train;
		criaArvore();
	}

	/**
	 * inicar a criação da arvore
	 */
	private void criaArvore() {
		String[] instancias = DecisionUtil.setInstancias(train.getTuplas());
		raiz = criaNoh(instancias, "");
	}

	/**
	 * Algoritmo ID3: Calculando a entropia, analisando o nivel de organização
	 *  e  separando as instancias criando os nos, ramos e a arvore.
	 * 
	 * @param instancias
	 * @param vlrAtributo
	 * @return
	 * 
	 */
	private DecisionNoh criaNoh(String[] instancias, String vlrAtributo) {
		DecisionNoh res = new DecisionNoh();
		Atributo[] atributos = train.getAtributos();
		Classificado aClass = new Classificado();
		aClass = classifica(atributos, instancias);
		double entropia = DecisionUtil.entropy(DecisionUtil.getInstacias(
				instancias, train.getTuplas()));
		/*
		 * considerações iniciais
		 */
		res.setNomeRamo(vlrAtributo);
		res.setInstancias(instancias);
		res.setEntropia(entropia);

		if (entropia == 0.0) {

			res.setNomeAtributo("FOLHA");
			res.setResposta(verificaResposta(instancias));

		} else {
			ArrayList<String> valorAtrib = new ArrayList<String>();
			String[] conjInstancia = null;
			for (int i = 0; i < atributos.length; i++) {
				if (atributos[i].name.equals(aClass.atributo)) {
					valorAtrib = atributos[i].valores;
					String nome = atributos[i].name;
					DecisionNoh[] nohsFilho = new DecisionNoh[valorAtrib.size()];
					int count = 0;
					for (String vlrAtrib : valorAtrib) {
						conjInstancia = splitInstancias(instancias, nome,
								vlrAtrib);
						nohsFilho[count] = criaNoh(conjInstancia, vlrAtrib);
						count++;
					}
					res.setNohs(nohsFilho);
					res.setEntropia(entropia);
					res.setNomeAtributo(aClass.atributo);
					res.setResposta(Resposta.VAZIO);
				}
			}
		}

		return res;
	}
/**
 * impressao da arvore
 */
	public void printTree() {
		if (raiz != null) {
			prePrintTree(raiz, 1);
		}
	}

	private void prePrintTree(DecisionNoh noh, int nivel) {
		int i = 0;

		if (!noh.getNomeAtributo().equals("FOLHA"))
			System.out.print(noh.getNomeAtributo());
		else
			System.out.print("");

		if (noh.getResposta() != Resposta.VAZIO)
			System.out.println(noh.getResposta());
		else
			System.out.println("");

		if (noh.getNohs() != null) {
			for (DecisionNoh no : noh.getNohs()) {
				i = 0;

				while (i < nivel) {
					System.out.print("\t");
					i++;
					if (i % 2 == 1)
						System.out.print("|");
				}
				System.out.print("__");
				System.out.print(no.getNomeRamo() + " >>>> ");
				prePrintTree(no, nivel + 2);
			}

		}
	}
/**
 * verifica o valor da resposta nas instancias
 * @param inst
 * @return
 */
	private Resposta verificaResposta(String[] inst) {
		Resposta resposta = null;
		String[][] dados = DecisionUtil.getInstacias(inst, train.getTuplas());
		int numCol = dados[0].length;
		if (dados.length != 1) {
			if (dados[1][numCol - 1].equals("true")) {
				resposta = Resposta.SIM;
			} else {
				resposta = Resposta.NAO;
			}
		} else {
			resposta = Resposta.NAO;
		}
		return resposta;
	}

/**
 * divide as instancias para definir os proximos nohs	
 * @param instanciaAtual
 * @param atributo
 * @param vlrAtributo
 * @return
 */
	private String[] splitInstancias(String[] instanciaAtual, String atributo,
			String vlrAtributo) {
		ArrayList<String> res = new ArrayList<String>();
		String[][] dados = DecisionUtil.getInstacias(instanciaAtual, train
				.getTuplas());
		boolean flag = true;

		res.add(dados[0][0]);
		for (int i = 0; i < dados[0].length && flag; i++) {
			if (dados[0][i].equals(atributo)) {
				for (int j = 1; j < dados.length; j++) {
					if (dados[j][i].equals(vlrAtributo)) {
						res.add(dados[j][0]);
					}
				}
				flag = false;
			}

		}

		String[] instancias = new String[res.size()];
		return res.toArray(instancias);
	}
/**
 * classifica as instancias de acordo com os atributos
 * @param atributos
 * @param instancias
 * @return
 */
	private Classificado classifica(Atributo[] atributos, String[] instancias) {
		double[] ganho = new double[atributos.length];
		String[][] tuplas = DecisionUtil.getInstacias(instancias, train
				.getTuplas());
		Classificado aClass = new Classificado();

		for (int i = 0; i < atributos.length; i++) {
			ganho[i] = DecisionUtil.gain(tuplas, atributos[i]);
		}

		double max = -9999;
		int idMax = 0;
		for (int j = 0; j < ganho.length; j++) {
			if (ganho[j] >= max) {
				max = ganho[j];
				idMax = j;
			} else {
				continue;
			}
		}
		aClass.atributo = atributos[idMax].name;
		aClass.ganho = max;
		return aClass;
	}
/**
 * A partir de uma pergunta eh gerada uma resposta depois de arvore gerada.
 * @param pergunta
 * @return
 */
	public Resposta responder(Pergunta pergunta) {
		DecisionNoh no = raiz;
		String atributo = no.getNomeAtributo();
		String valor = pergunta.getAtributo(atributo);

		while (!no.isFolha()) {
			no = no.getNoh(valor);
			atributo = no.getNomeAtributo();
			valor = pergunta.getAtributo(atributo);
		}

		return no.getResposta();
	}
}
