package com.workpoint.mwallet.client.ui.dashboard;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface DataPropertyAccess extends PropertyAccess<Data> {
	ValueProvider<Data, Double> data1();

	ValueProvider<Data, Double> data2();

	ValueProvider<Data, Double> data3();

	ValueProvider<Data, Double> data4();

	ValueProvider<Data, Double> data5();

	ValueProvider<Data, Double> data6();

	ValueProvider<Data, Double> data7();

	ValueProvider<Data, String> name();

	@Path("id")
	ModelKeyProvider<Data> nameKey();
}