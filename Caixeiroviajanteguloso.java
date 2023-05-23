import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Caixeiroviajanteguloso{
    
    private static final Random aleatorio = new Random(42);

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        int vertices = recebeQuantidadeVertices();

        int[][] matriz = grafoCompletoPonderado(vertices);
        
        imprimiGrafo(matriz);

        int cidade_inicial = recebeCidadeInicial(matriz);
        
        loopCaixeiroViagante(matriz, cidade_inicial);

    }

    public static int recebeQuantidadeVertices(){

        System.out.println("Informe a quantidade de vértices: ");
        
        String vertices = scanner.nextLine();

        return trataString(vertices);
        

    }

    public static int recebeCidadeInicial(int[][] matriz){

        System.out.println("Informe a cidade inicial: ");
        
        String cidade_inicial = scanner.nextLine();
        
        int cidade_inicial_int = trataString(cidade_inicial);

        boolean existe = false;

        while(!existe){

            for(int i = 0 ; i < matriz.length; i++){

                if(cidade_inicial_int == i){
                    return cidade_inicial_int;
                }
    
            }

        }

        return cidade_inicial_int;        

    }

    public static void loopCaixeiroViagante(int[][] matriz, int cidade_inicial){

        int distancia_total = 0;
        int contador = 0;
        int i = 0;
        int j = 0;

        int menor_distancia = 999999999;
        int tamanho_matriz = matriz.length;

        int[] rota_tracada = new int[tamanho_matriz];

        LinkedList<Integer> vertices_visitados = new LinkedList<>();
        vertices_visitados.add(cidade_inicial);

        //vai até o penúltimo objeto
        while(contador >= (tamanho_matriz - 1)){

            //se não estivermos no mesmo vértice e não tiver sido visitado
            if(j != i && !(vertices_visitados.contains(j)) ){

                //caso esta seja a menor distância de i até j
                if(matriz[i][j] < menor_distancia){
                    menor_distancia = matriz[i][j];
                    rota_tracada[contador] = (j+1);
                }
            }
            j++;

            //caso j seja igual ao tamanho de i quer dizer que o último vértice é o mais próximo de i
            if(j == matriz[i].length){

                int aux = rota_tracada[contador]-1;

                distancia_total += menor_distancia;
                menor_distancia = 999999999;
                vertices_visitados.add(aux);
                j = 0;
                i = aux;
                contador++;

            }
        }

        //Para facilitar na descoberta usa-se força bruta já que sua complexidade vai ser de igual a O(N),
        //sendo este a última cidade a ser visitada.
        for (j = 0; j < tamanho_matriz; j++) {
 
            if ((i != j) && matriz[i][j] < menor_distancia) {
                menor_distancia = matriz[i][j];
                rota_tracada[contador] = j + 1;
            }
        }

        distancia_total += menor_distancia;

        System.out.println("A distância de menor custo é de: " + distancia_total);
    
    }

    /* Gerador */

    public static int[][] grafoCompletoPonderado(int vertices){
        int[][] matriz = new int[vertices][vertices];
        int valor;
        for (int i = 0; i < matriz.length; i++) {
            matriz[i][i]=-1;         
            for (int j = i+1; j < matriz.length; j++) {
                valor = aleatorio.nextInt(25)+1;
                matriz[i][j] = valor;
                matriz[j][i] = valor;
            }
        }  
        return matriz;
    }

    /* Utils */

    public static void imprimiGrafo(int[][] matriz){

        for(int i = 0; i < matriz.length; i++){
            for(int j = 0; j < matriz.length; j++){
                System.out.print("[" + matriz[i][j] + "]");
            }
            System.out.println();
        }

    }

    /* Tratamentos */

    public static int trataString(String numero){

        if(numero.matches("\\d+")){
            return Integer.parseInt(numero);
        }else{
            throw new RuntimeException("Erro ao converter a string: " + numero);
        }

    }

}