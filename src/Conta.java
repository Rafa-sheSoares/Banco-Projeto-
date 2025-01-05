
import java.util.ArrayList;
import java.util.List;

public abstract class Conta extends IConta {
    private static final int AGENCIA_PADRAO = 1;
    private static int SEQUENCIAL = 1;

    protected int agencia;
    protected int numero;
    protected double saldo;
    protected Cliente cliente;
    protected List<String> historicoTransacoes = new ArrayList<>(); // Nova implementação

    public Conta(Cliente cliente) {
        this.agencia = Conta.AGENCIA_PADRAO;
        this.numero = SEQUENCIAL++;
        this.cliente = cliente;
    }

    @Override
    public void sacar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de saque deve ser positivo");
        }
        if (valor > saldo) {
            throw new IllegalStateException("Saldo insuficiente");
        }
        saldo -= valor;
        historicoTransacoes.add("Saque: R$ " + String.format("%.2f", valor));
    }

    @Override
    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de depósito deve ser positivo");
        }
        saldo += valor;
        historicoTransacoes.add("Depósito: R$ " + String.format("%.2f", valor));
    }

    @Override
    public void transferir(double valor, IConta contaDestino) {
        if (contaDestino == null) {
            throw new IllegalArgumentException("Conta destino não pode ser nula");
        }
        this.sacar(valor);
        contaDestino.depositar(valor);
        historicoTransacoes.add("Transferência enviada: R$ " + String.format("%.2f", valor));
    }

    public int getAgencia() {
        return agencia;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    private List<Emprestimo> emprestimos = new ArrayList<>();

    public Emprestimo solicitarEmprestimo(double valor, int parcelas) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor do empréstimo deve ser positivo");
        }
        if (parcelas < 1 || parcelas > 48) {
            throw new IllegalArgumentException("Número de parcelas deve ser entre 1 e 48");
        }

        Emprestimo emprestimo = new Emprestimo(valor, parcelas, this.cliente);

        if (emprestimo.aprovar()) {
            emprestimos.add(emprestimo);
            this.depositar(valor);
            historicoTransacoes.add("Empréstimo aprovado: R$ " + String.format("%.2f", valor));
            return emprestimo;
        }

        return null;
    }

    public List<Emprestimo> getEmprestimos() {
        return new ArrayList<>(emprestimos);
    }

    // Método original modificado para incluir empréstimos
    protected void imprimirInfosComuns() {
        System.out.println(String.format("Titular: %s", this.cliente.getNome()));
        System.out.println(String.format("Agencia: %d", this.agencia));
        System.out.println(String.format("Numero: %d", this.numero));
        System.out.println(String.format("Saldo: R$ %.2f", this.saldo));

        // Adicionar informações de empréstimos
        if (!emprestimos.isEmpty()) {
            System.out.println("\nEmpréstimos Ativos:");
            for (Emprestimo emp : emprestimos) {
                System.out.println(String.format("Valor: R$ %.2f - Parcelas: %d x R$ %.2f",
                        emp.getValor(), emp.getParcelas(), emp.getValorParcela()));
            }
        }

        System.out.println("\nHistórico de Transações:");
        if (historicoTransacoes.isEmpty()) {
            System.out.println("Não há transações registradas");
        } else {
            for (String transacao : historicoTransacoes) {
                System.out.println(transacao);
            }
        }
    }
}
