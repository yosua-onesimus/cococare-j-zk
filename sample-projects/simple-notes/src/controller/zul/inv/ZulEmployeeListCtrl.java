package controller.zul.inv;

import model.obj.inv.InvEmployee;
import cococare.framework.zk.CFZkCtrl;

public class ZulEmployeeListCtrl extends CFZkCtrl {
	@Override
	protected Class _getEntity() {
		return InvEmployee.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.LIST_FUNCTION;
	}
}