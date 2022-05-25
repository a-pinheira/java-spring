package com.ladyjava.camel.samplecamel.bd;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class JDBCExample {

	public static void main(String[] args) throws Exception {
		String url = "jdbc:mysql://localhost:3306/bankdb";
		DataSource dataSource = setupDataSource(url);
		
		SimpleRegistry reg = new DefaultCamelContext(reg);
		reg.put("myDataSource", dataSource);
		
		CamelContext context = new DefaultCamelContext(reg);
		context.addRoutes(new JDBCExample().new myRouteBuilder());
		context.start();
		Thread.sleep(5000);
		context.stop();		
	}
	
	class MyRouterBuilder extends RouteBuilder {
		public void configure() {
			from ("timer://foo?period=1000").process(new SimpleProcessor().to("jdbc:myDataSource");
		}
	}
	
	private static DataSource setupDataSource(String connectURI) {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUserName("root");
		ds.setPassword("SkillSoft123");
		ds.setUrl(connectURI);
		return ds;		
	}
}
 