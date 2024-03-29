
package FrontEnd;

import BackEnd.Cliente;
import BackEnd.ClienteDAO;
import BackEnd.ConexaoDAO;
import BackEnd.ContaDAO;
import BackEnd.ExtratoDAO;
import BackEnd.MenuPrincipal;
import java.util.Scanner;


public class Teste {
   
    Scanner scanner = new Scanner(System.in);
    ClienteDAO cdao = new ClienteDAO();
    MenusTitulos mt = new MenusTitulos();
    ContaDAO ccd = new ContaDAO();
    ExtratoDAO exd = new ExtratoDAO();
    ConexaoDAO cond = new ConexaoDAO();
            
   
    public Teste()
    {
        int opcao;
        
        do
        {
            cdao.limpaArrayList();
            cdao.executaBuscaClienteNoBancoDeDados(cond);
            
            ccd.limpaArrayListConta();
            cdao.buscaClienteEInsereNaConta(cond, ccd);
            
            exd.LimpaArrayListExtrato();
            ccd.buscaContaEInsereNoExtrato(cond, exd);
            
            opcao = mt.menuInicial();
            
            switch(opcao)
            {
                case 1:
                {
                      
                      
                      System.out.println("\nLogin: ");
                      String login = scanner.nextLine();
                      System.out.println("\n");

                      System.out.println("Senha: ");
                      String senha = scanner.nextLine();
                      System.out.println("\n");
                      
                      Cliente retornoLogin = cdao.fazLogin(login,senha,cond);
                     
                     if(retornoLogin == null)
                     {
                         System.out.println("\n usuario nao cadastrado!!!!!\n");
                     }
                     else
                     {
                         System.out.println("\n Login efetuado com sucesso!!!\n");
                         MenuPrincipal mp = new MenuPrincipal(login,senha,cdao,ccd,exd,cond);
                     }
                       
                      
                    break;
                }
                
                case 2:
                {
                   cdao.cadastraCliente(cond);
                    break;
                }
                
                case 3:
                {
                    System.out.println("\n******************** Mostrando os clientes Cadastrados: ************************\n");
                    cdao.mostraCadastros();
                    System.out.println("\n=================== Mostrando As Contas Bancarias:\n ==============================");
                    ccd.mostraTodasContas();
                    System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$ Mostrando Todos os Extratos:\n $$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                    exd.mostraTodosExtratos();
                    break;
                }
                
                
            }
            
        }while(opcao != 0);
        
    }
    
    
    public static void main(String[] args) {
         new Teste();
    }
    
}
