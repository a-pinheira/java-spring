package com.ladyjava.camel.samplecamel;

import org.apache.camel.RecipientList;
import org.apache.camel.language.xpath.XPath;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

public class RecipientListBean {
	@RecipientList
	public String[] route(@XPath("/order/@customer") String customer) {
		if (isGoldCustomer(customer)) {
			return new String[] {"jms:accounting", "jms:production"};
		} else {
			return new String[] {"jms:accounting"};
		}
	}
	
	private void isGoldCustomer(String customer) {
		return customer.equals("honda");
	}

}
