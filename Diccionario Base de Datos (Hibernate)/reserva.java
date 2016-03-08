import org.hibernate.SessionFactory;//interfaz esta clase me permite crear la factoria de sesiones
import org.hibernate.Session;//interfaz me permite abrir una de las sesiones que me creo la factoria
import org.hibernate.Transaction;//interfaz me permite utilizar las propiedades de las transacciones 
import org.hibernate.cfg.Configuration; //Clase que me permite configurar mi archivo xml

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


public class BaseDatos{
	
	public static Session s;
	public static Transaction ts;
	public static String nameCliente;
	public static String ipCliente;
	
	private DefaultListModel listModel;
	private JList lista;
	
	
	{
		try {
			s = new Configuration().configure("cnfghibernate.hbm.xml").buildSessionFactory().openSession();
			ts = s.beginTransaction();
			nameCliente= InetAddress.getLocalHost().getHostName(); 
			ipCliente= InetAddress.getLocalHost().getHostAddress();
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
	
	public BaseDatos(JList lista, DefaultListModel listModel){
		this.lista = lista;
		this.listModel = listModel;
		//configuracion();
	}
	public BaseDatos(){
		
			
			
		
		
		//configuracion();
	}
	
	
	// public final static void configuracion(){	

		// try {
			// s= new Configuration().configure("cnfghibernate.hbm.xml").buildSessionFactory().openSession();
			// ts = s.beginTransaction();
			// nameCliente= InetAddress.getLocalHost().getHostName(); 
			// ipCliente= InetAddress.getLocalHost().getHostAddress();
			
		// }catch(Exception e){
			// e.printStackTrace();
			// System.out.println(e.getMessage());
		// }
		
	// }
	public void buscar(String palabra){
		// configuracion();
			try{
				Query sentencia  = s.createQuery("SELECT palabra from Diccionario WHERE palabra LIKE'%"+ palabra + "%'");
				List <String> lista = sentencia.list();
				
				TreeSet <String> lista1 = new TreeSet <>();
				
				for (String dc : lista){
					lista1.add(dc);
				}
				
				 Iterator it = lista1.iterator();
				
				 while(it.hasNext()){
					
				 }
				
				
				ts.commit();//cerrando la transacion		
				//s.disconnect();//desconectando la session
				
			}
			catch(Exception e ){
				e.printStackTrace();	
			}
	}
	
	public void reset(){
		try{
			// configuracion();
			
			Query sentencia = s.createQuery("from Diccionario");
			
			//TreeSet <Diccionario> lista = new TreeSet ((TreeSet)sentencia);
			List <Diccionario> lista2 = sentencia.list();
			
			TreeSet <String> lista1 = new TreeSet <>();
			
			for (Diccionario dc: lista2){ //recorriendo la lista de id devueltos
				lista1.add(dc.getPalabra());
			}
			listModel = new DefaultListModel<String>();
			
			Iterator it = lista1.iterator();
			
			while(it.hasNext()){
				 listModel.addElement(it.next());
			}
			lista.setModel(listModel);
			
			ts.commit();//cerrando la transacion		
			//s.disconnect();//desconec
		}catch(Exception e){
			
		}
	}
	
	public void eliminar(String palabra){
		// configuracion();
		try{
			Query sentencia = s.createQuery("Select id from Diccionario where palabra = '"+ palabra +"'"); // extrayendo id para eliminar la palabra
			List<Integer> lista = sentencia.list();	// enlistando los id de la sentencia
			
			
			
			for (int i: lista){ //recorriendo la lista de id devueltos
				Diccionario d1 = new Diccionario (i); // toma el id uno por uno para despues eliminarlo mediante la session
				s.delete(d1);// eliminando el registro mediante el el obejto id
				
			}	
			
				final String accion = "Elimin\u00f3";
				Date fecha = new Date();
				DateFormat formato = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
				
				Acciones eli1 = new Acciones(palabra, ipCliente, nameCliente, accion, formato.format(fecha));
				s.save(eli1);
		
			
			ts.commit();//cerrando la transacion		
			//s.disconnect();//desconectando la session
			reset();
		
		}catch(Exception e){
			
		}
		
	}
	
	public void guardar(String palabra, String definicion, String genero, String ejemplo){
		// configuracion();	
		try{
			Diccionario reg1 = new Diccionario(palabra, definicion, genero, ejemplo);
			s.save(reg1);
			
			
			final String accion = "Guard\u00f3";	
			Date fecha = new Date();
			DateFormat formato = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			//DateFormat formato = new SimpleDateFormat("hh:mm:ss yyyy-MM-dd ");
			
			Acciones guar = new Acciones(palabra, ipCliente, nameCliente, accion, formato.format(fecha));
			s.save(guar);
			
			ts.commit();//cerrando la transacion		
			//s.disconnect();//desconectando la session
			reset();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void editar(String palabra_Vieja, String palabra, String definicion, String genero, String ejemplo){
		// configuracion();
		try{
		Query secuencia = s.createQuery("select id from Diccionario where palabra ='"+palabra_Vieja+"'");
		List<Integer> lista = secuencia.list();
		
		
		for (int i : lista){
			Diccionario actualizar = new Diccionario(i, palabra, definicion, genero, ejemplo);
			s.update(actualizar);
		}
			final String accion = "Edit\u00f3";
			Date fecha = new Date();
			DateFormat formato = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			
			Acciones edit = new Acciones(palabra, ipCliente, nameCliente, accion, formato.format(fecha));
			s.save(edit);
			ts.commit();
			//s.disconnect();
			reset();
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	// public static void main(String args[]){
		// BaseDatos m = new BaseDatos();
		// //m.guardar("LAABBNco", "aADSBBJHJHJAo", "mascASDFASDF" ,"el eSADsta loco");
		// //m.guardar("Loca", "alASDFASDF", "masculino", "elADSFADta loco");
		// //m.guardar("Loco", "alguien malo del juicio", "masculino", "el esta loco");
		// //m.eliminar("Loco");
		// //m.reset();
		// m.editar("Loca", "Pupusa", "siguapa","Femenina", "Lasiguapa es fea");
		// m.buscar("Loc");
		// System.exit(-1);
	// }
}