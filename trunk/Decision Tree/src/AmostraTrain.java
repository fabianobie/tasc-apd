import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class AmostraTrain {
	private Atributo[] atributos;
	private String[][] tuplas;
	
	public AmostraTrain(String adress) {
		readDataMining( adress , this );
	}
	
	public void  printOut(){
		System.out.println("\nHEAD");
		
		for (Atributo atributo : atributos) {
			System.out.print("\n# "+atributo.name+" >> ");
			for (String tipoAtributo : atributo.valores) {
				System.out.print("  "+tipoAtributo);
			}
		}
		
		System.out.println("\n\nBODY\n");
		
		for (String[] strings : tuplas) {
			for (String string : strings) {
				System.out.print(" | "+string);
			}
			System.out.println(" >");
		}
	}
	
	public static void readDataMining(String adress, AmostraTrain train) {

		try {
			File inFile = new File(adress);
			BufferedReader buffer = new BufferedReader(new FileReader(inFile));
			Scanner sc = new Scanner(buffer);
			String[][] words = null;
			int numCol = 0;
			int numLin = 0;

			while (sc.hasNext()) {
				numLin = sc.nextInt();
				numCol = sc.nextInt();
				
				words = new String[numLin][numCol];

				for (int i = 0; i < numLin; i++) {
					for (int j = 0; j < numCol; j++) {
						words[i][j] = sc.next();
					}
				}
			}
			
			Atributo[] atributoList = new Atributo[numCol - 2];
			
			for (int z=0; z < atributoList.length ; z++) {
				for (int i = 1; i < numCol - 1; i++) {
					Atributo atributo = new Atributo();
					atributo.name = words[0][i];
					for (int j = 1; j < numLin ; j++) {					
						if (!atributo.valores.contains(words[j][i])) {
							atributo.valores.add(words[j][i]);
						}
					}
					atributoList[i-1] = atributo; 
				}
				
			}
			
			train.setAtributos(atributoList);
			train.setTuplas(words);

		} catch (IOException ioe) {

		}
	}
	

	public Atributo[] getAtributos() {
		return atributos;
	}

	public void setAtributos(Atributo[] atributos) {
		this.atributos = atributos;
	}

	public String[][] getTuplas() {
		return tuplas;
	}

	public void setTuplas(String[][] tuplas) {
		this.tuplas = tuplas;
	}
	
	public static void readSet(String fileName) throws IOException{
		File file = new File(fileName);
		File fileNovo = new File("novo_"+fileName);
		BufferedReader buffer = new BufferedReader(new FileReader(file));
		BufferedWriter out  = new BufferedWriter(new FileWriter(fileNovo));
		String linha = "";
		String linhaNova = "";
		int i=1;
		while(buffer.ready()){
			linha=buffer.readLine();
			Scanner sc = new Scanner(linha);
			String a = sc.next();
			   a=sc.next();
			   a=sc.next();
			   
			 int b = Integer.parseInt(sc.next());
			 int c = Integer.parseInt(sc.next());
			if(b>=c){
				linha+=" true";
			}else{
				linha+=" false";
			}
			out.append(i+++" "+ linha +"\n");
		}
		out.close();
	}
}
