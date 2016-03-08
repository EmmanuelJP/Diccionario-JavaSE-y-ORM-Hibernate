import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JTextPane;
import javax.swing.JPopupMenu;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JMenuBar;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ImageIcon;

import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;

import java.net.*;
import java.io.DataOutputStream;




public class ManejoEvento extends MouseAdapter implements ActionListener, ItemListener, CaretListener{
	
	private JFrame ventana;
	private JPanel panelIzquierdoAbajo;
	private String palabraClick;
	private JPopupMenu menu;
	private JLabel vema, logo;
	private JTextPane definicion;
	private DefaultListModel listModel;
	private JList lista;
	private JTextField barraBuscar;
	public BaseDatos gestor;
	
	private Socket cliente, cliente2, cliente3, cliente4;
	private DataOutputStream flujo, flujo2, flujo3, flujo4;
	private Socket emma; 
	
	
	public ManejoEvento(JFrame ventana, JPanel panelIzquierdoAbajo, JList lista, JPopupMenu menu, JTextPane definicion, JLabel vema, JLabel logo,  DefaultListModel listModel, JTextField barraBuscar){
		this.ventana = ventana;
		this.panelIzquierdoAbajo = panelIzquierdoAbajo;
		this.menu = menu;
		this.definicion = definicion;
		this.vema = vema;
		this.logo = logo;
		this.lista = lista;
		this.listModel = listModel;
		this.barraBuscar = barraBuscar;
		gestor = new BaseDatos(lista, listModel);
		
	}
	
	public void caretUpdate(CaretEvent e){
		gestor.buscar(barraBuscar.getText());
		if(barraBuscar.getText().equals("")){
			gestor.reset();
		}
	}
	
	public void actionPerformed(ActionEvent e){
		String label = e.getActionCommand();
		lista.clearSelection();
		
		
		if(label.equals("Administrar")){
			Login login  = new Login(ventana, "Acceso", true);
			login.show();
		}
		if(label.equals("Acerca de")){
			DialogAcercaDe dialogo = new DialogAcercaDe(ventana, "Acerca de VMETA Dictionary", true);
			dialogo.show();
			
		}
		if(label.equals("Ayuda")){
			DialogAyuda dialogo = new DialogAyuda(ventana, "Ayuda y soporte de VMETA Dictionary", true);
			dialogo.show();
		}
		if(label.equals("Nuevo")){
			NuevoEditar agregar = new NuevoEditar(ventana, "Agragar nueva palabra",lista, listModel, vema, logo, definicion);
			agregar.show();
			
		}
		if(label.equals("Editar")){
			NuevoEditar agregar = new NuevoEditar(ventana, "Editar palabra", lista, listModel, palabraClick, vema, logo, definicion);
			agregar.show();
		}
		if(label.equals("Borrar")){
			int resp = JOptionPane.showConfirmDialog(ventana, "Est\u00e1s seguro que deseas borrar la palabra \"" + palabraClick + "\" de VMETA Dictionary ? \nSi lo haces no podr\u00e1s recuperar esta informaci\u00f3n nuevamente.", "Aviso", JOptionPane.YES_NO_OPTION);
			if (resp == JOptionPane.YES_OPTION){
				gestor.eliminar(palabraClick);	
				vema.setText("VMETA Corp.");
				definicion.setText("<html><body><font face=\"Arial\" size=5><p style='margin: 60px; text-align: center; color:#1F6FBF'>Bienvenido a VMETA Dictionary donde podr&aacute;s encontrar t&eacute;rminos, definicionones y ejemplos de tu inter&eacute;s.</p></font</body></html>"); 
				logo.setIcon(new ImageIcon("icons/logo.png"));
				socket();
			}
		}
		if(label.equals("Inicio")){
			vema.setText("VMETA Corp.");
			definicion.setText("<html><body><font face=\"Arial\" size=5><p style='margin: 60px; text-align: center; color:#1F6FBF'>Bienvenido a VMETA Dictionary donde podr&aacute;s encontrar t&eacute;rminos, definicionones y ejemplos de tu inter&eacute;s.</p></font</body></html>"); 
			logo.setIcon(new ImageIcon("icons/logo.png"));
		}
	}
	
