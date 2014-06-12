package modulos;

import grafico.Desktop;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.*;
import sistema.*;

public class Clientes {
	public static JPanel getFormCadastro(){
		JLabel id = new JLabel("Identificador: ");
		JLabel nome = new JLabel("Nome: ");
		JLabel rg = new JLabel("RG: ");
		JLabel cpf = new JLabel("CPF: ");
		JLabel tel = new JLabel("Telefone: ");
		JLabel status = new JLabel("Status: ");
		JTextField fieldID = new JTextField(5);
		JTextField fieldNome = new JTextField(50);
		JTextField fieldRG = new JTextField(15);
		JTextField fieldCPF = new JTextField(15);
		JTextField fieldTEL = new JTextField(15);
		JTextField fieldStatus = new JTextField(20);
		JPanel panelNome = new JPanel();
		JPanel panelDocumentos = new JPanel();
		JPanel panelContatos = new JPanel();
		JPanel panelEndereco = new Enderecos();
		
		JPanel form = new JPanel();
		
		form.setLayout(new BoxLayout(form,BoxLayout.Y_AXIS));
		form.setSize(800,150);
		panelNome.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelNome.add(id);
		panelNome.add(fieldID);
		panelNome.add(nome);
		panelNome.add(fieldNome);
		form.add(panelNome);
		panelDocumentos.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelDocumentos.add(rg);
		panelDocumentos.add(fieldRG);
		panelDocumentos.add(cpf);
		panelDocumentos.add(fieldCPF);
		form.add(panelDocumentos);
		panelContatos.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelContatos.add(tel);
		panelContatos.add(fieldTEL);
		panelContatos.add(status);
		panelContatos.add(fieldStatus);
		form.add(panelContatos);
		form.add(panelEndereco);
		
		return form;
	}
	
	public static JPanel[] getPanels(){
		JPanel backListPanel = new JPanel();
		
		JPanel frontListPanel = new JPanel();
		
		JPanel productList = new JPanel();
		ArrayList<ArrayList<String>> ps = Desktop.banco.lerArquivo("ClienteFisico");

		for(int x=0; x<ps.size(); x++){
			for(int y=0;y<6;y++){
				productList.add(new JLabel(ps.get(x).get(y)));
			}
		}
		backListPanel.add(frontListPanel);
		frontListPanel.add(productList);
		
		
		JPanel castPanel = new JPanel();
		
		JPanel Relatorios = Clientes.getFormCadastro();
		castPanel.add(Relatorios);
		JPanel [] p = { backListPanel, castPanel };
		return p;
		
	}

	public static JButton[] getButtons(){
		JButton [] b = { new JButton("Listagem"),new JButton("Cadastro")};
		return b;
	}
}
