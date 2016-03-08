import javax.swing.*;
import java.awt.*;

public class DialogAcercaDe extends JDialog{
	
	private JPanel panelSur, panelNorte;
	private JTextArea informacion;
	private JLabel titulo, separador;
	private JButton aceptar;
	private JFrame ventana;
	
	public DialogAcercaDe (JFrame ventana, String title, boolean comportamiendo){
		super(ventana, title, comportamiendo);
		this.ventana = ventana;
		GUI();
		init();
	}
	
	public void init(){
		setSize(500,440);
		setResizable(false); 
		setLocationRelativeTo(ventana);
	}
	
	public void GUI(){
		aceptar = new JButton("Aceptar");
		aceptar.setBackground(new Color(139,195,74));
		
		titulo = new JLabel("VMETA Dictionary", new ImageIcon("icons/vema3.png"), JLabel.CENTER);
		titulo.setForeground(new Color (31,111,191));
		titulo.setFont(new Font ("Times New Roman", 2, 40));
		
		separador = new JLabel("___________________________________________________", JLabel.CENTER);
		separador.setForeground(new Color (139,195,74));
		
		informacion = new JTextArea("\tVMETA  \n\tVersi\u00f3n 1.0 (compilaci\u00f3n 0000)" + 
												"\n\t\u00a9 2015 VMETASoft Corporaci\u00f3n. Todos los derechos reservados." +
												"\n\n\tGestor de contactos y su interfaz de usuario est\u00e1n protegidos" + 
												"\n\tpor las leyes de marcas comercial y otros derechos de propiedad" + 
												"\n\tintelectual actuales y pendientes en la Rep\u00fablica Dominica y otros" + 
												"\n\tpa\u00edses o regiones." + 
												"\n\n\tProgramado por:" +
												"\n\tAngel Torres" + 
												"\n\tLeandro Jim\u00e9nez" + 
												"\n\tEmmanuel Jimenez" + 
												"\n\tManuel Olega" + 
												"\n\tJos\u00e9 Aquino" + 
												"\n\n\tAsignado por Keneth Aponte." + 
												"\n\tLicencia concedida bajo software libre.");
		
		informacion.setBackground(new Color(238, 238, 238));
		informacion.setFont(new Font ("Arial", 2, 13));
		informacion.setEditable(false);
		
		panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
		panelSur.add(aceptar);
		panelNorte = new JPanel(new GridLayout(2,1,0,-20));
		panelNorte.add(titulo);
		panelNorte.add(separador);
		
		DialogEvento evento = new DialogEvento(this);
		aceptar.addActionListener(evento);
		
		add(panelNorte, BorderLayout.NORTH);
		add(panelSur, BorderLayout.SOUTH);
		add(informacion, BorderLayout.CENTER);
	}
}