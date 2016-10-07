package br.com.Formulario;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import br.com.Banco_Dados.Conexao_MySQL;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

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
public class Imprimir extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblIcon, lblAno;
	private JComboBox<String> cbxTabela, cbxMes, cbxAno;
	private JCheckBox chckbxTodos;

	private String tabelas[] = {"Despesa","Pagamento"},
					meses[] = null, anos[] = null;
	
	private Conexao_MySQL con_MySQL = new Conexao_MySQL();
	
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
			Imprimir dialog = new Imprimir();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Imprimir() {
		initialize();
	}
	private void initialize() {
		setType(Type.UTILITY);
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Imprimir Registros");
		setBounds(100, 100, 416, 280);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		lblIcon = new JLabel("");
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon.setIcon(new ImageIcon(Imprimir.class.getResource("/br/com/Imagem/imprimir.png")));
		JLabel lblRegistro = new JLabel("Registro: ");
		lblRegistro.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		
		cbxTabela = new JComboBox<String>(tabelas);
		cbxTabela.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				meses = con_MySQL.ConsultaMes(cbxTabela.getSelectedItem().toString());
				cbxMes.removeAllItems();
				for (String mes : meses) {
					cbxMes.addItem(mes);
				}
				
				cbxAno.removeAllItems();
				anos = con_MySQL.ConsultaAno(cbxTabela.getSelectedItem().toString());
				for (String ano : anos) {
					cbxAno.addItem(ano);
				}
			}
		});
		cbxTabela.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		
		JLabel lblMes = new JLabel("Mês: ");
		lblMes.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		
		meses = con_MySQL.ConsultaMes(cbxTabela.getSelectedItem().toString());
		
		cbxMes = new JComboBox<String>(meses);
		cbxMes.setFont(new Font("Lucida Sans", Font.PLAIN, 15));

		lblAno = new JLabel("Ano: ");
		lblAno.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		
		anos = con_MySQL.ConsultaAno(cbxTabela.getSelectedItem().toString());
		
		cbxAno = new JComboBox<String>(anos);
		cbxAno.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		
		chckbxTodos = new JCheckBox("Todos");
		chckbxTodos.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(chckbxTodos.isSelected()){
					cbxMes.setEnabled(false);
					cbxAno.setEnabled(false);
				}
				else{
					cbxMes.setEnabled(true);
					cbxAno.setEnabled(true);
				}
			}
		});
		chckbxTodos.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addComponent(lblIcon, GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblRegistro)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbxTabela, 0, 332, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMes)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbxMes, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAno)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbxAno, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
					.addComponent(chckbxTodos)
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblIcon)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRegistro)
						.addComponent(cbxTabela, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblMes)
							.addComponent(cbxMes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(chckbxTodos))
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblAno)
							.addComponent(cbxAno, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnImprimir = new JButton("Imprimir");
				btnImprimir.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {						
						try {
							if(cbxTabela.getSelectedItem().toString() == "Despesa"){
								InputStream relatorio = Imprimir.class.getResourceAsStream("/br/com/Relatorio/RelatorioDespesa.jrxml");
								ImprimirRelatorio(relatorio);
								dispose();
							}
							else{
								InputStream relatorio = Imprimir.class.getResourceAsStream("/br/com/Relatorio/RelatorioPagamento.jrxml");
								ImprimirRelatorio(relatorio);
								dispose();
							}
						} catch (Exception erro) {
							erro.printStackTrace();
						}
					}
				});
				btnImprimir.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
				btnImprimir.setActionCommand("OK");
				buttonPane.add(btnImprimir);
				getRootPane().setDefaultButton(btnImprimir);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void ImprimirRelatorio(InputStream layout) throws JRException, SQLException, ClassNotFoundException {
		String tabela, mes="", ano, sql = null;
		
		DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance();
		JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.xpath.executer.factory",
		    "net.sf.jasperreports.engine.util.xml.JaxenXPathExecuterFactory");
		
		JasperDesign desenho = JRXmlLoader.load(layout);
		   
		JasperReport relatorio = JasperCompileManager.compileReport(desenho);
      			
		if(cbxTabela.getSelectedItem().toString() == "Despesa")
			tabela = "vwDespesas";
		else
			tabela = "vwPagamentos";
		
		if(chckbxTodos.isSelected())
			sql = "SELECT * FROM " + tabela;
		else{			
			switch(cbxMes.getSelectedItem().toString()){
			case "Janeiro":
				mes = "1";
				break;
			case "Fevereiro":
				mes = "2";
				break;
			case "Março":
				mes = "3";
				break;
			case "Abril":
				mes = "4";
				break;
			case "Maio":
				mes = "5";
				break;
			case "Junho":
				mes = "6";
				break;
			case "Julho":
				mes = "7";
				break;
			case "Agosto":
				mes = "8";
				break;
			case "Setembro":
				mes = "9";
				break;
			case "Outubro":
				mes = "10";
				break;
			case "Novembro":
				mes = "11";
				break;
			case "Dezembro":
				mes = "12";
				break;
			}			
			
			ano = String.valueOf(cbxAno.getSelectedItem());
			
			if(tabela == "vwDespesas")
				sql = "SELECT *, (SELECT ROUND(SUM(total)) FROM Despesa WHERE "
						+ "EXTRACT(MONTH FROM data_desp) = " + mes 
						+ "&& EXTRACT(YEAR FROM data_desp) = " + ano + ") AS totalDespesas "
						+ "FROM vwDespesas WHERE EXTRACT(MONTH FROM data_desp) = " + mes 
						+ " && EXTRACT(YEAR FROM data_desp) = " + ano;
			else
				sql = "SELECT *, (SELECT ROUND(SUM(valor)) FROM Pagamento WHERE "
						+ "EXTRACT(MONTH FROM data_pag) = " + mes 
						+ "&& EXTRACT(YEAR FROM data_pag) = " + ano + ") AS totalPagamentos "
						+ "FROM vwPagamentos WHERE EXTRACT(MONTH FROM data_pag) = " + mes 
						+ " && EXTRACT(YEAR FROM data_pag) = " + ano;
		}
		
		JasperPrint impressao = JasperFillManager.fillReport(relatorio, null, con_MySQL.DadosRelatorio(sql));
   
		JasperViewer viewer = new JasperViewer(impressao, false);
		
		viewer.setTitle("Relatório do registro " + cbxTabela.getSelectedItem().toString());		
		viewer.setLocationRelativeTo(null);
		viewer.setExtendedState(Frame.MAXIMIZED_BOTH);
		viewer.toFront();
		this.setVisible(false);
		viewer.setVisible(true);
	}
}
