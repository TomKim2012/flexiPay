package com.workpoint.mwallet.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

//
@GinModules(properties = { "gin.ginjector.modules" }, value = { ClientModule.class })
public interface FlexiPayGinjector extends Ginjector {
}
