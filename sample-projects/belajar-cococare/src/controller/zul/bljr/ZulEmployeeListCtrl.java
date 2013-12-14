package controller.zul.bljr;

import model.obj.bljr.Employee;
import cococare.framework.zk.CFZkCtrl;

public class ZulEmployeeListCtrl extends CFZkCtrl {
	@Override
	protected Class _getEntity() {
		return Employee.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.LIST_FUNCTION;
	}
}