import java.rmi.Remote;
import java.rmi.RemoteException;

// Ponto importante 1: estende a interface java.rmi.Remote
public interface AloMundo extends Remote 
{
	// Nosso método remoto. *.* 
	// Ponto importante 2: todos os métodos propagam RemoteException
    String digaAloMundo() throws RemoteException;
}