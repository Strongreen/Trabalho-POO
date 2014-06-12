package sistema;
import java.awt.FlowLayout;
import java.util.Scanner;
import javax.swing.*;

public class Endereco{
	public String ruaVal;
	public int numeroVal;
	public String complementoVal;
	public String bairroVal;
	public String cidadeVal;
	public String estadoVal;
	
	public Endereco (String rua,int numero, String complemento, String bairro, String cidade, String estado){
		this.ruaVal=rua;
		this.numeroVal=numero;
		this.complementoVal=complemento;
		this.bairroVal=bairro;
		this.cidadeVal=cidade;
		this.estadoVal=estado;
	}
}
