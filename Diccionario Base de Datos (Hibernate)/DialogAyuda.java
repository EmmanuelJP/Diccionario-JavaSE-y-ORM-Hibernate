import javax.swing.*;
import java.awt.*;

public class DialogAyuda extends JDialog{
	
	private JFrame ventana;
	private JButton aceptar;
	private JLabel titulo;
	private JTextPane definicion, informacion;
	private JScrollPane scroll;
	private JPanel panelSur, panelNorte;
	
	public DialogAyuda (JFrame ventana, String title, boolean comportamiendo){
		super(ventana, title, comportamiendo);
		this.ventana = ventana;
		GUI();
		init();
	}
	
	public void init(){
		setSize(550,420);
		setResizable(false); 
		setLocationRelativeTo(ventana);
	}
	
	public void GUI(){
		aceptar = new JButton("Aceptar");
		aceptar.setBackground(new Color(139,195,74));
		
		titulo = new JLabel("Dictionary: sugerencias m\u00e1s frecuentes", new ImageIcon("icons/idea.png"), JLabel.CENTER);
		titulo.setForeground(new Color (31,111,191));
		titulo.setFont(new Font ("Times New Roman", 2, 24));
		
		definicion = new JTextPane();
		definicion.setContentType("text/html");
		definicion.setBackground(new Color(238, 238, 238));
		definicion.setEditable(false);
		String objetivo = "Dictionary es una aplicaci\u00f3n que permite realizar b\u00faquedas de palabras que se encuentran almacenadas alfab\u00e9ticamente.";
		definicion.setText("<html><body><font size=\"4\" face=\"Courier new\"><p style='margin: 2px; text-align: center;'>&nbsp;&nbsp;"+objetivo+"</p></font</body></html>");
		
		informacion = new JTextPane();
		informacion.setContentType("text/html");
		informacion.setBackground(new Color(238, 238, 238));
		informacion.setEditable(false);
		String ayuda = "<dl><dt><b>Para agregar: </b></dt><dd>Hacer click en men&uacute;, dir&iacute;jase a Nuevo y complete los campos requiridos.</dd><dt><b>Para Editar: </b></dt><dd>Hacer click secundario sobre la palabra, luego saldr&aacute; un men&uacute; donde podr&aacute;s escoger la opci&oacute;n editar.</dd><dt><b>Para Eliminar: </b></dt><dd>Hacer click secundario sobre la palabra, luego saldr&aacute; un men&uacute; donde podr&aacute;s escoger la opci&oacute;n eliminar.</dd><dt><b>Para Activar la Barra de Buscar: </b></dt><dd>Hacer click en men&uacute;, dir&iacute;jase a \"Barra Buscar\" y de click en mostrar, luego su barra de \"Buscar\" aparecer&aacute; debajo de las palabras.</dd></dl>";
		informacion.setText("<html><body><font face=\"Arial\">"+ ayuda +"</font</body></html>"); 
		
		
		scroll = new JScrollPane(informacion, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBorder(BorderFactory.createLineBorder(new Color(31,111,191)));
		scroll.setBackground(Color.BLUE);
		
		panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
		panelSur.add(aceptar);
		
		panelNorte = new JPanel(new GridLayout(2,1));
		panelNorte.add(titulo);
		panelNorte.add(definicion);
		
		JLabel labelDerecho = new JLabel("               ");
		JLabel labelIzquiero = new JLabel("               ");
		
		
		DialogEvento evento = new DialogEvento(this);
		aceptar.addActionListener(evento);
		
		
		add(panelNorte, BorderLayout.NORTH);
		add(panelSur, BorderLayout.SOUTH);
		add(scroll, BorderLayout.CENTER);
		add(labelDerecho, BorderLayout.WEST);
		add(labelIzquiero, BorderLayout.EAST);
		
		// aceptar.addActionListener(new ActionListener(){
			// public void actionPerformed(ActionEvent e){
				// String label = e.getActionCommand();
				// if(label.equals("salir")){
					// help.hide();
				// }
			// }
		// });
	}
	
}