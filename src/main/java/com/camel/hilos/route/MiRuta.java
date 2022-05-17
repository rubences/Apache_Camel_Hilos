package com.camel.hilos.route;

import java.util.concurrent.ExecutorService;

import org.apache.camel.builder.ThreadPoolBuilder;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import com.camel.hilos.sei.InputServicio;
import com.camel.hilos.strategy.HilosAggregationStrategy;

/**
 * Esta ruta esta implementada con manejo de hilos (Configurando el pool diferente al default, 
 * donde (segun el caso) podria obtenerse una mejora en el rendimiento de la ruta)
 * 
 * @author ptamburro
 */
@Component
public class MiRuta extends SpringRouteBuilder {

    public void configure() throws Exception {
    	
 	   //CONFIGURACION DEL POOL (poolSize: tamaño del pool inicial / maxQueueSize: tamaño de tareas a alocar en la cola /maxPoolSize: tamaño maximo de hilos nuevos que se crearan cuando las tareas sobrepasen el tamaño de la cola)
 	   ThreadPoolBuilder builder = new ThreadPoolBuilder(getContext());
       ExecutorService bigPool = builder.poolSize(20).maxPoolSize(50).maxQueueSize(2000).build("bigPool");
    	       
    	from("{{initial.endpoint.notificar.hilos}}")
    		.convertBodyTo(InputServicio.class).to("direct:rutaHilos")
    			.setId("principalHilosRoute");
    	
    	
    	from("direct:rutaHilos")
	    	//spliteo por cada uno de los elementos que existan en la lista "letras" e indico un Strategy que luego unira los resultados, ademas amplio el pool por defecto
			.split(simple("${body.letras}"), new HilosAggregationStrategy()).executorService(bigPool)
			
				//cada hilo se procesa en paralelo
				.parallelProcessing()
					
				//cualquier hilo que de exception finaliza a todos los restantes
				.stopOnException()

				//cada hilo realizara la accion definida en el Processor
				.beanRef("hiloProcessor", "manejarHilo")
    	
    	.end();
    	
    }

}
