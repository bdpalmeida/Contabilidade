package br.com.Modelo;

import java.util.Date;

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

public class AdicionarPagamento {
	
	private Date data;
	private String lancamento, descricao;
	private double valor;
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getLancamento() {
		return lancamento;
	}
	public void setLancamento(String lancamento) {
		this.lancamento = lancamento;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}

}
