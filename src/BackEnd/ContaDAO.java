
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


public final class ContaDAO {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Conta> adicionaconta = new ArrayList();
    
    public ContaDAO()
    {
       
    }
    
    public boolean criaContaCliente(String login, String senha, ClienteDAO cd, ConexaoDAO cond, ContaDAO ccd)
    {
        Cliente c = cd.buscaCliente(login, senha);
        limpaArrayListConta();
        
       if(insereContaNoBancoDeDados(c, cond) == true)
       {
           cd.buscaClienteEInsereNaConta(cond, ccd);    
           return true;
       }
        
        return false; 
    }
    
    
    
    /*------------------------------- BLOCO DE FUNCOES QUE FAZEM BUSCA NO ARRAYLIST DE CONTAS -------------------------------*/
    
    public boolean verificaContaCliente(String login, String senha)
    {
        for (Conta cont : adicionaconta) {
            if(cont.getCliente().getLogin().equals(login) && cont.getCliente().getSenha().equals(senha))
            {
                return true;
            }
        } 
        return false;
    }
    
    public Conta mostraContaCliente(String login, String senha)
    {
        for (Conta cont : adicionaconta) {
            if(cont.getCliente().getLogin().equals(login) && cont.getCliente().getSenha().equals(senha))
            {
                return cont;
            }
        } 
        return null;
    }
    
    
    
    public void mostraSaldoContaCliente(String login, String senha)
    {
        for (Conta cont : adicionaconta) {
            if(cont.getCliente().getLogin().equals(login) && cont.getCliente().getSenha().equals(senha))
            {
                System.out.println("\nNome: " + cont.getCliente().getNome() + 
                        "\n" + "Cpf: " + cont.getCliente().getCpf() + "\n" + 
                        "Saldo Atualizado: " + cont.getSaldo() + "\n");
            }
        } 
    }
    
    

    
    public void mostraTodasContas()
    {
        for (Conta cont : adicionaconta) {
           
            System.out.println(cont);
        }
    }
    
    public boolean excluiConta(String login, String senha)
    {   
        boolean x = false;
        for (Iterator<Conta> iterator = adicionaconta.iterator(); iterator.hasNext();) {
            Conta next = iterator.next();
            
            if(next.getCliente().getLogin().equals(login) && next.getCliente().getSenha().equals(senha))
            {
                iterator.remove();
                x = true;
            }
            
        }
        return x;
       
    }
    
    public void executaBuscaContaClienteNoBancoDeDados(ConexaoDAO cond, Cliente c)
    {
        BuscaContaNoBancoDeDados(cond, c);
    }
    
