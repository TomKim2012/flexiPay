package com.workpoint.mwallet.client.ui.category.table;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.RowWidget;
import com.workpoint.mwallet.client.ui.events.ActivitySelectionChangedEvent;
import com.workpoint.mwallet.client.ui.util.DateUtils;
import com.workpoint.mwallet.client.util.AppContext;
import com.workpoint.mwallet.shared.model.CategoryDTO;

public class CategoryTableRow extends RowWidget {

	private static ActivitiesTableRowUiBinder uiBinder = GWT
			.create(ActivitiesTableRowUiBinder.class);

	interface ActivitiesTableRowUiBinder extends
			UiBinder<Widget, CategoryTableRow> {
	}

	@UiField
	HTMLPanel row;

	@UiField
	CheckBox chkSelect;

	@UiField
	HTMLPanel divCategoryName;

	@UiField
	HTMLPanel divCategoryCode;

	@UiField
	HTMLPanel divCategoryType;
	@UiField
	HTMLPanel divLastModified;

	private CategoryDTO category;

	public CategoryTableRow() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public CategoryTableRow(CategoryDTO category) {
		this();
		init(category);

		chkSelect.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				 AppContext.fireEvent(new ActivitySelectionChangedEvent(
				 CategoryTableRow.this.category, event.getValue()));
			}
		});
	}

	ValueChangeHandler<Boolean> selectionHandler;

	public void setSelectionChangeHandler(ValueChangeHandler<Boolean> handler) {
		this.selectionHandler = handler;
		chkSelect.addValueChangeHandler(handler);
	}

	private void init(CategoryDTO category) {
		this.category = category;
		if (category != null) {
			if (category.getId() != null)
				bindText(divCategoryCode, Long.toString(category.getId()));
			
			bindText(divCategoryName, category.getCategoryName());
			bindText(divCategoryType, category.getCategoryType());
			if (category.getLastModified() != null)
				bindText(divLastModified,
						DateUtils.DATEFORMAT.format(category.getLastModified()));
		}
	}

	private void bindText(HTMLPanel panel, String text, String title) {
		panel.getElement().setInnerText(text);
		if (title != null) {
			panel.getElement().setTitle(title);
		}
	}

	public void bindText(HTMLPanel panel, String text) {
		bindText(panel, text, null);
	}

}
