import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.ArrayList;

public class PrintClient
{
	
	private static int id;
	private static ArrayList<Job> jobs = new ArrayList<Job>();
	
    private PrintClient() 
	{}

    public static void main(String[] args) 
	{
        String host = (args.length < 1) ? null : args[0];
        try 
		{

            Registry registry = LocateRegistry.getRegistry(host);
			PrintServerInterface stub = (PrintServerInterface) registry.lookup("PrintServer");
			
			id = stub.login();
			System.out.println( "Logado no servidor com o id: "  + id);
			
			Scanner scanner = new Scanner( System.in );
			String input;
			
			String caso = "p";
			boolean sair = false;
			
			while(!sair){
				
						System.out.print( "Digite o que voce deseja fazer.\np para imprimir\nl para log de impressao.\nj exibir a posicao de um job.\ns para sair. " );
						caso = scanner.nextLine();
				switch(caso){
					case "p" : 
						System.out.print( "Digite o que voce quer imprimir: " );
						input = scanner.nextLine();
						
						Job resposta = stub.requestPrint(input, id);
						if(resposta!=null){
						jobs.add(resposta);
						// Aqui a gente exibe o fruto produzido por esse lindo e maravilho sistema distribuído
						// Passo importante 4: Verificar resposta dada pelo método remoto.
						System.out.println("resposta: " + resposta);
						}else{
							System.out.println("Buffer cheio");
						}
						//sair = true;
						break;
					case "l" : 
					System.out.println("Log:");
						for(Job j : jobs){
							System.out.println(j.toString());
						}
						break;
					case "s" : 
						sair = true;
						System.out.println("Encerrando a aplicacao");
						break;
					case "j" :
						System.out.print( "Digite o job que voce quer saber a posicao: " );
						int num = scanner.nextInt();
						num = stub.getPosition(num);
						if(num != -1) {
							System.out.println( "O seu job esta na posicao: " + num );
						}
						else {
							System.out.println( "Seu job ja foi impresso" );
						}
						break;
					default: 
						System.out.println("Comando nao identificado tente novamente");
						break;
				}
			}

			
        } 
		catch (Exception e) // Esse catch trata os RemoteException caso ocorra algum
		{
            System.err.println("Capturando a exceção no Cliente: " + e.toString());
            e.printStackTrace();
        }
    }	
}
