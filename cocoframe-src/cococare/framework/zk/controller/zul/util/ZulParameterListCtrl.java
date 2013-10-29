package cococare.framework.zk.controller.zul.util;

import static cococare.common.CCClass.getCCTypeConfig;
import static cococare.common.CCLogic.isNotNull;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;

import cococare.database.CCEntityModule;
import cococare.framework.model.obj.util.UtilConfig;
import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCZk;

public class ZulParameterListCtrl extends CFZkCtrl {
	private Combobox cmbEntity;

	@Override
	protected Class _getEntity() {
		if (isNotNull(cmbEntity) && cmbEntity.getSelectedIndex() > -1) {
			return CCEntityModule.INSTANCE.getCCHibernate().getParameterClasses().get(cmbEntity.getSelectedIndex());
		} else {
			return UtilConfig.class;
		}
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.LIST_FUNCTION;
	}

	@Override
	protected void _initComponent() {
		super._initComponent();
		//
		for (Class clazz : CCEntityModule.INSTANCE.getCCHibernate().getParameterClasses()) {
			cmbEntity.appendItem(getCCTypeConfig(clazz).label());
		}
		cmbEntity.setSelectedIndex(0);
	}

	@Override
	protected void _initListener() {
		super._initListener();
		//
		CCZk.addEventListenerOnChange_OnOk(cmbEntity, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				_doSearch();
			}
		});
	}

	@Override
	public void doUpdateTable() {
		tblEntity.setEntity(_getEntity());
		//
		super.doUpdateTable();
	}
}