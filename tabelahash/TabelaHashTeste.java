package tabelahash;

public class TabelaHashTeste {
    private static int aprovados;
    private static int reprovados;

    public static void main(String[] args) {
        executar("Busca e remoção em tabela vazia", TabelaHashTeste::tabelaVazia);
        executar("Inserção e recuperação dos dados", TabelaHashTeste::insercaoEBusca);
        executar("Colisões e inserção no final da lista", TabelaHashTeste::colisoesEOrdem);
        executar("Duplicatas no início, meio e fim", TabelaHashTeste::matriculasDuplicadas);
        executar("Remoção no início da lista", TabelaHashTeste::removerInicio);
        executar("Remoção no meio da lista", TabelaHashTeste::removerMeio);
        executar("Remoção no fim e nova inserção", TabelaHashTeste::removerFim);
        executar("Remoção do único aluno e reinserção", TabelaHashTeste::removerUnicoEReinserir);
        executar("Matrícula ausente em lista ocupada", TabelaHashTeste::matriculaAusente);
        executar("Mais alunos que posições e remoções alternadas", TabelaHashTeste::multiplasPosicoes);
        executar("Matrículas zero, negativas e limites de int", TabelaHashTeste::matriculasLimite);

        System.out.println("\nResultado: " + aprovados + " aprovados; " + reprovados + " reprovados.");
        if (reprovados > 0) {
            throw new AssertionError("Há testes reprovados.");
        }
    }

    private static void executar(String nome, Runnable teste) {
        try {
            teste.run();
            aprovados++;
            System.out.println("[OK] " + nome);
        } catch (AssertionError | RuntimeException erro) {
            reprovados++;
            System.out.println("[FALHOU] " + nome + ": " + erro);
        }
    }

