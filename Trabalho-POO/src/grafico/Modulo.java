package grafico;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;
import javax.swing.plaf.IconUIResource;
import javax.swing.text.IconView;

import sistema.*;
import sistema.MinhaEmpresa;
import modulos.*;

public class Modulo extends JPanel implements MouseListener, WindowListener{
	JLabel icone = new JLabel("");
	ImageIcon imgNormal;
	ImageIcon imgSelected;
	String ImgUrl;
	static WindowOfModule window = null;
	
	public Modulo(String IMG, String altText){
		setLayout(new GridBagLayout());
		setOpaque(false);
		setBackground(new Color(0,0,0,0));
		
		ImgUrl = IMG;
		
		icone.setToolTipText(altText);
		
		imgNormal = new ImageIcon("Imagens/"+ImgUrl.toLowerCase()+".png");
		imgSelected = new ImageIcon("Imagens/"+ImgUrl.toLowerCase()+"_selected.png");
		
		icone.setIcon(imgNormal);
		add(this.icone);
		
		icone.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		icone.addMouseListener(this);
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		switch(ImgUrl){
			case "produtos":
				if(window == null){
					Produtos modProd = new Produtos();
					window = new WindowOfModule("Produtos",modProd.getPanels(),modProd.getButtons());
			 		window.addWindowListener(this);
				}
				
				window.setVisible(true);
				break;
			case "fornecedores":
				if(window == null){
					Fornecedores modForn = new Fornecedores();
					window = new WindowOfModule("Teste",modForn.getPanels(),modForn.getButtons());
			 		window.addWindowListener(this);
				}
				
				window.setVisible(true);
				break;
			case "clientes":
				if(window == null){
					window = new WindowOfModule("Teste",Clientes.getPanels(),Clientes.getButtons());
					window.addWindowListener(this);
				}
				
				
				window.setVisible(true);
				break;
			case "notaFiscalEntrada":
				if(window == null){
					window = new WindowOfModule("Teste",NFEntrada.getPanels(),NFEntrada.getButtons());
			 		window.addWindowListener(this);
				}
				
				
				window.setVisible(true);
				break;
			case "notaFiscalSaida":
				if(window == null){
					Saidas mod = new Saidas();
					window = new WindowOfModule("Teste",mod.getPanels(),mod.getButtons());
				//	window = new WindowOfModule("Teste",NFSaidaJuridico.getPanels(),NFSaidaJuridico.getButtons());
			 		window.addWindowListener(this);
				}
				
				
				window.setVisible(true);
				break;
					
			case "minhaEmpresa":
				if(window == null){
					window = new WindowOfModule("Teste",MinhaEmpresa.getPanels(),MinhaEmpresa.getButtons());
			 		window.addWindowListener(this);
				}
				
				window.setVisible(true);
				break;
			case "funcionarios":
				if(window == null){
					window = new WindowOfModule("Teste",Funcionario.getPanels(),Funcionario.getButtons());
			 		window.addWindowListener(this);
				}
				
				window.setVisible(true);
				break;
			case "relatorios":
				if(window == null){
					window = new WindowOfModule("Teste",Relatorios.getPanels(),Relatorios.getButtons());
			 		window.addWindowListener(this);
				}
				
				window.setVisible(true);
				break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		icone.setIcon(imgSelected);
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		icone.setIcon(imgNormal);
	}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
	@Override
	public void windowActivated(WindowEvent arg0) {}
	@Override
	public void windowClosed(WindowEvent arg0) {}
	@Override
	public void windowClosing(WindowEvent arg0) {
		window = null;
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	@Override
	public void windowDeiconified(WindowEvent arg0) {}
	@Override
	public void windowIconified(WindowEvent arg0) {}
	@Override
	public void windowOpened(WindowEvent arg0) {}
}
