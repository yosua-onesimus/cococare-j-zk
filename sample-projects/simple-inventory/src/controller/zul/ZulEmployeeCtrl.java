package controller.zul;

import model.obj.InvEmployee;
import cococare.framework.zk.CFZkCtrl;

public class ZulEmployeeCtrl extends CFZkCtrl {
	@Override
	protected Class _getEntity() {
		return InvEmployee.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}
}