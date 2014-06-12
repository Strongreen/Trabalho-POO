package sistema;

import java.sql.SQLException;
import banco.BD;

public class GrupoProduto {
	public int grupoID;
	public String nome;
	
	public GrupoProduto(String nome) throws SQLException{
		BD banco = new BD();
		
		this.grupoID = banco.getGrupoProduto(nome);
		this.nome = nome;
		
		banco.connection.close();
	}
	public GrupoProduto(int cod) throws SQLException{
		BD banco = new BD();
		
		this.nome = banco.getGrupoProduto(cod);
		this.grupoID = cod;
		
		banco.connection.close();
	}
	public GrupoProduto(int cod, String nome){
		this.grupoID = cod;
		this.nome = nome;
	}
}
