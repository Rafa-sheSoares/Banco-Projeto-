/* encapsulamento - esconder as implementações que não
faz sentido ser expostas

 private -  só os pais enxergam
 protected - modificador de acesso
             faz com que as classes filhas consigam enxergar informação
 */

public class Main {
    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.setNome("João");

  /*      Conta cc = new ContaCorrente(cliente);
        try {
            cc.depositar(100.0);
            cc.sacar(50.0);
            cc.imprimirExtrato();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }*/
        Conta cc = new ContaCorrente(cliente);

        try {
            Emprestimo emp = cc.solicitarEmprestimo(1000.0, 10);
            if (emp != null) {
                System.out.println("Empréstimo aprovado!");
                System.out.println("Valor da parcela: R$ " + String.format("%.2f", emp.getValorParcela()));
            }

            cc.imprimirExtrato();

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}