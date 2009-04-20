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

public class ProblemaFutebolApp {

	public static final String TIME1 = "time_1";

	public static final String TIME2 = "time_2";

	public static final String RENDIMENTO = "rendimento";

	public static final String PARTIDA = "tempo_partida";

	public static final String DIF_GOLS = "diff";

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
		File out = new File("saida.txt");
		BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(out));

		List<Pergunta> perguntas = obterPerguntas();
		Pergunta pergunta = new Pergunta();
		pergunta.setAtributo(TIME1, "America/RN");
		pergunta.setAtributo(TIME2, "Vasco/RJ");
		pergunta.setAtributo(RENDIMENTO, "Fantastico");
		pergunta.setAtributo(PARTIDA, "inicio");
		
		//List<Pergunta> perguntas = new LinkedList<Pergunta>();
		//perguntas.add(pergunta);		
		//train.printOut();
		//dmTree.printTree();

		for (Pergunta p : perguntas) {
			Resposta resposta = dmTree.responder(p);
			System.out.println(resposta);
		}
	}

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
		
		while(linha != null){
			sc_linha = new Scanner(linha);
			sc_cabecalho = new Scanner(cabecalho);
			pergunta = new Pergunta();
			while(sc_linha.hasNext()) {
				pergunta.setAtributo(sc_cabecalho.next(), sc_linha.next());
			}
			perguntas.add(pergunta);
			linha = buffer.readLine();
		}
		
		return perguntas;
	}

}
