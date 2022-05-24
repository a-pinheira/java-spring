package com.ladyjava.camel.samplecamel;

import org.apache.camel.main.Main;

/**
 * A Camel Application
 */
public class App {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.configure().addRoutesBuilder(new RouteBuilderWithRecipientListBean());
        main.run(args);
    }

}

