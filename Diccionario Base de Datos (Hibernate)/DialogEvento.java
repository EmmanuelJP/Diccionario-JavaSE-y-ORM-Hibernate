import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.net.*;
import java.io.DataOutputStream;


public class DialogEvento extends KeyAdapter implements ActionListener{
	
	private JFrame ventana;
	private JDialog dialogo;
	private JTextArea definicion, ejemplo;
	private JTextField palabra, usuario;
	private JRadioButton masculino, femenino;
	private String genero = "Masculino";
	private DefaultListModel listModel;
	private JList lista;
	private JPasswordField clave;
	private String palabraClick;
	private JLabel vema, logo;
	private JTextPane definicion1;
	public BaseDatos gestor;
	
	private Socket cliente, cliente2, cliente3, cliente4;
	private DataOutputStream flujo, flujo2, flujo3, flujo4;
	
	public DialogEvento(JDialog dialogo, JTextField palabra, JTextArea ejemplo, JTextArea definicion, JRadioButton masculino, JRadioButton femenino, JList lista, DefaultListModel listModel, JLabel vema, JLabel logo, JTextPane definicion1){
		this.dialogo = dialogo;
		this.palabra = palabra;
		this.definicion = definicion;
		this.ejemplo = ejemplo;
		this.masculino = masculino;
		this.femenino = femenino;
		this.lista = lista;
		this.listModel = listModel;
		this.vema = vema;
		this.logo = logo;
		this.definicion1 = definicion1;
		gestor =  new BaseDatos(lista, listModel);
	}	
	
	public DialogEvento(JDialog dialogo, JTextField palabra, JTextArea ejemplo, JTextArea definicion, JRadioButton masculino, JRadioButton femenino, JList lista, DefaultListModel listModel, String palabraClick,  JLabel vema, JLabel logo, JTextPane definicion1){
		this.dialogo = dialogo;
		this.palabra = palabra;
		this.definicion = definicion;
		this.ejemplo = ejemplo;
		this.masculino = masculino;
		this.femenino = femenino;
		this.lista = lista;
		this.listModel = listModel;
		this.palabraClick = palabraClick;
		this.vema = vema;
		this.logo = logo;
		this.definicion1 = definicion1;
		gestor = new BaseDatos(lista, listModel);
	}
	
	public DialogEvento(JFrame ventana, JDialog dialogo, JTextField usuario, JPasswordField clave){
		this.ventana = ventana;
		this.dialogo = dialogo;
		this.usuario = usuario;
		this.clave = clave;
	}
	
	public DialogEvento(JDialog dialogo){
		this.dialogo = dialogo;
	}
	
	public DialogEvento(){
	}
	
	public void actionPerformed(ActionEvent e){
		String label = e.getActionCommand();
		
		if(label.equals("AceptarLogin")){
			login();
		}
		
		if(label.equals("Aceptar")){
			dialogo.hide();
		}
		
		if(label.equals("Cancelar")){
			dialogo.hide();
		}
		if(label.equals("Guardar")){
			//try{	
				restriccion(palabra, definicion);
				if (!palabra.getText().equals("")  && !definicion.getText().equals("")){
					String complemento = palabra.getText();
					complemento = complemento.replaceFirst( complemento.charAt(0)+"", (String.valueOf(palabra.getText().charAt(0))).toUpperCase());
					
					gestor.guardar(complemento, definicion.getText(), genero, ejemplo.getText());
					vema.setText("VMETA Corp.");
					definicion1.setText("<html><body><font face=\"Arial\" size=5><p style='margin: 60px; text-align: center; color:#1F6FBF'>Bienvenido a VMETA Dictionary donde podr&aacute;s encontrar t&eacute;rminos, definicionones y ejemplos de tu inter&eacute;s.</p></font</body></html>"); 
					logo.setIcon(new ImageIcon("icons/logo.png"));
					dialogo.hide();	
					socket();
				}
		}
		
		if(label.equals("Editar")){
				
			restriccion(palabra, definicion);
			if (!palabra.getText().equals("")  && !definicion.getText().equals("")){
				String complemento = palabra.getText();
				complemento = complemento.replaceFirst( complemento.charAt(0)+"", (String.valueOf(palabra.getText().charAt(0))).toUpperCase());
				
				gestor.editar(palabraClick, complemento, definicion.getText(), genero, ejemplo.getText());
				vema.setText("VMETA Corp.");
				definicion1.setText("<html><body><font face=\"Arial\" size=5><p style='margin: 60px; text-align: center; color:#1F6FBF'>Bienvenido a VMETA Dictionary donde podr&aacute;s encontrar t&eacute;rminos, definicionones y ejemplos de tu inter&eacute;s.</p></font</body></html>"); 
				logo.setIcon(new ImageIcon("icons/logo.png"));
				dialogo.hide();
				socket();
				}
		}
		
		if(e.getSource() == masculino){
			genero = "Masculino";
			masculino.setIcon(new ImageIcon("icons/check.png"));
			femenino.setIcon(new ImageIcon("icons/uncheck.png"));
		}
		if(e.getSource() == femenino){
			genero = "Femenino";
			femenino.setIcon(new ImageIcon("icons/check.png"));
			masculino.setIcon(new ImageIcon("icons/uncheck.png"));
		}
	}
	
