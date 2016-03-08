
package Modelo;

import javax.persistence.Entity; //Me dice que la clase que yo marque como entity sera una tabla en la bd
import javax.persistence.Table; // Me permite dar un nombre a la tabla(es decir a la clase que yo maque como entity )
import javax.persistence.Id;// Estableces la notacion identificador unico (ID)
import javax.persistence.GeneratedValue; //Establecer un valor generado mediante una estrategia
import javax.persistence.GenerationType; //Establecer estrategia para generar un valor, mas bien un tquienGuardoo de estrategia4
import javax.persistence.Column;//Me permite asignar un nombre a un atributo que hace referencia al campo en la BD
import java.io.Serializable;

@Entity
	//@Table (name = "Diccionario") //Me permite dar un nombre a la tabla(es decir a la clase que yo maque como entity )
public class Diccionario implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)//Asignando a mi identificar un valor generado con la estrategia de auto incremento
	private int id; //Me permite tomar un atributo como un identificador primario en la tabla Diccionario(en este caso es el atributo id)
	
	//@Column (name = "words")   me permite cambiar el nombre a un campo o columna
	private String palabra;
	
	private String definicion;
	private String genero;
	private String ejemplo;
	
	public Diccionario(int id, String palabra, String definicion, String genero, String ejemplo){
		this.id = id;
		this.palabra =  palabra;
		this.definicion= definicion;
		this.genero = genero;
		this.ejemplo = ejemplo;	
	}
	
	
	
	public Diccionario(String palabra, String definicion, String genero, String ejemplo){
		this.palabra =  palabra;
		this.definicion= definicion;
		this.genero=genero;
		this.ejemplo=ejemplo;	
	}
	
	public Diccionario (int id){
		this.id = id;
	}
	
	public Diccionario (){
		
	}
	
	public void setId (int id){
		this.id = id;
	}
	
	public int getId (){
		return this.id;
	}
	
	public void setPalabra (String palabra){
		this.palabra = palabra;	

	}
	
	public String getPalabra (){
		return this.palabra;
	}
	
	public void setDefinicion (String definicion){
		this.definicion = definicion;
		
	}
	
	public String getDefinicion (){
		return this.definicion;
	}
	
	public void setGenero(String genero){
		this.genero = genero;
	}
	
	public String getGenero(){
		return this.genero;
	}
	
	
	public void setEjemplo(String ejemplo){
		this.ejemplo  = ejemplo;
	}
	
	public String getEjemplo(){
		return this.ejemplo;
	}
	
}