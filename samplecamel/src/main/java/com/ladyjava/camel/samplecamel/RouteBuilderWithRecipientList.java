// pacote
package com.ladyjava.camel.samplecamel;

import javax.jms.ConnectionFactory;

//import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.activemq.spring.*;
import org.apache.camel.CamelContext;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.processor.errorhandler.ExceptionPolicy;
import org.apache.camel.tooling.model.Strings;

/**
 * A Camel Java DSL Router
 */
public class RouteBuilderWithRecipientList {

	public static void main(Strings args[]) throws ExceptionPolicy {
		// create CamelContext
		CamelContext context = new DefaultCamelContext();

		// connect to embeldded ActiveMQ JMS broker
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

		// add our route to the CamelContext
		context.addRoutes(new RouteBuilder() {
			@Overide
			public void configure() {
				// load file orders from sr/data into the JMS queue
				from("file:src/data?noop=true").to("jms:incomingOrders");

				// content-based router
				from("jms:incomingOrders")
				.choice()
					.when(header("camelFileName").endsWith(".xml"))
						.to("jms:xmlOrders")
					.when(header("camelFileName").regex("^.*(csv|csl)$"))
						.to("jms:csvOrders")
					.otherwise()
						.to("jms:badOrders");

				from("jms:xmlOrders")
				.setHeader("customer", xpath("/order/@customer"))
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						String recipients = "jms:accounting";
						String customer = exchange.getIn().getHeader("customer", String.class);
						if (customer.equals("honda")) {
							recipients += ",jms:production";
						}
						exchange.getIn().setHeader("recipients", recipients);
					}
				});
				.recipientsList(header("recipients"));
				
				// test that our route is working
				from("jms:accouting").process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						System.out.pritln("Accouting received order:" 
								+ exchange.getIn().getHeader("CamelFileName"));
					}
				});
				from("jms:prodution").process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						System.out.pritln("Prodution received order:" 
								+ exchange.getIn().getHeader("CamelFileName"));
					}
				});
			}
		});
	}
}
