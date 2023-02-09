
package BackEnd;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Extrato {
    private String tipotransacao;
    private double valor;
    private Date datatransacao;
    private Conta conta;
    private final SimpleDateFormat fd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
   

    public String getTipotransacao() {
        return tipotransacao;
    }

    public void setTipotransacao(String tipotransacao) {
        this.tipotransacao = tipotransacao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getDatatransacao() {
        return datatransacao;
    }

    public void setDatatransacao(Date datatransacao) {
        this.datatransacao = datatransacao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Extrato(String tipotransacao, double valor, Date datatransacao, Conta conta) {
        this.tipotransacao = tipotransacao;
        this.valor = valor;
        this.datatransacao = datatransacao;
        this.conta = conta;
    }
    

   
    @Override
    public String toString() {
        return "\n" + "Cliente: " + this.conta.getCliente().getNome() + "\n" + "Cpf: " +
                this.conta.getCliente().getCpf() + "\n" + "Conta: " +
                this.conta.getNumeroconta() + "\n" + "Valor: " +
                this.valor  + "\n" + "Tipo de Transacao: " + this.tipotransacao + 
                "\n" + "Data: e Hora: " + this.fd.format(datatransacao) + "\n";
    }

}
