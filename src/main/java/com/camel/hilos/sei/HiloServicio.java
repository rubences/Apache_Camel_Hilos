package com.camel.hilos.sei;



import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "HilosServicio", targetNamespace = "http://camelHilos.Ejemplo.Servicio")
public interface HiloServicio {

	@WebMethod(operationName = "notificarNombreHilos")
	@WebResult(name = "outputServicio")
	OutputServicio notificarNombreHilos(@WebParam(name ="inputServicio") InputServicio inputServicio);

}