import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Caixeiroviajanteguloso {

    private static final Random aleatorio = new Random(42);

    private static final Scanner scanner = new Scanner(System.in);

    private static LinkedList<LinkedList<Integer>> CAMINHOS = new LinkedList<>();

    private static LinkedList<Long> TEMPOS = new LinkedList<>();

    private static LinkedList<Integer> TAMANHO_TOTAL = new LinkedList<>();

    private static Long TEMPO_TOTAL = 0L;

    public static void main(String[] args) throws IOException {

        // int vertices = recebeQuantidadeVertices();

        // int[][] matriz = grafoCompletoPonderado(vertices);

        // imprimiGrafo(matriz);

        // int cidade_inicial = recebeCidadeInicial(matriz);

        // loopCaixeiroViagante(matriz, cidade_inicial);

        System.out.println("INICIO LOOP");

        for (int i = 0; i < 1000; i++) {

            System.out.println(i);

            int[][] matriz = grafoCompletoPonderado(500);

            loopCaixeiroViagante(matriz, 0);

        }

        escreveArquivo();

        System.out.println(TEMPO_TOTAL + "ms");
    }

    public static int recebeQuantidadeVertices() {

        System.out.println("Informe a quantidade de vértices: ");

        String vertices = scanner.nextLine();

        return trataString(vertices);

    }

    public static int recebeCidadeInicial(int[][] matriz) {

        boolean existe = false;

        while (!existe) {

            System.out.println("Informe a cidade inicial: ");

            String cidade_inicial = scanner.nextLine();

            int cidade_inicial_int = trataString(cidade_inicial);

            for (int i = 0; i < matriz.length; i++) {

                if (cidade_inicial_int == i) {
                    return cidade_inicial_int;
                }

            }

        }

        return 0;

    }

    public static void loopCaixeiroViagante(int[][] matriz, int cidade_inicial) {

        int distancia_total = 0;
        int contador = 0;
        int i = 0;
        int j = 0;

        int menor_distancia = 999999999;
        int tamanho_matriz = matriz.length;

        int[] rota_tracada = new int[tamanho_matriz];

        LinkedList<Integer> vertices_visitados = new LinkedList<>();
        vertices_visitados.add(cidade_inicial);

        long tempo_inicial = System.currentTimeMillis();

        // vai até o penúltimo objeto
        while (i < tamanho_matriz && j < tamanho_matriz) {

            if (contador >= (tamanho_matriz - 1)) {
                break;
            }

            // se não estivermos no mesmo vértice e não tiver sido visitado
            if (j != i && !(vertices_visitados.contains(j))) {

                // caso esta seja a menor distância de i até j
                if (matriz[i][j] < menor_distancia) {
                    menor_distancia = matriz[i][j];
                    rota_tracada[contador] = (j + 1);
                }
            }
            j++;

            // caso j seja igual ao tamanho de i quer dizer que o último vértice é o mais
            // próximo de i
            if (j == matriz[i].length) {

                int aux = rota_tracada[contador] - 1;

                distancia_total += menor_distancia;
                menor_distancia = 999999999;
                vertices_visitados.add(aux);
                j = 0;
                i = aux;
                contador++;

            }
        }

        // Para facilitar na descoberta usa-se força bruta já que sua complexidade vai
        // ser de igual a O(N),
        // sendo este a última cidade a ser visitada.
        for (j = 0; j < tamanho_matriz; j++) {

            if ((i != j) && matriz[i][j] < menor_distancia) {
                menor_distancia = matriz[i][j];
                rota_tracada[contador] = j + 1;
            }
        }

        distancia_total += menor_distancia;

        long tempo_total = System.currentTimeMillis() - tempo_inicial;

        CAMINHOS.add(vertices_visitados);
        TAMANHO_TOTAL.add(distancia_total);
        TEMPOS.add(tempo_total);
        TEMPO_TOTAL += tempo_total;

        // System.out.println("O tempo de execução foi de : " +
        // (System.currentTimeMillis() - tempo_inicial));

        // System.out.println("A distância de menor custo é de: " + distancia_total);

        // System.out.println("O caminho percorrido foi: ");

        // for (Integer caminho : vertices_visitados) {

        // System.out.print(caminho + ",");

        // }

        // System.out.println();

        // imprimiGrafo(matriz);

    }

    /* Gerador */

    public static int[][] grafoCompletoPonderado(int vertices) {
        int[][] matriz = new int[vertices][vertices];
        int valor;
        for (int i = 0; i < matriz.length; i++) {
            matriz[i][i] = -1;
            for (int j = i + 1; j < matriz.length; j++) {
                valor = aleatorio.nextInt(25) + 1;
                matriz[i][j] = valor;
                matriz[j][i] = valor;
            }
        }
        return matriz;
    }

    /* Utils */

    public static void imprimiGrafo(int[][] matriz) {

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                System.out.print("[" + matriz[i][j] + "]");
            }
            System.out.println();
        }

    }

    public static void escreveArquivo() throws IOException {

        BufferedWriter br = new BufferedWriter(new FileWriter("file-500.txt"));

        for (int i = 0; i < CAMINHOS.size(); i++) {
            String escrita = i + ";" + TEMPOS.get(i) + ";" + TAMANHO_TOTAL.get(i);
            System.out.println(escrita);
            br.write(escrita);
            br.newLine();
        }

        br.close();

    }

    public static String geraCaminhos(LinkedList<Integer> caminhos) {

        String caminho = "";

        for (Integer c : caminhos) {
            caminho += (c + ",");
        }

        return caminho;
    }

    /* Tratamentos */

    public static int trataString(String numero) {

        if (numero.matches("\\d+")) {
            return Integer.parseInt(numero);
        } else {
            throw new RuntimeException("Erro ao converter a string: " + numero);
        }

    }

}