	public void keyPressed(KeyEvent e){
		int a = e.getKeyCode();
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			login();
		}
	}
	
	public void restriccion(JTextField palabra, JTextArea definicion){
		if(palabra.getText().equals("")  && definicion.getText().equals("")){
			dialogo.getToolkit().beep();
			JOptionPane.showMessageDialog(dialogo, "Los campos Palabra y Definici\u00f3n son obligatorios para ejecutar esta acci\u00f3n.", "Advertencia",JOptionPane.WARNING_MESSAGE);
		}
		
		else if(palabra.getText().equals("")){
			dialogo.getToolkit().beep();
			JOptionPane.showMessageDialog(dialogo, "El campo Palabra es obligatorio para ejecutar esta acci\u00f3n.", "Advertencia",JOptionPane.WARNING_MESSAGE,  new ImageIcon("Advertencia.png"));
		}
		
		else if(definicion.getText().equals("")){
			dialogo.getToolkit().beep();
			JOptionPane.showMessageDialog(dialogo, "El campo Definici\u00f3n es obligatorio para ejecutar esta acci\u00f3n.", "Advertencia",JOptionPane.WARNING_MESSAGE,  new ImageIcon("Advertencia.png"));
		}
	}
	public void sLetras(JTextField a, final Component object){
		a.addKeyListener(new KeyAdapter() { 
		public void keyTyped(KeyEvent e){
			char c = e.getKeyChar();
			char espacios = e.getKeyChar();
			
			if(Character.isDigit(c)){
				object.getToolkit().beep();
				JOptionPane.showMessageDialog(object, "Lo sentimos no es posible insertar digitos en este campo.", "Aviso", JOptionPane.WARNING_MESSAGE);
				e.consume();
			}
			
			if(espacios == ' '){
				object.getToolkit().beep();
				JOptionPane.showMessageDialog(object, "Lo sentimos no se permiten espacios en este campo.", "Aviso", JOptionPane.WARNING_MESSAGE);
				e.consume();
			}
		}
		});
	}
	
	public void login(){
		if((usuario.getText().equals("vema") && clave.getText().equals("123")) || (usuario.getText().equals("admin") && clave.getText().equals("123"))){
			dialogo.hide();
			DialogAdministracion admin = new DialogAdministracion(ventana, "\u00c1rea de administraci\u00f3n", true);
			admin.show();
		}else{
			JLabel aviso = new JLabel("Acceso denegado, usuarios o clave incorrectos.");
			aviso.setFont(new Font ("Arial", 4, 15)); 
			JOptionPane.showMessageDialog(dialogo, aviso, "Aviso", JOptionPane.WARNING_MESSAGE, new ImageIcon("icons/sad.png"));
		}
	}
	
	public void socket(){
		try{
			cliente = new Socket("127.0.0.1",5000);
			DataOutputStream message = new DataOutputStream(cliente.getOutputStream());			
			message.writeUTF("Actualizar");
			cliente.close();
		}catch(Exception ex){
			System.out.println("Problemas al enviar por socket DialogEvento...");
			ex.printStackTrace();
			
		}
	}
}