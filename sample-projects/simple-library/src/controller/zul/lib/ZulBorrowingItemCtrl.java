package controller.zul.lib;

import static cococare.common.CCClass.extract;
import static cococare.common.CCFormat.formatNumber;
import static cococare.common.CCLogic.isNotNull;
import static cococare.zk.CCZk.addListener;
import static model.obj.lib.LibFilter.isBorrowedFalse;
import static model.obj.lib.LibFilter.isSuspendFalse;

import java.util.List;

import model.bo.lib.LibBorrowingItemBo;
import model.obj.lib.LibBook;
import model.obj.lib.LibBorrowingItem;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;

import cococare.framework.model.obj.util.UtilFilter.isIdNotInIds;
import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCBandbox;

public class ZulBorrowingItemCtrl extends CFZkCtrl {
	private LibBorrowingItemBo borrowingItemBo;
	private Datebox dtpdate;
	private CCBandbox bndBook;
	private Doublebox txtBorrowingCost;
	private Datebox dtpDateReturn;
	private Doublebox txtBorrowingFine;

	@Override
	protected Class _getEntity() {
		return LibBorrowingItem.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}

	@Override
	protected void _initComponent() {
		super._initComponent();
		dtpdate = (Datebox) parameter.get(callerCtrl.toString() + "dtpDate");
	}

	@Override
	protected void _initEditor() {
		super._initEditor();
		bndBook.getTable().setHqlFilters(isSuspendFalse, isBorrowedFalse, new isIdNotInIds() {
			@Override
			public Object getFieldValue() {
				// get borrowed books from screen
				return extract((List) parameter.get(callerCtrl.toString() + childsValue), "book.id");
			}
		});
	}

	@Override
	protected void _initListener() {
		super._initListener();
		addListener(bndBook.getBandbox(), new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				_doUpdateBookInfo();
			}
		});
	}

	private void _doUpdateBookInfo() {
		LibBook book = bndBook.getObject();
		if (isNotNull(book)) {
			txtBorrowingCost.setText(formatNumber(book.getBorrowingCost()));
			dtpDateReturn.setValue(borrowingItemBo.calculateDateReturn(dtpdate.getValue(), book.getBorrowingLimit()));
			txtBorrowingFine.setText(formatNumber(book.getBorrowingFine()));
		}
	}
}