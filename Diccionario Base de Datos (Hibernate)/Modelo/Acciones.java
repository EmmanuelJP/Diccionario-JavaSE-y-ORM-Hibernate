
package Modelo;

import javax.persistence.Entity; //Me dice que la clase que yo marque como entity sera una tabla en la bd
import javax.persistence.Table; // Me permite dar un nombre_Responsable a la tabla(es decir a la clase que yo maque como entity )
import javax.persistence.Id;// Estableces la notacion identificador unico (ID)
import javax.persistence.GeneratedValue; //Establecer un valor generado mediante una estrategia
import javax.persistence.GenerationType; //Establecer estrategia para generar un valor, mas bien un tipo de estrategia4
import javax.persistence.Column;//Me permite asignar un nombre_Responsable a un atributo que hace referencia al campo en la BD
import java.io.Serializable;

@Entity
public class Acciones implements Serializable{
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String palabra;
	private String ip_Responsable;
	private String nombre_Responsable;
	private String accion;
	private String fecha_hora;

	public Acciones(String palabra, String ip_Responsable, String nombre_Responsable, String accion, String fecha_hora){
		this.palabra = palabra;
		this.ip_Responsable = ip_Responsable;
		this.nombre_Responsable = nombre_Responsable;
		this.accion = accion;
		this.fecha_hora = fecha_hora;
		
	}
	public Acciones(){
		
	}

	public void setPalabra(String palabra){
		this.palabra=palabra;
	}

	public String getPalabra(){
		return this.palabra;
		
	}

	public void setip_Responsable (String ip_Responsable){
		this.ip_Responsable = ip_Responsable;
		
	}

	public String getip_Responsable(){
		return this.ip_Responsable;
	}


	public void setnombre_Responsable(String nombre_Responsable){
		this.nombre_Responsable = nombre_Responsable;
	}
	public String getnombre_Responsable(){
		return nombre_Responsable;
		
	}		
	
	public void setAccion(String accion){
		this.accion = accion;
	}
	public String getAccion(){
		return accion;
		
	}		
	
	public void setFecha_Hora(String fecha_hora){
		this.fecha_hora = fecha_hora;
	}
	public String getFecha_Hora(){
		return fecha_hora;	
	}	
	
	public void setId(int id){
		this.id= id;
	}
	public int getId(){
		return id;
		
	}
	

}