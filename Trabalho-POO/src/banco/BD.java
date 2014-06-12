package banco;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException; //imports necessarios para mysql
import java.sql.Statement;

import java.io.*;
import java.util.ArrayList;
import sistema.*;


//nota- adicionar: public boolean is_cliente;
//public boolean is_fornecedor; na funçao PessoaJuridica


public class BD {
	String serverName ="localhost";
	String mydatabase = "softy";
	String url ="jdbc:mysql://"+ serverName + "/" + mydatabase;
	String username= "softy";
	String password ="123456";
	
	public Connection connection = null;
	
	public BD(){
		try{
			String driverName= "com.mysql.jdbc.Driver";
			Class.forName(driverName);
			connection = DriverManager.getConnection(url,username,password);
			
//			 Statement state = connection.createStatement();
//			ResultSet result = state.executeQuery("SELECT * FROM produto");
//			while(result.next()){
//				System.out.println(result.getString("nome"));
//			}
		}
		catch (SQLException e){
			System.out.println("Nao foi possivel conectar ao Banco de Dados");
		}
		catch (ClassNotFoundException e){
			System.out.println("O driver expecificado nao foi encontrado.");
		}
		
	}
	public ResultSet getProdutos() throws SQLException{
		Statement s = connection.createStatement();
		s.executeQuery(
			"SELECT * FROM tb_produto p, tb_grupoproduto g " +
			"WHERE p.codGrupoProduto = g.codGrupoProduto"
		);
		return s.getResultSet();
	}
	
	//Cadastro e edição de produtos:
	public int gravaProduto(Produto p, boolean editar) throws SQLException{
		String controleGrupo = (p.grupo != null? "'"+p.grupo.grupoID+"'":"NULL");
		
		Statement s = connection.createStatement();
		
		int result;
		if (editar){
			s.executeUpdate(
				"UPDATE tb_produto " +
				"SET nome='"+p.nome+"', valor='"+p.valor+"', peso='"+p.peso+"', " +
				"quantidadeEstocada= '"+p.quantidadeEstocada+"', codGrupoProduto="+controleGrupo+" " +
				"WHERE codProduto='"+p.produto_id+"' ",
				Statement.RETURN_GENERATED_KEYS
			);
			result = p.produto_id;
		}
		else {
			String q = "INSERT INTO tb_produto " +
					"(nome, valor, peso, quantidadeEstocada, codGrupoProduto) "+
					"VALUES ('"+p.nome+"', '"+p.valor+"', '"+p.peso+"', '"+p.quantidadeEstocada+"', "+controleGrupo+")";
			System.out.println(q);
			s.executeUpdate(
				q,
				Statement.RETURN_GENERATED_KEYS
			);
			ResultSet resultado = s.getGeneratedKeys();
			resultado.next();
			result = resultado.getInt(1);
		}
		
		return result;
	}
	
	public ResultSet getFornecedores() throws SQLException{
		Statement s = connection.createStatement();
		s.executeQuery(
			"SELECT * FROM `tb_pessoajuridica` WHERE `is_fornecedor`='1'"
		);
		return s.getResultSet();
	}
	//Cadastro e edição de Fornecedor/ Cliente Juridico
	public ResultSet gravaPessoaJuridica(Fornecedor f,boolean editar, int isCliente, int isFornecedor) throws SQLException{
		
		Statement s = connection.createStatement();
		
		if (editar){
			s.executeUpdate(
				"UPDATE tb_fornecedor " +
				"SET razaoSocial='"+f.razaoSocialVal+"', nomeFantasia='"+f.nomeFantasiaVal+"', CNPJ='"+f.cnpjVal+"', " +
				"tel= '"+f.telefoneVal+"', rua= '"+f.endereco.ruaVal+"', num= '"+f.endereco.numeroVal+"', complemento= '"+f.endereco.complementoVal+"'," +
				"bairro= '"+f.endereco.bairroVal+"', cidade= '"+f.endereco.cidadeVal+"', estado= '"+f.endereco.estadoVal+"', status= '"+f.statusVal+"' "+
				"is_cliente= '"+isCliente+"', is_fornecedor= '"+isFornecedor+"'"+
				"WHERE codPessoaJuridica='"+f.pessoa_id+"' ",
				Statement.RETURN_GENERATED_KEYS
			);
		}
		else {
			s.executeUpdate(
				"INSERT INTO tb_fornecedor " +
				"(razaoSocial, nomeFantasia, cnpj, tel, rua, num, complemento, bairro, cidade, estado, status, is_cliente, is_fornecedor) "+
				"VALUES ('"+f.razaoSocialVal+"', '"+f.nomeFantasiaVal+"','"+f.cnpjVal+"', '"+f.telefoneVal+"','"+f.endereco.ruaVal+"', " +
				"'"+f.endereco.numeroVal+"','"+f.endereco.complementoVal+"','"+f.endereco.bairroVal+"', '"+f.endereco.cidadeVal+"', " + 
				"'"+f.endereco.estadoVal+"','"+f.statusVal+"','"+f.is_cliente+"','"+f.is_fornecedor+"')",
				Statement.RETURN_GENERATED_KEYS
			);
		}
		return s.getGeneratedKeys();
		
	}
	
