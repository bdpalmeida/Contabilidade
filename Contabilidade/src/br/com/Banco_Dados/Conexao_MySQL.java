package br.com.Banco_Dados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

import net.sf.jasperreports.engine.JRResultSetDataSource;

/**
 * @author Brenno 
 *
 * Copyright (c) 2016 Brenno Dário Pimenta de Almeida
 * A permissão é concedida, gratuitamente, a qualquer pessoa que obtenha uma cópia deste software e 
 * dos arquivos de documentação associados (o "Software"), para lidar com o Software sem restrições, 
 * incluindo, sem limitação, os direitos de usar, copiar, modificar, mesclar , publicar, distribuir, 
 * sub-licenciar e/ou vender cópias do Software, e para permitir que as pessoas a quem o Software é 
 * fornecido a fazê-lo, sujeito às seguintes condições:
 * O aviso de copyright acima e este aviso de permissão devem ser incluídos em todas as cópias ou partes 
 * substanciais do Software.
 * O SOFTWARE É FORNECIDO "COMO ESTÁ", SEM GARANTIA DE QUALQUER TIPO, expressa ou implícita, INCLUINDO, 
 * SEM LIMITAÇÃO, AS GARANTIAS DE COMERCIALIZAÇÃO, ADEQUAÇÃO A UM DETERMINADO FIM E NÃO VIOLAÇÃO. 
 * EM NENHUM CASO OS AUTORES ou direitos de autor DETENTORES DE SER RESPONSÁVEL POR QUALQUER RECLAMAÇÃO, 
 * DANOS OU OUTRA RESPONSABILIDADE, SEJA EM UMA AÇÃO DE CONTRATO, DELITO OU DE OUTRA FORMA, DECORRENTES DE, 
 * OU EM CONEXÃO COM O SOFTWARE OU O USO OU OUTRA APLICAÇÃO DO PROGRAMAS.
 *
 */

public class Conexao_MySQL {		
	
	private String servidor = "com.mysql.jdbc.Driver", urlBanco = "jdbc:mysql://localhost:3306/Contabilidade?useSSL=false", 
			usuarioBanco = "root", senhaBanco = "admin", sql;
	private String saldo;
	
	private Connection con = null;

