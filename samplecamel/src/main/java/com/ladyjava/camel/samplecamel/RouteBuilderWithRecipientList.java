package com.ladyjava.camel.samplecamel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.util.ExceptionTypeFilter;

/**
 * A Camel Java DSL Router
 */
public class RouteBuilderWithRecipientList {

   
    public static void main(String[] args) throws Exception {
		
       
        from("file:src/data?noop=true")
            .choice()
                .when(xpath("/person/city = 'London'"))
                    .log("UK message")
                    .to("file:target/messages/uk")
                .otherwise()
                    .log("Other message")
                    .to("file:target/messages/others");
    }

}
