import javax.swing.*;
import java.awt.*;

public class Login extends JDialog{

	private JFrame ventana;
	private JButton aceptar, cancelar;
	private JPanel sur, primer, segundo, centro;
	private JLabel usuarioLabel, claveLabel, logo;
	private JTextField usuario;
	private JPasswordField clave;

	public Login(JFrame ventana, String tittle, boolean comportamiento){
		super(ventana, tittle, comportamiento);
		this.ventana = ventana;
		GUI();
		init();
	}
	
	public void init(){
		setSize(400, 220);
		setResizable(false);
		setLocationRelativeTo(ventana);
	}
	
	public void GUI(){
		aceptar = new JButton("Aceptar");
		aceptar.setActionCommand("AceptarLogin");
		aceptar.setBackground(new Color(139,195,74));
		
		cancelar = new JButton("Cancelar");
		cancelar.setBackground(new Color(139,195,74));
		
		sur = new JPanel();
		sur.setBackground(new Color (31,111,191));
		sur.add(aceptar);
		sur.add(cancelar);
		
		usuarioLabel = new JLabel("Administrador: ");
		usuarioLabel.setFont(new Font ("Arial", 1, 17)); 
		
		claveLabel = new JLabel("Contrase\u00f1a:    ");
		claveLabel.setFont(new Font ("Arial", 1, 17)); 
		
		usuario = new JTextField(15);
		usuario.setFont(new Font ("Arial", 4, 17)); 
		
		clave = new JPasswordField(15);
		clave.setFont(new Font ("Arial", 4, 17)); 
		
		primer = new JPanel();
		primer.add(usuarioLabel);
		primer.add(usuario);
		
		segundo = new JPanel();
		segundo.add(claveLabel);
		segundo.add(clave);
		
		logo = new JLabel(new ImageIcon("icons/login.png"));
		
		centro = new JPanel(new GridLayout(2,1));
		centro.add(primer);
		centro.add(segundo);
		
		DialogEvento evento = new DialogEvento(ventana, this, usuario, clave);
		aceptar.addActionListener(evento);
		cancelar.addActionListener(evento);
		usuario.addKeyListener(evento);
		clave.addKeyListener(evento);
		
		add(logo, BorderLayout.NORTH);
		add(sur, BorderLayout.SOUTH);
		add(centro, BorderLayout.CENTER);

	}
}