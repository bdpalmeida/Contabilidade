package br.com.Formulario;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.Banco_Dados.Conexao_MySQL;
import br.com.Modelo.AdicionarTipoDespesa;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
public class TipoDespesa extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblNome;
	private JTextField txtNome;

	//private Conexao_MySQL con_MySQL = new Conexao_MySQL();
	private Conexao_MySQL con_MySQL = new Conexao_MySQL();
	
	private AdicionarTipoDespesa modelo = new AdicionarTipoDespesa();

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
			TipoDespesa dialog = new TipoDespesa();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TipoDespesa() {
		initialize();
	}
	private void initialize() {
		setResizable(false);
		setType(Type.UTILITY);
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Adicionar Tipo de Despesa");
		setBounds(100, 100, 416, 237);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblIcon = new JLabel("");
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon.setIcon(new ImageIcon(TipoDespesa.class.getResource("/br/com/Imagem/tipodespesa.png")));
		
		lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		
		txtNome = new JTextField();
		txtNome.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		txtNome.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(lblIcon, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblNome)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtNome, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblIcon)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNome)
						.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(176, Short.MAX_VALUE))
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
							modelo.setNome(txtNome.getText().trim());
							
							if(con_MySQL.AdicionarTipoDespesa(modelo.getNome()))
								LimparCampo();
						}
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
				
				JButton btnAlterar = new JButton("Alterar");
				btnAlterar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(CampoValido()){
							modelo.setNome(txtNome.getText().trim());
							
							if(con_MySQL.ConsultaExisteTabelaTipoDespesa(modelo.getNome())){
								JOptionPane.showMessageDialog(null, "Já existe um tipo de despesa com este nome.", "Tipo de despesa existente", JOptionPane.ERROR_MESSAGE);
							}		
							else{								
								JComboBox<String> tipo = new JComboBox<String>(con_MySQL.ExibeTipoDespesa());
								int selecionado = JOptionPane.showOptionDialog(null, tipo,
										"Tipo de despesa anterior", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
										null, new String[] {"Cancelar", "Confirmar"}, "Cancelar");
								
								if( selecionado == 1 )							
									if(con_MySQL.AlterarTipoDespesa(modelo.getNome(), tipo.getSelectedItem().toString()))
										LimparCampo();
							}							
						}						
					}
				});
				btnAlterar.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
				buttonPane.add(btnAlterar);
				btnFechar.setActionCommand("Cancel");
				buttonPane.add(btnFechar);
			}
		}
	}
	
	private boolean CampoValido(){
		if(txtNome.getText().trim().length() < 3){
			JOptionPane.showMessageDialog(null, "Preencha o nome corretamente.", "Campo incorreto!", JOptionPane.ERROR_MESSAGE);
				return false;
		}
		else
			return true;
	}
	
	private void LimparCampo(){
		txtNome.setText("");
	}
}
