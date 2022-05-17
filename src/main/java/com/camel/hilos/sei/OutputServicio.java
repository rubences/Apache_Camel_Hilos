package com.camel.hilos.sei;



import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase output del SEI del servicio
 * 
 * @author ptamburro
 */
@XmlRootElement(name = "outputServicio")
public class OutputServicio {
	
	//Atributos del output
	private Long cantHilosUsados;
	private List<String> resultado = new ArrayList<String>();

	//Getters & Setters
	public Long getCantHilosUsados() {
		return cantHilosUsados;
	}
	public void setCantHilosUsados(Long cantHilosUsados) {
		this.cantHilosUsados = cantHilosUsados;
	}
	public List<String> getResultado() {
		return resultado;
	}
	public void setResultado(List<String> resultado) {
		this.resultado = resultado;
	}

}




