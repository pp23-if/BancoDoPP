
package BackEnd;

import FrontEnd.MenusTitulos;
import java.time.LocalDateTime;
import java.util.Scanner;


public final class MenuPrincipal {
    Scanner scanner = new Scanner(System.in);
    MenusTitulos mt = new MenusTitulos();
    boolean reslogin , ressenha;
    boolean resdelete = false;
    LocalDateTime agora = LocalDateTime.now();
    
    public MenuPrincipal(String login, String senha, ClienteDAO cd,ContaDAO ccd, ExtratoDAO exd, ConexaoDAO cond)
    {
       
        int opcad;
        int opcont;
        int opmp;
        
        do
        {
            
            opmp = mt.menuPrincipal();
            
           switch(opmp)
           {
               case 1:
               {
                  opcad = mt.menuCadastro();
                  opcoesCadastro(opcad,login, senha, cd, cond, ccd, exd); 
                  if(reslogin == true || ressenha == true)
                  {
                      opmp = 0;
                  }
          
                  break;
               }
               
               case 2:
               {
                   opcont = mt.menuConta();
                   opcoesConta(opcont, login, senha, cd, ccd, exd, cond);
                   
                   if(resdelete == true)
                   {
                       opmp = 0;
                   }
                   
                   break;
               }
               
     
           }
            
        }while(opmp != 0);
        
        
    }
    
    
   
    /*----------------- BLOCO DE FUNCOES COM SWITCH CASE DE CADA OPCAO ------------------*/
    
    public void opcoesCadastro(int op,String login, String senha, ClienteDAO cd, ConexaoDAO cond, ContaDAO ccd, ExtratoDAO exd)
    {
        
        
            switch(op)
            {
                case 1:
                {
                    mostraDadosCliente(login, senha, cd);
                    break;
                }
                
                case 2:
                {   
                    int opatl;
                    
                    opatl = mt.menuAtualizaDados();
                    
                    switch(opatl)
                    {
                        case 1:
                        {
                            System.out.println("\n Informe o NOVO Login: ");
                            String novoLogin = scanner.nextLine();
                            System.out.println("\n");
                            novoLogin = cd.ValidaLogin(novoLogin);
                            
                            reslogin = atualizaLogin(login, senha,novoLogin, cd,cond);
                            
                            break;
                        }
                        
                        case 2:
                        {
                             System.out.println("\n Informe a NOVA Senha: ");
                             String novaSenha = scanner.nextLine();
                             System.out.println("\n");
                             novaSenha = cd.ValidaSenha(novaSenha);
                             
                             ressenha = atualizaSenha(login, senha, novaSenha, cd, cond);
                             break;
                        }
                        
                        case 3:
                        {
                             System.out.println("\n Informe o NOVO Nome: ");
                             String novoNome = scanner.nextLine();
                             System.out.println("\n");
                             novoNome = cd.ValidaNome(novoNome);
                             
                             atualizaNome(login, senha, novoNome, cd, cond, ccd, exd);
                           break; 
                        }
                        
                        case 4:
                        {
                             System.out.println("\n Informe o NOVO Cpf: ");
                             String novoCpf = scanner.nextLine();
                             System.out.println("\n");
                             novoCpf = cd.ValidaCpf(novoCpf);
                             
                             atualizaCpf(login, senha, novoCpf, cd, cond, ccd, exd);
                           break; 
                        }
                        
                        case 5:
                        {
                             System.out.println("\n Informe a NOVA Rua: ");
                             String novaRua = scanner.nextLine();
                             novaRua = cd.ValidaRua(novaRua);
                             System.out.println("\n");
                             
                             System.out.println("\n Informe o NOVO Numero: ");
                             String novoNumero = scanner.nextLine();
                             int numero = Integer.parseInt(novoNumero);
                             numero = cd.ValidaNumero(numero);
                             System.out.println("\n");
                             
                             System.out.println("\n Informe o NOVO Bairro: ");
                             String novoBairro = scanner.nextLine();
                             novoBairro = cd.ValidaBairro(novoBairro);
                             System.out.println("\n");
                             
                             System.out.println("\n Informe o NOVA Cidade: ");
                             String novaCidade = scanner.nextLine();
                             novaCidade = cd.ValidaCidade(novaCidade);
                             System.out.println("\n");
                             
                             System.out.println("\n Informe o NOVO Estado: ");
                             String novoEstado = scanner.nextLine();
                             novoEstado = cd.ValidaEstado(novoEstado);
                             System.out.println("\n");
                             
                             System.out.println("\n Informe o NOVO Cep: ");
                             String novoCep = scanner.nextLine();
                             novoCep = cd.ValidaCep(novoCep);
                             System.out.println("\n");
                             
                             Endereco novoEndereco = new Endereco(novaRua, numero, novoBairro, novaCidade, novoEstado, novoCep);
                             
                             atualizaEndereco(login, senha, novoEndereco, cd, cond, ccd, exd);
                           break; 
                        }
                        
                        case 6:
                        {
                             System.out.println("\n Informe o NOVO Telefone: ");
                             String novoTelefone = scanner.nextLine();
                             System.out.println("\n");
                             novoTelefone = cd.ValidaTelefone(novoTelefone);
                             
                             atualizaTelefone(login, senha, novoTelefone, cd, cond, ccd, exd);
                           break; 
                        }
                        
                        case 7:
                        {
                             System.out.println("\n Informe a NOVA Data de Nascimento: ");
                             String novaDatanasc = scanner.nextLine();
                             System.out.println("\n"); 
                             
                             novaDatanasc = cd.ValidaData(novaDatanasc);
                             atualizaDataNascimento(login, senha, novaDatanasc, cd, cond, ccd, exd);
                             
                             break;
                        }
                            
                    }
                    
                    
                    break;
                }
            }       
      
    }
    
