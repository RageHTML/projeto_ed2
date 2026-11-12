package algoritimosordenacao;

import java.util.Scanner;
import tabelahash.Aluno;
import tabelahash.TabelaHash;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            TabelaHash tabela = new TabelaHash();
            while (true) {
                System.out.println("\n========================================");
                System.out.println("            MENU PRINCIPAL");
                System.out.println("========================================");
                System.out.println("1. Algoritmos de Ordenação");
                System.out.println("2. Tabela Hash de Alunos");
                System.out.println("3. Simulação de Complexidade (n, n², n³, n¹⁰)");
                System.out.println("0. Sair");
                Integer opcao = lerInteiro(scanner, "Escolha uma opção: ");
                if (opcao == null || opcao == 0) {
                    break;
                }
                switch (opcao) {
                    case 1 -> menuOrdenacao(scanner);
                    case 2 -> menuHash(scanner, tabela);
                    case 3 -> SimuladorComplexidade.executar();
                    default -> System.out.println("Opção inválida! Tente novamente.");
                }
            }
        }
        System.out.println("Sistema encerrado.");
    }


    private static void menuOrdenacao(Scanner scanner) {
        int tamanho = 5000;
        int[] vetorOriginal = Utils.gerarVetor(tamanho, 10000);

        AlgoritimoOrdenacao[] algoritmos = {
            new BubbleSort(), new InsertionSort(), new SelectionSort(),
            new MergeSort(), new QuickSort()
        };

        while (true) {
            System.out.println("\n--- ALGORITMOS DE ORDENAÇÃO (vetor de " + tamanho + " elementos) ---");
            for (int i = 0; i < algoritmos.length; i++) {
                System.out.println((i + 1) + ". " + algoritmos[i]);
            }
            System.out.println("0. Voltar");
            Integer opcao = lerInteiro(scanner, "Escolha uma opção: ");
            if (opcao == null || opcao == 0) {
                return;
            }
            if (opcao < 1 || opcao > algoritmos.length) {
                System.out.println("Opção inválida! Tente novamente.");
                continue;
            }

            AlgoritimoOrdenacao alg = algoritmos[opcao - 1];
            int[] vetorTrabalho = vetorOriginal.clone();

            System.out.println("\n--- Executando " + alg + " ---");
            imprimirCemPrimeiros(vetorTrabalho, "Entrada (100 primeiros)");

            long inicio = System.nanoTime();
            alg.ordenarVetor(vetorTrabalho);
            long fim = System.nanoTime();

            imprimirCemPrimeiros(vetorTrabalho, "Saída Ordenada (100 primeiros)");
            System.out.println("Tempo de execução: " + (fim - inicio) / 1_000_000.0 + " ms");
        }
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

    private static void menuHash(Scanner scanner, TabelaHash tabela) {
        while (true) {
            System.out.println("\n--- TABELA HASH DE ALUNOS ---");
            System.out.println("1. Inserir novo aluno");
            System.out.println("2. Buscar aluno");
            System.out.println("3. Remover aluno");
            System.out.println("4. Exibir tabela hash");
            System.out.println("0. Voltar");
            Integer opcao = lerInteiro(scanner, "Escolha uma opção: ");
            if (opcao == null || opcao == 0) {
                return;
            }
            switch (opcao) {
                case 1 -> {
                    Integer matricula = lerInteiro(scanner, "Matrícula: ");
                    if (matricula == null) return;
                    System.out.print("Nome: ");
                    if (!scanner.hasNextLine()) return;
                    String nome = scanner.nextLine();
                    Integer idade = lerInteiro(scanner, "Idade: ");
                    if (idade == null) return;
                    System.out.println(tabela.inserir(new Aluno(matricula, nome, idade))
                            ? "Aluno cadastrado." : "Matrícula já cadastrada.");
                }
                case 2 -> {
                    Integer matricula = lerInteiro(scanner, "Matrícula: ");
                    if (matricula == null) return;
                    Aluno aluno = tabela.buscar(matricula);
                    System.out.println(aluno == null ? "Aluno não encontrado." : aluno);
                }
                case 3 -> {
                    Integer matricula = lerInteiro(scanner, "Matrícula: ");
                    if (matricula == null) return;
                    System.out.println(tabela.remover(matricula)
                            ? "Aluno removido." : "Aluno não encontrado.");
                }
                case 4 -> System.out.print(tabela);
                default -> System.out.println("Opção inválida.");
            }
        }
    }


    private static Integer lerInteiro(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            if (!scanner.hasNextLine()) {
                return null;
            }
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }
}
