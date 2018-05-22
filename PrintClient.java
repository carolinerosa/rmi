import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PrintClient
{
    private PrintClient() 
	{}

    public static void main(String[] args) 
	{
        String host = (args.length < 1) ? null : args[0];
        try 
		{

            Registry registry = LocateRegistry.getRegistry(host);
			PrintServerInterface stub = (PrintServerInterface) registry.lookup("PrintServer");

			
			
            String resposta = stub.requestPrint("");
			// Aqui a gente exibe o fruto produzido por esse lindo e maravilho sistema distribuído
			// Passo importante 4: Verificar resposta dada pelo método remoto.
            System.out.println("resposta: " + resposta);
        } 
		catch (Exception e) // Esse catch trata os RemoteException caso ocorra algum
		{
            System.err.println("Capturando a exceção no Cliente: " + e.toString());
            e.printStackTrace();
        }
    }	
}