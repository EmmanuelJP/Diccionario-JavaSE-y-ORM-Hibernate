import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.JPopupMenu;
import javax.swing.DefaultListModel;

import javax.swing.KeyStroke;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.net.*;
import java.io.*;

public class Diccionario extends JFrame implements Runnable{
	
	private JPanel arriba, panelCentral, panelIzquierdo, panelIzquierdoAbajo, panelDerecho, panelCentralDerecho, panelCentralDerechoDefinicion;
	private JMenu menu, buscar;
	private JMenuBar barraMenu;
	private JMenuItem  inicio, guardar, nuevo, editar, borrar,administrar, acercade, ayuda;
	private JCheckBoxMenuItem mostrar;
	private JList lista;
	private JScrollPane scroll, scroll2;
	private JTextPane definicion;
	private JLabel titulo, vema, logo, encontrar;
	private JTextField barraBuscar;
	private JPopupMenu menuSecundario;
	private DefaultListModel listModel;
	
	private ServerSocket servidor;
	private Socket leo, aquino, oleaga, angel, leandro;
	private DataInputStream flujo;
	private DataOutputStream resp;
	private String msg;
	
	public Diccionario(String title){
		super(title);
		GUI();
		
		Image imagen = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/vema.png"));
		setIconImage(imagen);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000,700);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}
	
	public void GUI(){
		inicio = new JMenuItem("Inicio", new ImageIcon("icons/inicio.png"));
		inicio.setFont(new Font ("Courier new", 1, 15));
		inicio.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		
		guardar = new JMenuItem("Nuevo", new ImageIcon("icons/add.png"));
		guardar.setFont(new Font ("Courier new", 1, 15));
		guardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		
		nuevo = new JMenuItem("Nuevo", new ImageIcon("icons/add.png"));
		nuevo.setFont(new Font ("Courier new", 1, 15));
		
		editar = new JMenuItem("Editar", new ImageIcon("icons/edit.png"));
		editar.setFont(new Font ("Courier new", 1, 15));
		borrar = new JMenuItem("Borrar", new ImageIcon("icons/delete.png"));
		borrar.setFont(new Font ("Courier new", 1, 15));
		
		acercade = new JMenuItem("Acerca de", new ImageIcon("icons/acercade.png"));
		acercade.setFont(new Font ("Courier new", 1, 15));
		acercade.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		
		ayuda = new JMenuItem("Ayuda", new ImageIcon("icons/help.png"));
		ayuda.setFont(new Font ("Courier new", 1, 15));
		ayuda.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		
		administrar = new JMenuItem("Administrar", new ImageIcon("icons/administrator.png"));
		administrar.setFont(new Font ("Courier new", 1, 15));
		administrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		
		JCheckBoxMenuItem mostrar = new JCheckBoxMenuItem("Mostrar", new ImageIcon("icons/show.png"));
		mostrar.setFont(new Font ("Courier new", 1, 15));
		mostrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
		
		buscar = new JMenu("Barra buscar");
		buscar.setMnemonic(KeyEvent.VK_W);
		buscar.setIcon(new ImageIcon("icons/search.png"));
		buscar.setFont(new Font ("Courier new", 1, 15));
		buscar.add(mostrar);
		
		menuSecundario = new JPopupMenu();
		menuSecundario.add(nuevo);
		menuSecundario.add(editar);
		menuSecundario.add(borrar);
		
		menu = new JMenu();
		menu.setMnemonic(KeyEvent.VK_M);
		menu.setIcon(new ImageIcon("icons/menu.png"));
		menu.add(inicio);
		menu.add(guardar);
		menu.addSeparator();
		menu.add(buscar);
		menu.addSeparator();
		menu.add(administrar);
		menu.add(ayuda);
		menu.add(acercade);
		
		barraMenu = new JMenuBar();
		barraMenu.add(menu);
		barraMenu.setBackground(new Color(31,111,191));
		barraMenu.setBorder(BorderFactory.createLineBorder(new Color(31,111,191)));
		
		arriba = new JPanel();
		arriba.setBackground(new Color(31,111,191));
		arriba.add(barraMenu);
//----------------------------------------------------------------------------------------------------------------------------------------------------------
		titulo = new JLabel("Glosario", JLabel.CENTER);
		titulo.setFont(new Font("Arial", 2, 25));
		
		
		panelIzquierdo = new JPanel(new BorderLayout());
		panelIzquierdo.setBackground(Color.WHITE);
		
		lista = new JList<DefaultListModel>();
		
		BaseDatos gestor = new BaseDatos(lista, listModel);
		gestor.reset();
		
		lista.setFont(new Font ("Comic Sans MS", 3, 20)); 
		lista.setSelectionBackground(new Color(31,111,191));
		// lista.setBackground(new Color(139, 195, 74));
		//lista.setListModel(palabras);
		scroll = new JScrollPane(lista);
		
		
				//------------------------------------------------------------------------------------------------------------------------------------------
		encontrar = new JLabel("Buscar: ");
		encontrar.setFont(new Font ("Arial", 1, 17)); 
		barraBuscar = new JTextField(15);
		barraBuscar.setFont(new Font ("Arial", 2, 17));
		
		panelIzquierdoAbajo = new JPanel();
		panelIzquierdoAbajo.setVisible(false);
		panelIzquierdoAbajo.setBackground(new Color(31,111,191));
		panelIzquierdoAbajo.add(encontrar);
		panelIzquierdoAbajo.add(barraBuscar);
		
		
		panelIzquierdo.add(scroll, BorderLayout.CENTER);
		panelIzquierdo.add(titulo, BorderLayout.NORTH);
		panelIzquierdo.add(panelIzquierdoAbajo, BorderLayout.SOUTH);
//------------------------------------------------------------------------------------------------------------------------------------------------------------
		//definicion = new JLabel("Objetivo: Al finalizar el curso, el alumno, conocerá las características de las principales drogas, factores causales, prevalencia de consumo y conceptos asociados, y sobre todo tendrá una visión general e integral que le permitirá comprender el complejo problema de las adicciones.");
		
		//scroll2 = new JScrollPane(definicion);
		// panelCentralDerechoDefinicion = new JPanel(new GridLayout(2,1));
		// panelCentralDerechoDefinicion.add(definicion);
		definicion = new JTextPane();
		definicion.setContentType("text/html");
		definicion.setBackground(Color.WHITE);
		definicion.setEditable(false);
		definicion.setText("<html><body><font face=\"Arial\" size=5><p style='margin: 60px; text-align: center; color:#1F6FBF'>Bienvenido a VMETA Dictionary donde podr&aacute;s encontrar t&eacute;rminos, definicionones y ejemplos de tu inter&eacute;s.</p></font</body></html>"); 

		
		logo = new JLabel(new ImageIcon("icons/logo.png"));
		
		panelCentralDerecho = new JPanel(new GridLayout(2,1));
		panelCentralDerecho.setBackground(Color.WHITE);
		//panelCentralDerecho.add(panelCentralDerechoDefinicion);
		panelCentralDerecho.add(definicion);
		panelCentralDerecho.add(logo);
		
		vema = new JLabel("VMETA Corp.", JLabel.CENTER);
		vema.setFont(new Font("Arial", 2, 25));
		
		panelDerecho = new JPanel(new BorderLayout());
		panelDerecho.setBackground(Color.WHITE);
		panelDerecho.add(vema, BorderLayout.NORTH);
		panelDerecho.add(panelCentralDerecho, BorderLayout.CENTER);
		
		
//-------------------------------------------------------------------------------------------------------------------------------------------------------------
		panelCentral= new JPanel(new GridLayout(1,2));
		panelCentral.add(panelIzquierdo);
		panelCentral.add(panelDerecho);
		
		ManejoEvento evento = new ManejoEvento(this, panelIzquierdoAbajo, lista, menuSecundario, definicion, vema, logo, listModel, barraBuscar);
		acercade.addActionListener(evento);
		inicio.addActionListener(evento);
		guardar.addActionListener(evento);
		nuevo.addActionListener(evento);
		administrar.addActionListener(evento);
		editar.addActionListener(evento);
		borrar.addActionListener(evento);
		ayuda.addActionListener(evento);
		mostrar.addItemListener(evento);
		
		barraBuscar.addCaretListener(evento);
		
		lista.addMouseListener(evento);
		vema.addMouseListener(evento);
		definicion.addMouseListener(evento);
		titulo.addMouseListener(evento);
		arriba.addMouseListener(evento);
		logo.addMouseListener(evento);
		panelIzquierdoAbajo.addMouseListener(evento);
		
		DialogEvento evento2  = new DialogEvento();
		evento2.sLetras(barraBuscar, this);
		
		
		add(panelCentral, BorderLayout.CENTER);
		add(arriba, BorderLayout.NORTH);
		//add(logo, BorderLayout.NORTH);
		Thread hilo = new Thread(this);
		hilo.start();
	}
	
	
	public static void main(String [] args){
		JFrame ventana = new Diccionario("VMETA Dictionary");
	}
	
	public void run(){
		try {
			
		
			while(true){
			 servidor = new ServerSocket(5000);
				leo = servidor.accept();
				flujo = new DataInputStream(leo.getInputStream());
				
				msg = flujo.readUTF();
				
				leo.close();
				// System.out.println(leo.getRemoteSocketAddress());
				// System.out.println(leo.getLocalAddress());
				// System.out.println(leo.getInetAddress());
				servidor.close(); 
				
				if (msg.equalsIgnoreCase("Actualizar")){
					System.out.println("Mensaje resibido: "+msg + " ip: " +leo.getInetAddress());
					BaseDatos bd = new BaseDatos(lista, listModel);
						bd.reset();	
						//bd.historial();
						enviarRespuesta();
				}
			}
		}catch(Exception e){
			System.out.println("Problemas al enviar y recibir por socket en Diccionario...");
			e.printStackTrace();
		}
		
	}
	public void enviarRespuesta(){
		try{
					// Socket aquino = new Socket("192.168.59.128", 7070);
					// DataOutputStream esp = new DataOutputStream(aquino.getOutputStream());
					// esp.writeUTF("Actualizar");
					// aquino.close();	
			
					// Socket oleaga = new Socket("10.0.0.7", 7070);
					// DataOutputStream esp3 = new DataOutputStream(oleaga.getOutputStream());
					// esp3.writeUTF("Actualizar");
					// oleaga.close();	
					
				
					Socket angel = new Socket("192.168.1.103", 5000);
					DataOutputStream esp4 = new DataOutputStream(angel.getOutputStream());
					esp4.writeUTF("Actualizar");
					angel.close();	

					// Socket leandro = new Socket("10.0.0.3",7070);
					// DataOutputStream esp2 = new DataOutputStream(leandro.getOutputStream());
					// esp2.writeUTF("Actualizar");
					// leandro.close();					
		}catch(Exception ex){
			
		}
					
	}
}