	//Cadastro e edição de Cliente Fisico:
	public ResultSet gravaClienteFis(ClienteFisico f,boolean editar) throws SQLException{
		
		Statement s = connection.createStatement();
		
		if (editar){ 
			s.executeUpdate(
				"UPDATE tb_clientefisico " +
				"SET nome='"+f.nome+"', CPF='"+f.cpf+"', RG= '"+f.rg+"', tel= '"+f.telefone+"'," +
				"rua= '"+f.endereco.ruaVal+"', num= '"+f.endereco.numeroVal+"', complemento= '"+f.endereco.complementoVal+"'," +
				"bairro= '"+f.endereco.bairroVal+"', cidade= '"+f.endereco.cidadeVal+"', estado= '"+f.endereco.estadoVal+"', "+
				"WHERE codCLienteFisico='"+f.pessoa_id+"' ",
				Statement.RETURN_GENERATED_KEYS
			);
		}
		else {
			s.executeUpdate(
				"INSERT INTO tb_clientefisico " +
				"(nome, CPF, RG, tel, rua, num, complemento, bairro, cidade, estado) "+
				"VALUES ('"+f.nome+"', '"+f.cpf+"','"+f.rg+"', '"+f.telefone+"','"+f.endereco.ruaVal+"', " +
				"'"+f.endereco.numeroVal+"','"+f.endereco.complementoVal+"', '"+f.endereco.bairroVal+"', '"+f.endereco.cidadeVal+"', '"+f.endereco.estadoVal+"')",
				Statement.RETURN_GENERATED_KEYS
			);
		}
		return s.getGeneratedKeys();
	
	}
	
	//Cadastro e edição de Funcionarios:
	public ResultSet gravaFuncionario(Funcionario f,boolean editar) throws SQLException{
		
		Statement s = connection.createStatement();
		
		if (editar){ 
			s.executeUpdate(
				"UPDATE tb_funcionario " +
				"SET nome='"+f.nome+"', CPF='"+f.cpf+"', RG= '"+f.rg+"', tel= '"+f.telefone+"', rua= '"+f.endereco.ruaVal+"'," +
				"num= '"+f.endereco.numeroVal+"', complemento= '"+f.endereco.complementoVal+"', bairro= '"+f.endereco.bairroVal+"'," +
				"cidade= '"+f.endereco.cidadeVal+"', estado= '"+f.endereco.estadoVal+"', cargo= '"+f.cargo+"', "+
				"horario_entrada= '"+f.horarioEntrada+"', horario_pausa= '"+f.horarioPausa+"', horario_retorno= '"+f.horarioRetorno+"' "+
				"horario_saida= '"+f.horarioSaida+"', salario= '"+f.salario+"' "+
				"WHERE codFuncionario='"+f.pessoa_id+"' ",
				Statement.RETURN_GENERATED_KEYS
			);
		}
		else {
			s.executeUpdate(
				"INSERT INTO tb_clientefisico " +
				"(nome, CPF, RG, tel, rua, num, complemento, bairro, cidade, estado, cargo, horario_entrada, horario_pausa, horario_retorno, horario_saida, salario) "+
				"VALUES ('"+f.nome+"', '"+f.cpf+"','"+f.rg+"', '"+f.telefone+"','"+f.endereco.ruaVal+"', " +
				"'"+f.endereco.numeroVal+"','"+f.endereco.complementoVal+"', '"+f.endereco.bairroVal+"', '"+f.endereco.cidadeVal+"', "+ 
				"'"+f.endereco.estadoVal+"','"+f.cargo+"','"+f.horarioEntrada+"','"+f.horarioPausa+"','"+f.horarioRetorno+"','"+f.horarioSaida+"', '"+f.salario+"' )",
				Statement.RETURN_GENERATED_KEYS
			);
		}
		return s.getGeneratedKeys();
	}
	
