package tabelahash;

public final class Aluno {
    private final int matricula;
    private final String nome;
    private final int idade;

    public Aluno(int matricula, String nome, int idade) {
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
    }

    public int getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    @Override
    public String toString() {
        return "Aluno{matricula=" + matricula + ", nome='" + nome + "', idade=" + idade + "}";
    }
}
