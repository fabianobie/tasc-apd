
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
		AmostraTrain train = new AmostraTrain("file2.dm");
		DecisionTree dmTree = new DecisionTree(train);
		//dmTree.perguntar("file2008.dm");
		train.printOut();
		dmTree.printTree();
	}

}
