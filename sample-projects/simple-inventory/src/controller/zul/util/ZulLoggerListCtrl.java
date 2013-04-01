package controller.zul.util;

import static cococare.common.CCFinal.FORMAT_MYSQL;
import static cococare.common.CCFormat.getString;
import static cococare.zk.CCZk.addEventListenerOnChange_OnOk;
import static cococare.zk.CCZk.fillUp;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;

import cococare.common.CCCustomField;
import cococare.database.CCHibernateFilter;
import cococare.framework.model.obj.util.UtilLogger;
import cococare.framework.zk.CFZkCtrl;

public class ZulLoggerListCtrl extends CFZkCtrl {
	private Combobox cmbUsername;
	private Combobox cmbScreen;
	private Combobox cmbAction;

	@Override
	protected Class _getEntity() {
		return UtilLogger.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.LIST_FUNCTION;
	}

	@Override
	protected void _initComponent() {
		super._initComponent();
		fillUp(cmbUsername, loggerBo.getUsernames(), true, false);
		fillUp(cmbScreen, loggerBo.getScreens(), true, false);
		fillUp(cmbAction, loggerBo.getActions(), true, false);
	}

	@Override
	protected void _initTable() {
		super._initTable();
		tblEntity.addField(0, new CCCustomField() {
			@Override
			public String getLabel() {
				return "Created On";
			}

			@Override
			public Object getCustomView(Object object) {
				return getString(((UtilLogger) object).getLogCreatedOn(), FORMAT_MYSQL);
			}
		});
		tblEntity.setColumnWidth(150, 100, 100, 100, 150, 100, null);
		tblEntity.setHqlFilters(new CCHibernateFilter() {
			@Override
			public String getFieldName() {
				return "username";
			}

			@Override
			public Object getFieldValue() {
				return cmbUsername.getSelectedIndex() < 1 ? null : cmbUsername.getText();
			}
		}, new CCHibernateFilter() {
			@Override
			public String getFieldName() {
				return "screen";
			}

			@Override
			public Object getFieldValue() {
				return cmbScreen.getSelectedIndex() < 1 ? null : cmbScreen.getText();
			}
		}, new CCHibernateFilter() {
			@Override
			public String getFieldName() {
				return "action";
			}

			@Override
			public Object getFieldValue() {
				return cmbAction.getSelectedIndex() < 1 ? null : cmbAction.getText();
			}
		});
		tblEntity.setHqlOrderSyntax("id DESC");
	}

	@Override
	protected void _initListener() {
		super._initListener();
		EventListener elSearch = new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				_doSearch();
			}
		};
		addEventListenerOnChange_OnOk(cmbUsername, elSearch);
		addEventListenerOnChange_OnOk(cmbScreen, elSearch);
		addEventListenerOnChange_OnOk(cmbAction, elSearch);
	}
}