    public void opcoesConta(int op, String login, String senha, ClienteDAO cd,ContaDAO ccd, ExtratoDAO exd, ConexaoDAO cond)
    {
        switch(op)
        {
            case 1:
            {
                verificaCliente(login, senha, ccd, cd,cond);
                break;
            }
            
            case 2:
            {
                
                mostraConta(login, senha, ccd);
                break;
            }
            
            case 3:
            {
                ccd.mostraTodasContas();
                break;
            }
            
            case 4:
            {
                ccd.mostraSaldoContaCliente(login, senha);
              break;  
            }
            
            case 5:
            {
                System.out.println("\n Informe o Valor do DEPOSITO: ");
                String dep = scanner.nextLine();
                double depostito = Double.parseDouble(dep);
                System.out.println("\n");
               
                Depositar(login, senha, ccd, depostito, cond, cd, exd);
                
                break;
            }
            
            case 6:
            {
                System.out.println("\n Informe o Valor do SAQUE: ");
                String sq = scanner.nextLine();
                double saque = Double.parseDouble(sq);
                System.out.println("\n");
                
                Sacar(login, senha, ccd, saque, cond, cd, exd);
                
               
               break; 
            }
            
            case 7:
            {
                System.out.println("\n Informe a Conta Para Qual Deseja TRANSFERIR: ");
                String nc = scanner.nextLine();
                int numeroconta = Integer.parseInt(nc);
                System.out.println("\n");
                
                
                System.out.println("\n Informe o Valor a Ser TRANSFERIDO: ");
                String trans = scanner.nextLine();
                double transferencia = Double.parseDouble(trans);
                System.out.println("\n");
                
                Transferir(login, senha, ccd, transferencia, numeroconta, cond, cd, exd);
                
                System.out.println("\n");
               break; 
            }
            
            case 8:
            {
                exd.mostraExtratoContaCliente(login, senha);
                ccd.mostraSaldoContaCliente(login, senha);
               
           
                break;
            }
            
            case 9:
            {
               exd.mostraTodosExtratos();
                break;
            }
            
            case 10:
            {
                resdelete = Excluir(login, senha, cd, ccd, exd, cond);
                break;
            }
        }
        
            
    }
    
/*---------------------------------------------------------------------------------------*/
    
    
    
      
    
/*----------------- BLOCO DE FUNCOES DO CLIENTE ------------------------------------*/
    
