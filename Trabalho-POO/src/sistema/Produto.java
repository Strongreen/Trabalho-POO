package sistema;
import banco.BD;
import grafico.Desktop;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class Produto {
	public int produto_id;
	public String nome;
	public float valor, peso;
	public int quantidadeEstocada;
	public int quantidadeNaNota;
	public GrupoProduto grupo=null;
	
	public Produto(int produto_id,String nome, float valor, float peso, int quatidade,GrupoProduto grupo){
		this.produto_id=produto_id;
		this.nome=nome;
		this.valor=valor;
		this.peso=peso;
		this.quantidadeNaNota=quatidade;
		this.quantidadeEstocada=quatidade;
		this.grupo=grupo;
	}
	
	public Produto(ArrayList<String> M){
		// produto_id|nome|valor|peso|quatidadeEstocada|grupo
		
		this.produto_id=Integer.parseInt(M.get(0));
		this.nome=M.get(1);
		this.valor=Float.parseFloat(M.get(2));
		this.peso=Float.parseFloat(M.get(3));
		this.quantidadeNaNota=Integer.parseInt(M.get(4));
		this.quantidadeEstocada=Integer.parseInt(M.get(4));
		
	}
	public Produto(){
		
	}
	
	public void abaterEstoque(int quantidade){
		this.quantidadeEstocada -= quantidade;
		
	}
	public void adicionarEstoque(int quantidade){
		this.quantidadeEstocada += quantidade;
		
	}
}
