import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class App {

    public static void main(String[] args) {
        final int numeroVertices = 12;
        final String meusGrafos = "grafos12V.txt";
        final String fileName = "resultadoCaixeiroBruto12V.txt";

        // gerarTxtDeGrafosPonderados(numeroVertices,1000, meusGrafos);
        ArrayList<int[][]> listaMatriz = lerArquivoGrafos(meusGrafos, numeroVertices);

        long start;
        long tempoDecorrido;
        
        try {
            FileWriter arq = new FileWriter(fileName);
            PrintWriter gravarArq = new PrintWriter(arq);
            for (int[][] m : listaMatriz) {
                CaixeiroViajanteForcaBruta brutus = new CaixeiroViajanteForcaBruta(m);
                start = System.currentTimeMillis();
                ArrayList<Integer> caminho = brutus.encontrarMelhorCaminho();
                tempoDecorrido = System.currentTimeMillis() - start;
                int distanciaCaminho = valorTotalDoCaminho(caminho, m);
                gravarArq.printf("[\n");
                gravarArq.printf("Melhor caminho: " + Arrays.toString(caminho.toArray()) + "\n");
                gravarArq.printf("Menor distância: " + distanciaCaminho + "\n");
                gravarArq.printf("tempo decorrido: " + tempoDecorrido + " em minutos: "
                        + converteParaMinuto(tempoDecorrido) + "\n");
                gravarArq.printf("]\n");
            }
            gravarArq.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    /**
     * Aleatório "fixo" para geração de testes repetitíveis
     */
    static Random aleatorio = new Random();

    /**
     * Retorna uma matriz quadrada de "vertices" x "vertices" com números inteiros,
     * representando um grafo completo. A diagonal principal está preenchida com
     * valor -1, indicando que não há aresta.
     * 
     * @param vertices A quantidade de vértices do grafo.
     * @return Matriz quadrada com custos de movimentação entre os vértices.
     */
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

    public static void printMatriz(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int valorTotalDoCaminho(ArrayList<Integer> caminho, int[][] m) {
        int distancia = 0;
        for (int i = 0; i < caminho.size() - 1; i++) {
            distancia += m[caminho.get(i)][caminho.get(i + 1)];
        }

        return distancia;
    }

    public static double converteParaMinuto(Long mili) {

        return mili / 60000;
    }

    public static long calculatempoMedio(long[] tempos) {
        long soma = 0;
        for (long l : tempos) {
            soma = soma + l;
        }
        return soma / tempos.length;
    }

    /**
     * 
     * 
     * @param numeroDeGrafos
     */
    public static void gerarTxtDeGrafosPonderados(int numeroVertices, int numeroDeGrafos, String fileName) {
        try {
            FileWriter arq = new FileWriter(fileName);
            PrintWriter gravarArq = new PrintWriter(arq);
            for (int k = 0; k < numeroDeGrafos; k++) {
                gravarArq.printf("[\n");
                int[][] m = grafoCompletoPonderado(numeroVertices);
                for (int i = 0; i < m.length; i++) {
                    for (int j = 0; j < m.length; j++) {
                        gravarArq.printf(m[i][j] + ";");
                    }
                    gravarArq.printf("\n");
                }
                gravarArq.printf("]\n");

            }
            arq.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public static ArrayList<int[][]> lerArquivoGrafos(String fileName, int numeroVertices) {
        ArrayList<int[][]> listaMatriz = new ArrayList<int[][]>();
        try {
            File arq = new File(fileName);
            Scanner scan = new Scanner(arq);
            while (scan.hasNext()) {
                String line = scan.nextLine();
                if (line.contains("[")) {
                    line = scan.nextLine();
                    int[][] matriz = new int[numeroVertices][numeroVertices];

                    for (int i = 0; i < numeroVertices; i++) {
                        String[] pesos = line.split(";");
                        for (int j = 0; j < numeroVertices; j++) {
                            matriz[i][j] = Integer.parseInt(pesos[j]);
                        }
                        line = scan.nextLine();
                    }

                    listaMatriz.add(matriz);
                }
                
            }
            scan.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

        return listaMatriz;

    }

}