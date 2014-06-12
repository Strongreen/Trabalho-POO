package sistema;
import grafico.Desktop;

import javax.swing.*;

import banco.BD;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.*;
public abstract class PessoaJuridica extends JPanel{
	public int pessoa_id;
	public String razaoSocialVal;
	public String nomeFantasiaVal;
	public long cnpjVal;
	public long telefoneVal;
	public boolean statusVal;
	public Endereco endereco;

	public int is_cliente;
	public int is_fornecedor;
	
	public PessoaJuridica(ArrayList<String> M){
		this.pessoa_id=Integer.parseInt(M.get(0));
		this.razaoSocialVal=M.get(1);
		this.nomeFantasiaVal = M.get(2);
		this.cnpjVal=Long.parseLong(M.get(3));
		this.telefoneVal=Long.parseLong(M.get(4));
		this.statusVal=Boolean.parseBoolean(M.get(5));
		this.endereco=new Endereco(M.get(6), Integer.parseInt(M.get(7)), M.get(8),M.get(9),M.get(10),M.get(11));
	}
		
	public PessoaJuridica(){
	}
		
}
	