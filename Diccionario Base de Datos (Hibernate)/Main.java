import org.hibernate.SessionFactory;//interfaz esta clase me permite crear la factoria de sesiones
import org.hibernate.Session;//interfaz me permite abrir una de las sesiones que me creo la factoria
import org.hibernate.Transaction;//interfaz me permite utilizar las propiedades de las transacciones 
import org.hibernate.cfg.Configuration; //Clase que me permite configurar mi archivo xml
import Modelo.Diccionario;
import Modelo.Eliminar;
import org.hibernate.*;
import java.util.List;
import java.io.Serializable;
import java.net.InetAddress;


public class Main{
	
	public static Session s;
	public static Transaction ts;
	public static String nameCliente;
	public static String ipCliente;
	
	public static void configuracion(){	
		try {
			s= new Configuration().configure("cnfghibernate.hbm.xml").buildSessionFactory().openSession();
			ts = s.beginTransaction();
			 nameCliente= InetAddress.getLocalHost().getHostName(); 
			ipCliente= InetAddress.getLocalHost().getHostAddress();
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
		
	public void eliminar(){
		configuracion();
		try{
			String palabra = "yo"; // palabra a borrar
			Query sentencia = s.createQuery("Select id from Diccionario where palabra = '"+ palabra +"'"); // extrayendo id para eliminar la palabra
			List<Integer> lista = sentencia.list();	// enlistando los id de la sentencia
				for (int i: lista){ //recorriendo la lista de id devueltos
					Diccionario d1 = new Diccionario (i); // toma el id uno por uno para despues eliminarlo mediante la session
					s.delete(d1);// eliminando el registro mediante el el obejto id
				}			
			
				
			Eliminar eli1 = new Eliminar(""+palabra, ""+ipCliente, ""+nameCliente);
				s.save(eli1);
	
		}catch(Exception e){
			
		}
		
	}
	
	public void guardar(){
		configuracion();	
		try{
			String nameCliente= InetAddress.getLocalHost().getHostName();
			String ipCliente= InetAddress.getLocalHost().getHostAddress();
			Diccionario reg1 = new Diccionario("yo","definicion","masculino","ejemplo","Name: " + nameCliente + "\n"+ "IP: " + ipCliente);
			s.save(reg1);
		}catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
	

	public static void main(String args[]){
		Main m = new Main();
		//m.guardar();
		m.eliminar();
		ts.commit();//cerrando la transacion		
		s.disconnect();//desconectando la session
		System.exit(-1);
	}
}