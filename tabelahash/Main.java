package tabelahash;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TabelaHash tabela = new TabelaHash();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n=== TABELA HASH DE ALUNOS ===");
                System.out.println("1. Inserir novo aluno");
                System.out.println("2. Buscar aluno");
                System.out.println("3. Remover aluno");
                System.out.println("4. Exibir tabela hash");
                System.out.println("0. Encerrar sistema");
                Integer opcao = lerInteiro(scanner, "Escolha uma opção: ");
                if (opcao == null || opcao == 0) {
                    break;
                }

                switch (opcao) {
                    case 1: {
                        Integer matricula = lerInteiro(scanner, "Matrícula: ");
                        if (matricula == null) {
                            return;
                        }
                        System.out.print("Nome: ");
                        if (!scanner.hasNextLine()) {
                            return;
                        }
                        String nome = scanner.nextLine();
                        Integer idade = lerInteiro(scanner, "Idade: ");
                        if (idade == null) {
                            return;
                        }
                        Aluno aluno = new Aluno(matricula, nome, idade);
                        System.out.println(tabela.inserir(aluno)
                                ? "Aluno cadastrado." : "Matrícula já cadastrada.");
                        break;
                    }
                    case 2: {
                        Integer matricula = lerInteiro(scanner, "Matrícula: ");
                        if (matricula == null) {
                            return;
                        }
                        Aluno aluno = tabela.buscar(matricula);
                        System.out.println(aluno == null ? "Aluno não encontrado." : aluno);
                        break;
                    }
                    case 3: {
                        Integer matricula = lerInteiro(scanner, "Matrícula: ");
                        if (matricula == null) {
                            return;
                        }
                        System.out.println(tabela.remover(matricula)
                                ? "Aluno removido." : "Aluno não encontrado.");
                        break;
                    }
                    case 4:
                        System.out.print(tabela);
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            }
        }
        System.out.println("Sistema encerrado.");
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