	public void TestarConexao() {
	
		try {
			try {
				Class.forName(servidor);
			} catch (ClassNotFoundException erro) {
				erro.printStackTrace();
			}
			con = DriverManager.getConnection(urlBanco, usuarioBanco,
					senhaBanco);

		} catch (CommunicationsException erro) {
			JOptionPane.showMessageDialog(null,
					"Conexão com a Base de Dados está desativada!",
					"Erro, conexão com a Base de Dados",
					JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(null, "O programa Contabilidade será encerrado.",
					"Encerrando Contabilidade", JOptionPane.WARNING_MESSAGE);
			
			System.exit(0);
			
		} catch (SQLException erro) {
			JOptionPane.showMessageDialog(null,
					"Conexão com a Base de Dados está desativada!",
					"Erro, conexão com a Base de Dados",
					JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(null, "O programa Contabilidade será encerrado.",
					"Encerrando Contabilidade", JOptionPane.WARNING_MESSAGE);
			
			System.exit(0);
		}
	}
	
	public boolean ConsultaExisteTabelaTipoDespesa(String ondeNome) {
		
		TestarConexao();

		try {			
			Statement stmt = con.createStatement();
			
			sql = "SELECT * FROM TipoDespesa USE INDEX (nomeTipoDespesa) WHERE nome_Tipo ='" + ondeNome + "'";
			
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
				return true;
			else
				return false;
			
		}catch (SQLException erro) {
			erro.printStackTrace();
		}

		return false;
	}
	
	public boolean AdicionarTipoDespesa(String nome){
		
		TestarConexao();
		
		if(ConsultaExisteTabelaTipoDespesa(nome)){
			JOptionPane.showMessageDialog(null, "Já existe um tipo de despesa com este nome.", "Tipo de despesa existente", JOptionPane.ERROR_MESSAGE);
			return false;
		}		
		else{
			
			try {				
				sql = "INSERT INTO TipoDespesa(nome_Tipo) VALUES"
						+ "(?)";

				PreparedStatement stmt = con.prepareStatement(sql);

				stmt.setString(1, nome);
				
				stmt.executeUpdate();
				stmt.close();

				JOptionPane.showMessageDialog(null,
						"Tipo de despesa adicionado com sucesso!",
						"Tipo de despesa adicionado!", JOptionPane.INFORMATION_MESSAGE);

				return true;
			} catch (SQLException erro) {
				JOptionPane.showMessageDialog(null,
						"Erro ao adicionar tipo de despesa!", "Erro",
						JOptionPane.ERROR_MESSAGE);
				erro.printStackTrace();

				return false;
			} finally {
				try {
					con.close();
				} catch (SQLException erro) {
					erro.printStackTrace();
				}
			}				
		}
	}
	
	public boolean AlterarTipoDespesa(String nomeAlterado, String nomeAntigo){
		
		TestarConexao();
		
		try{			
			sql = "UPDATE TipoDespesa USE INDEX(nomeTipoDespesa) SET nome_Tipo = ? WHERE nome_Tipo = ?";

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, nomeAlterado);
			stmt.setString(2, nomeAntigo);

			stmt.executeUpdate();
			stmt.close();
			
			JOptionPane.showMessageDialog(null,
					"Alteração realizada com sucesso!", "Alteração Realizada!",
					JOptionPane.INFORMATION_MESSAGE);
			
			return true;
		} catch (SQLException erro) {
			JOptionPane.showMessageDialog(null, "Erro ao efetuar alteração!",
					"Erro", JOptionPane.ERROR_MESSAGE);
			erro.printStackTrace();
			return false;
		} finally {
			try {
				con.close();
			} catch (SQLException erro) {
				erro.printStackTrace();
			}
		}		
	}
	
	public String[] ExibeTipoDespesa(){
		String nomeTipoDespesa[] = {};
		
		TestarConexao();
		
		try {			
			Statement stmt = con.createStatement();	
			
			sql = "SELECT COUNT(cod) FROM TipoDespesa";
			
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();

			nomeTipoDespesa = new String[rs.getInt(1)];
			
			sql = "SELECT * FROM vwTipoDespesa";
						
			rs = stmt.executeQuery(sql);

			for (int contador = 0; (rs.next()); contador++) {
				String nome = rs.getString(1);
				
				nomeTipoDespesa[contador] = nome;
			}

		} catch (SQLException erro) {
			erro.printStackTrace();
		}
		
		return nomeTipoDespesa;
	}
	
	public boolean AdicionarDespesa(String tipo, Date data, String nome, double entrada, double saida, double total){
		
		TestarConexao();
					
		try {			
			Statement stmt = con.createStatement();
			
			sql = "SELECT cod FROM TipoDespesa WHERE nome_Tipo ='" + tipo + "'";
			
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			
			int cod_TipoDespesa = rs.getInt(1);	

			sql = "INSERT INTO Despesa(data_desp, cod_TipoDespesa, nome, entrada, saida, total) VALUES" 
				+ "(?,?,?,?,?,?)";
			
			PreparedStatement stmt2 = con.prepareStatement(sql);	

			stmt2.setTimestamp(1, new java.sql.Timestamp(data.getTime()));
			stmt2.setInt(2, cod_TipoDespesa);
			stmt2.setString(3, nome);
			stmt2.setDouble(4, entrada);
			stmt2.setDouble(5, saida);
			stmt2.setDouble(6, total);
			
			stmt2.executeUpdate();
			stmt2.close();
				
			JOptionPane.showMessageDialog(null,
					"Despesa adicionada com sucesso!",
					"Despesa adicionada!", JOptionPane.INFORMATION_MESSAGE);
			
			return true;
		} catch (SQLException erro) {
			JOptionPane.showMessageDialog(null,
					"Erro ao adicionar Despesa!", "Erro",
					JOptionPane.ERROR_MESSAGE);
			erro.printStackTrace();
				return false;
		} finally {
			try {
				con.close();
			} catch (SQLException erro) {
				erro.printStackTrace();
			}
		}				
	}

	public boolean AdicionarPagamento(Date data, String lancamento, double valor, String descricao){
		
		TestarConexao();
					
		try {			
			sql = "INSERT INTO Pagamento(data_pag, lancamento, valor, descricao) VALUES" 
				+ "(?,?,?,?)";
			
			PreparedStatement stmt = con.prepareStatement(sql);	

			stmt.setTimestamp(1, new java.sql.Timestamp(data.getTime()));
			stmt.setString(2, lancamento);
			stmt.setDouble(3, valor);
			stmt.setString(4, descricao);
			
			stmt.executeUpdate();
			stmt.close();
				
			JOptionPane.showMessageDialog(null,
					"Pagamento adicionado com sucesso!",
					"Pagamento adicionado!", JOptionPane.INFORMATION_MESSAGE);
			
			return true;
		} catch (SQLException erro) {
			JOptionPane.showMessageDialog(null,
					"Erro ao adicionar Pagamento!", "Erro",
					JOptionPane.ERROR_MESSAGE);
			erro.printStackTrace();
				return false;
		} finally {
			try {
				con.close();
			} catch (SQLException erro) {
				erro.printStackTrace();
			}
		}				
	}
	
	public void ExcluirLinha(String tabela, int cod){
		
		TestarConexao();
		
		try {			
			sql = "DELETE FROM " + tabela + " WHERE cod = ?";

			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setInt(1, cod);
			stmt.executeUpdate();
			stmt.close();

			JOptionPane.showMessageDialog(null,
					"Linha excluída com sucesso!", "Exclusão Realizada!",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException erro) {
			JOptionPane.showMessageDialog(null, "Erro ao efetuar exclusão!",
					"Erro", JOptionPane.ERROR_MESSAGE);
			erro.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException erro) {
				erro.printStackTrace();
			}
		}

	}
	
	public String[][] ConsultaTabelaDespesa() {
		
		TestarConexao();
		
		String linhas[][] = null;
		int contador;

		try {			
			Statement stmt = con.createStatement();

			sql = "SELECT COUNT(cod) FROM Despesa";

			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			
			linhas = new String[rs.getInt(1)][8];

			sql = "SELECT * FROM vwDespesas";
			
			rs = stmt.executeQuery(sql);

			for (contador = 0; (rs.next()) && (contador < linhas.length); contador++) {
				int cod;
				Date data;
				String nomeTipo, nome;
				double entrada, saida, total;
				
				cod = rs.getInt(1);
				
				SimpleDateFormat formataData = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");

				data = rs.getTimestamp(2);
				nomeTipo = rs.getString(3);
				nome = rs.getString(4);
				entrada = rs.getDouble(5);
				saida = rs.getDouble(6);
				total = rs.getDouble(7);
				
				linhas[contador][0] = String.valueOf(cod);
				linhas[contador][1] = formataData.format(data);
				linhas[contador][2] = nomeTipo;
				linhas[contador][3] = nome;
				linhas[contador][4] = NumberFormat.getCurrencyInstance().format(entrada);
				linhas[contador][5] = NumberFormat.getCurrencyInstance().format(saida);
				linhas[contador][6] = NumberFormat.getCurrencyInstance().format(total);
			}
			
			sql = "SELECT * FROM vwTotalDespesas";
			
			rs = stmt.executeQuery(sql);
			rs.next();		
			
			saldo = NumberFormat.getCurrencyInstance().format(rs.getDouble(1));
			
			rs.close();
		} catch (SQLException erro) {
			erro.printStackTrace();
		}

		return linhas;
	}
	
	public String[][] ConsultaTabelaPagamento() {
		
		TestarConexao();
		
		String linhas[][] = null;
		int contador;

		try {			
			Statement stmt = con.createStatement();

			sql = "SELECT COUNT(cod) FROM Pagamento";

			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			
			linhas = new String[rs.getInt(1)][6];

			sql = "SELECT * FROM vwPagamentos";
			
			rs = stmt.executeQuery(sql);

			for (contador = 0; (rs.next()) && (contador < linhas.length); contador++) {
				int cod;
				Date data;
				String lancamento, descricao;
				double valor;
				
				cod = rs.getInt(1);
				
				SimpleDateFormat formataData = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");

				data = rs.getTimestamp(2);
				lancamento = rs.getString(3);
				valor = rs.getDouble(4);
				descricao = rs.getString(5);
				
				linhas[contador][0] = String.valueOf(cod);
				linhas[contador][1] = formataData.format(data);
				linhas[contador][2] = lancamento;
				linhas[contador][3] = NumberFormat.getCurrencyInstance().format(valor);
				linhas[contador][4] = descricao;
			}
			
			sql = "SELECT * FROM vwTotalPagamentos";
			
			rs = stmt.executeQuery(sql);
			rs.next();		
			
			saldo = NumberFormat.getCurrencyInstance().format(rs.getDouble(1));
			
			rs.close();
		} catch (SQLException erro) {
			erro.printStackTrace();
		}

		return linhas;
	}

	public String getSaldo() {
		return saldo;
	}
	
	public String[] ConsultaMes(String tabela){
		
		TestarConexao();
		
		String mes[] = {};
		
		
		
		try {			
			Statement stmt = con.createStatement();	
			
			if(tabela == "Despesa")			
				sql = "SELECT COUNT(DISTINCT MONTH(data_desp)) FROM vwDespesas";
			else
				sql = "SELECT COUNT(DISTINCT MONTH(data_pag)) FROM vwPagamentos";
			
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();

			mes = new String[rs.getInt(1)];
			
			if(tabela == "Despesa")			
				sql = "SELECT DISTINCT MONTH(data_desp) FROM vwDespesas";
			else
				sql = "SELECT DISTINCT MONTH(data_pag) FROM vwPagamentos";
						
			rs = stmt.executeQuery(sql);

			for (int contador = 0; (rs.next()); contador++) {
				int nMes = rs.getInt(1);
				
				switch (nMes) {
				case 1: 
					mes[contador] = "Janeiro";					
					break;
				case 2: 
					mes[contador] = "Fevereiro";					
					break;
				case 3: 
					mes[contador] = "Março";					
					break;
				case 4: 
					mes[contador] = "Abril";					
					break;
				case 5: 
					mes[contador] = "Maio";					
					break;
				case 6: 
					mes[contador] = "Junho";					
					break;
				case 7: 
					mes[contador] = "Julho";					
					break;
				case 8: 
					mes[contador] = "Agosto";					
					break;
				case 9: 
					mes[contador] = "Setembro";					
					break;
				case 10: 
					mes[contador] = "Outubro";					
					break;
				case 11: 
					mes[contador] = "Novembro";					
					break;
				case 12: 
					mes[contador] = "Dezembro";					
				break;
				}
			}

		} catch (SQLException erro) {
			erro.printStackTrace();
		}
		
		return mes;
	}
	
	public String[] ConsultaAno(String tabela){
		
		TestarConexao();
		
		String ano[] = {};
			
		try {			
			Statement stmt = con.createStatement();	
			
			if(tabela == "Despesa")			
				sql = "SELECT COUNT(DISTINCT YEAR(data_desp)) FROM vwDespesas";
			else
				sql = "SELECT COUNT(DISTINCT YEAR(data_pag)) FROM vwPagamentos";
			
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();

			ano = new String[rs.getInt(1)];
			
			if(tabela == "Despesa")			
				sql = "SELECT DISTINCT YEAR(data_desp) FROM vwDespesas";
			else
				sql = "SELECT DISTINCT YEAR(data_pag) FROM vwPagamentos";
						
			rs = stmt.executeQuery(sql);

			for (int contador = 0; (rs.next()); contador++) {
				int nAno = rs.getInt(1);
				
				ano[contador] = String.valueOf(nAno);
			}

		} catch (SQLException erro) {
			erro.printStackTrace();
		}
		
		return ano;
	}
	
	public JRResultSetDataSource DadosRelatorio(String sql){
		
		TestarConexao();
		
		JRResultSetDataSource jrRS = null;
				
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			jrRS = new JRResultSetDataSource(rs);
		} catch (SQLException erro){
			erro.printStackTrace();
		}
		
		return jrRS;
	}
	
}