    public void mostraDadosCliente(String login, String senha, ClienteDAO cd)
    {
        Cliente mostrarDadosCliente = cd.mostrarDadosCliente(login, senha);
        System.out.println(mostrarDadosCliente);
    }
    
    
    public boolean atualizaLogin(String login, String senha, String novoLogin, ClienteDAO cd, ConexaoDAO cond)
    {
       boolean loginatualizado = cd.atualizaLoginCliente(login, senha, novoLogin,cond);
       
       if(loginatualizado == true)
       {
           System.out.println("\nLogin atualizado com sucesso. Por favor faca login novamente!\n");
           return true;
       }
       else
       {
           System.out.println("\nNao foi possivel atualizar o Login.\n");
       }
        return false;
    }
    
    
    public boolean atualizaSenha(String login, String senha, String novaSenha, ClienteDAO cd, ConexaoDAO cond)
    {
       boolean Senhaatualizada = cd.atualizaSenhaCliente(login, senha, novaSenha, cond);
       
       if(Senhaatualizada == true)
       {
           System.out.println("\nSenha atualizada com sucesso. Por favor faca login novamente!\n");
           return true;
       }
       else
       {
           System.out.println("\nNao foi possivel atualizar a Senha.\n");
       }
        return false;
    }
    
    
    
    public boolean atualizaNome(String login, String senha, String novoNome, ClienteDAO cd, ConexaoDAO cond, 
            ContaDAO ccd, ExtratoDAO exd)
    {
       boolean Nomeatualizado = cd.atualizaNomeCliente(login, senha, novoNome, cond, ccd, exd);
       
       if(Nomeatualizado == true)
       {
           System.out.println("\nNome atualizado com sucesso!\n");
           return true;
       }
       else
       {
           System.out.println("\nNao foi possivel atualizar o seu Nome.\n");
       }
        return false;
    }
    
    
    
    public boolean atualizaCpf(String login, String senha, String novoCpf, ClienteDAO cd, ConexaoDAO cond, ContaDAO ccd,
            ExtratoDAO exd)
    {
       boolean cpfatualizado = cd.atualizaCpfCliente(login, senha, novoCpf, cond, ccd, exd);
       
       if(cpfatualizado == true)
       {
           System.out.println("\nCpf atualizado com sucesso!\n");
           return true;
       }
       else
       {
           System.out.println("\nNao foi possivel atualizar o seu Cpf.\n");
       }
        return false;
    }      
    
    
    
    public boolean atualizaEndereco(String login, String senha, Endereco novoEndereco, ClienteDAO cd, ConexaoDAO cond, ContaDAO ccd,
            ExtratoDAO exd)
    {
       boolean enderecoatualizado = cd.atualizaEnderecoCliente(login, senha, novoEndereco, cond, ccd, exd);
       
       if(enderecoatualizado == true)
       {
           System.out.println("\nEndereco atualizado com sucesso!\n");
           return true;
       }
       else
       {
           System.out.println("\nNao foi possivel atualizar o seu Endereco.\n");
       }
        return false;
    } 
    
    
    public boolean atualizaTelefone(String login, String senha, String novoTelefone, ClienteDAO cd, ConexaoDAO cond, ContaDAO ccd,
            ExtratoDAO exd)
    {
       boolean telefoneatualizado = cd.atualizaTelefoneCliente(login, senha, novoTelefone, cond, ccd, exd);
       
       if(telefoneatualizado == true)
       {
           System.out.println("\nTelefone atualizado com sucesso!\n");
           return true;
       }
       else
       {
           System.out.println("\nNao foi possivel atualizar o seu Telefone.\n");
       }
        return false;
    }     
    
    
    public boolean atualizaDataNascimento(String login, String senha, String novaData, ClienteDAO cd, ConexaoDAO cond, ContaDAO ccd,
            ExtratoDAO exd)
    {
       boolean dataatualizada = cd.atualizaDataCliente(login, senha, novaData, cond, ccd, exd);
       
       if(dataatualizada == true)
       {
           System.out.println("\nData atualizada com sucesso!\n");
           return true;
       }
       else
       {
           System.out.println("\nNao foi possivel atualizar a Data.\n");
       }
        return false;
    }      

/*--------------------------------------------------------------------------------------*/
 
   
    
    
/*--------------------------- BLOCO DE FUNCOES DA CONTA ---------------------------------*/ 
    
