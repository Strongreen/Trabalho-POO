package modulos;

import grafico.CJButton;
import grafico.Desktop;
import grafico.HeaderOfModule;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import banco.BD;

import sistema.Fornecedor;
import sistema.GrupoProduto;
import sistema.Produto;

public class Fornecedores {
	JTextField fieldCNPJ = new JTextField(15);
	JTextField fieldNomeF = new JTextField(49);
	JTextField fieldRazaoS = new JTextField(49);
	JTextField fieldTel = new JTextField(10);
	
	JCheckBox fieldStatus = new JCheckBox("Status",true);
	JCheckBox fieldIsCliente = new JCheckBox("Cliente",true);
	JCheckBox fieldIsFornecedor = new JCheckBox("Fornecedor",true);

	Enderecos endereco = new Enderecos();
	
	private JPanel getListagem(){
		JPanel backListPanel = new JPanel();
		
		JPanel frontListPanel = new JPanel();
		
		JPanel productList = new JPanel();
		ArrayList<ArrayList<String>> ps = Desktop.banco.lerArquivo("Fornecedor");

		productList.setLayout(new GridLayout((ps.size()+1),6,10,4));
		productList.add(new JLabel("Razao Social"));
		productList.add(new JLabel("Nome Fantasia"));
		productList.add(new JLabel("CNPJ"));
		productList.add(new JLabel("Telefone"));
		productList.add(new JLabel("Rua"));
		productList.add(new JLabel("Numero"));
		productList.add(new JLabel("Complemento"));
		productList.add(new JLabel("Bairro"));
		productList.add(new JLabel("Cidade"));
		productList.add(new JLabel("Estado"));
		productList.add(new JLabel("Status"));
		for(int x=0; x<ps.size(); x++){
			for(int y=0;y<6;y++){
				productList.add(new JLabel(ps.get(x).get(y)));
			}
		}
		backListPanel.add(frontListPanel);
		frontListPanel.add(productList);
		
		return frontListPanel;
	}
	private JPanel getCadastro(){
		JLabel CNPJ = new JLabel("CNPJ: ");
		JLabel nomeF = new JLabel("Nome Fantasia: ");
		JLabel razaoS = new JLabel("Razão Social:     ");
		JLabel tel = new JLabel("Telefone: ");
		JPanel linha1 = new JPanel();
		JPanel linha2 = new JPanel();
		JPanel linha3 = new JPanel();
		JPanel linha4 = new JPanel();
		
		JPanel cadastroExt = new JPanel();
		HeaderOfModule header = new HeaderOfModule("Cadastro");
		cadastroExt.add(header);
		
		JPanel cadastro = new JPanel();
		JPanel cadastroWButtons = new JPanel();
		cadastroWButtons.setLayout(new BoxLayout(cadastroWButtons,BoxLayout.Y_AXIS));
		
		cadastro.setLayout(new BoxLayout(cadastro,BoxLayout.Y_AXIS));
		cadastro.setSize(800,150);
		
		linha1.setLayout(new FlowLayout(FlowLayout.LEFT));
		linha1.add(CNPJ);
		linha1.add(fieldCNPJ);
		cadastro.add(linha1);
		
		linha2.setLayout(new FlowLayout(FlowLayout.LEFT));
		linha2.add(nomeF);
		linha2.add(fieldNomeF);
		cadastro.add(linha2);

		linha3.setLayout(new FlowLayout(FlowLayout.LEFT));
		linha3.add(razaoS);
		linha3.add(fieldRazaoS);
		cadastro.add(linha3);
		
		linha4.setLayout(new FlowLayout(FlowLayout.LEFT));
		linha4.add(tel);
		linha4.add(fieldTel);
		linha4.add(fieldStatus);
		linha4.add(fieldIsCliente);
		linha4.add(fieldIsFornecedor);
		
		cadastro.add(linha4);
		cadastro.add(endereco);
		
		cadastroWButtons.add(cadastro);
		
		//Botoes de cadastro
		JPanel actionButtons = new JPanel();
		
		CJButton limpar = new CJButton("Limpar");
		limpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		limpar.addActionListener(new ButtonHandlerLimpar());
		actionButtons.add(limpar);
		
		CJButton cadastrar = new CJButton("Cadastrar");
		cadastrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cadastrar.addActionListener(new ButtonHandlerCadastrar());
		actionButtons.add(cadastrar);
		
		cadastroWButtons.add(actionButtons);
		
		//adiciona botoes na tela
		cadastroExt.add(cadastroWButtons);
		
		//define tamanho da tela pra aparecer os scrollers
		cadastroExt.setPreferredSize(new Dimension(650,500));
		
		return cadastroExt;
	}
	private class ButtonHandlerCadastrar implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
//				String nome = fieldNome.getText();
//				float valor = Float.parseFloat(fieldValor.getText());
//				float peso = Float.parseFloat(fieldPeso.getText());
//				GrupoProduto grupo = new GrupoProduto(fieldGrupo.getSelectedItem().toString());
//				
//				if(nome.length() < 1)
//					throw new NumberFormatException();
//				if(valor < 0)
//					throw new NumberFormatException();
//				if(peso < 0)
//					throw new NumberFormatException();
//				
//				Produto p = new Produto(0, nome, valor, peso, 0, grupo);
				
				BD banco = new BD();
				
				//int id_produto = banco.gravaProduto(p, false);
				
				banco.connection.close();
				//JOptionPane.showMessageDialog(null,"Produto cadastrado com sucesso, ID: "+id_produto,"Ok",1);
				
				limparCadastro();
			}
			catch(NumberFormatException except){
				JOptionPane.showMessageDialog(null,"Dado inválido informado","Erro",0);
			}
			catch(SQLException except){
				JOptionPane.showMessageDialog(null,"Ocorreu um erro ao processar requisição","Erro",0);
			}
		}
	}
	private class ButtonHandlerLimpar implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			limparCadastro();
		}
	}
	public void limparCadastro(){
		fieldCNPJ.setText("");
		fieldNomeF.setText("");
		fieldRazaoS.setText("");
		fieldTel.setText("");
		
		endereco.reset();
	}
	public JPanel[] getPanels(){
		JPanel [] p = { getListagem(), getCadastro() };
		return p;
	}

	public JButton[] getButtons(){
		JButton [] b = { new JButton("Listagem"), new JButton("Cadastro") };
		return b;
	}
}
