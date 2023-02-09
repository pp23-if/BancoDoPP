
package BackEnd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExtratoDAO {
    
  ArrayList<Extrato> adicionaExtrato = new ArrayList();  
  
    public ExtratoDAO()
    {
         
    }
    
    
  /*-------------------------------- BLOCO DE FUNCOES DE ACESSO AO BANCO DE DADOS -----------------------------*/
    private boolean BuscaExtratoNoBancoDeDados(ConexaoDAO cond, Conta cc)
    {
        boolean x = true;
        Connection conn; 
        PreparedStatement pstm;
        ResultSet rs;// essa variavel em particular precisa ser do tipo ResultSet e nao
        //Resultset;
        String tipotransacao;
        double valor;
        String dataT;
       
        String sql = "select tipotransacao,valor,datahoratrans from extrato where numeroconta = ?";
       
        conn = cond.ConectaBD();
        
        try 
        {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, cc.getNumeroconta());
            pstm.execute();
            rs = pstm.executeQuery();
            
            while(rs.next())
            {
               
               tipotransacao = rs.getString("tipotransacao");
               valor = rs.getDouble("valor");
               dataT = rs.getString("datahoratrans");
              
               SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               
                Date dt;
                try 
                {
                    dt = fd.parse(dataT);
                    Extrato e = new Extrato(tipotransacao, valor, dt, cc);
                    adicionaExtrato.add(e);
                } 
                catch (ParseException ex) {
                    Logger.getLogger(ExtratoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
            
            pstm.close();
            
        } 
        catch (SQLException erro) 
        {
             System.out.println("\n Nao foi possivel Buscar os dados de Extratos das contas dos clientes no banco de dados!\n" + erro); 
             x = false;
        }
        
          return x != false;
    }
    
    
    private boolean ExcluiRegistrosNoBancoDeDados(ConexaoDAO cond, Conta cc)
    {
        boolean x = true;
        Connection conn; 
        PreparedStatement pstm;
        
       
        String sql = "delete from clientes where cpf = ?";
       
        conn = cond.ConectaBD();
        
        try 
        {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, cc.getCliente().getCpf());
            pstm.execute();
           
            pstm.close();
            
        } 
        catch (SQLException erro) 
        {
             System.out.println("\n Nao foi possivel Buscar os dados de Extratos das contas dos clientes no banco de dados!\n" + erro); 
             x = false;
        }
        
          return x != false;
    }
    
  /*-----------------------------------------------------------------------------------------------------------*/
    
    
    
    public void executaBuscaextrato(ConexaoDAO cond, Conta cc)
    {
        BuscaExtratoNoBancoDeDados(cond, cc);
    }
    
    public void executaExcluiRegistrosNoBancoDeDados(String login, String senha, ConexaoDAO cond)
    {
        for (Extrato extrato : adicionaExtrato) {
            if(extrato.getConta().getCliente().getLogin().equals(login))
            {
                ExcluiRegistrosNoBancoDeDados(cond, extrato.getConta());
            }
        }
    }
    
    public void mostraTodosExtratos()
    {
        for (Extrato extrato : adicionaExtrato) {
            System.out.println(extrato);
        }
    }
    
    public void mostraExtratoContaCliente(String login, String senha)
    {
        for (Extrato extrato : adicionaExtrato) {
            if(extrato.getConta().getCliente().getLogin().equals(login) && extrato.getConta().getCliente().getSenha().equals(senha))
            {
                System.out.println(extrato);
            }
        }
      
    }
    
    
  
     public void LimpaArrayListExtrato()
     {
         for (Iterator<Extrato> iterator = adicionaExtrato.iterator(); iterator.hasNext();) {
             Extrato next = iterator.next();
             
             if(next != null)
             {
                 iterator.remove();
             }
             
         }
     }
 
    
 }