    public void verificaCliente(String login,String Senha, ContaDAO ccd, ClienteDAO cd, ConexaoDAO cond)
    {
        boolean vconta = ccd.verificaContaCliente(login, Senha);
        boolean vcriconta;
        if(vconta == true)
        {
            System.out.println("\nO Cliente ja tem uma Conta Criada!\n");
        }
        else
        {
           vcriconta = ccd.criaContaCliente(login, Senha, cd, cond, ccd);
           
           if(vcriconta == true)
           {
               System.out.println("\nConta criada com Sucesso!!!\n");
           }
            else
           {
               System.out.println("\nNao foi Possivel Criar Sua Conta.\n"); 
           }
        }
    }
    
    public void mostraConta(String login, String senha, ContaDAO ccd)
    {
        Conta c = ccd.mostraContaCliente(login, senha);
        System.out.println(c);
    }
    
    public boolean Depositar(String login, String senha, ContaDAO ccd, double valor, ConexaoDAO cond, ClienteDAO cd, ExtratoDAO exd)
    {
        boolean verificadeposito = ccd.depositarContaCliente(login, senha, valor, cond, cd, ccd,exd);
        
        if(verificadeposito == true)
        {
            System.out.println("\nDeposito Realizado Com Sucesso!!!\n");
            return true;
        }
         else
        {
           System.out.println("\nNao Foi Possivel Realizar o Deposito.\n"); 
           return false;
        }
        
    }
    
    public boolean Sacar(String login, String senha, ContaDAO ccd, double valor, ConexaoDAO cond, ClienteDAO cd, ExtratoDAO exd)
    {
        boolean verificasaque = ccd.sacarContaCliente(login, senha, valor, cond, cd, ccd,exd);
        
        if(verificasaque == true)
        {
            System.out.println("\nSaque Realizado Com Sucesso!!!\n");
            return true;
        }
         else
        {
           System.out.println("\nNao Foi Possivel Realizar o Saque.\n"); 
            return false;
        }
       
    }
    
    public boolean Transferir(String login, String senha, ContaDAO ccd, double valor, int numeroconta, 
            ConexaoDAO cond, ClienteDAO cd, ExtratoDAO exd)
    {
        boolean verificatranseferencia = ccd.transfereContaCliente(login, senha, valor,numeroconta,cond,cd,ccd,exd);
        
        if(verificatranseferencia == true)
        {
            System.out.println("\nTransferencia Realizada Com Sucesso!!!\n");
            return true;
        }
         else
        {
           System.out.println("\nNao Foi Possivel Realizar a Transferencia.\n"); 
           return false;
        }
        
    }
    
    
    public boolean Excluir(String login, String senha, ClienteDAO cd, ContaDAO ccd, ExtratoDAO exd, ConexaoDAO cond)
    {
        exd.executaExcluiRegistrosNoBancoDeDados(login, senha, cond);
        System.out.println("\nConta excluida com Sucesso!!!!!\n");
        return true;
    }
    
    
   
    
   
/*--------------------------------------------------------------------------------------*/    
   
   
}
