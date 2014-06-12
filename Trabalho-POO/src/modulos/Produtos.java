package modulos;

import grafico.CJButton;
import grafico.HeaderOfModule;

import java.awt.Color;
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
	JComboBox fieldGrupo = getComboBoxGrupos();
	
	TableTabajara tabelaProdutos;
	
	public JPanel getFormCadastro(){
		JLabel nome = new JLabel("Nome do Produto: ");
		JLabel valor = new JLabel("Valor: ");
		JLabel peso = new JLabel("Peso: ");
		JLabel grupo = new JLabel("Grupo do Produto: ");
		JPanel panelNome = new JPanel();
		JPanel panelValor = new JPanel();
		
		JPanel cadastroExt = new JPanel();
		HeaderOfModule header = new HeaderOfModule("Cadastro");
		cadastroExt.add(header);
		
		JPanel cadastro = new JPanel();
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
				String nome = fieldNome.getText();
				float valor = Float.parseFloat(fieldValor.getText());
				float peso = Float.parseFloat(fieldPeso.getText());
				GrupoProduto grupo = new GrupoProduto(fieldGrupo.getSelectedItem().toString());
				
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
		fieldNome.setText("");
		fieldValor.setText("");
		fieldPeso.setText("");
		fieldGrupo.setSelectedIndex(0);
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
				if(y==5)
					listaProdutos[ps.getRow()-1][y] = ps.getString(8);
				else
					listaProdutos[ps.getRow()-1][y] = ps.getString(y+1);
			}
		}
		
		banco.connection.close();
		return listaProdutos;
	}
	private JComboBox getComboBoxGrupos(){
		String [] grupos;
		try {
			BD banco = new BD();
			
			//listagem de produtos
			ResultSet ps = banco.getGruposProdutos();
			ps.last();
			int size = ps.getRow();
			ps.beforeFirst();
			grupos = new String[size];
			while(ps.next()){
				grupos[ps.getRow()-1]= ps.getString("nomeGrupo");
			}
			
			banco.connection.close();
			
		} catch (Exception e) {
			grupos = new String[1];
		}
		return new JComboBox(grupos);
	}
	public JPanel getListagem(){
		//Panel grandao com as barras de rolagem que se ajustam ao redimensionar a janela
		JPanel principal = new JPanel();
		HeaderOfModule header = new HeaderOfModule("Listagem");
		principal.add(header);
		
		try{
			Object[][] listaProdutos = getProdutos();
			
			//Cabecalho da listagem de produtos
			String[] productHeader = {"Codigo", "Nome", "Valor", "Peso", "Qnt. Estocada", "Grupo" };
			//permissoes de edicao
			boolean[] permissions = { false, true, true, true, false, true };
			//largura das colunas
			int[] columWidth = { 70, 360, 70, 70, 70, 157 };
			
			//criacao da tabela e edicao de evento update
			tabelaProdutos = new TableTabajara(listaProdutos, productHeader,permissions,columWidth){
				@Override
				public void update(int linha){
					try {
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
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,"Dado Inválido Inserido","Erro",0);
					}
				}
				@Override
				public void setEditors(){
						TableColumn grupoColum = getColumnModel().getColumn(5);
						grupoColum.setCellEditor(new DefaultCellEditor(getComboBoxGrupos()));
				}
			};
			
			//Scroller que vai aparecer caso tenham muitos produtos
			JScrollPane scroller = new JScrollPane(tabelaProdutos);
			scroller.setPreferredSize(new Dimension(800,400));
			principal.add(scroller);
			
			JPanel bPanel = new JPanel();
			bPanel.setPreferredSize(new Dimension(2000,200));
			bPanel.setBackground(Color.WHITE);
			
			//botao de edicao
			CJButton editar = new CJButton("Salvar Modificações");
			editar.addActionListener(new ButtonHandlerEditar());
			bPanel.add(editar);
	
			//botao de desfazer
			CJButton desfazer = new CJButton("Desfazer Modificações");
			desfazer.addActionListener(new ButtonHandlerResetar());
			bPanel.add(desfazer);
			
			//botao de Atualizar
			CJButton atualizar = new CJButton("Atualizar tabela");
			atualizar.addActionListener(new ButtonHandlerAtualizar());
			bPanel.add(atualizar);
			
			principal.add(bPanel);
			
			//define o tamanho minimo do panel grandao pra aparecer os scrollers
			principal.setPreferredSize(new Dimension(800,500));
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
	private class ButtonHandlerAtualizar implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				tabelaProdutos.editados.clear();
				tabelaProdutos.idDosEditados.clear();
				
				tabelaProdutos.start(getProdutos());
			}
			catch(Exception except){
				JOptionPane.showMessageDialog(null,"Ocorreu um erro ao processar requisição","Erro",0);
			}
		}
	}
	
	
	//comeca janela grupos
	TableTabajara tabelaGrupos;
	JTextField campoCadastroGrupo;
	private Object [][] getGrupos() throws SQLException{
		BD banco = new BD();
		
		//listagem de produtos
		ResultSet ps = banco.getGruposProdutos();
		ps.last();
		int size = ps.getRow();
		ps.beforeFirst();
		Object[][] listaGrupos = new Object[size][2];
		while(ps.next()){
			for(int y=0;y<2;y++){
				listaGrupos[ps.getRow()-1][y] = ps.getString(y+1);
			}
		}
		
		banco.connection.close();
		return listaGrupos;
	}
	
	public JPanel getTelaGrupos(){
		//Panel grandao com as barras de rolagem que se ajustam ao redimensionar a janela
		JPanel principal = new JPanel();
		HeaderOfModule header = new HeaderOfModule("Grupos");
		principal.add(header);
		try {
			Object[][] listaGrupos = getGrupos();
		
			//Cabecalho da listagem de produtos
			String[] productHeader = {"Codigo", "Nome"};
			//permissoes de edicao
			boolean[] permissions = { false, true};
			//largura das colunas
			int[] columWidth = { 70, 727};
			
			//criacao da tabela e edicao de evento update
			tabelaGrupos = new TableTabajara(listaGrupos, productHeader,permissions,columWidth){
				@Override
				public void update(int linha){
					try {
						int codigoGrupo = Integer.parseInt((String) getValueAt(linha,0));
						String nome = (String) getValueAt(linha,1);
						
						GrupoProduto g = new GrupoProduto(codigoGrupo, nome);
						
						if(idDosEditados.contains(codigoGrupo)){
							int i = idDosEditados.indexOf(codigoGrupo);
							editados.set(i, g);
						}
						else{
							idDosEditados.add(codigoGrupo);
							editados.add(g);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,"Dado Inválido Inserido","Erro",0);
					}
				}
			};
			//Scroller que vai aparecer caso tenham muitos produtos
			JScrollPane scroller = new JScrollPane(tabelaGrupos);
			scroller.setPreferredSize(new Dimension(800,400));
			principal.add(scroller);
			
			JPanel bPanel1 = new JPanel();
			bPanel1.setPreferredSize(new Dimension(2000,35));
			bPanel1.setBackground(Color.WHITE);
			
			JPanel bPanel2 = new JPanel();
			bPanel2.setPreferredSize(new Dimension(2000,35));
			bPanel2.setBackground(Color.WHITE);
			
			JLabel textoCadastroGrupo = new JLabel("Nome do Grupo: ");
			bPanel1.add(textoCadastroGrupo);
			
			campoCadastroGrupo = new JTextField(20);
			bPanel1.add(campoCadastroGrupo);
			
			//botao de Cadastro
			CJButton cadastrar = new CJButton("Criar Grupo");
			cadastrar.addActionListener(new ButtonHandlerCriarGrupo());
			bPanel1.add(cadastrar);
			
			//botao de edicao
			CJButton editar = new CJButton("Salvar Modificações");
			editar.addActionListener(new ButtonHandlerEditarGrupo());
			bPanel2.add(editar);
	
			//botao de desfazer
			CJButton desfazer = new CJButton("Desfazer Modificações");
			desfazer.addActionListener(new ButtonHandlerResetarGrupo());
			bPanel2.add(desfazer);
			
			principal.add(bPanel1);
			principal.add(bPanel2);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		principal.setPreferredSize(new Dimension(800,500));
		return principal;
	}
	
	private class ButtonHandlerEditarGrupo implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				BD banco = new BD();
				
				for(int x = 0; x < tabelaGrupos.editados.size(); x++)
					banco.gravaGrupo((GrupoProduto) tabelaGrupos.editados.get(x), true);
				
				banco.connection.close();
				tabelaGrupos.start(getGrupos());
			}
			catch(SQLException except){
				JOptionPane.showMessageDialog(null,"Ocorreu um erro ao processar requisição","Erro",0);
			}
		}
	}
	private class ButtonHandlerResetarGrupo implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			tabelaGrupos.editados.clear();
			tabelaGrupos.idDosEditados.clear();
			
			tabelaGrupos.start();
		}
	}
	private class ButtonHandlerCriarGrupo implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				String nome = campoCadastroGrupo.getText();
				
				if(nome.length() < 1)
					throw new NumberFormatException();
				
				GrupoProduto g = new GrupoProduto(0, nome);
				
				BD banco = new BD();
				
				int codigoGrupo = banco.gravaGrupo(g, false);
				
				banco.connection.close();
				JOptionPane.showMessageDialog(null,"Grupo cadastrado com sucesso, ID: "+codigoGrupo,"Ok",1);
				
				campoCadastroGrupo.setText("");
				tabelaGrupos.start(getGrupos());
			}
			catch(NumberFormatException except){
				JOptionPane.showMessageDialog(null,"Dado inválido informado","Erro",0);
			}
			catch(SQLException except){
				JOptionPane.showMessageDialog(null,"Ocorreu um erro ao processar requisição","Erro",0);
			}
		}
	}
	public JPanel[] getPanels(){
		JPanel [] p = { getListagem(), getFormCadastro(), getTelaGrupos() };
		return p;
		
	}

	public JButton[] getButtons(){
		JButton [] b = { new JButton("Listagem"), new JButton("Cadastro"), new JButton("Grupos") };
		return b;
	}
}
