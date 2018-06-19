import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrintServerInterface extends Remote 
{
	// Metodo em que um usuario solicita que um arquivo seja impresso
    Job requestPrint(String s, int c) throws RemoteException;
	int getPosition(int j) throws RemoteException; 
	int login() throws RemoteException;
}
