import java.util.ArrayList;

/**
 * 
 * @author Fabiano Tavares
 * 
 */
public class DecisionTree {
	private DecisionNoh raiz;
	private AmostraTrain train;

	public DecisionTree(AmostraTrain train) {
		this.train = train;
		criaArvore();
	}

	private void criaArvore() {
		String[] instancias = DecisionUtil.setInstancias(train.getTuplas());
		raiz = criaNoh(instancias, "");
	}

	private DecisionNoh criaNoh(String[] instancias, String vlrAtributo) {
		DecisionNoh res = new DecisionNoh();
		Atributo[] atributos = train.getAtributos();
		Classificado aClass = new Classificado();
		aClass = classifica(atributos, instancias);
		double entropia = DecisionUtil.entropy(DecisionUtil.getInstacias(instancias, train
				.getTuplas()));
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
						conjInstancia = splitInstancias(instancias, nome, vlrAtrib);
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
				}

				while (i < nivel) {
					System.out.print("\t");
					i++;
				}
				System.out.print(no.getNomeRamo() + " >> ");

				prePrintTree(no, nivel + 2);
			}

		}
	}

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

	private String[] splitInstancias(String[] instanciaAtual, String atributo, String vlrAtributo) {
		ArrayList<String> res = new ArrayList<String>();
		String[][] dados = DecisionUtil.getInstacias(instanciaAtual, train.getTuplas());
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

	private Classificado classifica(Atributo[] atributos, String[] instancias) {
		double[] ganho = new double[atributos.length];
		String[][] tuplas = DecisionUtil.getInstacias(instancias, train.getTuplas());
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

	public Resposta responder(Pergunta pergunta) {
		DecisionNoh no = raiz;
		String atributo = no.getNomeAtributo();
		String valor = pergunta.getAtributo(atributo);

		while(!no.isFolha()) {
			no = no.getNoh(valor);
			atributo = no.getNomeAtributo();
			valor = pergunta.getAtributo(atributo);
		}

		return no.getResposta();
	}
}
