package tabelahash;

public final class TabelaHash {
    private static final int TAMANHO = 11;
    private final No[] tabela = new No[TAMANHO];

    private static final class No {
        private final Aluno aluno;
        private No proximo;

        private No(Aluno aluno) {
            this.aluno = aluno;
        }
    }

    private int hash(int matricula) {
        return Math.floorMod(matricula, TAMANHO);
    }

    public boolean inserir(Aluno aluno) {
        int indice = hash(aluno.getMatricula());
        No atual = tabela[indice];
        if (atual == null) {
            tabela[indice] = new No(aluno);
            return true;
        }

        while (true) {
            if (atual.aluno.getMatricula() == aluno.getMatricula()) {
                return false;
            }
            if (atual.proximo == null) {
                atual.proximo = new No(aluno);
                return true;
            }
            atual = atual.proximo;
        }
    }

    public Aluno buscar(int matricula) {
        int indice = hash(matricula);
        No atual = tabela[indice];
        while (atual != null) {
            if (atual.aluno.getMatricula() == matricula) {
                return atual.aluno;
            }
            atual = atual.proximo;
        }
        return null;
    }

    public boolean remover(int matricula) {
        int indice = hash(matricula);
        No atual = tabela[indice];
        No anterior = null;
        while (atual != null) {
            if (atual.aluno.getMatricula() == matricula) {
                if (anterior == null) {
                    tabela[indice] = atual.proximo;
                } else {
                    anterior.proximo = atual.proximo;
                }
                return true;
            }
            anterior = atual;
            atual = atual.proximo;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder resultado = new StringBuilder();
        for (int indice = 0; indice < TAMANHO; indice++) {
            resultado.append('[').append(indice).append(']');
            No atual = tabela[indice];
            if (atual == null) {
                resultado.append(" -> vazio");
            }
            while (atual != null) {
                resultado.append(" -> ").append(atual.aluno);
                atual = atual.proximo;
            }
            resultado.append(System.lineSeparator());
        }
        return resultado.toString();
    }
}
