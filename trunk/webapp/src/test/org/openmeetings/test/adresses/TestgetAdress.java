package org.openmeetings.test.adresses;

import org.slf4j.Logger;
import org.red5.logging.Red5LoggerFactory;
import org.openmeetings.app.hibernate.beans.adresses.Adresses;
import org.openmeetings.app.data.user.Addressmanagement;

import junit.framework.TestCase;

public class TestgetAdress extends TestCase {
	
	private static final Logger log = Red5LoggerFactory.getLogger(TestgetAdress.class, "openmeetings");
	
	public TestgetAdress(String testname){
		super(testname);
	}
	
	public void testGetAdress(){
		
		Adresses adresses = Addressmanagement.getInstance().getAdressbyId(1);
		
		log.error("Adresses: "+adresses.getStates().getName());
		log.error("Adresses: "+adresses.getEmail());
		log.error("Adresses: "+adresses.getStreet());
		log.error("Adresses: "+adresses.getTown());
		
	}

}