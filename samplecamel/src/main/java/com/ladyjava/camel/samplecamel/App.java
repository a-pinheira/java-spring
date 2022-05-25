package com.ladyjava.camel.samplecamel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.jndi.JndiContext;

import org.apache.camel.ProducerTemplate; 

/**
 * A Camel Application
 */
public class App {

    public static void main(String[] args) throws Exception {
    	JndiContext jndiContext = new JndiContext();
    	jndiContext.bind("routeBuilderWithRecipientListBean", new RouteBuilderWithRecipientListBean());
    	CamelContext camelContext = new DefaultCamelContext(jndiContext);
    	try {
    		camelContext.addRoutes(new RouteBuilder() {
    			public void configure() {
    				from("direct:start")
    				.log("Main route: '${body}' to tap router")
    				.wireTap("direct:tap")
    			}
    		});
    	}
    }
}

