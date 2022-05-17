package com.camel.hilos.processor;

import org.springframework.stereotype.Component;


@Component("hiloProcessor")
public class HiloProcessor {

	public String manejarHilo(String letra){
		
		//System.out.println("-------> la letra es: " + letra);
		
		String respuestaThread = "EL HILO: " + letra + ", FUE PROCESADO.";
		return respuestaThread;
	}
	
	
	
	
}
