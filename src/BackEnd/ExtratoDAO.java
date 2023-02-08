
package BackEnd;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class ExtratoDAO {
    
  ArrayList<Extrato> adicionaExtrato = new ArrayList();  
  
    public ExtratoDAO()
    {
         
    }
    
        
    public void adicionaExtrato(String login, String senha, String tipotransacao, 
    double valor, LocalDateTime datatransacao, ContaDAO ccd)
    {
        //Conta cc = ccd.mostraContaCliente(login, senha);
        
       /* Extrato e = new Extrato(tipotransacao, valor, datatransacao, cc);
        
        adicionaExtrato.add(e);*/
        
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
    
    public double extratoAtualizado(String login, String senha)
    {
         for (Extrato extrato : adicionaExtrato) {
            if(extrato.getConta().getCliente().getLogin().equals(login) && extrato.getConta().getCliente().getSenha().equals(senha))
            {
               return extrato.getConta().getSaldo();
            }
        }
      return 0;
    }
    
   
    public boolean excluiExtrato(String login, String senha)
    {   
        boolean x = false;
        for (Iterator<Extrato> iterator = adicionaExtrato.iterator(); iterator.hasNext();) {
            Extrato next = iterator.next();
            
            if(next.getConta().getCliente().getLogin().equals(login) && next.getConta().getCliente().getSenha().equals(senha))
            {
                 iterator.remove();
                 x = true;
                
            }
            
        }
        
      return x;
     
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