    private static void verificar(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new AssertionError(mensagem);
        }
    }

    private static void tabelaVazia() {
        TabelaHash tabela = new TabelaHash();
        verificar(tabela.buscar(10) == null, "Busca vazia deve retornar null.");
        verificar(!tabela.remover(10), "Remoção vazia deve retornar false.");
        verificar(tabela.toString().split("\\R").length == 11, "Devem ser exibidas 11 posições.");
    }

    private static void insercaoEBusca() {
        TabelaHash tabela = new TabelaHash();
        verificar(tabela.inserir(new Aluno(15, "Ana Maria", 20)), "Inserção de Ana recusada.");
        verificar(tabela.inserir(new Aluno(27, "João Silva", 22)), "Inserção de João recusada.");
        Aluno ana = tabela.buscar(15);
        verificar(ana != null, "Ana não encontrada.");
        verificar(ana.getMatricula() == 15, "Matrícula incorreta.");
        verificar("Ana Maria".equals(ana.getNome()), "Nome completo não preservado.");
        verificar(ana.getIdade() == 20, "Idade incorreta.");
        verificar("João Silva".equals(tabela.buscar(27).getNome()), "Aluno de outra posição perdido.");
    }

    private static TabelaHash criarListaComColisoes() {
        TabelaHash tabela = new TabelaHash();
        tabela.inserir(new Aluno(12, "Ana", 20));
        tabela.inserir(new Aluno(1, "Bruno", 21));
        tabela.inserir(new Aluno(23, "Carla", 22));
        return tabela;
    }

    private static void verificarOrdem(String linha, int... matriculas) {
        int posicaoAnterior = -1;
        for (int matricula : matriculas) {
            int posicao = linha.indexOf("matricula=" + matricula + ",");
            verificar(posicao > posicaoAnterior, "Matrícula " + matricula + " ausente ou fora de ordem.");
            posicaoAnterior = posicao;
        }
    }

    private static void colisoesEOrdem() {
        TabelaHash tabela = criarListaComColisoes();
        verificar("Ana".equals(tabela.buscar(12).getNome()), "Primeiro aluno perdido.");
        verificar("Bruno".equals(tabela.buscar(1).getNome()), "Aluno intermediário perdido.");
        verificar("Carla".equals(tabela.buscar(23).getNome()), "Último aluno perdido.");
        String[] linhas = tabela.toString().split("\\R");
        verificarOrdem(linhas[1], 12, 1, 23);
    }

    private static void matriculasDuplicadas() {
        TabelaHash tabela = criarListaComColisoes();
        for (int matricula : new int[] {12, 1, 23}) {
            Aluno original = tabela.buscar(matricula);
            verificar(!tabela.inserir(new Aluno(matricula, "Substituto", 99)), "Duplicata aceita.");
            verificar(tabela.buscar(matricula) == original, "Aluno original foi substituído.");
        }
        verificarOrdem(tabela.toString().split("\\R")[1], 12, 1, 23);
        for (int matricula : new int[] {12, 1, 23}) {
            verificar(tabela.remover(matricula), "Original não removido.");
            verificar(tabela.buscar(matricula) == null, "Duplicata ficou na lista.");
        }
    }

    private static void removerInicio() {
        TabelaHash tabela = criarListaComColisoes();
        verificar(tabela.remover(12), "Remoção do início falhou.");
        verificar(tabela.buscar(12) == null, "Aluno removido ainda encontrado.");
        verificar(tabela.buscar(1) != null && tabela.buscar(23) != null, "Restante da lista perdido.");
        verificarOrdem(tabela.toString().split("\\R")[1], 1, 23);
    }

    private static void removerMeio() {
        TabelaHash tabela = criarListaComColisoes();
        verificar(tabela.remover(1), "Remoção do meio falhou.");
        verificar(tabela.buscar(1) == null, "Aluno removido ainda encontrado.");
        verificar(tabela.buscar(12) != null && tabela.buscar(23) != null, "Vizinhos perdidos.");
        verificarOrdem(tabela.toString().split("\\R")[1], 12, 23);
    }

    private static void removerFim() {
        TabelaHash tabela = criarListaComColisoes();
        verificar(tabela.remover(23), "Remoção do fim falhou.");
        verificar(tabela.buscar(23) == null, "Aluno removido ainda encontrado.");
        verificar(tabela.inserir(new Aluno(34, "Daniel", 23)), "Nova inserção falhou.");
        verificar(tabela.buscar(34) != null, "Novo aluno não encontrado.");
        verificarOrdem(tabela.toString().split("\\R")[1], 12, 1, 34);
    }

    private static void removerUnicoEReinserir() {
        TabelaHash tabela = new TabelaHash();
        tabela.inserir(new Aluno(22, "Ana", 20));
        verificar(tabela.remover(22), "Remoção do único aluno falhou.");
        verificar(tabela.buscar(22) == null, "Aluno removido ainda encontrado.");
        verificar(!tabela.remover(22), "Segunda remoção não deve ter sucesso.");
        verificar(tabela.inserir(new Aluno(22, "Ana atualizada", 21)), "Reinserção recusada.");
        verificar("Ana atualizada".equals(tabela.buscar(22).getNome()), "Dados anteriores reapareceram.");
        verificar(tabela.buscar(22).getIdade() == 21, "Idade da reinserção incorreta.");
    }

    private static void matriculaAusente() {
        TabelaHash tabela = criarListaComColisoes();
        verificar(tabela.buscar(34) == null, "Busca deve comparar a matrícula, não só o endereço.");
        verificar(!tabela.remover(34), "Remoção de matrícula ausente teve sucesso.");
        verificar(tabela.buscar(12) != null && tabela.buscar(1) != null && tabela.buscar(23) != null,
                "Remoção ausente alterou alunos existentes.");
    }

    private static void multiplasPosicoes() {
        TabelaHash tabela = new TabelaHash();
        for (int i = 0; i < 66; i++) {
            verificar(tabela.inserir(new Aluno(i, "Aluno " + i, 18 + i)), "Inserção recusada: " + i);
        }
        for (int i = 0; i < 66; i++) {
            Aluno aluno = tabela.buscar(i);
            verificar(aluno != null && ("Aluno " + i).equals(aluno.getNome()) && aluno.getIdade() == 18 + i,
                    "Dados perdidos após colisões: " + i);
        }
        for (int i = 0; i < 66; i += 2) {
            verificar(tabela.remover(i), "Remoção recusada: " + i);
        }
        for (int i = 0; i < 66; i++) {
            verificar((tabela.buscar(i) != null) == (i % 2 != 0), "Estado após remoções incorreto: " + i);
        }
    }

    private static void matriculasLimite() {
        TabelaHash tabela = new TabelaHash();
        int[] matriculas = {Integer.MIN_VALUE, Integer.MAX_VALUE, -11, -1, 0, 11};
        for (int matricula : matriculas) {
            verificar(tabela.inserir(new Aluno(matricula, "Limite " + matricula, 20)),
                    "Inserção de inteiro válido recusada: " + matricula);
            verificar(tabela.buscar(matricula).getMatricula() == matricula, "Matrícula limite incorreta.");
        }
        String[] linhas = tabela.toString().split("\\R");
        verificarOrdem(linhas[0], -11, 0, 11);
        verificarOrdem(linhas[1], Integer.MAX_VALUE);
        verificarOrdem(linhas[9], Integer.MIN_VALUE);
        verificarOrdem(linhas[10], -1);
        for (int matricula : matriculas) {
            verificar(tabela.remover(matricula), "Remoção de matrícula limite falhou.");
            verificar(tabela.buscar(matricula) == null, "Matrícula limite permaneceu após remoção.");
        }
    }
}