    public void limpaArrayListConta()
    {
        for (Iterator<Conta> iterator = adicionaconta.iterator(); iterator.hasNext();) {
            Conta next = iterator.next();
            
            if(next != null)
            {
               iterator.remove();
            }
        }
    }
    
    
    public void buscaContaEInsereNoExtrato(ConexaoDAO cond, ExtratoDAO e)
    {
        for (Conta conta : adicionaconta) {
             e.executaBuscaextrato(cond, conta);
        }
    }
    
    
 /*----------------------------------------------------------------------------------------------------------*/
    
    
    
 
/*---------------------------------------- BLOCO DE FUNCOES DE ACESSO AO BANCO DE DADOS -----------------------*/
    
   
    private boolean insereContaNoBancoDeDados(Cliente c, ConexaoDAO cond)
    {
        boolean x = true;
        Connection conn;
        PreparedStatement pstm;
        int nconta;
       
        String inserenaconta = "insert into conta (cpf,saldo) \n" +
        "values (?,?)";
         
        conn = cond.ConectaBD();
        
        
        try 
        {
            conn.setAutoCommit(false);
           
            pstm = conn.prepareStatement(inserenaconta);
            pstm.setString(1, c.getCpf());
            pstm.setDouble(2, 20000);
            pstm.execute();
           
 
            conn.commit();
            
            nconta  = BuscaNumeroContaNoBancoDeDados(cond, c);
            insereExtratoNoBancoDeDados(nconta, cond);
           
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
    
    
    private boolean insereExtratoNoBancoDeDados(int numeroconta, ConexaoDAO cond)
    {
        
        boolean x = true;
        Connection conn;
        PreparedStatement pstm;
       
        String inserenaconta = "insert into extrato (numeroconta,tipotransacao,valor,datahoratrans) \n" +
        "values (?,?,?,now())";
         
        conn = cond.ConectaBD();
        
        
        try 
        {
            conn.setAutoCommit(false);
           
            pstm = conn.prepareStatement(inserenaconta);
            pstm.setInt(1,numeroconta);
            pstm.setString(2, "CREDITO");
            pstm.setDouble(3, 20000);
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
            
             System.out.println("\n Nao foi possivel inserir o extrato no banco de dados!\n" + erro);
             x = false;
         }
        
        return x != false;
    }
    
    
    
    private boolean BuscaContaNoBancoDeDados(ConexaoDAO cond, Cliente c)
    {
        boolean x = true;
        int nconta;
        double saldo;
        Connection conn; 
        PreparedStatement pstm;
        ResultSet rs;// essa variavel em particular precisa ser do tipo ResultSet e nao
        //Resultset;
       
        String sql = "select numeroconta,saldo from conta where cpf = ?";
        
 
        
        conn = cond.ConectaBD();
        
        try 
        {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, c.getCpf());
            pstm.execute();
            rs = pstm.executeQuery();
            
            while(rs.next())
            {
               nconta = rs.getInt("numeroconta");
               saldo = rs.getDouble("saldo");
               
               Conta cc = new Conta(saldo, c, nconta);
               
               adicionaconta.add(cc);
            }
            
            pstm.close();
            
        } 
        catch (SQLException erro) 
        {
            System.out.println("\n Nao foi possivel Buscar os dados das contas dos clientes no banco de dados!\n" + erro); 
             x = false;
        }
        
          return x != false;
    }
    
    
    
    private int BuscaNumeroContaNoBancoDeDados(ConexaoDAO cond, Cliente c)
    {
        int nconta;
        Connection conn; 
        PreparedStatement pstm;
        ResultSet rs;// essa variavel em particular precisa ser do tipo ResultSet e nao
        //Resultset;
       
        String sql = "select numeroconta from conta "
                + "where cpf = ?";
        
 
        
        conn = cond.ConectaBD();
        
        try 
        {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, c.getCpf());
            pstm.execute(); 
            rs = pstm.executeQuery();
            
            while(rs.next())
            {
               nconta = rs.getInt("numeroconta");
               return nconta;
            }
            
            pstm.close();
            
        } 
        catch (SQLException erro) 
        {
            System.out.println("\n Nao foi possivel Buscar os dados das contas dos clientes no banco de dados!\n" + erro); 
            
        }
        return 0;
        
    }
    
    
    
