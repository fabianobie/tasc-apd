
import java.io.IOException;
/**
 * 
 * @author Fabiano
 *
 */

public class Teste {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//AmostraTrain.readSet("file.dm");
		AmostraTrain train = new AmostraTrain("novo_file.dm");
		DecisionTree dmTree = new DecisionTree(train);
		train.printOut();
		dmTree.printTree();
	
	}

}
