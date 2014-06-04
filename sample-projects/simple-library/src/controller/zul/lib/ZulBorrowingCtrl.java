package controller.zul.lib;

import static cococare.zk.CCZk.getCCBandbox;
import static model.obj.lib.LibFilter.isSuspendFalse;
import model.bo.lib.LibBorrowingBo;
import model.obj.lib.LibBorrowing;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;

import cococare.common.CCFieldConfig.Accessible;
import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCBandbox;

public class ZulBorrowingCtrl extends CFZkCtrl {
	private LibBorrowingBo borrowingBo;
	private Datebox dtpDate;
	private CCBandbox bndMember;
	private Intbox txtTotalItem;
	private Doublebox txtTotalCost;

	@Override
	protected Class _getEntity() {
		return LibBorrowing.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}

	@Override
	protected void _initComponent() {
		super._initComponent();
		parameter.put(toString() + "dtpDate", dtpDate);
		parameter.put(toString() + "txtTotalItem", txtTotalItem);
		parameter.put(toString() + "txtTotalCost", txtTotalCost);
		parameter.put(toString() + "dtpDate-MANDATORY", new EventListener() {
			public void onEvent(Event event) throws Exception {
				_doUpdateDtpDate(Accessible.MANDATORY);
			}
		});
		parameter.put(toString() + "dtpDate-MANDATORY_READONLY", new EventListener() {
			public void onEvent(Event event) throws Exception {
				_doUpdateDtpDate(Accessible.MANDATORY_READONLY);
			}
		});
		_addChildScreen("borrowing", new ZulBorrowingItemListCtrl(), "zulBorrowingItem");
	}

	@Override
	protected void _initEditor() {
		super._initEditor();
		bndMember = getCCBandbox(getContainer(), "bndMember");
		bndMember.getTable().setHqlFilters(isSuspendFalse);
	}

	@Override
	protected boolean _doSaveEntity() {
		return borrowingBo.saveOrUpdate((LibBorrowing) objEntity, _getEntityChilds());
	}

	private void _doUpdateDtpDate(Accessible accessible) {
		edtEntity.setAccessible(dtpDate, accessible);
	}
}