    private boolean insereDepositoNoBancoDeDados(Conta cc, ConexaoDAO cond, double valor)
    {
        
        boolean x = true;
        Connection conn;
        PreparedStatement pstm;
       
       
        String inserenoextrato = "insert into extrato (numeroconta,tipotransacao,valor,datahoratrans) \n" +
        "values (?,?,?,now())";
         
        conn = cond.ConectaBD();
        
        
        try 
        {
            conn.setAutoCommit(false);
           
            pstm = conn.prepareStatement(inserenoextrato);
            pstm.setInt(1,cc.getNumeroconta());
            pstm.setString(2, "CREDITO");
            pstm.setDouble(3, valor);
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
            
             System.out.println("\n Nao foi possivel inserir o  no banco de dados!\n" + erro);
             x = false;
         }
        
        return x != false;
    }
    
    
    private boolean insereSaqueNoBancoDeDados(Conta cc, ConexaoDAO cond, double valor)
    {
        
        boolean x = true;
        Connection conn;
        PreparedStatement pstm;
       
        valor *= - 1;
       
        String inserenoextrato = "insert into extrato (numeroconta,tipotransacao,valor,datahoratrans) \n" +
        "values (?,?,?,now())";
         
        conn = cond.ConectaBD();
        
        
        try 
        {
            conn.setAutoCommit(false);
           
            pstm = conn.prepareStatement(inserenoextrato);
            pstm.setInt(1,cc.getNumeroconta());
            pstm.setString(2, "DEBITO");
            pstm.setDouble(3, valor);
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
            
             System.out.println("\n Nao foi possivel inserir o  no banco de dados!\n" + erro);
             x = false;
         }
        
        return x != false;
    }
    
    
    private boolean insereTransferenciaNoBancoDeDados(Conta cc, ConexaoDAO cond, double valor, int numeroconta)
    {
        
        boolean x = true;
        Connection conn;
        PreparedStatement origem;
        PreparedStatement destino;
       
        double v2 = valor;
        double v1 = (valor *= - 1);
       
        String inserenoextrato = "insert into extrato (numeroconta,tipotransacao,valor,datahoratrans) \n" +
        "values (?,?,?,now())";
         
        conn = cond.ConectaBD();
        
        
        try 
        {
            conn.setAutoCommit(false);
           
            origem = conn.prepareStatement(inserenoextrato);
            origem.setInt(1,cc.getNumeroconta());
            origem.setString(2, "TRANSFERENCIA -");
            origem.setDouble(3, v1);
            origem.execute();
            
            destino = conn.prepareStatement(inserenoextrato);
            destino.setInt(1,numeroconta);
            destino.setString(2, "TRANSFERENCIA +");
            destino.setDouble(3, v2);
            destino.execute();
            
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
            
             System.out.println("\n Nao foi possivel inserir o  no banco de dados!\n" + erro);
             x = false;
         }
        
        return x != false;
    }
    
    private boolean atualizaSaldoNoBancoDeDadosAoDepositar(Conta cc, ConexaoDAO cond, double valor)
    {
        
        boolean x = true;
        Connection conn;
        PreparedStatement pstm;
       
        double saldoatualizado = cc.getSaldo() + valor;
       
        String saldo = "update conta set saldo = ? where numeroconta = ?";
         
        conn = cond.ConectaBD();
        
        if(insereDepositoNoBancoDeDados(cc, cond, valor) == true)
        {
          try 
          {
            conn.setAutoCommit(false);
            pstm = conn.prepareStatement(saldo);
            pstm.setDouble(1, saldoatualizado);
            pstm.setInt(2, cc.getNumeroconta());
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
            
             System.out.println("\n Nao foi possivel atualizar o saldo no banco de dados!\n" + erro);
             x = false;
           }  
        }
        
        return x != false;
    }
    
