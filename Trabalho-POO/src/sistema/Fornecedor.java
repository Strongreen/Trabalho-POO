package sistema;
import grafico.Desktop;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Fornecedor extends PessoaJuridica{
	public Fornecedor (int pessoa_id, String razao, String fantasia, long cnpj, long tel, boolean status, Endereco endereco){
		this.pessoa_id=pessoa_id;
		this.razaoSocialVal=razao;
		this.nomeFantasiaVal=fantasia;
		this.cnpjVal=cnpj;
		this.telefoneVal=tel;
		this.statusVal=status;
		this.endereco=endereco;
	}
	public Fornecedor(ArrayList<String> M){
		//pessoa_id|razaoSocial|nomeFantasia|cnpj|telefone|status|rua|numero|complemento|bairro|cidade|estado
		
		this.pessoa_id=Integer.parseInt(M.get(0));
		this.razaoSocialVal=M.get(1);
		this.nomeFantasiaVal=M.get(2);
		this.cnpjVal=Long.parseLong(M.get(3));
		this.telefoneVal=Long.parseLong(M.get(4));
		this.statusVal=Boolean.parseBoolean(M.get(5));
		this.endereco=new Endereco(M.get(6), Integer.parseInt(M.get(7)), M.get(8),M.get(9),M.get(10),M.get(11));
	}
	public Fornecedor(){
		super();
	}
}
