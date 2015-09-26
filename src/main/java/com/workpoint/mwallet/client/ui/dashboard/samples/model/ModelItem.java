/**
 * Sencha GXT 3.1.0-beta - Sencha for GWT
 * Copyright(c) 2007-2014, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */
package com.workpoint.mwallet.client.ui.dashboard.samples.model;

import java.util.HashMap;

public class ModelItem extends HashMap<String, Double> {
  private String key;

  public ModelItem(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
