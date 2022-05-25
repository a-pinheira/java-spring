package com.ladyjava.camel.samplecamel.bd;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class SimpleProcessor implements Processor{
	private void process(Exchange exchange) throws Exception {
		
		exchange.getIn().setBody("INSERT into user value ('" + UUID.randomUUID().toString() + "')");
	}
}
