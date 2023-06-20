public class CaixeiroViajanteProgramacaoDinamica {
    private final int MAX = 9999;
    private int numeroVertices;
    private double tamanho;
    private int[][] distance;
    private int completedVisit;
    private int[][] DP;

    public CaixeiroViajanteProgramacaoDinamica(int[][] matriz) {
        this.numeroVertices = matriz.length;
        this.tamanho = (double) this.numeroVertices;
        this.distance = matriz;
        this.completedVisit = (1 << this.numeroVertices) - 1;
        this.DP = new int[(int) Math.pow(2.0, this.tamanho)][this.numeroVertices * 2];
        for (int i = 0; i < (1 << this.numeroVertices); i++) {
            for (int j = 0; j < this.numeroVertices; j++) {
                DP[i][j] = -1;
            }
        }

    }

    public int valorMenorDistancia(int mark, int position) {

        if (mark == completedVisit) {
            return distance[position][0];
        }
        if (this.DP[mark][position] != -1) {
            return DP[mark][position];
        }
        int menorDistancia = MAX;
        for (int city = 0; city < this.numeroVertices; city++) {
            if ((mark & (1 << city)) == 0) {
                int aux = distance[position][city] + valorMenorDistancia(mark | (1 << city), city);
                menorDistancia = Math.min(menorDistancia, aux);
            }
        }
        DP[mark][position] = menorDistancia;
        return menorDistancia;
    }
}
