import javax.swing.event.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;

public class DialogAdministracion extends JDialog{
	private JButton aceptar;
	private JLabel titulo, genero, ejemplo, definicion, tipo, falso, cantidadContactos, imagen;
	private JList lista;
	private JTextField palabra1;
	private JPanel panelCentro, panelSur, panelNorte, panel1, panel2, panel3, panel4, panel5;
	private JFrame ventana;
	private JRadioButton masculino, femenino;
	private JTextArea ejemplo1, definicion1;
	private ButtonGroup grupoCheck;
	private JScrollPane scroll;
	private DefaultListModel listModel;
	private DefaultTableModel tablaModel;
	private JTable tabla;
	
	public DialogAdministracion(JFrame ventana, String tittle, boolean comportamiento){
		super(ventana, tittle, comportamiento);
		this.ventana = ventana;
		GUI();
		init();
	}
	
	public void init(){
		setSize(850,650);
		setResizable(false); 
		setLocationRelativeTo(ventana);
	}
	
	public void GUI(){
		
		aceptar = new JButton("Aceptar");
		aceptar.setBackground(new Color(139,195,74));
		
		titulo = new JLabel("Historial de eventos(CRUD)", JLabel.CENTER);
		titulo.setIcon(new ImageIcon("icons/vema3.png"));
		titulo.setForeground(new Color (31,111,191));
		titulo.setFont(new Font ("Times New Roman", 2, 40));
		
		JLabel separador = new JLabel("________________________________________________________________________________", JLabel.CENTER);
		separador.setForeground(new Color (139,195,74));
		
		panelNorte = new JPanel(new GridLayout(2,1,0,-20));
		panelNorte.add(titulo);
		panelNorte.add(separador);
		
		String titulo [] = {"Palabra","Acci\u00f3n","IP","Responsable","Fecha"};
		tablaModel = new DefaultTableModel(null, titulo);
		
		tabla = new JTable(tablaModel);
		
		
		BaseDatos gestor = new BaseDatos();
		gestor.historial(tabla, tablaModel);

		
		tabla.setFont(new Font ("Courier New", 4, 20));
		tabla.setEnabled(false);
		TableColumnModel columnModel = tabla.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(280);
		columnModel.getColumn(1).setPreferredWidth(100);
		columnModel.getColumn(2).setPreferredWidth(180);
		columnModel.getColumn(3).setPreferredWidth(180);
		columnModel.getColumn(4).setPreferredWidth(250);
		tabla.setRowHeight(20);
		tabla.setAutoResizeMode(tabla.AUTO_RESIZE_OFF);

		scroll = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JTableHeader  tableHeader =  tabla.getTableHeader();
		tableHeader.setFont(new Font("Courier New", 4, 20));
		tableHeader.setForeground(new Color(31,111,191));
		
		panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
		panelSur.setBackground(new Color(31,111,191));
		panelSur.add(aceptar);
		
		DialogEvento evento = new DialogEvento(this);
		aceptar.addActionListener(evento);

		add(scroll, BorderLayout.CENTER);
		add(panelSur, BorderLayout.SOUTH);
		add(panelNorte, BorderLayout.NORTH);
	}
}