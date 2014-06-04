package controller.zul.lib;

import model.obj.lib.LibBook;
import cococare.framework.zk.CFZkCtrl;

public class ZulBookListCtrl extends CFZkCtrl {
	@Override
	protected Class _getEntity() {
		return LibBook.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.LIST_FUNCTION;
	}
}