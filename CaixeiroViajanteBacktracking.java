import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CaixeiroViajanteBacktracking {
    private int[][] grafo;
    private int numVertices;
    private boolean[] visitado;
    private int[] melhorCaminho;
    private int melhorDistancia;
    private int distanciaAtual;

    public CaixeiroViajanteBacktracking(int[][] grafo) {
        this.grafo = grafo;
        this.numVertices = grafo.length;
        this.visitado = new boolean[numVertices];
        this.melhorCaminho = new int[numVertices];
        this.melhorDistancia = Integer.MAX_VALUE;
        this.distanciaAtual = 0;
    }

    public ArrayList<Integer> encontrarMelhorCaminho() {
        visitado[0] = true;
        melhorCaminho[0] = 0;
        backtrack(0, 1);
        return new ArrayList<>(Arrays.asList(Arrays.stream(melhorCaminho).boxed().toArray(Integer[]::new)));
    }


    private void backtrack(int verticeAtual, int nivel) {
        if (nivel == numVertices) {
            int distancia = distanciaAtual + grafo[verticeAtual][0];
            if (distancia < melhorDistancia) {
                melhorDistancia = distancia;
                System.arraycopy(visitado, 0, melhorCaminho, 0, numVertices);
            }
            return;
        }

        for (int i = 1; i < numVertices; i++) {
            if (!visitado[i]) {
                visitado[i] = true;
                distanciaAtual += grafo[verticeAtual][i];
                backtrack(i, nivel + 1);
                distanciaAtual -= grafo[verticeAtual][i];
                visitado[i] = false;
            }
        }
    }
}
