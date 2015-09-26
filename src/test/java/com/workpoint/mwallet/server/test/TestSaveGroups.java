package com.workpoint.mwallet.server.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.server.helper.auth.LoginHelper;
import com.workpoint.mwallet.shared.model.UserGroup;

public class TestSaveGroups {

	@Before
	public void init(){
		DB.beginTransaction();
	}
	
	@Test
	public void save(){
		UserGroup test = new UserGroup();
		test.setName("Test2");
		test.setFullName("Testing 2");
		UserGroup group = LoginHelper.get().createGroup(test);
		Assert.assertNotNull(group.getId());
		
	}
	
	@After
	public void destroy(){
		DB.commitTransaction();
		DB.closeSession();
	}
}
