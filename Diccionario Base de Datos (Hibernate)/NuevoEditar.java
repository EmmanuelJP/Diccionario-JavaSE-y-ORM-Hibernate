import javax.swing.*;
import java.awt.*;

public class NuevoEditar extends JDialog{
	private JButton guardar, cancelar;
	private JLabel palabra, genero, ejemplo, definicion, tipo, falso, cantidadContactos, imagen, vema, logo;
	private JList lista;
	private JTextField palabra1;
	private JPanel panelCentro, panelSur, panelNorte, panel1, panel2, panel3, panel4, panel5;
	private JFrame ventana;
	private JRadioButton masculino, femenino;
	private JTextArea ejemplo1, definicion1;
	private ButtonGroup grupoCheck;
	private JScrollPane scrollEjemplo, scrollDefinicion;
	private DefaultListModel listModel;
	private String palabraClick, def, ejemp, gene;
	private JTextPane definicionPane;
	
	public NuevoEditar(JFrame ventana, String title, JList lista, DefaultListModel listModel, JLabel vema, JLabel logo, JTextPane definicionPane){
		super(ventana, title , true);
		this.ventana = ventana;
		this.lista = lista;
		this.listModel = listModel;
		this.vema = vema;
		this.logo = logo;
		this.definicionPane = definicionPane;
		nuevoGUI();
		instanciaNuevo();
	}
	
	public NuevoEditar(JFrame ventana, String title, JList lista, DefaultListModel listModel, String palabraClick, JLabel vema, JLabel logo, JTextPane definicionPane){
		super(ventana, title , true);
		this.ventana = ventana;
		this.lista = lista;
		this.listModel = listModel;
		this.palabraClick = palabraClick;
		this.vema = vema;
		this.logo = logo;
		this.definicionPane = definicionPane;
		llenadoVariables();
		nuevoGUI();
		editarGUI();
		instanciaEditar();
	}
	
	public void nuevoGUI(){
		guardar = new JButton("Guardar");
		guardar.setIcon(new ImageIcon("icons/Guardar.png"));
		guardar.setBackground(new Color(139,195,74));
		
		cancelar = new JButton("Cancelar");
		cancelar.setIcon(new ImageIcon("icons/Cancelar.png"));
		cancelar.setBackground(new Color(139,195,74));
		
		panelSur = new JPanel();
		panelSur.setBackground(new Color(31,111,191));
		panelSur.add(guardar);
		panelSur.add(cancelar);
		
		palabra = new JLabel("Palabra: ", JLabel.LEFT);
		palabra.setFont(new Font ("Arial", 1, 17)); 
		
		genero = new JLabel("G\u00e9nero:       ", JLabel.LEFT);
		genero.setFont(new Font ("Arial", 1, 17)); 
		
		ejemplo = new JLabel("Ejemplo:  ", JLabel.LEFT);
		ejemplo.setFont(new Font ("Arial", 1, 17)); 
		
		
		definicion = new JLabel("Definici\u00f3n:  ", JLabel.LEFT);
		definicion.setFont(new Font ("Arial", 1, 17)); 
		
		imagen = new JLabel(new ImageIcon("icons/vema2.png"));
		
		palabra1 = new JTextField("", 30);
		palabra1.setBorder(BorderFactory.createLineBorder(new Color(31,111,191)));
		palabra1.setFont(new Font ("Arial", 2, 15));
		
		masculino = new JRadioButton("Masculino    ", new ImageIcon("icons/check.png"));
		femenino = new JRadioButton("Femenino     ", new ImageIcon("icons/uncheck.png"));
		masculino.setFont(new Font ("Arial", 2, 15));
		femenino.setFont(new Font ("Arial", 2, 15));
		
		grupoCheck = new ButtonGroup();
		grupoCheck.add(femenino);
		grupoCheck.add(masculino);
		
		ejemplo1 = new JTextArea("", 4,30);
		ejemplo1.setBorder(BorderFactory.createLineBorder(new Color(31,111,191)));
		ejemplo1.setFont(new Font ("Arial", 2, 15));
		ejemplo1.setLineWrap(true); 
		ejemplo1.setWrapStyleWord(true);
		
		scrollEjemplo = new JScrollPane(ejemplo1);
		scrollEjemplo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		definicion1 = new JTextArea("", 4,30);
		definicion1.setBorder(BorderFactory.createLineBorder(new Color(31,111,191)));
		definicion1.setFont(new Font ("Arial", 2, 15));
		definicion1.setLineWrap(true); 
		definicion1.setWrapStyleWord(true);
		
		scrollDefinicion = new JScrollPane(definicion1);
		scrollDefinicion.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		panelNorte = new JPanel();
		panelNorte.add(imagen);
		
		panel1 = new JPanel();
		panel1.add(palabra);
		panel1.add(palabra1);
		
		panel2 = new JPanel();
		panel2.add(genero);
		panel2.add(masculino);
		panel2.add(femenino);
		
		panel3 = new JPanel();
		panel3.add(ejemplo);
		panel3.add(scrollEjemplo);
		
		panel4 = new JPanel();
		panel4.add(definicion);
		panel4.add(scrollDefinicion);
		
		panelCentro = new JPanel (new GridLayout(4, 1, 0 , 10));
		panelCentro.add(panel1);
		panelCentro.add(panel4);
		panelCentro.add(panel3);
		panelCentro.add(panel2);
		
		DialogEvento evento2  = new DialogEvento();
		evento2.sLetras(palabra1, this);
		
		add(panelCentro, BorderLayout.CENTER);
		add(panelSur, BorderLayout.SOUTH);
		add(panelNorte, BorderLayout.NORTH);
		
		setSize(450,690);
		setResizable(false); 
		setLocationRelativeTo(ventana);
	}
	
	public void editarGUI(){
		palabra1.setText(palabraClick);
		definicion1.setText(def);
		ejemplo1.setText(ejemp);
		guardar.setActionCommand("Editar");
		
		if(gene.equals("Masculino")){
			masculino.setIcon( new ImageIcon("icons/check.png"));
			femenino.setIcon( new ImageIcon("icons/uncheck.png"));
		}else if (gene.equals("Femenino")){
			masculino.setIcon( new ImageIcon("icons/uncheck.png"));
			femenino.setIcon( new ImageIcon("icons/check.png"));
		}
	}
	
	public void instanciaEditar(){
		DialogEvento evento = new DialogEvento(this, palabra1, ejemplo1, definicion1, masculino, femenino,lista, listModel, palabraClick, vema, logo, definicionPane);
		cancelar.addActionListener(evento);
		guardar.addActionListener(evento);
		masculino.addActionListener(evento);
		femenino.addActionListener(evento);
	}
	
	public void instanciaNuevo(){
		DialogEvento evento = new DialogEvento(this, palabra1, ejemplo1, definicion1, masculino, femenino,lista, listModel, vema, logo, definicionPane);
		cancelar.addActionListener(evento);
		guardar.addActionListener(evento);
		masculino.addActionListener(evento);
		femenino.addActionListener(evento);
	}
	
	public void llenadoVariables(){
		BaseDatos gestor = new BaseDatos(lista, listModel);
		
		String datos [] = new String [3];
		datos = gestor.mostrar(palabraClick);
		
		def = datos[0];
		ejemp = datos[1];
		gene = datos[2];
	}
}