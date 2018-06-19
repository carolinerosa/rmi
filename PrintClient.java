import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.ArrayList;

public class PrintClient
{
	
	private static int id;
	private static ArrayList<Job> jobs = new ArrayList<Job>();
	static long  inicio, fim;
	
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
			
				inicio= System.currentTimeMillis();
					System.out.print( "Digite o que voce quer imprimir: " );
						input = "Mensagem para imp";
						Job resposta=null;
						while(resposta==null){
						resposta = stub.requestPrint(input, id);
							if(resposta!=null){
							jobs.add(resposta);
							// Aqui a gente exibe o fruto produzido por esse lindo e maravilho sistema distribuído
							// Passo importante 4: Verificar resposta dada pelo método remoto.
							System.out.println("resposta: " + resposta);
							fim= System.currentTimeMillis();
							System.out.println("Duracao: " + (fim-inicio));
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