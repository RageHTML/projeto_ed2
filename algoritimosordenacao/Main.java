package algoritimosordenacao;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Gera um vetor com dados aleatórios para teste
        int tamanho = 5000;
        int[] vetorOriginal = Utils.gerarVetor(tamanho, 10000);

        AlgoritimoOrdenacao[] algoritmos = {
            new BubbleSort(),
            new InsertionSort(),
            new SelectionSort(),
            new MergeSort(),
            new QuickSort()
        };

        int opcao;
        do {
            System.out.println("\n========================================");
            System.out.println("       MENU - ALGORITMOS DE ORDENAÇÃO");
            System.out.println("========================================");
            for (int i = 0; i < algoritmos.length; i++) {
                System.out.println((i + 1) + ". " + algoritmos[i].toString());
            }
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();

            if (opcao >= 1 && opcao <= algoritmos.length) {
                AlgoritimoOrdenacao alg = algoritmos[opcao - 1];
                
                int[] vetorTrabalho = vetorOriginal.clone();

                System.out.println("\n--- Executando " + alg.toString() + " ---");
                
                imprimirCemPrimeiros(vetorTrabalho, "Entrada (100 primeiros)");

                long inicio = System.nanoTime();
                alg.ordenarVetor(vetorTrabalho);
                long fim = System.nanoTime();

                imprimirCemPrimeiros(vetorTrabalho, "Saída Ordenada (100 primeiros)");

                double tempoMs = (fim - inicio) / 1_000_000.0;
                System.out.println("Tempo de execução: " + tempoMs + " ms");

            } else if (opcao != 0) {
                System.out.println("Opção inválida! Tente novamente.");
            }

        } while (opcao != 0);

        scanner.close();
        System.out.println("Sistema encerrado.");
    }

    public static void imprimirCemPrimeiros(int[] vetor, String titulo) {
        System.out.print(titulo + " = {");
        int limite = Math.min(vetor.length, 100);
        for (int i = 0; i < limite; i++) {
            System.out.print(vetor[i]);
            if (i < limite - 1) {
                System.out.print(", ");
            }
        }
        if (vetor.length > 100) {
            System.out.print(", ... (exibindo apenas os 100 primeiros de " + vetor.length + ")");
        }
        System.out.println("}");
    }
}