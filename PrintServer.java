import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrintServer implements PrintServerInterface
{
	
	public static String[] printBuffer = new String[3];
	public static int primeiro = 0; //endereco dp primeiro elemento que entrou no vetor. se o vetor esta cheio, o ultimo elemento dele eh esse valor -1
									//ou 4 se esse valor eh 0
	public static int vazio = 0; //endereco do primeiro espaço vazio do vetor. -1 se não houver espaço vazio
	
    public PrintServer() 
	{}

    public String requestPrint(String s) 
	{
		if(vazio == -1){
			return "Buffer cheio, tente mais tarde";
		}
		printBuffer[vazio] = s;
		vazio ++;
		if(vazio == primeiro || vazio == 3) vazio = -1;
		return "Seu arquivo foi adicionado ao buffer, aguarde enquanto ele eh impresso";
    }

    public static void main(String args[]) 
	{
		try 
		{
		
            PrintServer obj = new PrintServer();

            PrintServerInterface stub = (PrintServerInterface) UnicastRemoteObject.exportObject(obj, 0); 

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("PrintServer", stub);

            System.out.println("Servidor pronto e rodando, pressione ctrl+c para terminar");
			while(true){
				
			}
        }
		catch (Exception e) 
		{
            System.err.println("Capturando exceção no Servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}
