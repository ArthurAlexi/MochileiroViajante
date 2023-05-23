import java.util.ArrayList;

public class CaixeiroViajanteForcaBruta {

    public int[][] grafo;
    private int numVertices;
    private ArrayList<Integer> melhorCaminho;
    private int menorDistancia;

    public CaixeiroViajanteForcaBruta(int[][] grafo) {
        this.grafo = grafo;
        this.numVertices = grafo.length;
        this.melhorCaminho = new ArrayList<>();
        this.menorDistancia = Integer.MAX_VALUE;
    }
   

    public ArrayList<Integer> encontrarMelhorCaminho() {
        ArrayList<Integer> caminhoAtual = new ArrayList<>();
        caminhoAtual.add(0); // Começa a partir do vértice 0
        encontrarMelhorCaminhoRecursivo(caminhoAtual, 0);
        return melhorCaminho;
    }

    private void encontrarMelhorCaminhoRecursivo(ArrayList<Integer> caminhoAtual, int distanciaAtual) {
        int ultimoVertice = caminhoAtual.get(caminhoAtual.size() - 1);

        if (caminhoAtual.size() == numVertices) {
            // Adiciona o caminho de volta ao vértice inicial
            caminhoAtual.add(0);
            distanciaAtual += grafo[ultimoVertice][0];

            if (distanciaAtual < menorDistancia) {
                menorDistancia = distanciaAtual;
                melhorCaminho = new ArrayList<>(caminhoAtual);
            }

            // Remove o caminho de volta ao vértice inicial para explorar outras possibilidades
            caminhoAtual.remove(caminhoAtual.size() - 1);
            return;
        }

        for (int proximoVertice = 0; proximoVertice < numVertices; proximoVertice++) {
            if (!caminhoAtual.contains(proximoVertice)) {
                caminhoAtual.add(proximoVertice);
                int novaDistancia = distanciaAtual + grafo[ultimoVertice][proximoVertice];

                encontrarMelhorCaminhoRecursivo(caminhoAtual, novaDistancia);

                caminhoAtual.remove(caminhoAtual.size() - 1);
            }
        }
    }
}