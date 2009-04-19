import java.io.IOException;


public class ProblemaEstudanteApp {

	public static final String AGE = "AGE"; 

	public static final String INCOME = "INCOME";

	public static final String STUDENT = "STUDENT";

	public static final String CREDIT_RATING = "CREDIT_RATING";

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		AmostraTrain train = new AmostraTrain("file2.dm");
		DecisionTree dmTree = new DecisionTree(train);

		Pergunta pergunta = new Pergunta();

		pergunta.setAtributo(AGE, ">40");
		pergunta.setAtributo(STUDENT, "true");
		pergunta.setAtributo(CREDIT_RATING, "excellent");
		pergunta.setAtributo(INCOME, "high");

		Resposta resposta = dmTree.responder(pergunta);
		//train.printOut();
		dmTree.printTree();
		System.out.println(resposta);
	}

}
