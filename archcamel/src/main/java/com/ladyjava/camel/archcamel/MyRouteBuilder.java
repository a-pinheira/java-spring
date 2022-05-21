package com.ladyjava.camel.archcamel;

import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;

/**
 * A Camel Java8 DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

//	private static final Object[] OBJECTS = new Object[] { "A string", new Integer(1), new Double(1.0) };
//
//	private int index;

	/**
	 * Let's configure the Camel routing rules using Java code...
	 */
	public void configure() {
		// here is a sample which set a raondom body then performs content
		// based routing on the message using method references
		
		/*
		 * Esta tag(entradas?noop=true) igual a true, vai fazer uma copia, sem ela os arquivos são enviados 
		 * para a saída e somem da pasta entrada 
		 */
//		from("file:src/entradas?noop=true")
		from("file:src/entradas?noop=true")
			.log("${body}")
		.to("file:src/saidas");
	
	}

}
