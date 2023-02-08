
package BackEnd;


public class Cliente {
    
    private String cpf;
    private String nome;
    private String datanasc;
    private String telefone;
    private String login;
    private String senha;
    private Endereco end;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

   
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Endereco getEnd() {
        return end;
    }

    public void setEnd(Endereco end) {
        this.end = end;
    }

    public Cliente(String cpf, String nome, String datanasc, String telefone, String login, String senha, Endereco end) {
        this.cpf = cpf;
        this.nome = nome;
        this.datanasc = datanasc;
        this.telefone = telefone;
        this.login = login;
        this.senha = senha;
        this.end = end;
   
    }
    
    public Cliente() {
        
    }

    public String getDatanasc() {
        return datanasc;
    }

    public void setDatanasc(String datanasc) {
        this.datanasc = datanasc;
    }
    
    @Override
    public String toString() {
        
         
        return "\n" + " Cliente: " + "\n" + " Cpf: " + this.cpf + "," + 
                " Nome: " + this.nome + "," + " Data de Nascimento: " + this.datanasc + "\n" +
                " Telefone: " + this.telefone + "," + " Login: " + this.login + "," + " Senha: " + this.senha + ","
                + this.end;
    }
    
}
