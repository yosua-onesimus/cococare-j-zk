package controller.zul.lib;

import model.obj.lib.LibReturning;
import cococare.framework.zk.CFZkCtrl;

public class ZulReturningListCtrl extends CFZkCtrl {
	@Override
	protected Class _getEntity() {
		return LibReturning.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.LIST_FUNCTION;
	}
}