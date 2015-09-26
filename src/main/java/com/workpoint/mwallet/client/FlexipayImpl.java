package com.workpoint.mwallet.client;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class FlexipayImpl implements Bootstrapper {

	private PlaceManager placeManager;

	@Inject
	public FlexipayImpl(PlaceManager placeManager) {
		this.placeManager = placeManager;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onBootstrap() {
		placeManager.revealCurrentPlace();
	}

}