	//cadastro e edição da Empresa
	public ResultSet gravaEmpresa(MinhaEmpresa f,boolean editar) throws SQLException{
		
		Statement s = connection.createStatement();
		
		if (editar){
			s.executeUpdate(
				"UPDATE tb_minhaempresa " +
				"SET razaoSocial='"+f.razaoSocialVal+"', nomeFantasia='"+f.nomeFantasiaVal+"', CNPJ='"+f.cnpjVal+"', " +
				"tel= '"+f.telefoneVal+"', rua= '"+f.endereco.ruaVal+"', num= '"+f.endereco.numeroVal+"', complemento= '"+f.endereco.complementoVal+"'," +
				"bairro= '"+f.endereco.bairroVal+"', cidade= '"+f.endereco.cidadeVal+"', estado= '"+f.endereco.estadoVal+"', status= '"+f.statusVal+"' "+
				"saldo= '"+f.saldo+"'"+
				"WHERE codEmpresa='"+f.pessoa_id+"' ",
				Statement.RETURN_GENERATED_KEYS
			);
		}
		else {
			s.executeUpdate(
				"INSERT INTO tb_fornecedor " +
				"(razaoSocial, nomeFantasia, cnpj, tel, rua, num, complemento, bairro, cidade, estado, status, is_cliente, is_fornecedor) "+
				"VALUES ('"+f.razaoSocialVal+"', '"+f.nomeFantasiaVal+"','"+f.cnpjVal+"', '"+f.telefoneVal+"','"+f.endereco.ruaVal+"', " +
				"'"+f.endereco.numeroVal+"','"+f.endereco.complementoVal+"','"+f.endereco.bairroVal+"', '"+f.endereco.cidadeVal+"', " + 
				"'"+f.endereco.estadoVal+"','"+f.statusVal+"','"+f.saldo+"')",
				Statement.RETURN_GENERATED_KEYS
			);
		}
		return s.getGeneratedKeys();
		
	}
	
	//emitir nota fiscal de Saida:
	public ResultSet gravaNotaSaida(NotaFiscal n, int tipoCliente) throws SQLException{
		Statement s = connection.createStatement();
		
		if(tipoCliente==0){ //0-fis 1-jur
			NFSaidaFisico nf = (NFSaidaFisico)n;
			s.executeUpdate(
				"INSERT INTO tb_nfsaida " +
				"(tipoCliente, data, valorNota, codFuncionario, codClienteFisico, codClienteJuridico) "+
				"VALUES ("+tipoCliente+", '"+nf.data+"', '"+nf.funcionario.pessoa_id+"', '"+nf.cliente.pessoa_id+"', NULL)",
				Statement.RETURN_GENERATED_KEYS
			);	
		}
		else{
			NFSaidaJuridico nf = (NFSaidaJuridico)n;
			s.executeUpdate(
				"INSERT INTO tb_nfsaida " +
				"(tipoCliente, data, valorNota, codFuncionario, codClienteFisico, codClienteJuridico) "+
				"VALUES ("+tipoCliente+", '"+nf.data+"', '"+nf.funcionario.pessoa_id+"', NULL, '"+nf.cliente.pessoa_id+"')",
				Statement.RETURN_GENERATED_KEYS
			);
		}
		ResultSet t = s.getGeneratedKeys();
		t.next();
		int codNota=t.getInt(1);
		String query ="INSERT INTO tb_nfsaidaproduto (codNotaSaida, codProduto, quantidadeProduto, valorProduto) VALUES ";
		
		for(int x=0; x < n.arrayProduto.size(); x++){
			query += "("+codNota+", "+n.arrayProduto.get(x).produto_id+", "+n.arrayProduto.get(x).quantidadeNaNota+", "+n.arrayProduto.get(x).valor+")";
			if(x < n.arrayProduto.size()-1){
				query += ",";
			}
		}
		s.executeUpdate(query);
		
		return s.getGeneratedKeys();
	}

	//nota fiscal Entrada (criar a funçao de gravar a nota fiscal de entrada para 0-fis 1-jur
	public ResultSet gravaNotaEntrada(NotaFiscal n) throws SQLException{
		Statement s = connection.createStatement();
		
	
		
		
		
		
		
		
		return s.getGeneratedKeys();	
	}