	public void itemStateChanged(ItemEvent e){
		int check = e.getStateChange();
		lista.clearSelection();
		switch (e.getStateChange()){
			case ItemEvent.SELECTED: 
			panelIzquierdoAbajo.setVisible(true);  
			barraBuscar.requestFocus();
			break;
			
			case ItemEvent.DESELECTED: 
				panelIzquierdoAbajo.setVisible(false);
				barraBuscar.setText("");
				gestor.reset(); break;
		}
	}
	
	public void mouseClicked(MouseEvent e){  
	Object source = e.getSource();
	try{
		if (source instanceof JList){
			//lista = (JList) e.getSource(); 
			//
			palabraClick = "";
			
			if (e.getClickCount() == 2 || e.getClickCount() == 1){
				
				Object objeto = lista.getSelectedValue();
				palabraClick = objeto.toString();
				
				if (e.getClickCount() == 2){
					
					String datos [] = new String [3];
					String ejemplo = "";
					
					datos = gestor.mostrar(palabraClick);
					
					String def = datos[0];
					String ejemp = datos[1];
					String gene = datos[2];
					
					String texto = "<font size= 5><p>&nbsp; &nbsp; " + palabraClick +"</p></font><dl><dd><font face=\"Arial\" size=4><p style=' text-align: justify; color:#1F6FBF'>"+def+"</p></font></dd></dl>";
					
					if(!ejemp.equals("")){
						ejemplo = "<font size= 5><p>&nbsp; &nbsp; Ejemplo: </p></font><dl><dd><font face=\"Arial\" size=4><p style=' text-align: justify; color:#1F6FBF'>"+ejemp+"</p></font></dd></dl>";
					}
					
					String genero = "<font size= 5><p>&nbsp; &nbsp; G\u00e9nero: </p></font><dl><dd><font face=\"Arial\" size=4><p style=' text-align: justify; color:#1F6FBF'>"+gene+"</p></font></dd></dl>";
					
					vema.setText("Definici\u00f3n");
					definicion.setText("<html><body>"+ texto + ejemplo + genero + "</body></html>"); 
					logo.setIcon(new ImageIcon("icons/learning.png"));
				}
			}
		}
		else{
			lista.clearSelection();
			palabraClick  = "";
		}
		}catch(Exception error){
			
		}
	}
	 public void mousePressed(MouseEvent e) {
		 Object source = e.getSource();
		 try{
		 if (source instanceof JList){
			 
			int index = lista.locationToIndex(e.getPoint());
			
			if(index >= 0){
				lista.setSelectedIndex(index);
				Object objeto = lista.getSelectedValue();
				palabraClick = objeto.toString();
			}
			 
			if (e.isPopupTrigger() && !palabraClick.equals("")) {
				menu.show(e.getComponent(), e.getX(), e.getY());
			}
		 }
		}catch(Exception error){
			
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		Object source = e.getSource();
		try{
		if (source instanceof JList){
			
			if (e.isPopupTrigger() && !palabraClick.equals("")) {
				int index = lista.locationToIndex(e.getPoint());
				if(index >= 0){
					lista.setSelectedIndex(index);
					Object objeto = lista.getSelectedValue();
					palabraClick = objeto.toString();
				}
				menu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
		}catch(Exception error){
			
		}
	}
	public void socket(){
		try{
			emma = new Socket("127.0.0.1",5000);
			DataOutputStream message = new DataOutputStream(emma.getOutputStream());			
			message.writeUTF("Actualizar");
			emma.close();
		}catch(Exception ex){
			System.out.println("Problemas al enviar por socket DialogEvento...");
			
		}
	}
	
	// public void valueChanged(ListSelectionEvent e){ 
		// if(e.getSource() == lista){ 
			// System.out.println("Item seleccionado: " + lista.getSelectedIndex()); } 
		
	// } 
}