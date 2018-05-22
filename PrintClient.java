import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.ArrayList;

public class PrintClient
{
	
	private int id;
	private ArrayList<Job> jobs = new ArrayList();
	
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
				switch(caso){
					case "p" : 
						System.out.print( "Digite o que voce quer imprimir: " );
						input = scanner.nextLine();
						
						Job resposta = stub.requestPrint(input, id);
						jobs.add(resposta);
						// Aqui a gente exibe o fruto produzido por esse lindo e maravilho sistema distribuído
						// Passo importante 4: Verificar resposta dada pelo método remoto.
						System.out.println("resposta: " + resposta);
						sair = true;
						break;
					//case "s" 
					
					default: 
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
