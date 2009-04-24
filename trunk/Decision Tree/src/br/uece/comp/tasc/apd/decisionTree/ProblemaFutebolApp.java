package br.uece.comp.tasc.apd.decisionTree;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
/**
 * 
 * @author Fabiano
 *
 */
public class ProblemaFutebolApp {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		new ProblemaFutebolApp();
	}

	public ProblemaFutebolApp() throws IOException {
		AmostraTrain train = new AmostraTrain("entrada.txt");
		DecisionTree dmTree = new DecisionTree(train);
		train.printOut();
		dmTree.printTree();
		List<Pergunta> perguntas = obterPerguntas();
		for (Pergunta p : perguntas) {
			Resposta resposta = dmTree.responder(p);
			System.out.println(resposta);
		}
	}

	/**
	 * Obtemas as perguntas a partir de uma arquivo de perguntas com o formato
	 * de instancias igual ao dos dados de treinamento
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<Pergunta> obterPerguntas() throws IOException {
		List<Pergunta> perguntas = new LinkedList<Pergunta>();
		File file = new File("perguntas.txt");
		BufferedReader buffer = new BufferedReader(new FileReader(file));
		Scanner sc_cabecalho;
		String cabecalho = buffer.readLine();
		Scanner sc_linha;
		String linha = "";

		Pergunta pergunta;
		linha = buffer.readLine();

		while (linha != null) {
			sc_linha = new Scanner(linha);
			sc_cabecalho = new Scanner(cabecalho);
			pergunta = new Pergunta();
			while (sc_linha.hasNext()) {
				pergunta.setAtributo(sc_cabecalho.next(), sc_linha.next());
			}
			perguntas.add(pergunta);
			linha = buffer.readLine();
		}

		return perguntas;
	}

}
