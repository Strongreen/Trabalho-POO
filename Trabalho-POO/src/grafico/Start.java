package grafico;
import java.util.ArrayList;

import javax.swing.JFrame;
public class Start {
	public Start() {
		Modulo produtos = new Modulo("produtos","Controle o Estoque e Cadastre Produtos.");
		Modulo fornecedores = new Modulo("fornecedores","Gerencie os seus Fornecedores.");
		Modulo clientes = new Modulo("clientes","Cadastre clientes.");
		Modulo notaFiscalEntrada = new Modulo("notaFiscalEntrada","Notas Fiscais de Entrada");
		Modulo notaFiscalSaida = new Modulo("notaFiscalSaida","Notas Fiscais de Saída");
		Modulo funcionarios = new Modulo("funcionarios","Cadastre Funcionários.");
		Modulo minhaEmpresa = new Modulo("minhaEmpresa","Adicione informações sobre sua empresa");
		Modulo relatorios = new Modulo("relatorios","Obtenha e Imprima Relatórios");
		
		ArrayList<Modulo> modulos = new ArrayList<Modulo>();
		modulos.add(produtos);
		modulos.add(fornecedores);
		modulos.add(clientes);
		modulos.add(funcionarios);
		modulos.add(notaFiscalEntrada);
		modulos.add(notaFiscalSaida);
		modulos.add(minhaEmpresa);
		modulos.add(relatorios);
		
		Desktop desktop = new Desktop(modulos);
		desktop.setVisible(true);
		desktop.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

}
