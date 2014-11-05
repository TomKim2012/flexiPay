package com.workpoint.mwallet.client.ui.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.workpoint.mwallet.shared.model.SearchFilter;

public class SearchTransactionsEvent extends
		GwtEvent<SearchTransactionsEvent.SearchTransactionsHandler> {

	public static Type<SearchTransactionsHandler> TYPE = new Type<SearchTransactionsHandler>();
	private SearchFilter filter;

	public interface SearchTransactionsHandler extends EventHandler {
		void onSearchTransactions(SearchTransactionsEvent event);
	}

	public interface SearchTransactionsHasHandlers extends HasHandlers {
		HandlerRegistration addSearchTransactionsHandler(
				SearchTransactionsHandler handler);
	}

	public SearchTransactionsEvent(SearchFilter filter) {
		this.filter = filter;
	}

	public SearchFilter getFilter() {
		return filter;
	}

	@Override
	protected void dispatch(SearchTransactionsHandler handler) {
		handler.onSearchTransactions(this);
	}

	@Override
	public Type<SearchTransactionsHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<SearchTransactionsHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, SearchFilter filter) {
		source.fireEvent(new SearchTransactionsEvent(filter));
	}
}
