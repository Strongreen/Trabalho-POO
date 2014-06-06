package modulos;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import banco.BD;

import sistema.GrupoProduto;
import sistema.Produto;

public class Produtos {
	JTextField fieldNome = new JTextField(50);
	JTextField fieldValor = new JTextField(10);
	JTextField fieldPeso = new JTextField(5);
	JTextField fieldGrupo = new JTextField(20);
	
	TableTabajara tabelaProdutos;
	
	public JPanel getFormCadastro(){
		JLabel nome = new JLabel("Nome do Produto: ");
		JLabel valor = new JLabel("Valor: ");
		JLabel peso = new JLabel("Peso: ");
		JLabel grupo = new JLabel("Grupo do Produto: ");
		JPanel panelNome = new JPanel();
		JPanel panelValor = new JPanel();
		
		JPanel cadastro = new JPanel();
		JPanel cadastroExt = new JPanel();
		JPanel cadastroWButtons = new JPanel();
		cadastroWButtons.setLayout(new BoxLayout(cadastroWButtons,BoxLayout.Y_AXIS));
		
		cadastro.setLayout(new BoxLayout(cadastro,BoxLayout.Y_AXIS));
		cadastro.setSize(800,150);
		
		panelNome.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelNome.add(nome);
		panelNome.add(fieldNome);
		cadastro.add(panelNome);
		
		panelValor.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelValor.add(valor);
		panelValor.add(fieldValor);
		panelValor.add(peso);
		panelValor.add(fieldPeso);
		panelValor.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelValor.add(grupo);
		panelValor.add(fieldGrupo);
		cadastro.add(panelValor);
		
		cadastroWButtons.add(cadastro);
		
		//Botoes de cadastro
		JPanel actionButtons = new JPanel();
		
		JButton limpar = new JButton("Limpar");
		limpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		limpar.addActionListener(new ButtonHandlerLimpar());
		actionButtons.add(limpar);
		
		JButton cadastrar = new JButton("Cadastrar");
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
				String nome = fieldNome.getText();
				float valor = Float.parseFloat(fieldValor.getText());
				float peso = Float.parseFloat(fieldPeso.getText());
				GrupoProduto grupo = new GrupoProduto(fieldGrupo.getText());
				
				if(nome.length() < 1)
					throw new NumberFormatException();
				if(valor < 0)
					throw new NumberFormatException();
				if(peso < 0)
					throw new NumberFormatException();
				
				Produto p = new Produto(0, nome, valor, peso, 0, grupo);
				
				BD banco = new BD();
				
				int id_produto = banco.gravaProduto(p, false);
				
				banco.connection.close();
				JOptionPane.showMessageDialog(null,"Produto cadastrado com sucesso, ID: "+id_produto,"Ok",1);
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
			fieldNome.setText("");
			fieldValor.setText("");
			fieldPeso.setText("");
			fieldGrupo.setText("");
		}
	}
	
	
	private Object [][] getProdutos() throws SQLException{
		BD banco = new BD();
		
		//listagem de produtos
		ResultSet ps = banco.getProdutos();
		ps.last();
		int size = ps.getRow();
		ps.beforeFirst();
		Object[][] listaProdutos = new Object[size][6];
		while(ps.next()){
			for(int y=0;y<6;y++){
				listaProdutos[ps.getRow()-1][y] = ps.getString(y+1);
			}
		}
		
		banco.connection.close();
		return listaProdutos;
	}
	public JPanel getListagem(){
		//Panel grandao com as barras de rolagem que se ajustam ao redimensionar a janela
		JPanel principal = new JPanel();
		
		try{
			Object[][] listaProdutos = getProdutos();
			
			//Cabecalho da listagem de produtos
			String[] productHeader = {"Codigo", "Nome", "Valor", "Peso", "Qnt. Estocada", "Grupo" };
			//permissoes de edicao
			boolean[] permissions = { false, true, true, true, false, true };
			//largura das colunas
			int[] columWidth = { 70, 360, 70, 70, 70, 155 };
			
			//criacao da tabela e edicao de evento update
			tabelaProdutos = new TableTabajara(listaProdutos, productHeader,permissions,columWidth){
				@Override
				public void update(int linha){
					int produto_id = Integer.parseInt((String) getValueAt(linha,0));
					String nome = (String) getValueAt(linha,1);
					float valor = Float.parseFloat((String)getValueAt(linha,2));
					float peso = Float.parseFloat((String)getValueAt(linha,3));
					int quatidade = Integer.parseInt((String)getValueAt(linha,4));
					GrupoProduto grupo = new GrupoProduto((String) getValueAt(linha,5));
					
					Produto p = new Produto(produto_id, nome, valor, peso, quatidade, grupo);
					
					//verifica se o produto ja foi alterado anteriormente
					if(idDosEditados.contains(produto_id)){
						int i = idDosEditados.indexOf(produto_id);
						editados.set(i, p);
					}
					//adiciona o produto a lista de editados para posterior alteracao no banco
					else{
						idDosEditados.add(produto_id);
						editados.add(p);
					}
				}
				@Override
				public void setEditors(){
					String [] grupos = { "00000001", "00000002", "00000003", "00000004", "00000005" };
					JComboBox grupoProdutos = new JComboBox(grupos);
					TableColumn grupoColum = getColumnModel().getColumn(5);
					grupoColum.setCellEditor(new DefaultCellEditor(grupoProdutos));
				}
			};
			
			//Scroller que vai aparecer caso tenham muitos produtos
			JScrollPane scroller = new JScrollPane(tabelaProdutos);
			scroller.setPreferredSize(new Dimension(800,300));
			principal.add(scroller);
			
			//botao de edicao
			JButton editar = new JButton("Salvar Modificações");
			editar.addActionListener(new ButtonHandlerEditar());
			principal.add(editar);
	
			//botao de desfazer
			JButton desfazer = new JButton("Desfazer Modificações");
			desfazer.addActionListener(new ButtonHandlerResetar());
			principal.add(desfazer);
			
			//define o tamanho minimo do panel grandao pra aparecer os scrollers
			principal.setPreferredSize(new Dimension(800,340));

		}
		catch(Exception e){
			System.out.println(e);
		}
		return principal;
	}
	private class ButtonHandlerEditar implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				BD banco = new BD();
				
				for(int x = 0; x < tabelaProdutos.editados.size(); x++)
					banco.gravaProduto((Produto) tabelaProdutos.editados.get(x), true);
				
				banco.connection.close();
				tabelaProdutos.start(getProdutos());
			}
			catch(SQLException except){
				JOptionPane.showMessageDialog(null,"Ocorreu um erro ao processar requisição","Erro",0);
			}
		}
	}
	private class ButtonHandlerResetar implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				tabelaProdutos.editados.clear();
				tabelaProdutos.idDosEditados.clear();
				
				tabelaProdutos.start();
			}
			catch(Exception except){
				JOptionPane.showMessageDialog(null,"Ocorreu um erro ao processar requisição","Erro",0);
			}
		}
	}
	
	
	public JPanel[] getPanels(){
		JPanel [] p = { getListagem(), getFormCadastro(), new JPanel() };
		return p;
		
	}

	public JButton[] getButtons(){
		JButton [] b = { new JButton("Listagem"), new JButton("Cadastro"), new JButton("Grupos") };
		return b;
	}
}
