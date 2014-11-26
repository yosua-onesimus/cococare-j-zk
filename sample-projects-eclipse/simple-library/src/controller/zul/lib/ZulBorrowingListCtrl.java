package controller.zul.lib;

import model.obj.lib.LibBorrowing;
import cococare.framework.zk.CFZkCtrl;

public class ZulBorrowingListCtrl extends CFZkCtrl {
	@Override
	protected Class _getEntity() {
		return LibBorrowing.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.LIST_FUNCTION;
	}
}