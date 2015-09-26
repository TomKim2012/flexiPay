/**
 * Sencha GXT 3.1.0-beta - Sencha for GWT
 * Copyright(c) 2007-2014, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */
package com.workpoint.mwallet.client.ui.dashboard.samples;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.workpoint.mwallet.client.ui.dashboard.samples.model.FileModel;

/**
 * Async <code>FileService<code> interface.
 */
public interface FileServiceAsync {

  public void getFolderChildren(FileModel model, AsyncCallback<List<FileModel>> children);

  // public void getFolderChildren(RemoteSortTreeLoadConfig loadConfig,
  // AsyncCallback<List<FileModel>> children);

}
