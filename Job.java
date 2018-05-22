public class Job{
	
	private int id;
	private int dono;
	private String conteudo;
	
	public Job(int id, int d, String s){
		this.id = id;
		dono = d;
		conteudo = s;
	}
	
	public int getId(){
		return id;
	}
	
	public int getDono(){
		return dono;
	}
	
	public String getConteudo(){
		return conteudo;
	}
	
	public String toString(){
		return "Job id = " + id + " dono = " + dono + " conteudo = " + conteudo;
	}
	
	public void setId(int i){
		id = i;
		return;
	}
	
	public void setDono(int d){
		dono = d;
		return;
	}
	
	public void setConteudo(String c){
		conteudo = c;
		return;
	}
}