    private boolean atualizaSaldoNoBancoDeDadosAoSacar(Conta cc, ConexaoDAO cond, double valor)
    {
        
        boolean x = true;
        Connection conn;
        PreparedStatement pstm;
       
        double saldoatualizado = cc.getSaldo() - valor;
       
        String saldo = "update conta set saldo = ? where numeroconta = ?";
         
        conn = cond.ConectaBD();
        
        if(insereSaqueNoBancoDeDados(cc, cond, valor) == true)
        {
          try 
          {
            conn.setAutoCommit(false);
            pstm = conn.prepareStatement(saldo);
            pstm.setDouble(1, saldoatualizado);
            pstm.setInt(2, cc.getNumeroconta());
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
            
             System.out.println("\n Nao foi possivel atualizar o saldo no banco de dados!\n" + erro);
             x = false;
           }  
        }
        
        return x != false;
    }
    
    
    private boolean atualizaSaldoNoBancoDeDadosAoTransferir(Conta cc, ConexaoDAO cond, double valor, int numeroconta, Conta d)
    {
        
        boolean x = true;
        Connection conn;
        PreparedStatement origem;
        PreparedStatement destino;
       
        double saldoatualizadoOrigem = cc.getSaldo() - valor;
        
        double saldoatualizadoDestino = d.getSaldo() + valor;
        
        
        
        
        String saldo = "update conta set saldo = ? where numeroconta = ?";
         
        conn = cond.ConectaBD();
        
        if(insereTransferenciaNoBancoDeDados(cc, cond, valor, numeroconta) == true)
        {
          try 
          {
            conn.setAutoCommit(false);
            
            origem = conn.prepareStatement(saldo);
            origem.setDouble(1, saldoatualizadoOrigem);
            origem.setInt(2, cc.getNumeroconta());
            origem.execute();
            
            destino = conn.prepareStatement(saldo);
            destino.setDouble(1, saldoatualizadoDestino);
            destino.setInt(2,d.getNumeroconta());
            destino.execute();
           
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
            
             System.out.println("\n Nao foi possivel atualizar o saldo no banco de dados!\n" + erro);
             x = false;
           }  
        }
        
        return x != false;
    }
    
    
/*-------------------------------------------------------------------------------------------------------------*/ 
    
    
 /*------------------------ BLOCO DE FUNCOES QUE RECEBEM O RESULTADO DE TRANSACOES BANCARIAS EXECUTADAS NO BANCO DE DADOS ---------------- */  
     public boolean depositarContaCliente (String login,String senha, double valor, ConexaoDAO cond, ClienteDAO cd, ContaDAO ccd)
     {
         
        for (Conta cc : adicionaconta) {
            if(cc.getCliente().getLogin().equals(login) && cc.getCliente().getSenha().equals(senha))
            {
                if(valor > 0)
                {
                    if(atualizaSaldoNoBancoDeDadosAoDepositar(cc, cond, valor) == true)
                    {
                        limpaArrayListConta();
                        cd.buscaClienteEInsereNaConta(cond, ccd);
                        return true;
                    }
 
                }
               
            }
        } 
        return false;
       
     }
    
    public boolean sacarContaCliente (String login,String senha, double valor, ConexaoDAO cond, ClienteDAO cd, ContaDAO ccd)
     {
        for (Conta cc : adicionaconta) {
            if(cc.getCliente().getLogin().equals(login) && cc.getCliente().getSenha().equals(senha))
            {
                if(valor > 0 && cc.getSaldo() >= valor)
                {
                    if(atualizaSaldoNoBancoDeDadosAoSacar(cc, cond, valor) == true)
                    {
                        limpaArrayListConta();
                        cd.buscaClienteEInsereNaConta(cond, ccd); 
                        return true; 
                    }
                   
                }
               
            }
        } 
        return false;
     }
    
    public Conta buscaContaClienteParaTransferir(int numeroconta)
     {
        for (Conta cc : adicionaconta) {
            if(cc.getNumeroconta() == numeroconta)
            {
                return cc;
            }
        } 
        return null;
     }
    
    public boolean transfereContaCliente(String login, String senha, double valor, 
            int numeroconta, ConexaoDAO cond, ClienteDAO cd, ContaDAO ccd)
    {
        Conta c = buscaContaClienteParaTransferir(numeroconta);
        
        if(c == null)
        {
            System.out.println("\nConta Informada Nao Encontrada\n");
        }
         else
        {
            for (Conta cc : adicionaconta) {
                if(cc.getCliente().getLogin().equals(login) && cc.getCliente().getSenha().equals(senha))
                {
                   if(valor <= cc.getSaldo() && valor > 0)
                   {
                       if(atualizaSaldoNoBancoDeDadosAoTransferir(cc, cond, valor, numeroconta,c) == true)
                       {
                           limpaArrayListConta();
                           cd.buscaClienteEInsereNaConta(cond, ccd); 
                           return true; 
                       }
                      
                   }
                }
            } 
        }
        return false;
        
    }
    
/*-----------------------------------------------------------------------------------------------------------------------*/   
    
    
}
