package controller.zul.lib;

import static cococare.common.CCClass.extract;
import static cococare.common.CCFormat.formatNumber;
import static cococare.common.CCFormat.getMinTime;
import static cococare.common.CCLogic.isNotNull;
import static cococare.zk.CCZk.getCCBandbox;
import static model.obj.lib.LibFilter.isReturnedFalse;

import java.util.List;

import model.bo.lib.LibReturningItemBo;
import model.obj.lib.LibBorrowingItem;
import model.obj.lib.LibMember;
import model.obj.lib.LibReturningItem;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;

import cococare.database.CCHibernateFilter;
import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCBandbox;

public class ZulReturningItemCtrl extends CFZkCtrl {
	private LibReturningItemBo returningItemBo;
	private Datebox dtpdate;
	private CCBandbox bndMember;
	private CCBandbox bndBorrowingItem;
	private Doublebox txtBorrowingFine;

	@Override
	protected Class _getEntity() {
		return LibReturningItem.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}

	@Override
	protected void _initComponent() {
		super._initComponent();
		dtpdate = (Datebox) parameter.get(callerCtrl.toString() + "dtpDate");
		bndMember = (CCBandbox) parameter.get(callerCtrl.toString() + "bndMember");
	}

	@Override
	protected void _initEditor() {
		super._initEditor();
		bndBorrowingItem = getCCBandbox(getContainer(), "bndBorrowingItem");
		bndBorrowingItem.getTable().setHqlFilters(isReturnedFalse, new CCHibernateFilter() {
			@Override
			public String getFieldName() {
				return "borrowing.member_";
			}

			@Override
			public Object getFieldValue() {
				return bndMember.getObject();
			}
		}, new CCHibernateFilter() {
			@Override
			public String getFieldName() {
				return "id";
			}

			@Override
			public String getExpression() {
				return "id NOT IN (:ids)";
			}

			@Override
			public String getParameterName() {
				return "ids";
			}

			@Override
			public Object getFieldValue() {
				// get borrowed items from database
				List ids = extract(returningItemBo.getUnlimitedReturningItems((LibMember) bndMember.getObject()), "borrowingItem.id");
				// get borrowed items from screen
				ids.addAll(extract((List) parameter.get(callerCtrl.toString() + childsValue), "borrowingItem.id"));
				return ids;
			}
		});
	}

	@Override
	protected void _initListener() {
		super._initListener();
		bndBorrowingItem.addEventListenerOnSelect(new EventListener() {
			public void onEvent(Event event) throws Exception {
				_doUpdateBookInfo();
			}
		});
	}

	private void _doUpdateBookInfo() {
		LibBorrowingItem borrowingItem = bndBorrowingItem.getObject();
		if (isNotNull(borrowingItem)) {
			if (getMinTime(dtpdate.getValue()).after(borrowingItem.getDateReturn())) {
				txtBorrowingFine.setText(formatNumber(borrowingItem.getBorrowingFine()));
			}
		}
	}
}