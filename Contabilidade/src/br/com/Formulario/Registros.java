package br.com.Formulario;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import br.com.Banco_Dados.Conexao_MySQL;

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

@SuppressWarnings("serial")
public class Registros extends JDialog {

	private JPanel contentPane;
	private JLabel lblIcon;
	private JComboBox<String> cbxRegistro;
	private JScrollPane scrollPane;
	private JLabel lblRegistro;
	private JTable tableRegistros;
	private JButton btnFechar;
	
	private String tabelas[] = {"Despesa", "Pagamento"};
	private String linhas[][] = null, 
					colunasDespesa[] = {"Código", "Data", "Tipo", "Nome", "Entrada", "Saída", "Total"},
					colunasPagamentos[] = {"Código", "Data", "Lançamento", "Valor", "Descrição"};
	
	private Conexao_MySQL con_MySQL = new Conexao_MySQL();
	private JLabel lblSaldo;
	private JTextField txtSaldo;
	private JButton btnExcluirLinha;

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
					Registros frame = new Registros();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Registros() {
		initialize();
	}
	private void initialize() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setType(Type.UTILITY);
		setTitle("Registros");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lblIcon = new JLabel("");
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon.setIcon(new ImageIcon(Registros.class.getResource("/br/com/Imagem/tabela.png")));
		
		cbxRegistro = new JComboBox<String>(tabelas);
		cbxRegistro.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getItem() == "Pagamento"){
					linhas = con_MySQL.ConsultaTabelaPagamento();
					CarregarTabela(colunasPagamentos);
					txtSaldo.setText(String.valueOf(con_MySQL.getSaldo()));
				}
				else{
					linhas = con_MySQL.ConsultaTabelaDespesa();
					CarregarTabela(colunasDespesa);
					txtSaldo.setText(String.valueOf(con_MySQL.getSaldo()));
				}
			}
		});
		cbxRegistro.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		
		scrollPane = new JScrollPane();
		
		lblRegistro = new JLabel("Registro:");
		lblRegistro.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		
		btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnFechar.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		
		lblSaldo = new JLabel("Saldo:");
		lblSaldo.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblSaldo.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		
		txtSaldo = new JTextField();
		txtSaldo.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSaldo.setEditable(false);
		txtSaldo.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		txtSaldo.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		txtSaldo.setColumns(10);
		
		linhas = con_MySQL.ConsultaTabelaDespesa();		
		
		CarregarTabela(colunasDespesa);
		
		txtSaldo.setText(String.valueOf(con_MySQL.getSaldo()));
		
		btnExcluirLinha = new JButton("Excluir linha");
		btnExcluirLinha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con_MySQL.ExcluirLinha(cbxRegistro.getSelectedItem().toString(), 
						Integer.parseInt(linhas[tableRegistros.getSelectedRow()][0]));
				if(cbxRegistro.getSelectedItem().toString() == "Pagamento"){
					linhas = con_MySQL.ConsultaTabelaPagamento();
					CarregarTabela(colunasPagamentos);
					txtSaldo.setText(String.valueOf(con_MySQL.getSaldo()));
				}
				else{
					linhas = con_MySQL.ConsultaTabelaDespesa();
					CarregarTabela(colunasDespesa);
					txtSaldo.setText(String.valueOf(con_MySQL.getSaldo()));
				}
			}
		});
		btnExcluirLinha.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE)
						.addComponent(lblIcon, GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblRegistro)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbxRegistro, 0, 708, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnExcluirLinha)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnFechar))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblSaldo)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtSaldo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblIcon)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbxRegistro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRegistro))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtSaldo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSaldo))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnFechar)
						.addComponent(btnExcluirLinha))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void CarregarTabela(String colunas[]) {		
		tableRegistros = new JTable(linhas, colunas){
		    public boolean isCellEditable(int rowIndex, int vColIndex) {
	             return false;
		    }
		};
		tableRegistros.getTableHeader().setReorderingAllowed(false);
		tableRegistros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableRegistros.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		tableRegistros.setShowVerticalLines(true);
		tableRegistros.setShowHorizontalLines(true);
		tableRegistros.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		if(colunas == colunasDespesa){
			TableColumn col1 = tableRegistros.getColumnModel().getColumn(1);
			TableColumn col2 = tableRegistros.getColumnModel().getColumn(2);
			TableColumn col3 = tableRegistros.getColumnModel().getColumn(3);	
			TableColumn col4 = tableRegistros.getColumnModel().getColumn(4);
			TableColumn col5 = tableRegistros.getColumnModel().getColumn(5);
			TableColumn col6 = tableRegistros.getColumnModel().getColumn(6);
		
			col1.setPreferredWidth(170);
			col1.setResizable(false);
			col2.setPreferredWidth(250);
			col2.setResizable(false);
			col3.setPreferredWidth(250);
			col3.setResizable(false);
			col4.setPreferredWidth(150);
			col4.setResizable(false);
			col5.setPreferredWidth(150);
			col5.setResizable(false);
			col6.setPreferredWidth(200);
			col6.setResizable(false);
		}
		else{
			TableColumn col1 = tableRegistros.getColumnModel().getColumn(1);
			TableColumn col2 = tableRegistros.getColumnModel().getColumn(2);
			TableColumn col3 = tableRegistros.getColumnModel().getColumn(3);	
			TableColumn col4 = tableRegistros.getColumnModel().getColumn(4);
		
			col1.setPreferredWidth(170);
			col1.setResizable(false);
			col2.setPreferredWidth(250);
			col2.setResizable(false);
			col3.setPreferredWidth(150);		
			col4.setPreferredWidth(600);
			col4.setResizable(false);			
		}	
		
		tableRegistros.getColumnModel().getColumn( 0 ).setMaxWidth( 0 );  
	    tableRegistros.getColumnModel().getColumn( 0 ).setMinWidth( 0 );
		tableRegistros.getTableHeader().getColumnModel().getColumn( 0 ).setMaxWidth( 0 );  
	    tableRegistros.getTableHeader().getColumnModel().getColumn( 0 ).setMinWidth( 0 );	      
		
		scrollPane.setViewportView(tableRegistros);		
	}
}
