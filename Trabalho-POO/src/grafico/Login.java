package grafico;
import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener, KeyListener{
	CJButton login;
	CJButton sair;
	JTextField fieldUser;
	JPasswordField fieldSenha;
	public Login(){
		setTitle("Controle de Estoque - Login");
		setLocation(100,100);
		setSize(1000,500);
		setUndecorated(true);
		setResizable(false);
		
		setLayout(null);
		JLabel bg = new JLabel();
		ImageIcon im = new ImageIcon("Imagens/bg.jpg");
		bg.setIcon(im);
		bg.setBounds(0,0,1000,500);
		
		JLabel textUser = new JLabel("Usuario:");
		textUser.setBounds(25, 15, 70, 25);
		JLabel textSenha = new JLabel("Senha:");
		textSenha.setBounds(25, 45, 70, 25);
		
		fieldUser = new JTextField(10);
		fieldUser.setBounds(75, 15, 100, 25);
		fieldUser.addKeyListener(this);
		
		fieldSenha = new JPasswordField(10);
		fieldSenha.setEchoChar('*');
		fieldSenha.setBounds(75, 45, 100, 25);
		fieldSenha.addKeyListener(this);
		
		login = new CJButton("Entrar");
		login.setBounds(105, 80, 70, 25);
		login.addActionListener(this);
		login.setBackground(new Color(255,255,255));

		sair = new CJButton("Sair");
		sair.setBounds(25, 80, 70, 25);
		sair.addActionListener(this);
		sair.setBackground(new Color(255,255,255));
		
		JPanel center = new JPanel();
		center.setLayout(null);
		int posXCenter = 400;
		int posYCenter = 170;
		center.setBounds(posXCenter, posYCenter, 200, 120);
		center.setBorder(BorderFactory.createRaisedBevelBorder());
		
		center.add(textUser);
		center.add(fieldUser);
		center.add(textSenha);
		center.add(fieldSenha);
		center.add(login);
		center.add(sair);
		

		getContentPane().setLayout(null);
		getContentPane().add(center);
		getContentPane().add(bg);
	}
	
	public void logar(){
		String loginDigitado = fieldUser.getText();
		String senhaDigitada = fieldSenha.getText();
		
		if(loginDigitado.equals("admin") && senhaDigitada.equals("admin")){
			new Start();
			this.setVisible(false);
		}
		else{
			JOptionPane.showMessageDialog(null,"Usuario e/ou senha invalidos","Erro",0);
			fieldUser.setText("");
			fieldSenha.setText("");
			fieldUser.requestFocus();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == sair){
			System.exit(0);
		}
		else if(e.getSource() == login){
			logar();
		}
	}

	public static void main(String[] args) {
		Login l = new Login();
		l.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getSource() == fieldSenha){
			if(e.getExtendedKeyCode()==10){
				logar();
			}
		}
		else if(e.getSource() == fieldUser){
			if(e.getExtendedKeyCode()==10){
				fieldSenha.requestFocus();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
