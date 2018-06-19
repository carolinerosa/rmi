import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintServer implements PrintServerInterface
{
	private final Lock lock = new ReentrantLock();
	private Job[] printBuffer ;
	private int primeiro; //endereco dp primeiro elemento que entrou no vetor. se o vetor esta cheio, o ultimo elemento dele eh esse valor -1
									//ou 4 se esse valor eh 0
	private int vazio; //endereco do primeiro espaço vazio do vetor. -1 se não houver espaço vazio
	
	private int idCounter;
	private int jobCounter;
	
	
	private static boolean printer1Running;
	private static boolean printer2Running;
	
	private static String printer1Content;
	private static String printer2Content;
	
	
	
	
    public PrintServer() 
	{
		printBuffer = new Job[3];
		primeiro = 0; //endereco dp primeiro elemento que entrou no vetor. se o vetor esta cheio, o ultimo elemento dele eh esse valor -1
									//ou 4 se esse valor eh 0
		vazio = 0; //endereco do primeiro espaço vazio do vetor. -1 se não houver espaço vazio
		
		idCounter = 1;
		jobCounter = 1;
	}

    public Job requestPrint(String s, int c) 
	{
		lock.lock();
		try {
			if(vazio == -1){
				return null;
			}
			printBuffer[vazio] = new Job(jobCounter, c, s);
			Job resp = printBuffer[vazio];
			jobCounter ++;
			vazio ++;
			if(vazio == 3) vazio = 0;
			if(vazio == primeiro) vazio = -1;
			return resp;
		} finally {
				lock.unlock();
		}
    }
	
	public int getPosition(int j){
		lock.lock();
		try {
		int resp=-1;
		for(int i = 0; i <= 2; i++){
			if(printBuffer[i]!=null){
			if(printBuffer[i].getId() == j){
				if( i >= primeiro) resp = i - primeiro;
				else resp = 2 - primeiro + i;
			}
			}
		}
		return resp;
		}finally{
		lock.unlock();
		}
	}
	
	public int login(){
		
		//lock.lock();
		try {
			int resp = idCounter;
			idCounter ++;
			return resp;
		} finally {
				//lock.unlock();
		}
	}

    public synchronized static void main(String args[]) 
	{
		try 
		{
			
            PrintServer obj = new PrintServer();

            PrintServerInterface stub = (PrintServerInterface) UnicastRemoteObject.exportObject(obj, 0); 

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("PrintServer", stub);

			new Thread(printer1).start();
			new Thread(printer2).start();
			
            System.out.println("Servidor pronto e rodando, pressione ctrl+c para terminar");
			while(true){
				
				if(obj.printBuffer[obj.primeiro]!=null){
					 
					if(printer1Running == false){
						obj.lock.lock();
						try{
							printer1Content = obj.printBuffer[obj.primeiro].getConteudo();
							obj.printBuffer[obj.primeiro] = null;		//tira um objeto da fila(colocar um lock aqui)
							//jogar pro historico;
							if(obj.vazio == -1) obj.vazio = obj.primeiro;
							obj.primeiro ++;
							if(obj.primeiro > 2) {
								obj.primeiro = 0;
							}
							System.out.println("Job Enviado para a impressora 1 primeiro, vazio: " + obj.primeiro + " " + obj.vazio);
						}finally{
							obj.lock.unlock();
						}
					}else if(printer2Running == false){
						obj.lock.lock();
						try{
							printer2Content = obj.printBuffer[obj.primeiro].getConteudo();
							obj.printBuffer[obj.primeiro] = null;		//tira um objeto da fila(colocar um lock aqui)
							if(obj.vazio == -1) obj.vazio = obj.primeiro;
							obj.primeiro ++;
							if(obj.primeiro > 2) {
								obj.primeiro = 0;
							}
							System.out.println("Job Enviado para a impressora 2 primeiro, vazio: " + obj.primeiro + " " + obj.vazio);
						}finally{
							obj.lock.unlock();
						}
					}
					
				}
				Thread.currentThread().sleep(1000);
				
				
			}
        }
		catch (Exception e) 
		{
            System.err.println("Capturando exceção no Servidor: " + e.toString());
            e.printStackTrace();
        }
    }
	
	private static Runnable printer1 = new Runnable() {
        public void run() {
            try{
                while(true){
					
					if(printer1Content != null){
						printer1Running = true;
						System.out.println("Impressora 1 imprimindo... ");
						Thread.currentThread().sleep(10000);
						System.out.println("Impressora 1 imprimiu: " + printer1Content);
						printer1Content = null;
						printer1Running = false;
						
					}
					Thread.currentThread().sleep(1000);
				}
            } catch (Exception e){}
 
        }
    };
	
	private static Runnable printer2 = new Runnable() {
        public void run() {
            try{
                while(true){
					if(printer2Content != null){
						printer2Running = true;
						System.out.println("Impressora 2 imprimindo... ");
						Thread.currentThread().sleep(10000);
						System.out.println("Impressora 2 imprimiu: " + printer2Content);
						printer2Content = null;
						printer2Running = false;
						
					}
					Thread.currentThread().sleep(1000);
				}
				
            } catch (Exception e){}
 
        }
    };
	
}
