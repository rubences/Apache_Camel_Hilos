package com.camel.hilos.sei;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;



/**
 * Clase input del SEI (Contiene List de letras que seran manejadas con hilos dentro de la ruta)
 * @author ptamburro
 *
 */
@XmlRootElement(name = "inputServicio")
public class InputServicio {

	
	//Atributos del Input	
	private List<String> letras = new ArrayList<String>();
	

	//Getters & Setters	
	public List<String> getLetras() {
		return letras;
	}

	public void setLetras(List<String> letras) {
		this.letras = letras;
	}


	
	
	
}