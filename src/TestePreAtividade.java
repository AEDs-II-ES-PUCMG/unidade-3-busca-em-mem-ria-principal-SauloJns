import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

public class TestePreAtividade {

    private static final int N = 10_000;
    private static final int M = 1_000;

    public static void main(String[] args) {
        int[] valoresAleatorios = gerarValoresAleatorios();
        int[] valoresOrdenados = Arrays.copyOf(valoresAleatorios, valoresAleatorios.length);
        int[] buscas = gerarBuscas();

        Arrays.sort(valoresOrdenados);

        System.out.println("Resultados brutos da pre-atividade:");
        executarCenario("ABB com insercao aleatoria", new ABB<>(), valoresAleatorios, buscas);
        executarCenario("AVL com insercao aleatoria", new AVL<>(), valoresAleatorios, buscas);
        executarCenario("ABB com insercao ordenada", new ABB<>(), valoresOrdenados, buscas);
        executarCenario("AVL com insercao ordenada", new AVL<>(), valoresOrdenados, buscas);
    }

    private static int[] gerarValoresAleatorios() {
        Random random = new Random(42);
        Set<Integer> valoresUnicos = new HashSet<>();
        int[] valores = new int[N];
        int posicao = 0;

        while (valoresUnicos.size() < N) {
            int valor = random.nextInt(N * 2);
            if (valoresUnicos.add(valor)) {
                valores[posicao] = valor;
                posicao++;
            }
        }

        return valores;
    }

    private static int[] gerarBuscas() {
        Random random = new Random(42);
        int[] buscas = new int[M];

        for (int i = 0; i < M; i++) {
            buscas[i] = random.nextInt(N * 2);
        }
        return buscas;
    }

    private static void executarCenario(String nome, ABB<Integer, Integer> arvore, int[] valores, int[] buscas) {
        for (int valor : valores) {
            arvore.inserir(valor, valor);
        }

        long totalComparacoes = 0;
        double tempoTotal = 0;

        for (int busca : buscas) {
            try {
                arvore.pesquisar(busca);
            } catch (NoSuchElementException e) {
                // Buscas sem resultado tambem fazem parte do experimento.
            }
            totalComparacoes += arvore.getComparacoes();
            tempoTotal += arvore.getTempo();
        }

        System.out.println(nome);
        System.out.println("Total de comparacoes: " + totalComparacoes);
        System.out.println("Tempo total: " + tempoTotal + " ns");
        System.out.println();
    }
}
