package algoritimosordenacao;

public class SimuladorComplexidade {

    private static final long LIMITE_OPS = 2_000_000_000L;
    private static final int[] TAMANHOS = {10, 20, 50, 100};
    private static final int[] EXPOENTES = {1, 2, 3, 10};

    public static void executar() {
        double nsPorOp = medirNsPorOperacao();
        System.out.printf("%nCusto médio medido de 1 operação: %.3f ns%n", nsPorOp);
        System.out.println("(* = tempo estimado, pois executar de verdade seria inviável)\n");

        System.out.printf("%-6s %-14s %-14s %-14s %-20s%n", "n", "O(n)", "O(n²)", "O(n³)", "O(n¹⁰)");
        for (int n : TAMANHOS) {
            StringBuilder linha = new StringBuilder(String.format("%-6d ", n));
            for (int exp : EXPOENTES) {
                double ops = Math.pow(n, exp);
                String celula;
                if (ops <= LIMITE_OPS) {
                    celula = formatar(executarReal((long) ops));
                } else {
                    celula = formatar(ops * nsPorOp) + " *";
                }
                linha.append(String.format("%-14s ", celula));
            }
            System.out.println(linha);
        }
    }

    private static volatile long sorvedouro;

    private static double executarReal(long ops) {
        long soma = 0;
        long ini = System.nanoTime();
        for (long i = 0; i < ops; i++) {
            soma += i ^ (soma >>> 3);
        }
        long fim = System.nanoTime();
        sorvedouro = soma;
        return fim - ini;
    }

    private static double medirNsPorOperacao() {
        executarReal(50_000_000L); 
        long ops = 200_000_000L;
        return executarReal(ops) / (double) ops;
    }

    private static String formatar(double ns) {
        if (ns < 1_000) return String.format("%.1f ns", ns);
        if (ns < 1e6) return String.format("%.1f µs", ns / 1e3);
        if (ns < 1e9) return String.format("%.1f ms", ns / 1e6);
        double s = ns / 1e9;
        if (s < 60) return String.format("%.2f s", s);
        if (s < 3600) return String.format("%.1f min", s / 60);
        if (s < 86400) return String.format("%.1f h", s / 3600);
        double anos = s / (86400.0 * 365);
        if (anos < 1) return String.format("%.1f dias", s / 86400);
        return String.format("%.1e anos", anos);
    }
}
