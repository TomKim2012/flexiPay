package com.workpoint.mwallet.client.ui.dashboard.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface DataModelProperties extends PropertyAccess<DataModel> {
	ModelKeyProvider<DataModel> id();

	ValueProvider<DataModel, String> name();

	ValueProvider<DataModel, Double> data();

	ValueProvider<DataModel, String> title();

	ValueProvider<DataModel, String> color();
}