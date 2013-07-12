package controller.zul.inv;

import static cococare.zk.CCZk.getTabpanel;
import static cococare.zk.CCZk.showPanel;

import java.util.ArrayList;
import java.util.List;

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
		parameter.put("employee", objEntity);
		parameter.put("employee_newEntity", newEntity);
		if (newEntity) {
			parameter.put("ownerships", new ArrayList());
		}
		ZulOwnershipListCtrl ownershipListCtrl = new ZulOwnershipListCtrl();
		ownershipListCtrl.with(parameter).with(this).init();
		showPanel(getTabpanel(getContainer(), "pnlOwnership"), ownershipListCtrl.getContainer());
	}

	@Override
	protected boolean _doSaveEntity() {
		if (newEntity) {
			return employeeBo.saveOrUpdate((InvEmployee) objEntity, (List) parameter.get("ownerships"));
		} else {
			return super._doSaveEntity();
		}
	}
}