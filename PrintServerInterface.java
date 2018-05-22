import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrintServerInterface extends Remote 
{
	// Metodo em que um usuario solicita que um arquivo seja impresso
    String requestPrint(String s) throws RemoteException;
}