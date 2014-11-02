package controller.zul.lib;

import static cococare.zk.CCZk.execute;
import model.obj.lib.LibReturningItem;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;

import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCBandbox;

public class ZulReturningItemListCtrl extends CFZkCtrl {
	private Datebox dtpDate;
	private CCBandbox bndMember;
	private Intbox txtTotalItem;
	private Doublebox txtTotalFine;

	@Override
	protected Class _getEntity() {
		return LibReturningItem.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.LIST_FUNCTION;
	}

	@Override
	protected void _initComponent() {
		super._initComponent();
		dtpDate = (Datebox) parameter.get(callerCtrl.toString() + "dtpDate");
		bndMember = (CCBandbox) parameter.get(callerCtrl.toString() + "bndMember");
		txtTotalItem = (Intbox) parameter.get(callerCtrl.toString() + "txtTotalItem");
		txtTotalFine = (Doublebox) parameter.get(callerCtrl.toString() + "txtTotalFine");
		parameter.put(toString() + "dtpDate", dtpDate);
		parameter.put(toString() + "bndMember", bndMember);
	}

	@Override
	protected void _initTable() {
		super._initTable();
		tblEntity.addListenerOnSelect(new EventListener() {
			public void onEvent(Event event) throws Exception {
				_doUpdateParentField();
			}
		});
	}

	private void _doUpdateParentField() {
		execute((EventListener) parameter.get(callerCtrl.toString() + (tblEntity.getRowCount() == 0 ? "returningInfo-MANDATORY" : "returningInfo-MANDATORY_READONLY")));
		txtTotalItem.setValue(tblEntity.getRowCount());
		double totalFine = 0;
		for (Object object : tblEntity.getList()) {
			LibReturningItem returningItem = (LibReturningItem) object;
			totalFine += returningItem.getBorrowingFine();
		}
		txtTotalFine.setValue(totalFine);
	}
}