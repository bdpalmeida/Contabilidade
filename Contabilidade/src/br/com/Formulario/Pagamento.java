package br.com.Formulario;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import br.com.Banco_Dados.Conexao_MySQL;
import br.com.Modelo.AdicionarPagamento;

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
public class Pagamento extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel labelIcon;
	private JSpinner sDataPagamento;
	private JLabel lblDescricao;
	private JTextField txtLancamento;
	private JTextField txtValor;
	private JScrollPane scrollPane;
	private JTextPane txtpnDescricao;
	private double valor = 0;
	
	
	private Conexao_MySQL con_MySQL = new Conexao_MySQL();
	private AdicionarPagamento modelo = new AdicionarPagamento();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			Pagamento dialog = new Pagamento();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Pagamento() {
		initialize();
	}
	private void initialize() {
		setType(Type.UTILITY);
		setModal(true);
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Adicionar Pagamento");
		setBounds(100, 100, 500, 400);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		labelIcon = new JLabel("");
		labelIcon.setHorizontalAlignment(SwingConstants.CENTER);
		labelIcon.setIcon(new ImageIcon(Pagamento.class.getResource("/br/com/Imagem/pagamento.png")));
		JLabel lblDataDoPagamento = new JLabel("Data do pagamento:");
		lblDataDoPagamento.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		
		sDataPagamento = new JSpinner();
		sDataPagamento.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		sDataPagamento.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
		
		JLabel lblLancamento = new JLabel("Lançamento:");
		lblLancamento.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		
		lblDescricao = new JLabel("Descricao:");
		lblDescricao.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescricao.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		
		txtLancamento = new JTextField();
		txtLancamento.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		txtLancamento.setColumns(10);
		
		txtValor = new JTextField();
		txtValor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				txtValor.setText(NumberFormat.getCurrencyInstance().format(
						Double.parseDouble(txtValor.getText().trim().replace("R$ ", "").replace(",", "."))));
			}
		});
		txtValor.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		txtValor.setColumns(10);
		
		scrollPane = new JScrollPane();
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(labelIcon, GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDataDoPagamento)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(sDataPagamento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(184, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblLancamento)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtLancamento, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblValor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtValor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(284, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDescricao, GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 478, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(labelIcon)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDataDoPagamento)
						.addComponent(sDataPagamento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLancamento)
						.addComponent(txtLancamento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblValor)
						.addComponent(txtValor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDescricao)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
		);
		
		txtpnDescricao = new JTextPane();
		txtpnDescricao.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		scrollPane.setViewportView(txtpnDescricao);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnAdicionar = new JButton("Adicionar");
				btnAdicionar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {						
						if(CampoValido()){
							modelo.setData((Date) sDataPagamento.getValue());
							modelo.setLancamento(txtLancamento.getText().trim());
							modelo.setValor(valor);
							modelo.setDescricao(txtpnDescricao.getText().trim());
							
							con_MySQL.AdicionarPagamento(modelo.getData(), modelo.getLancamento(), modelo.getValor(), 
									modelo.getDescricao());
							
							dispose();
						}						
					}
					
					private boolean CampoValido(){
						try {
							valor = NumberFormat.getNumberInstance().parse("0" + txtValor.getText().replace("R$ ", "")).doubleValue();
						} catch (ParseException erro) {
							erro.printStackTrace();
						}
						
						if(txtLancamento.getText().trim().length() < 3){
							JOptionPane.showMessageDialog(null, "Preencha o lançamento corretamente.", "Campo incorreto!", JOptionPane.ERROR_MESSAGE);
								return false;
						}
						else
							if(txtValor.getText().trim().length() < 1){
								JOptionPane.showMessageDialog(null, "Preencha o valor corretamente.", "Campo incorreto!", JOptionPane.ERROR_MESSAGE);
								return false;
							}
							else
								if( valor <= 0 ){
									JOptionPane.showMessageDialog(null, "Preencha o valor corretamente.", "Campo incorreto!", JOptionPane.ERROR_MESSAGE);
									return false;
								}
								else
									if(txtpnDescricao.getText().trim().length() < 1){
										JOptionPane.showMessageDialog(null, "Preencha a descrição corretamente.", "Campo incorreto!", JOptionPane.ERROR_MESSAGE);
										return false;
									}
									else
										return true;						
					}
				});
				btnAdicionar.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
				btnAdicionar.setActionCommand("OK");
				buttonPane.add(btnAdicionar);
				getRootPane().setDefaultButton(btnAdicionar);
			}
			{
				JButton btnFechar = new JButton("Fechar");
				btnFechar.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
				btnFechar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnFechar.setActionCommand("Cancel");
				buttonPane.add(btnFechar);
			}
		}
	}
}
