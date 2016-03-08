import org.hibernate.SessionFactory;//interfaz esta clase me permite crear la factoria de sesiones
import org.hibernate.Session;//interfaz me permite abrir una de las sesiones que me creo la factoria
import org.hibernate.Transaction;//interfaz me permite utilizar las propiedades de las transacciones 
import org.hibernate.cfg.Configuration; //Clase que me permite configurar mi archivo xml
import org.hibernate.*;
import Modelo.Diccionario;
import Modelo.Acciones;
import org.hibernate.Query;

import java.util.List;
import java.util.TreeSet;

import java.io.Serializable;
import java.net.InetAddress;

import java.util.Date;
import java.text.DateFormat;    
import java.text.SimpleDateFormat;   

import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import javax.swing.event.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;


public class BaseDatos{
	
	public  Session session;
	public  Transaction transaction;

	private DefaultListModel listModel;
	private JList lista;
	
	public BaseDatos(JList lista, DefaultListModel listModel){
		this.lista = lista;
		this.listModel = listModel;
	}
	public BaseDatos(){
		
	}
	
private void startSession(){
		 
	try{
		
		session  = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		
	}catch(HibernateException ex){
		ex.printStackTrace();
	}
}

	private void manejarExcepcion(HibernateException ex) throws HibernateException {
		
		transaction.rollback();
		 throw new HibernateException("Ocurrió un error en la capa de acceso a datos", ex);
		
	}
	
	
	public void buscar(String palabra){
		
			try{
				startSession();
				Query sentencia  = session.createQuery("SELECT palabra from Diccionario WHERE palabra LIKE '%"+ palabra + "%'");
				List <String> lista3 = sentencia.list();
				
				TreeSet <String> lista1 = new TreeSet <>();
				
				for (String dc : lista3){
					lista1.add(dc);
				}
				
				 Iterator it = lista1.iterator();
				listModel = new DefaultListModel<String>();
				
				 while(it.hasNext()){
					 listModel.addElement(it.next());
				 }
				lista.setModel(listModel);
				
				transaction.commit();//cerrando la transacion		
				//s.disconnect();//desconectando la session
				
			}catch(HibernateException ex){
			manejarExcepcion(ex);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.disconnect();
		}
	}
	
	public void reset(){
		try{ 
			startSession();
			Query sentencia = session.createQuery("SELECT palabra from Diccionario");

			List <String> lista2 = sentencia.list();
			
			TreeSet <String> lista1 = new TreeSet <>();
			
			for (String dc : lista2){ //recorriendo la lista de id devueltos
				lista1.add(dc);
			}
			
			listModel = new DefaultListModel<String>();
			
			Iterator it = lista1.iterator();
			
			while(it.hasNext()){
				 listModel.addElement(it.next());
			}
			lista.setModel(listModel);
			
			transaction.commit();//cerrando la transacion		
			//s.disconnect();//desconec
		}catch(HibernateException ex){
			manejarExcepcion(ex);
			
		}finally{
			session.disconnect();
		}
	}
	
	public void eliminar(String palabra){
		
		try{
			startSession();
			Query sentencia = session.createQuery("Select id from Diccionario where palabra = '"+ palabra +"'"); // extrayendo id para eliminar la palabra
			List<Integer> lista = sentencia.list();	// enlistando los id de la sentencia
			
			for (int i: lista){ //recorriendo la lista de id devueltos
				Diccionario d1 = new Diccionario (i); // toma el id uno por uno para despues eliminarlo mediante la session
				session.delete(d1);// eliminando el registro mediante el el obejto id
			}	
			
			String nameCliente = InetAddress.getLocalHost().getHostName(); 
			String ipCliente = InetAddress.getLocalHost().getHostAddress();
			
				final String accion = "Elimin\u00f3";
				
				Date fecha = new Date();
				
				DateFormat formato = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
				
				Acciones eli1 = new Acciones(palabra, ipCliente, nameCliente, accion, formato.format(fecha));
				session.save(eli1);

			transaction.commit();//cerrando la transacion		
			
			reset();
		
		}catch(HibernateException ex){
			manejarExcepcion(ex);
		}catch(Exception e ){
			e.printStackTrace();
		}finally{
			session.disconnect();
		}
		
	}
	
	public void guardar(String palabra, String definicion, String genero, String ejemplo){
		try{
			startSession();
			
			Diccionario reg1 = new Diccionario(palabra, definicion, genero, ejemplo);
			session.save(reg1);
			
			String nameCliente= InetAddress.getLocalHost().getHostName(); 
			String ipCliente= InetAddress.getLocalHost().getHostAddress();
			final String accion = "Guard\u00f3";	
			Date fecha = new Date();
			DateFormat formato = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			//DateFormat formato = new SimpleDateFormat("hh:mm:ss yyyy-MM-dd ");
			
			Acciones guar = new Acciones(palabra, ipCliente, nameCliente, accion, formato.format(fecha));
			session.save(guar);
			
			transaction.commit();//cerrando la transacion		
			//s.disconnect();//desconectando la session
			reset();
		}catch(HibernateException ex){
			manejarExcepcion(ex);
			
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			session.disconnect();
		}
	}
	public void editar(String palabra_Vieja, String palabra, String definicion, String genero, String ejemplo){
		
		try{
			startSession();
			Query secuencia = session.createQuery("select id from Diccionario where palabra ='"+palabra_Vieja+"'");
			List<Integer> lista = secuencia.list();

			for (int i : lista){
				Diccionario actualizar = new Diccionario(i, palabra, definicion, genero, ejemplo);
				session.update(actualizar);
			}
			
			final String accion = "Edit\u00f3";
			Date fecha = new Date();
			DateFormat formato = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			
			String nameCliente= InetAddress.getLocalHost().getHostName(); 
			String ipCliente= InetAddress.getLocalHost().getHostAddress();
			
			Acciones edit = new Acciones(palabra_Vieja + " como " + palabra, ipCliente, nameCliente, accion, formato.format(fecha));
			session.save(edit);
			transaction.commit();
			reset(); 
		}catch(HibernateException ex){
			manejarExcepcion(ex);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.disconnect();
		}	
	}
	public void historial(JTable table, DefaultTableModel tableModel){
		try{
			startSession();
			Query query = session.createQuery("from Acciones");
			
			List <Acciones> lista = query.list();
			
			String [] fila = new String [5];
			
			for (Acciones e : lista ){
			
				fila [0] = e.getPalabra();
				fila [1] = e.getAccion();
				fila [2] = e.getip_Responsable();
				fila [3] = e.getnombre_Responsable();
				fila [4] = e.getFecha_Hora();
				tableModel.addRow(fila);
			}
			
			table.setModel(tableModel);
			transaction.commit();
			
		}catch(HibernateException ex ){
			manejarExcepcion(ex);
		}catch(Exception ex){
		
		}finally{
			session.disconnect();
		}
	
	}
	
	public String[] mostrar(String palabra){
		String [] datos = new String [3];
		try{ 
			startSession();
			Query sentencia = session.createQuery("from Diccionario where palabra ='"+palabra+"'");

			List <Diccionario> lista2 = sentencia.list();
			
			
			for (Diccionario dc : lista2){ //recorriendo la lista de id devueltos
				datos[0] = dc.getDefinicion();
				datos[1] = dc.getEjemplo();
				datos[2] = dc.getGenero();
			}
			
			
			transaction.commit();//cerrando la transacion		
			//s.disconnect();//desconec
			
		}catch(HibernateException ex){
			manejarExcepcion(ex);
			
		}finally{
			session.disconnect();
		}
		return datos;
	}

}