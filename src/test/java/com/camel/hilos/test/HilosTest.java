package com.camel.hilos.test;


import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;

import com.camel.hilos.sei.InputServicio;
import com.camel.hilos.sei.OutputServicio;


@SuppressWarnings("deprecation")
@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/WEB-INF/camel-context.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisableJmx(false)
public class HilosTest {

	@Autowired
	protected ModelCamelContext context;
	
	@Produce(uri = "direct:start")
	protected ProducerTemplate template;

	private InputServicio inputServicio;
	
	@Before
	public final void setUp() {
		
		inputServicio = new InputServicio();
		inputServicio.getLetras().add("A");
		inputServicio.getLetras().add("B");
		inputServicio.getLetras().add("C");
	}
	
	
	@DirtiesContext
	@Test	
	public void verificarLista() throws Exception {
		
		MockEndpoint mock = interceptaryReemplazar(".*direct:rutaHilos.*");

		mock.expectedMessageCount(1);
		template.sendBody(inputServicio);
		mock.assertIsSatisfied();

		List<Exchange> exchanges = mock.getExchanges();
		for (Exchange exchange : exchanges) {
			OutputServicio out = (OutputServicio) exchange.getIn().getBody();
			assertTrue("Se espera distinto de null de salida " + out.getResultado().size(), null != out.getResultado());
			assertTrue("Se esperan resultado de tres hilos", out.getResultado().size() == 3);
		}

	}
	
	
	@DirtiesContext
	@Test
	public void verificarResultados() throws Exception {
		
		MockEndpoint mock = interceptaryReemplazar(".*direct:rutaHilos.*");

		mock.expectedMessageCount(1);
		template.sendBody(inputServicio);
		mock.assertIsSatisfied();

		List<Exchange> exchanges = mock.getExchanges();
		for (Exchange exchange : exchanges) {
			OutputServicio out = (OutputServicio) exchange.getIn().getBody();
			
			assertTrue("La respuesta del hilo no es la esperada ('EL HILO: A, FUE PROCESADO.')", "EL HILO: A, FUE PROCESADO.".equals(out.getResultado().get(0)));
			assertTrue("La respuesta del hilo no es la esperada ('EL HILO: B, FUE PROCESADO.')", "EL HILO: B, FUE PROCESADO.".equals(out.getResultado().get(1)));
			assertTrue("La respuesta del hilo no es la esperada ('EL HILO: C, FUE PROCESADO.')", "EL HILO: C, FUE PROCESADO.".equals(out.getResultado().get(2)));
			
				
			}
	}


	
	
	/**
	 * intercepto la ruta y reemplazo el endpoint que matchee con el string por
	 * mock:out
	 * 
	 */
	private MockEndpoint interceptaryReemplazar(final String pattern) throws Exception {
		
		RouteDefinition routeDefinition = context.getRouteDefinition("principalHilosRoute");

		routeDefinition.adviceWith(context, new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				weaveAddLast().to("mock:out");
			}
		});

		MockEndpoint mockOut = (MockEndpoint) context.getEndpoint("mock:out");
		return mockOut;
	}
	
	
}
