package controller.zul.lib;

import static cococare.zk.CCZk.execute;
import model.obj.lib.LibBorrowingItem;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;

import cococare.framework.zk.CFZkCtrl;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulBorrowingItemListCtrl extends CFZkCtrl {
	private Datebox dtpDate;
	private Intbox txtTotalItem;
	private Doublebox txtTotalCost;

	@Override
	protected Class _getEntity() {
		return LibBorrowingItem.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.LIST_FUNCTION;
	}

	@Override
	protected void _initComponent() {
		super._initComponent();
		dtpDate = (Datebox) parameter.get(callerCtrl.toString() + "dtpDate");
		txtTotalItem = (Intbox) parameter.get(callerCtrl.toString() + "txtTotalItem");
		txtTotalCost = (Doublebox) parameter.get(callerCtrl.toString() + "txtTotalCost");
		parameter.put(toString() + "dtpDate", dtpDate);
	}

	@Override
	protected void _initTable() {
		super._initTable();
		tblEntity.addListenerOnSelect(new EventListener() {
			public void onEvent(Event event) throws Exception {
				_doUpdateParentField(event);
			}
		});
	}

	private void _doUpdateParentField(Event event) {
		execute((EventListener) parameter.get(callerCtrl.toString() + (tblEntity.getRowCount() == 0 ? "dtpDate-MANDATORY" : "dtpDate-MANDATORY_READONLY")), event);
		txtTotalItem.setValue(tblEntity.getRowCount());
		double totalCost = 0;
		for (Object object : tblEntity.getList()) {
			LibBorrowingItem borrowingItem = (LibBorrowingItem) object;
			totalCost += borrowingItem.getBorrowingCost();
		}
		txtTotalCost.setValue(totalCost);
	}
}