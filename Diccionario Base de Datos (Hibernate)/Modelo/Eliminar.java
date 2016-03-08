
package Modelo;

import javax.persistence.Entity; //Me dice que la clase que yo marque como entity sera una tabla en la bd
import javax.persistence.Table; // Me permite dar un nombre a la tabla(es decir a la clase que yo maque como entity )
import javax.persistence.Id;// Estableces la notacion identificador unico (ID)
import javax.persistence.GeneratedValue; //Establecer un valor generado mediante una estrategia
import javax.persistence.GenerationType; //Establecer estrategia para generar un valor, mas bien un tipo de estrategia4
import javax.persistence.Column;//Me permite asignar un nombre a un atributo que hace referencia al campo en la BD

@Entity
public class Eliminar{
	@Id
	@GeneratedValue (strategy =GenerationType.AUTO)
	private int id;
	private String palabra;
	private String ip;
	private String nombre;

	public Eliminar(String palabra, String ip, String nombre){
		this.palabra = palabra;
		this.ip=ip;
		this.nombre=nombre;
		
	}

	public void setPalabra(String palabra){
		this.palabra=palabra;
	}

	public String getPalabra(){
		return this.palabra;
		
	}

	public void setIp (String ip){
		this.ip = ip;
		
	}

	public String getIp(){
		return this.ip;
	}


	public void setNombre(String nombre){
		this.nombre= nombre;
	}
	public String getNombre(){
		return nombre;
		
	}
	public void setId(int id){
		this.id  =id;
	}
	public int getId (){
		return id;
	}

}