	public int getGrupoProduto(String nomeGrupo) throws SQLException{
		Statement s = connection.createStatement();
		ResultSet id = s.executeQuery(
				"SELECT codGrupoProduto "+
				"FROM tb_grupoproduto "+
				"WHERE nomeGrupo LIKE '"+nomeGrupo+"'");
		
		id.next();
		return id.getInt("codGrupoProduto");
	}
	public String getGrupoProduto(int codGrupo) throws SQLException{
		Statement s = connection.createStatement();
		ResultSet id = s.executeQuery(
				"SELECT nomeGrupo "+
				"FROM tb_grupoproduto "+
				"WHERE codGrupoProduto = '"+codGrupo+"'");
		
		id.next();
		return id.getString("nomeGrupo");
	}
	public ResultSet getGruposProdutos() throws SQLException{
		Statement s = connection.createStatement();
		s.executeQuery(
			"SELECT * FROM tb_grupoproduto"
		);
		return s.getResultSet();
	}
	
	public int gravaGrupo(GrupoProduto g, boolean editar) throws SQLException{
		Statement s = connection.createStatement();
		
		int result;
		if (editar){
			s.executeUpdate(
				"UPDATE tb_grupoproduto " +
				"SET nomeGrupo='"+g.nome+"' " +
				"WHERE codGrupoProduto='"+g.grupoID+"' ",
				Statement.RETURN_GENERATED_KEYS
			);
			result = g.grupoID;
		}
		else {	
			s.executeUpdate(
				"INSERT INTO tb_grupoproduto " +
				"(nomeGrupo) " +
				"VALUES ('"+g.nome+"')",
				Statement.RETURN_GENERATED_KEYS
			);
			ResultSet resultado = s.getGeneratedKeys();
			resultado.next();
			result = resultado.getInt(1);
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	//funções absoletas:
	public boolean gravaArquivo(String arquivo, String dado){
		try{
			FileWriter arq = new FileWriter(arquivo+".txt",true);
			BufferedWriter escrever = new BufferedWriter(arq);
			
			escrever.write(dado);
			escrever.newLine();
			
			escrever.close();
			arq.close();
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		return true;
	}
	
	public ArrayList<ArrayList<String>> lerArquivo(String arquivo){
		ArrayList<ArrayList<String>> dados = new ArrayList<ArrayList<String>>();
		try{
			FileReader arq = new FileReader(arquivo+".txt");
			BufferedReader ler = new BufferedReader(arq);
			String linha;
			ArrayList<String> numLinha = new ArrayList<String>();
			while(ler.ready()){
				linha = ler.readLine();
				numLinha = decodificaRegistro(linha);
				dados.add(numLinha);
			}
			
			ler.close();
			arq.close();
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		return dados;
	}
	public ArrayList<String> decodificaRegistro(String registro){
		ArrayList<String> dados = new ArrayList<String>();
		String ch = "";
		char [] reg = registro.toCharArray();
		for(int x = 0; x<reg.length;x++){
			if(reg[x]=='|'){
				dados.add(ch);
				ch = "";
			}
			else{
				ch += reg[x];
			}
		}
		dados.add(ch);
		return dados;
	}
	public ArrayList<String> pesquisa(int id, String arquivo){
		ArrayList<ArrayList<String>> a= lerArquivo(arquivo);
		
		for(int x=0; x<a.size(); x++){
			if(Integer.parseInt(a.get(x).get(0))==id){
				return a.get(x);
			}	
		}
		return new ArrayList<String>();	
	}
	public String codificaRegistro(ArrayList<String> registro){
		String R="";
		for(int x=0; x<registro.size()-1; x++){
			R+=registro.get(x)+"|";
		}
		R+=registro.get(registro.size()-1);
		return R;
	}
	public void editaArquivo(String nomeDoArquivo, String registro){
		ArrayList<String> reg = decodificaRegistro(registro);
		ArrayList<ArrayList<String>> p= lerArquivo(nomeDoArquivo);
		for(int x=0; x<p.size(); x++){
			if(p.get(x).get(0).equals(reg.get(0))){
				p.set(x, reg);
				break;
			}	
		}
		try{
			FileWriter arq = new FileWriter(nomeDoArquivo+".txt");
			BufferedWriter escrever = new BufferedWriter(arq);
			
			for(int x=0; x<p.size(); x++){
				escrever.write(this.codificaRegistro(p.get(x)));
				escrever.newLine();
			}
			
			escrever.close();
			arq.close();
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	public int getLastID(String nomeArquivo){
		ArrayList<ArrayList<String>> a = lerArquivo(nomeArquivo);
		
		if(a.size()==0){
			return 0;
		}
		String lastId = a.get(a.size()-1).get(0);
		
		return Integer.parseInt(lastId);	
	}
}
