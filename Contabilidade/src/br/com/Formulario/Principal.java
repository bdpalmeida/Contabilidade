package br.com.Formulario;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.UIManager;

import br.com.Banco_Dados.Conexao_MySQL;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

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

public class Principal {

	private JFrame frmPrincipal;
	private JLabel lblNome;
	private JLabel lblIcon;
	private JButton btnPagamento;
	private JMenuBar menuBar;
	private JMenu mnTipoDespesa;
	private JMenuItem mntmAdicionar;
	private JButton btnRegistros;
	private JButton btnDespesa;
	
	private Conexao_MySQL con_MySQL = new Conexao_MySQL();
	private JMenu mnImprimir;
	private JMenuItem mntmRegistros;
	private JMenuItem mntmAlterar;
	private JMenu mnAjuda;
	private JMenuItem mntmSobreContabilidade;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.frmPrincipal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		con_MySQL.TestarConexao();
		
		frmPrincipal = new JFrame();
		frmPrincipal.setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/br/com/Imagem/registro.png")));
		frmPrincipal.setTitle("Contabilidade");
		frmPrincipal.setMinimumSize(new Dimension(800, 700));
		frmPrincipal.setLocationRelativeTo(null);
		frmPrincipal.setExtendedState(Frame.MAXIMIZED_BOTH);
		frmPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		lblNome = new JLabel("Contabilidade");
		lblNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblNome.setFont(new Font("Lucida Sans", Font.BOLD, 50));
		
		lblIcon = new JLabel("");
		lblIcon.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon.setIcon(new ImageIcon(Principal.class.getResource("/br/com/Imagem/registro.png")));
		
		btnPagamento = new JButton("Pagamento");
		btnPagamento.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPagamento.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPagamento.setIcon(new ImageIcon(Principal.class.getResource("/br/com/Imagem/pagamento.png")));
		btnPagamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Pagamento().setVisible(true);
			}
		});
		btnPagamento.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
		
		btnDespesa = new JButton("Despesa");
		btnDespesa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Despesa().setVisible(true);
			}
		});
		btnDespesa.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnDespesa.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDespesa.setIcon(new ImageIcon(Principal.class.getResource("/br/com/Imagem/despesa.png")));
		btnDespesa.setMaximumSize(new Dimension(100, 100));
		btnDespesa.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
		
		btnRegistros = new JButton("Registros");
		btnRegistros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Registros().setVisible(true);
			}
		});
		btnRegistros.setHorizontalTextPosition(SwingConstants.CENTER);
		btnRegistros.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnRegistros.setIcon(new ImageIcon(Principal.class.getResource("/br/com/Imagem/tabela.png")));
		btnRegistros.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
		
		GroupLayout groupLayout = new GroupLayout(frmPrincipal.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNome, GroupLayout.DEFAULT_SIZE, 1169, Short.MAX_VALUE)
						.addComponent(lblIcon, GroupLayout.DEFAULT_SIZE, 1169, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnRegistros, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDespesa, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPagamento, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(7)
							.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 110, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblIcon, GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnPagamento, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnDespesa, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnRegistros, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGap(0))
		);
		frmPrincipal.getContentPane().setLayout(groupLayout);
		
		menuBar = new JMenuBar();
		menuBar.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		frmPrincipal.setJMenuBar(menuBar);
		
		mnTipoDespesa = new JMenu("Tipo de Despesa");
		mnTipoDespesa.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		menuBar.add(mnTipoDespesa);
		
		mntmAdicionar = new JMenuItem("Adicionar");
		mntmAdicionar.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		mntmAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TipoDespesa().setVisible(true);
			}
		});
		mnTipoDespesa.add(mntmAdicionar);
		
		mntmAlterar = new JMenuItem("Alterar");
		mntmAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TipoDespesa().setVisible(true);
			}
		});
		mntmAlterar.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		mnTipoDespesa.add(mntmAlterar);
		
		mnImprimir = new JMenu("Imprimir");
		mnImprimir.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		menuBar.add(mnImprimir);
		
		mntmRegistros = new JMenuItem("Registros");
		mntmRegistros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Imprimir().setVisible(true);
			}
		});
		mntmRegistros.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		mnImprimir.add(mntmRegistros);
		
		mnAjuda = new JMenu("Ajuda");
		mnAjuda.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		menuBar.add(mnAjuda);
		
		mntmSobreContabilidade = new JMenuItem("Sobre Contabilidade");
		mntmSobreContabilidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, 
						"Contabilidade\n\n"
						+ "Versão 1.0\n"
						+ "A Licença MIT (MIT)\n"
						+ "Copyright (c) 2016 Brenno Dário Pimenta de Almeida\n"
						+ "A permissão é concedida, gratuitamente, a qualquer pessoa que obtenha uma cópia deste \n"
						+ "software e dos arquivos de documentação associados (o \"Software\"), para lidar com \n"
						+ "o Software sem restrições, incluindo, sem limitação, os direitos de usar, copiar, \n"
						+ "modificar, mesclar , publicar, distribuir, sub-licenciar e/ou vender cópias do \n"
						+ "Software, e para permitir que as pessoas a quem o Software é fornecido a fazê-lo, \n"
						+ "sujeito às seguintes condições: \n\n"
						+ "O aviso de copyright acima e este aviso de permissão \n"
						+ "devem ser incluídos em todas as cópias ou partes substanciais do Software. \n\n"
						+ "O SOFTWARE É FORNECIDO \"COMO ESTÁ\", SEM GARANTIA DE QUALQUER TIPO, expressa ou \n"
						+ "implícita, INCLUINDO, SEM LIMITAÇÃO, AS GARANTIAS DE COMERCIALIZAÇÃO, ADEQUAÇÃO A \n"
						+ "UM DETERMINADO FIM E NÃO VIOLAÇÃO. EM NENHUM CASO OS AUTORES ou direitos de autor \n"
						+ "DETENTORES DE SER RESPONSÁVEL POR QUALQUER RECLAMAÇÃO, DANOS OU OUTRA RESPONSABILIDADE, \n"
						+ "SEJA EM UMA AÇÃO DE CONTRATO, DELITO OU DE OUTRA FORMA, DECORRENTES DE,OU EM CONEXÃO \n"
						+ "COM O SOFTWARE OU O USO OU OUTRA APLICAÇÃO DO PROGRAMAS.\n\n"
						+ "Projeto do software disponível no GitHub: https://github.com/bdpalmeida/Contabilidade.git",
						"Sobre Contabilidade", JOptionPane.PLAIN_MESSAGE, null);
			}
		});
		mntmSobreContabilidade.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		mnAjuda.add(mntmSobreContabilidade);
	}
}
