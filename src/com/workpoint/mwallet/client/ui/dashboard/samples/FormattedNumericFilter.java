/**
 * Sencha GXT 3.1.0-beta - Sencha for GWT
 * Copyright(c) 2007-2014, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */
package com.workpoint.mwallet.client.ui.dashboard.samples;

import com.google.gwt.i18n.client.NumberFormat;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.grid.filters.NumericFilter;
import com.workpoint.mwallet.shared.model.dashboard.Data;

public class FormattedNumericFilter extends NumericFilter<Data, Double> {

  NumberFormat formatter;

  public FormattedNumericFilter(ValueProvider<? super Data, Double> valueProvider,
      NumberPropertyEditor<Double> propertyEditor, String format) {
    super(valueProvider, propertyEditor);
    formatter = NumberFormat.getFormat(format);
  }

  protected boolean equals(Double a, Double b) {
    return formatter.format(a).equals(formatter.format(b));
  }

}
