package com.camel.hilos.strategy;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import com.camel.hilos.sei.OutputServicio;

/**
 * Esta clase lo que hace es recopilar la respuesta de cada hilo y armar la 
 * respuesta general del servicio. 
 * 
 * @author ptamburro
 * @Info
 * http://camel.apache.org/aggregator.html
 * http://camel.apache.org/maven/current/camel-core/apidocs/org/apache/camel/processor/aggregate/AggregationStrategy.html
 *
 */
public class HilosAggregationStrategy implements AggregationStrategy{

	
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		
		//tomo la respuesta del processor desde el body
		String stringResponse = newExchange.getIn().getBody(String.class);
		
		if(oldExchange == null){
			
			OutputServicio out = new OutputServicio();
			out.getResultado().add(stringResponse);
			
			
			newExchange.getIn().setBody(out);
			return newExchange;
		}
		 
		OutputServicio out = oldExchange.getIn().getBody(OutputServicio.class);
		out.getResultado().add(stringResponse);
		
		
		return oldExchange;
	}

}
