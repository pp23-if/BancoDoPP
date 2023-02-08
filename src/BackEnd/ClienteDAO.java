
package BackEnd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClienteDAO {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Cliente> adicionacliente = new ArrayList();
    
    
    public ClienteDAO()
    {
       
    }
    
   
    
    public void cadastraCliente(ConexaoDAO cond) 
    {
       
        
        System.out.println("\nInforme seus dados pessoais: " + "\n");
        
        System.out.println("Informe um endereco de email que sera usado como login: ");
        String Login = scanner.nextLine();
        
        Login = ValidaLogin(Login);
        
   
        System.out.println("Informe uma senha: ");
        String senha = scanner.nextLine();
        
        senha = ValidaSenha(senha);
        
        if(verificaCadastro(Login, senha) == true)
        {
            System.out.println("\nCliente ja cadastrado");
        }
        
        else
        {

        System.out.println("Informe seu cpf: ");
        String cpf = scanner.nextLine();
        cpf = ValidaCpf(cpf);
        
        System.out.println("Informe seu nome: ");
        String nome = scanner.nextLine();
        nome = ValidaNome(nome);

        System.out.println("Informe seu telefone: ");
        String telefone = scanner.nextLine();
        telefone = ValidaTelefone(telefone);
        
        System.out.println("Informe sua data de nascimento: ");// preciso aprender a validar datas.
        String dtnasc = scanner.nextLine();
        dtnasc = ValidaData(dtnasc);
        
        
        System.out.println("\n Endereco: " + "\n");
        
        System.out.println("Rua/Avenida: ");
        String rua = scanner.nextLine();
        rua = ValidaRua(rua);
        
        System.out.println("Numero: ");
        String n = scanner.nextLine();
        int numero = Integer.parseInt(n);
        numero = ValidaNumero(numero);
        
        System.out.println("Bairro: ");
        String bairro = scanner.nextLine();
        bairro = ValidaBairro(bairro);
        
        System.out.println("Cidade: ");
        String cidade = scanner.nextLine();
        cidade = ValidaCidade(cidade);
        
        System.out.println("Estado: ");
        String estado = scanner.nextLine();
        estado = ValidaEstado(estado);
        
        System.out.println("Cep: ");
        String cep = scanner.nextLine();
        cep = ValidaCep(cep);
        
        Endereco end = new Endereco(rua, numero, bairro, cidade, estado, cep);
        
        Cliente cli = new Cliente(cpf, nome, dtnasc, telefone, Login, senha, end);
        
        
        System.out.println("\nO objeto gerado foi: " + cli + "\n");
        
       
          if(insereNoBancoDeDados(cli, cond) == true) 
          {
              System.out.println("\ncadastrado com sucesso!!\n");
          }
           else
          {
              System.out.println("\nnao foi possivel cadastrar o cliente.\n"); 
          }
       
 
      }
        
   }
    
    
/*-------------------------------- BLOCO COM FUNCOES DE ACESSO AO BANCO DE DADOS ----------------------*/ 
    
    private boolean insereNoBancoDeDados(Cliente c, ConexaoDAO cond)
    {
        boolean x = true;
        Connection conn;
        PreparedStatement pstm;
        PreparedStatement a;
        
        
        
        String inserecli = "insert into clientes (cpf,nome,dt_nasc,telefone,login,senha) \n" +
        "values (?,?,?,?,?,?)";
            
        String insereend = "insert into endereco (cpf,rua,numero,bairro,cidade,estado,cep) \n" +
        "values (?,?,?,?,?,?,?)";
        
        conn = cond.ConectaBD();
        
        
        
        try 
        {
            conn.setAutoCommit(false);
           
            pstm = conn.prepareStatement(inserecli);
            pstm.setString(1, c.getCpf());
            pstm.setString(2, c.getNome());
            pstm.setString(3, c.getDatanasc());
            pstm.setString(4, c.getTelefone());
            pstm.setString(5, c.getLogin());
            pstm.setString(6, c.getSenha());
            pstm.execute();
            
            a = conn.prepareStatement(insereend);
            a.setString(1, c.getCpf());
            a.setString(2, c.getEnd().getRua());
            a.setInt(3, c.getEnd().getNumero());
            a.setString(4, c.getEnd().getBairro());
            a.setString(5, c.getEnd().getCidade());
            a.setString(6, c.getEnd().getEstado());
            a.setString(7, c.getEnd().getCep());
            a.execute();
           
           
            conn.commit();
           
        } 
         catch (SQLException erro) 
         {
            try 
            {
                conn.rollback();
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
             System.out.println("\n Nao foi possivel inserir o cliente no banco de dados!\n" + erro);
             x = false;
         }
        
         
        
        return x != false;
        
    }
    
    
    
    private boolean BuscaClienteNoBancoDeDados(ConexaoDAO cond)
    {
        boolean x = true;
        Connection conn; 
        PreparedStatement pstm;
        ResultSet rs;// essa variavel em particular precisa ser do tipo ResultSet e nao
        //Resultset;
       
        String sql = "select c.nome,c.cpf,c.dt_nasc,c.telefone,c.login,c.senha,e.rua,e.numero,e.bairro,e.cidade,e.estado,e.cep\n" +
                      "from clientes c inner join endereco e\n" +
                      "on c.cpf = e.cpf";
        
 
        
        conn = cond.ConectaBD();
        
        try 
        {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next())
            {
               
                Cliente c = new Cliente();
                Endereco end = new Endereco();
                
                c.setCpf(rs.getString("cpf"));
                c.setNome(rs.getString("nome"));
                c.setDatanasc(rs.getString("dt_nasc"));
                c.setTelefone(rs.getString("telefone"));
                c.setLogin(rs.getString("login"));
                c.setSenha(rs.getString("senha"));
                
                end.setRua(rs.getString("rua"));
                end.setNumero(rs.getInt("numero"));
                end.setBairro(rs.getString("bairro"));
                end.setCidade(rs.getString("cidade"));
                end.setEstado(rs.getString("estado"));
                end.setCep(rs.getString("cep"));
                
                c.setEnd(end);
                
               adicionacliente.add(c);
            }
            
            pstm.close();
            
        } 
        catch (SQLException erro) 
        {
            System.out.println("\n Nao foi possivel Buscar os dados dos clientes no banco de dados!\n" + erro); 
             x = false;
        }
        
          return x != false;
    }
    
    
    public void executaBuscaClienteNoBancoDeDados(ConexaoDAO cond)
    {
        BuscaClienteNoBancoDeDados(cond);
    }
    
    
    private boolean atualizaLoginNoBancoDeDados(ConexaoDAO cond,Cliente c, String novologin)
    {
       boolean x = true;
       Connection conn; 
       PreparedStatement pstm;
       
        String sql = "update clientes set login = ? where cpf = ?";
        
        conn = cond.ConectaBD();
        
        try 
        {
          pstm = conn.prepareStatement(sql);
          pstm.setString(1, novologin);
          pstm.setString(2, c.getCpf());
          
          pstm.execute();
          pstm.close();
        } 
        catch (SQLException erro)
        {
          System.out.println("\n Nao foi possivel atualizar seus dados no banco de dados!\n" + erro);
           x = false;
        }
        
       
       return x != false;
    }
    
    private boolean atualizaSenhaNoBancoDeDados(ConexaoDAO cond,Cliente c, String novasenha)
    {
       boolean x = true;
       Connection conn; 
       PreparedStatement pstm;
       
        String sql = "update clientes set senha = ? where cpf = ?";
        
        conn = cond.ConectaBD();
        
        try 
        {
          pstm = conn.prepareStatement(sql);
          pstm.setString(1, novasenha);
          pstm.setString(2, c.getCpf());
          
          pstm.execute();
          pstm.close();
        } 
        catch (SQLException erro)
        {
          System.out.println("\n Nao foi possivel atualizar seus dados no banco de dados!\n" + erro);   
          x = false;
        }
        
       
       return x != false;
    }
    
    private boolean atualizaNomeNoBancoDeDados(ConexaoDAO cond,Cliente c, String novoNome)
    {
       boolean x = true;
       Connection conn; 
       PreparedStatement pstm;
       
        String sql = "update clientes set nome = ? where cpf = ?";
        
        conn = cond.ConectaBD();
        
        try 
        {
          pstm = conn.prepareStatement(sql);
          pstm.setString(1, novoNome);
          pstm.setString(2, c.getCpf());
          
          pstm.execute();
          pstm.close();
        } 
        catch (SQLException erro)
        {
          System.out.println("\n Nao foi possivel atualizar seus dados no banco de dados!\n" + erro);   
          x = false;
        }
        
       
       return x != false;
    }
    
    private boolean atualizaCpfNoBancoDeDados(Cliente c, ConexaoDAO cond, String novocpf)
    {
        boolean x = true;
        Connection conn;
        PreparedStatement pstm;
       
        String alteracpfcli = "update clientes set cpf = ? where cpf = ?";
            
        
        conn = cond.ConectaBD();
        
        
        
        try 
        {
            conn.setAutoCommit(false);
           
            pstm = conn.prepareStatement(alteracpfcli);
            pstm.setString(1, novocpf);
            pstm.setString(2, c.getCpf());
            pstm.execute();
            
            conn.commit();
           
        } 
         catch (SQLException erro) 
         {
            try 
            {
                conn.rollback();
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
             System.out.println("\n Nao foi possivel atualizar seus dados no banco de dados!\n" + erro);  
             x = false;
         }
        
         
        
        return x != false;
        
    }
    
    
    private boolean atualizaEnderecoNoBancoDeDados(ConexaoDAO cond,Cliente c, Endereco end)
    {
       boolean x = true;
       Connection conn; 
       PreparedStatement pstm;
       
        String sql = "update endereco set rua = ?, numero = ?, bairro = ?, cidade = ?, estado = ?, cep = ? "
                + "where cpf = ?";
        
        conn = cond.ConectaBD();
        
        try 
        {
          pstm = conn.prepareStatement(sql);
          pstm.setString(1, end.getRua());
          pstm.setInt(2, end.getNumero());
          pstm.setString(3, end.getBairro());
          pstm.setString(4, end.getCidade());
          pstm.setString(5, end.getEstado());
          pstm.setString(6, end.getCep());
          pstm.setString(7, c.getCpf());
          
          pstm.execute();
          pstm.close();
        } 
        catch (SQLException erro)
        {
          System.out.println("\n Nao foi possivel atualizar seus dados no banco de dados!\n" + erro);   
          x = false;
        }
        
       
       return x != false;
    }
    
    private boolean atualizaTelefoneNoBancoDeDados(ConexaoDAO cond,Cliente c, String novoTelefone)
    {
       boolean x = true;
       Connection conn; 
       PreparedStatement pstm;
       
        String sql = "update clientes set telefone = ? where cpf = ?";
        
        conn = cond.ConectaBD();
        
        try 
        {
          pstm = conn.prepareStatement(sql);
          pstm.setString(1, novoTelefone);
          pstm.setString(2, c.getCpf());
          
          pstm.execute();
          pstm.close();
        } 
        catch (SQLException erro)
        {
          System.out.println("\n Nao foi possivel atualizar seus dados no banco de dados!\n" + erro);   
          x = false;
        }
        
       
       return x != false;
    }
    
    
    private boolean atualizaDataDeNascimentoNoBancoDeDados(ConexaoDAO cond,Cliente c, String novaData)
    {
       boolean x = true;
       Connection conn; 
       PreparedStatement pstm;
       
        String sql = "update clientes set dt_nasc = ? where cpf = ?";
        
        conn = cond.ConectaBD();
        
        try 
        {
          pstm = conn.prepareStatement(sql);
          pstm.setString(1, novaData);
          pstm.setString(2, c.getCpf());
          
          pstm.execute();
          pstm.close();
        } 
        catch (SQLException erro)
        {
          System.out.println("\n Nao foi possivel atualizar seus dados no banco de dados!\n" + erro);   
          x = false;
        }
        
       
       return x != false;
    }
    
    
  /*----------------------------------------------------------------------------------------------------*/      
    
    
    
    
  /*-------------------------------- BLOCO COM FUNCOES DE BUSCA NO ARRAYLIST DE CLIENTES ----------------------*/   
    public void limpaArrayList()
    {
        for (Iterator<Cliente> iterator = adicionacliente.iterator(); iterator.hasNext();) {
            Cliente next = iterator.next();
            
            if(next != null)
            {
                iterator.remove();
            }
        }
    }
    
    
    public void mostraCadastros()
    {
        for (Cliente c : adicionacliente) {
            System.out.println(c);
        }
    }
    
    public boolean verificaCadastro(String login, String senha)
    {
        for (Cliente c : adicionacliente) {
            if(c.getLogin().equals(login) && c.getSenha().equals(senha))
            {
                return true;
            }
        }
        return false;
    }
    
    public Cliente fazLogin(String login, String senha, ConexaoDAO cond)
    {
         for (Cliente c : adicionacliente) {
            if(c.getLogin().equals(login) && c.getSenha().equals(senha))
            {
                return c;
            }
        }
        return null;
    }
    
    public Cliente buscaCliente(String login, String senha)
    {
         for (Cliente c : adicionacliente) {
            if(c.getLogin().equals(login) && c.getSenha().equals(senha))
            {
                return c;
            }
        }
        return null;
    }
    
    public Cliente mostrarDadosCliente(String login, String senha)
    {
        for (Cliente c : adicionacliente) {
            if(c.getLogin().equals(login) && c.getSenha().equals(senha))
            {   
                return c;
            }
        }
        return null;
    }
    
    
    public void buscaClienteEInsereNaConta(ConexaoDAO cond, ContaDAO ccd)
    {
        for (Cliente c : adicionacliente) {
               ccd.executaBuscaContaClienteNoBancoDeDados(cond, c);
        }
       
    }
    
  /*----------------------------------------------------------------------------------------------------*/
    
    
    
 /*-------------------------------- BLOCO COM FUNCOES QUE RECEBEM AS FUNCOES DAS ATUALIZACOES FEITAS NO BANCO DE DADOS ----------------------*/   
    public boolean atualizaLoginCliente(String login, String senha, String novoLogin, ConexaoDAO cond)
    {
        for (Cliente c : adicionacliente) {
            if(c.getLogin().equals(login) && c.getSenha().equals(senha))
            {   
                if(atualizaLoginNoBancoDeDados(cond, c, novoLogin) == true)
                {
                     return true;
                }
            }
        }
        return false;
        
    }
    
    
    public boolean atualizaSenhaCliente(String login, String senha, String novaSenha, ConexaoDAO cond)
    {
        for (Cliente c : adicionacliente) {
            if(c.getLogin().equals(login) && c.getSenha().equals(senha))
            {  
                if(atualizaSenhaNoBancoDeDados(cond, c, novaSenha)== true)
                {
                     return true;
                }
            }
        }
        return false;
        
    }
    
    
    public boolean atualizaNomeCliente(String login, String senha, String novoNome, ConexaoDAO cond, ContaDAO ccd)
    {
        for (Cliente c : adicionacliente) {
            if(c.getLogin().equals(login) && c.getSenha().equals(senha))
            {  
                if(atualizaNomeNoBancoDeDados(cond, c, novoNome)== true)
                {
                     limpaArrayList();
                     executaBuscaClienteNoBancoDeDados(cond);
                     ccd.limpaArrayListConta();
                     buscaClienteEInsereNaConta(cond, ccd);
                     return true;
                } 
            }
        }
        return false;
        
    }
    
    
    public boolean atualizaCpfCliente(String login, String senha, String novoCpf, ConexaoDAO cond, ContaDAO ccd)
    {
        for (Cliente c : adicionacliente) {
            if(c.getLogin().equals(login) && c.getSenha().equals(senha))
            {  
                if(atualizaCpfNoBancoDeDados(c, cond, novoCpf) == true)
                {
                     limpaArrayList();
                     executaBuscaClienteNoBancoDeDados(cond);
                     ccd.limpaArrayListConta();
                     buscaClienteEInsereNaConta(cond, ccd);
                     return true; 
                }
            }
        }
        return false;
        
    }
    
    
    public boolean atualizaEnderecoCliente(String login, String senha, Endereco novoEndereco, ConexaoDAO cond, ContaDAO ccd)
    {
        for (Cliente c : adicionacliente) {
            if(c.getLogin().equals(login) && c.getSenha().equals(senha))
            {  
                 if(atualizaEnderecoNoBancoDeDados(cond, c, novoEndereco) == true)
                 {
                     limpaArrayList();
                     executaBuscaClienteNoBancoDeDados(cond);
                     ccd.limpaArrayListConta();
                     buscaClienteEInsereNaConta(cond, ccd);
                     return true; 
                 }
            }
        }
        return false;
        
    }
    
    public boolean atualizaTelefoneCliente(String login, String senha, String novoTelefone, ConexaoDAO cond, ContaDAO ccd)
    {
        for (Cliente c : adicionacliente) {
            if(c.getLogin().equals(login) && c.getSenha().equals(senha))
            {  
                 if(atualizaTelefoneNoBancoDeDados(cond, c, novoTelefone) == true)
                 {
                     limpaArrayList();
                     executaBuscaClienteNoBancoDeDados(cond);
                     ccd.limpaArrayListConta();
                     buscaClienteEInsereNaConta(cond, ccd);
                     return true; 
                 }  
            }
        }
        return false;
        
    }
    
    
    
    public boolean atualizaDataCliente(String login, String senha, String novaData, ConexaoDAO cond, ContaDAO ccd)
    {
        for (Cliente c : adicionacliente) {
            if(c.getLogin().equals(login) && c.getSenha().equals(senha))
            {  
              if(atualizaDataDeNascimentoNoBancoDeDados(cond, c, novaData) == true)
              {
                     limpaArrayList();
                     executaBuscaClienteNoBancoDeDados(cond);
                     ccd.limpaArrayListConta();
                     buscaClienteEInsereNaConta(cond, ccd);
                     return true;  
              }
            }
        }
        return false;
    }
    
    
    public boolean excluiDadosCliente(String login, String senha)
    {
        boolean x = false;
        for (Iterator<Cliente> iterator = adicionacliente.iterator(); iterator.hasNext();) {
            Cliente next = iterator.next();
            
            if(next.getLogin().equals(login) && next.getSenha().equals(senha))
            {
                iterator.remove();
                x = true;
            }
            
        }
        return x;
    }
   /*----------------------------------------------------------------------------------------------------*/
    
    
    
    
    /*-------------------------------- BLOCO COM FUNCOES DE VALIDACAO ----------------------*/ 
    public String ValidaLogin(String login)
    {
        while(login.equals("") || login.equals(" "))
        {
          System.out.println("Login em BRANCO ou INVALIDO . Informe o Login Novamente: ");
          login = scanner.nextLine();  
        }
      return login;  
    }
    
    public String ValidaSenha(String senha)
    {
        while(senha.equals("") || senha.equals(" "))
        {
          System.out.println("Senha em BRANCO ou INVALIDA . Informe a Senha Novamente: ");
          senha = scanner.nextLine();  
        }
      return senha;  
    }
    
    public String ValidaCpf(String cpf)
    {
        while(cpf.equals("") || cpf.equals(" "))
        {
          System.out.println("Cpf em BRANCO ou INVALIDO . Informe o Cpf Novamente: ");
          cpf = scanner.nextLine();  
        }
      return cpf;  
    }
    
    public String ValidaNome(String nome)
    {
        while(nome.equals("") || nome.equals(" "))
        {
          System.out.println("Nome em BRANCO ou INVALIDO . Informe o Nome Novamente: ");
          nome = scanner.nextLine();  
        }
      return nome;  
    }
    
    
    public String ValidaTelefone(String telefone)
    {
        while(telefone.equals("") || telefone.equals(" "))
        {
          System.out.println("Telefone em BRANCO ou INVALIDA . Informe o Telefone Novamente: ");
          telefone = scanner.nextLine();  
        }
      return telefone;  
    }
    
    public String ValidaRua(String rua)
    {
        while(rua.equals("") || rua.equals(" "))
        {
          System.out.println("Rua em BRANCO ou INVALIDA . Informe a Rua Novamente: ");
          rua = scanner.nextLine();  
        }
      return rua;  
    }
    
    public int ValidaNumero(int numero)
    {
        while(numero <= 0)
        {
          System.out.println("Numero em BRANCO ou INVALIDO . Informe o Numero Novamente: ");
          String n  = scanner.nextLine(); 
          numero = Integer.parseInt(n);
        }
      return numero;  
    }
    
    public String ValidaBairro(String bairro)
    {
        while(bairro.equals("") || bairro.equals(" "))
        {
          System.out.println("Bairro em BRANCO ou INVALIDO . Informe o Bairro Novamente: ");
          bairro = scanner.nextLine();  
        }
      return bairro;  
    }
    
    public String ValidaCidade(String cidade)
    {
        while(cidade.equals("") || cidade.equals(" "))
        {
          System.out.println("Cidade em BRANCO ou INVALIDA . Informe a Cidade Novamente: ");
          cidade = scanner.nextLine();  
        }
      return cidade;  
    }
    
    public String ValidaEstado(String estado)
    {
        while(estado.equals("") || estado.equals(" "))
        {
          System.out.println("Estado em BRANCO ou INVALIDO . Informe o Estado Novamente: ");
          estado = scanner.nextLine();  
        }
      return estado;  
    }
    
    public String ValidaCep(String cep)
    {
        while(cep.equals("") || cep.equals(" "))
        {
          System.out.println("Cep em BRANCO ou INVALIDO . Informe o Cep Novamente: ");
          cep = scanner.nextLine();  
        }
      return cep;  
    }
    
    
     public String ValidaData(String datanascimento)
    {
        while(datanascimento.equals("") || datanascimento.equals(" "))
        {
          System.out.println("Data de Nascimento em BRANCO ou INVALIDA . Informe a Data Novamente: ");
          datanascimento = scanner.nextLine();  
        }
      return datanascimento;  
    }
    
/*-------------------------------------------------------------------------------------*/    
    
     
}
