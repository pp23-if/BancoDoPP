
package BackEnd;

public class Conta {
   
    private int numeroconta;
    private double saldo;
    private Cliente cliente;
  
    public int getNumeroconta() {
        return numeroconta;
    }

    public void setNumeroconta(int numeroconta) {
        this.numeroconta = numeroconta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Conta(double saldo, Cliente cliente, int numeroconta) {
        this.numeroconta = numeroconta;
        this.saldo = saldo;
        this.cliente = cliente;
    }
    
    
    @Override
    public String toString() {
        return this.cliente + "," + "\n"+" Conta: "+ "\n" + 
                " Numero da conta: " + this.numeroconta + "\n" + 
                " Saldo: " + this.saldo;
    }
    
    
}
