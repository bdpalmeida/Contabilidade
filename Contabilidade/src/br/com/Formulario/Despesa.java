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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import br.com.Banco_Dados.Conexao_MySQL;
import br.com.Modelo.AdicionarDespesa;

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
public class Despesa extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblIcon;
	private JLabel lblData;
	private JLabel lblNome;
	private JLabel lblEntrada;
	private JLabel lblSaida;	
	private JLabel lblTotal;
	private JSpinner sDataDespesa;
	private JTextField txtEntrada;
	private JTextField txtTotal;
	private JTextField txtSaida;
	private JTextField txtNome;
	private JLabel lblTipo;
	private JComboBox<String> cbxTipo;
	private double entrada = 0, saida = 0, total = 0;
	
	private Conexao_MySQL con_MySQL = new Conexao_MySQL();
	private AdicionarDespesa modelo = new AdicionarDespesa();

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
			Despesa dialog = new Despesa();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Despesa() {
		initialize();
	}
	private void initialize() {
		setType(Type.UTILITY);
		setTitle("Adicionar Despesa");
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 452);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		lblIcon = new JLabel("");
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon.setIcon(new ImageIcon(Despesa.class.getResource("/br/com/Imagem/despesa.png")));
		
		lblTipo = new JLabel("Tipo:");
		lblTipo.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		
		cbxTipo = new JComboBox<String>(con_MySQL.ExibeTipoDespesa());
		cbxTipo.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		
		lblData = new JLabel("Data:");
		lblData.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		sDataDespesa = new JSpinner();
		sDataDespesa.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		sDataDespesa.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
		
		lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		txtNome = new JTextField();
		txtNome.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		txtNome.setColumns(10);
		
		lblEntrada = new JLabel("Entrada:");
		lblEntrada.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		txtEntrada = new JTextField();
		txtEntrada.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				txtEntrada.setText(NumberFormat.getCurrencyInstance().format(
						Double.parseDouble(txtEntrada.getText().trim().replace("R$ ", "").replace(",", "."))));
				
				try {
					total = NumberFormat.getNumberInstance().parse("0" + 
									txtSaida.getText().replace("R$ ", "")).doubleValue() - 
							NumberFormat.getNumberInstance().parse("0" + 
							txtEntrada.getText().replace("R$ ", "")).doubleValue();
				} catch (ParseException erro) {
					erro.printStackTrace();
				}				
				
				txtTotal.setText(NumberFormat.getCurrencyInstance().format(total));
			}
		});
		txtEntrada.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		txtEntrada.setColumns(10);
		
		lblSaida = new JLabel("Saída:");		
		lblSaida.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		txtSaida = new JTextField();
		txtSaida.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				txtSaida.setText(NumberFormat.getCurrencyInstance().format(
						Double.parseDouble(txtSaida.getText().trim().replace("R$ ", "").replace(",", "."))));	
				
				try {
					total = NumberFormat.getNumberInstance().parse("0" + 
								txtSaida.getText().replace("R$ ", "")).doubleValue() - 
							NumberFormat.getNumberInstance().parse("0" + 
									txtEntrada.getText().replace("R$ ", "")).doubleValue();
				} catch (ParseException erro) {
					erro.printStackTrace();
				}				
				
				txtTotal.setText(NumberFormat.getCurrencyInstance().format(total));
			}
		});
		txtSaida.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		txtSaida.setColumns(10);	
		
		lblTotal = new JLabel("Total:");		
		lblTotal.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		txtTotal = new JTextField();
		txtTotal.setEditable(false);
		txtTotal.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		txtTotal.setColumns(10);	
		
				GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(lblIcon, GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTipo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbxTipo, 0, 436, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblEntrada)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtEntrada, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblSaida)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtSaida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(21))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblTotal)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtTotal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(25)))
							.addPreferredGap(ComponentPlacement.RELATED, 260, Short.MAX_VALUE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblNome)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtNome, GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblData)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sDataDespesa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 292, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblIcon)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTipo)
						.addComponent(cbxTipo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblData)
						.addComponent(sDataDespesa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNome)
						.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEntrada)
						.addComponent(txtEntrada, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSaida)
						.addComponent(txtSaida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTotal)
						.addComponent(txtTotal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(107, Short.MAX_VALUE))
		);
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
							modelo.setTipo(cbxTipo.getSelectedItem().toString());
							modelo.setData((Date) sDataDespesa.getValue());
							modelo.setNome(txtNome.getText().trim());
							modelo.setEntrada(entrada);
							modelo.setSaida(saida);
							modelo.setTotal(total);
							
							con_MySQL.AdicionarDespesa(modelo.getTipo(), modelo.getData(), modelo.getNome(), modelo.getEntrada(), modelo.getSaida(), 
									modelo.getTotal());
							
							dispose();
						}
					}
					
					private boolean CampoValido(){
						try {
							entrada = NumberFormat.getNumberInstance().parse("0" + 
									txtEntrada.getText().replace("R$ ", "")).doubleValue();
							saida = NumberFormat.getNumberInstance().parse("0" + 
									txtSaida.getText().replace("R$ ", "")).doubleValue();
						} catch (ParseException erro) {
							erro.printStackTrace();
						}
						
						if(txtNome.getText().trim().length() < 3){
							JOptionPane.showMessageDialog(null, "Preencha o nome corretamente.", "Campo incorreto!", JOptionPane.ERROR_MESSAGE);
								return false;
						}
						else
							if(txtEntrada.getText().trim().length() < 1){
								JOptionPane.showMessageDialog(null, "Preencha a entrada corretamente.", "Campo incorreto!", JOptionPane.ERROR_MESSAGE);
								return false;
							}
							else
								if(txtSaida.getText().trim().length() < 1){
									JOptionPane.showMessageDialog(null, "Preencha a saída corretamente.", "Campo incorreto!", JOptionPane.ERROR_MESSAGE);
									return false;
								}
								else
									return true;						
					}
				});
				btnAdicionar.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
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
				buttonPane.add(btnFechar);
			}
		}
	}
}