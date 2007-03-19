package org.xmlcrm.test.userdata;

import org.hibernate.Session;
import org.xmlcrm.app.data.basic.Configurationmanagement;
import org.xmlcrm.app.hibernate.beans.basic.Sessiondata;
import org.xmlcrm.app.hibernate.beans.user.Users;
import org.xmlcrm.app.hibernate.utils.HibernateUtil;
import org.xmlcrm.app.remote.MainService;

import junit.framework.TestCase;

public class AddConfigParams extends TestCase {
	
	Session session = HibernateUtil.currentSession();
	
	public AddConfigParams(String testname){
		super(testname);
	}
	
	public void testAddConfigParams(){
		
		MainService mService = new MainService();
		Sessiondata sessionData = mService.getsessiondata();
		
		Users us = mService.loginUser(sessionData.getSession_id(), "wagner", "test");		
		
		String ret = "";
		
		//ret = Configurationmanagement.getInstance().addConfByKey(3, "allow_frontend_register", "1", us.getUser_id().intValue(), "coment");
		
		System.out.println("ret: "+ret);
		
		ret = Configurationmanagement.getInstance().addConfByKey(3, "default_group_id", "1", us.getUser_id().intValue(), "coment");
		
		
		
	}

}
