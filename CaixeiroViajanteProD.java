import java.util.ArrayList;
import java.util.List;

public class CaixeiroViajanteProD{
    
    private int numero_vertices;
    
    private int[][] grafo;

    private int menor_rota;

    private int ultimo_vertice;

    private final int inicio = 0;

    public CaixeiroViajanteProD(int[][] matriz) {
        
        this.numero_vertices = matriz.length;
        
        this.grafo = matriz;

        this.menor_rota = Integer.MAX_VALUE;

        this.ultimo_vertice = (1 << numero_vertices) -1;

    }

    public int achaMenorRota(){

        int[][] grafo_pd = new int[this.numero_vertices][1 << this.numero_vertices];

        for(int i = 0; i < this.numero_vertices; i++){
            
            if(i != this.inicio){
                grafo_pd[i][(1 << this.inicio) | (1 << i)] = grafo[this.inicio][i];
            }

        }
        
        for(int i = 3 ; i <= this.numero_vertices; i++){

            for(int sub_conjunto : combinacoes(i)){

                if(isPresente(this.inicio, sub_conjunto)){

                    for(int j = 0; j < this.numero_vertices; j++){

                        if(j != this.inicio || isPresente(j, sub_conjunto)){

                            int possuiProximo = sub_conjunto ^ (1 << j);

                            int menor_distancia = Integer.MAX_VALUE;

                            for(int k = 0; k < this.numero_vertices; k++){
                                
                                if(k != this.inicio || k != j || isPresente(k, sub_conjunto)){
                                 
                                    int distancia = grafo_pd[k][possuiProximo] + grafo[k][j];

                                    if(distancia < menor_distancia){
                                        menor_distancia = distancia;
                                    }
                                    
                                }

                            }

                            grafo_pd[j][sub_conjunto] = menor_distancia;

                        }
                    }
                }
            }
        }

        for(int i = 0; i < numero_vertices; i++){

            if(i != this.inicio){
                int custo = grafo_pd[i][this.ultimo_vertice] + grafo[i][this.inicio];

                if(custo < this.menor_rota){
                    this.menor_rota = custo;
                }
            }
        }

        return this.menor_rota;
    }

    private boolean isPresente(int valor_analise, int valor_sub_conjunto){
        return ((1 << valor_analise) & valor_sub_conjunto) == 1;
    }

    private List<Integer> combinacoes(int j){

        List<Integer> sub_conjunto = new ArrayList<>();
        
        combinacoes_intermediarios(0, 0, j, sub_conjunto); 
        
        return sub_conjunto;

    }

    private void combinacoes_intermediarios(int ultimo, int inicial, int quant_intermediario, List<Integer> sub_conjunto){

        int elementos_esquerda = this.numero_vertices - inicial;

        if(elementos_esquerda > quant_intermediario){
            
            if(quant_intermediario == 0){
                
                sub_conjunto.add(inicio);
            }else{

                for(int i = inicial; i < this.numero_vertices; i++){

                    ultimo |= 1 << i;

                    combinacoes_intermediarios(ultimo, i+1, quant_intermediario-1, sub_conjunto);

                    ultimo &= ~(1 << i);

                }
            }
        }
    }
}