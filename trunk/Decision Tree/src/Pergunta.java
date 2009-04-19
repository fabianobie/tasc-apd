import java.util.HashMap;

public class Pergunta {

	private HashMap<String, String> atributos;

	public Pergunta() {
		atributos = new HashMap<String, String>();
	}

	public String getAtributo(String atributo) {
		return atributos.get(atributo);
	}

	public void setAtributo(String nomeAtrib, String valor) {
		atributos.put(nomeAtrib, valor);
	}
}
