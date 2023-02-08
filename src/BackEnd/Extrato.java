
package BackEnd;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Extrato {
    private String tipotransacao;
    private double valor;
    private LocalDateTime datatransacao;
    private Conta conta;
    
   

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

    public LocalDateTime getDatatransacao() {
        return datatransacao;
    }

    public void setDatatransacao(LocalDateTime datatransacao) {
        this.datatransacao = datatransacao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
    

    public Extrato(String tipotransacao, double valor, LocalDateTime datatransacao, Conta conta) {
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
                "\n" + "Data: e Hora: " + this.datatransacao.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)) + "\n";
    }

}
