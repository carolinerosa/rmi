import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AloMundoServidor implements AloMundo 
{
    public AloMundoServidor() 
	{}

    public String digaAloMundo() 
	{
		System.out.println("Chamada de aplicação Cliente recebida!");
        return "Agora não tem desculpa. Você já sabe RMI. Vambora programar!? Beleza!?";
    }

    public static void main(String args[]) 
	{
		try 
		{
			// Instancia o objeto remoto
			// Esse objeto fica no servidor. Executando as chamadas externas, assim que recebidas.
			// Passo importante 1: Instanciar o objeto remoto
            AloMundoServidor obj = new AloMundoServidor();
			// A linha abaixo serve para gerar o Stub, esse sim será exportado (registrado) mais abaixo
			// Obs.: 
			//  Stub é uma nomenclatura associada ao padrão de projeto Proxy
			//  Para maiores informações procurar o livro Use a Cabeça Padrões de Projetos
			//  também conhecido por Head First Design Patterns
			// Em suma, é o objeto utilizado que efetua a conversa entre a aplicação Cliente e a aplicação Servidora
            // Repare no cast efetuado, sabemos que o tipo do objeto é AloMundo
			// Relembre que AloMundo é uma interface. Mas é uma interface remota pois estende a interface java.rmi.Remote
			// O zero na instrução abaixo é referente a porta. Zero significa que uma porta será selecionada dinamicamente.
			// Passo importante 2: Gerar o Stub
            AloMundo stub = (AloMundo) UnicastRemoteObject.exportObject(obj, 0); 

            // "Amarra" o Stub do objeto remoto no regisro
			// Melhor pensar que o Stub está sendo publicado no Registro
			// Passo importante 3: Obter o registro e regisrar o Stub gerado
            Registry registry = LocateRegistry.getRegistry();
			// A string abaixo indica o nome associado ao Stub "amarrado" (ou publicado, se você for como eu e preferir esse termo)
            registry.bind("AloMundo", stub);

			// Uma mensagem para aparecer no console de forma a indicar que tudo deu certo
			// Uma vez que o console vai ficar "parado" esperando chamadas de aplicações clientes
            System.out.println("Servidor pronto!");
        }
		catch (Exception e) 
		{
            System.err.println("Capturando exceção no Servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}
