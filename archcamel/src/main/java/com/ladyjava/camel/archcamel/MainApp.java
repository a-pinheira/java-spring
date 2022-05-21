package com.ladyjava.camel.archcamel;

import org.apache.camel.main.Main;
import org.apache.camel.main.MainListener;

/**
 * A Camel Application
 */
public class MainApp {

	/**
	 * A main() so we can easily run these routing rules in our IDE
	 */
	public static void main(String... args) throws Exception {
		Main main = new Main();
//        main.addMainListener(new myMainListener());
		//((Object) main).addRouteBuilder(new MyRouteBuilder());
		main.configure().addRoutesBuilder(new MyRouteBuilder());
		main.run(args);
	}
}
