public class Emprestimo {
    private double valor;
    private int parcelas;
    private double taxaJuros = 0.02; // 2% ao mês
    private Cliente cliente;
    private boolean aprovado;
    private double valorParcela;

    public Emprestimo(double valor, int parcelas, Cliente cliente) {
        this.valor = valor;
        this.parcelas = parcelas;
        this.cliente = cliente;
        this.calcularValorParcela();
    }

    private void calcularValorParcela() {
        // Cálculo simples de parcelas com juros
        double valorTotal = valor * (1 + (taxaJuros * parcelas));
        this.valorParcela = valorTotal / parcelas;
    }

    public boolean aprovar() {
        // Lógica simples de aprovação
        this.aprovado = true;
        return true;
    }

    // Getters
    public double getValor() {
        return valor;
    }

    public double getValorParcela() {
        return valorParcela;
    }

    public int getParcelas() {
        return parcelas;
    }

    public boolean isAprovado() {
        return aprovado;
    }
}
