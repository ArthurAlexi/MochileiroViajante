public class CaixeiroViajanteProgramacaoDinamica{
    
    private int numero_vertices;
    
    private int visita_completa;
    
    private int[][] grafo;
    
    private int[][] grafo_pd;

    public CaixeiroViajanteProgramacaoDinamica(int[][] matriz) {
        
        this.numero_vertices = matriz.length;
        
        this.grafo = matriz;
        
        this.visita_completa = (1 << this.numero_vertices) - 1;
        
        //2^n pelo tamanho (casas) dos bits à serem utilizados na análise
        this.grafo_pd = 
            new int[(int) Math.pow(2.0, this.numero_vertices)][this.numero_vertices * 2];
        
        for (int i = 0; i < (1 << this.numero_vertices); i++) {
            for (int j = 0; j < this.numero_vertices; j++) {
                
                grafo_pd[i][j] = -1;
            }
        }

    }

    public int valorMenorDistancia (int marco_visita, int posicao_atual) {

        if (marco_visita == this.visita_completa) {
            return this.grafo[posicao_atual][0];
        }

        if (this.grafo_pd[marco_visita][posicao_atual] != -1) {
            return this.grafo_pd[marco_visita][posicao_atual];
        }

        int menor_distancia = Integer.MAX_VALUE;

        for (int i = 0; i < this.numero_vertices; i++) {

            int calculo = (1 << i);
            
            if ((marco_visita & calculo) == 0) {

                int aux = this.grafo[posicao_atual][i] + valorMenorDistancia(marco_visita | calculo, i);
                
                menor_distancia = Math.min(menor_distancia, aux);
            }

        }

        this.grafo_pd[marco_visita][posicao_atual] = menor_distancia;
        
        return menor_distancia;
    }
}
