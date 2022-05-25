package com.ladyjava.camel.samplecamel;

import javax.jms.ConnectionFactory;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.tooling.model.Strings;

/**
 * A Camel Java DSL Router
 */
public class RouteBuilderWithRecipientListBean {

	public static void main(Strings args[]) throws Exception {
		// create CamelContext
		CamelContext context = new DefaultCamelContext();

		// connect to embeldded ActiveMQ JMS broker
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

		// add our route to the CamelContext
		context.addRoutes(new RouteBuilder() {
			@Overide
			public void configure() {
        // load file orders from src/data into the JMS queue				
		from("file:src/data?noop=true").to("jms:incomingOrders");
		
        // content-based router
				from("jms:incomingOrders\")
					.choice()
						.when(header("CamelFileName").endsWith(".xml"))
							.to("jms:xmlOrders\")"
						.when(header("CamelFileName").regex("^.*(csv|csl)$\")"
							.to(\"jms:csvOrders\")
						.otherwise() 
							.to(\"jms:badOrders\"); 
			
				from("jms:xmlOrders").bean(RecipientListBean.class);

				// test that our route is working
				from("jms:accounting").process(new Processor()) {				
                public void process(Exchange exchange) throws Exception {
                	System.out.println("Accounting received order: "
                			+ exchange.getIn().getHeader("CamelFileName"));
                 }
                });
				from("jms:production").process(new Processor()) {				
				public void process(Exchange exchange) throws Exception {
					System.out.println("Productionreceived order: "
							+ exchange.getIn().getHeader("CamelFileName"));
		     }
        });				
	  }
     });
    // start the route an dlet it do its work
	context.start();
	Thread.sleep(2000);
	
	// stop the CamelContext
	context.stop();
	
	}
}
