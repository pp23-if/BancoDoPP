
package FrontEnd;

import java.util.Scanner;


public class MenusTitulos {
     Scanner scanner = new Scanner(System.in);

   

    /*Este e o menu inicial, o primeiro menur a ser visualizado*/
    public int menuInicial() {

        StringBuilder builder = new StringBuilder("");

        builder.append("\n============SEJA BEM VINDO AO BANCO DO PP===========");
        builder.append("\n0 - Para sair do programa");
        builder.append("\n1 - Login");
        builder.append("\n2 - Ainda nao possuo cadastro");
        builder.append("\n3 - Listar Todos os cadastros");
        builder.append("\n\nOpcao: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    
     public int menuPrincipal() {

        StringBuilder builder = new StringBuilder("");

        builder.append("\n============ MENU PRINCIPAL ===========");
        builder.append("\n0 - sair");
        builder.append("\n1 - Opcoes cadastro");
        builder.append("\n2 - Opcoes conta");
        builder.append("\n\nOpcao: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
     
     
      public int menuCadastro() {

        StringBuilder builder = new StringBuilder("");

        builder.append("\n============ CADASTRO ===========");
        builder.append("\n0 - Sair");
        builder.append("\n1 - Mostrar meus Dados");
        builder.append("\n2 - Atualizar meus dados");
        builder.append("\n\nOpcao: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
      
      
      public int menuAtualizaDados() {

        StringBuilder builder = new StringBuilder("");

        builder.append("\n============ ATUALIZACAO ===========");
        builder.append("\n1 - Alterar login.");
        builder.append("\n2 - Alterar senha");
        builder.append("\n3 - Alterar nome.");
        builder.append("\n4 - Alterar cpf.");
        builder.append("\n5 - Alterar endereco.");
        builder.append("\n6 - Alterar telefone.");
        builder.append("\n7 - Alterar data de nascimento.");
        builder.append("\n0 - Sair");
        builder.append("\nOpcao: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());

    }

      
       public int menuConta() {

        StringBuilder builder = new StringBuilder("");

        builder.append("\n============ CONTA ===========");
        builder.append("\n1 - Abrir Conta.");
        builder.append("\n2 - Mostrar Dados da Conta");
        builder.append("\n3 - Mostrar Todas Contas.");
        builder.append("\n4 - Ver Saldo.");
        builder.append("\n5 - Depositar.");
        builder.append("\n6 - Sacar.");
        builder.append("\n7 - Transferir.");
        builder.append("\n8 - Gerar Extrato.");
        builder.append("\n9 - Mostrar Todos Extratos.");
        builder.append("\n10 - Excluir Cadastro/Conta.");
        builder.append("\n0 - Sair");
        builder.append("\nOpcao: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());

    }

}
