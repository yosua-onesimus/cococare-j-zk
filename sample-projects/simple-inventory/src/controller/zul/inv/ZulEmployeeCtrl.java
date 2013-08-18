package controller.zul.inv;

import model.bo.inv.InvEmployeeBo;
import model.obj.inv.InvEmployee;
import cococare.framework.zk.CFZkCtrl;

public class ZulEmployeeCtrl extends CFZkCtrl {
	//
	private InvEmployeeBo employeeBo;

	@Override
	protected Class _getEntity() {
		return InvEmployee.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}

	@Override
	protected void _initComponent() {
		super._initComponent();
		_addChildScreen("employee", new ZulOwnershipListCtrl(), "pnlOwnership");
	}

	@Override
	protected boolean _doSaveEntity() {
		return employeeBo.saveOrUpdate((InvEmployee) objEntity, _getEntityChilds());
	}
}