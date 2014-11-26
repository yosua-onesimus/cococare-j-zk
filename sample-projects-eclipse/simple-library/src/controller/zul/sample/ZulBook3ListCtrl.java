package controller.zul.sample;

import model.obj.lib.LibBook;
import cococare.framework.zk.CFZkCtrl;

public class ZulBook3ListCtrl extends CFZkCtrl {
	@Override
	protected Class _getEntity() {
		return LibBook.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.LIST_FUNCTION;
	}
}