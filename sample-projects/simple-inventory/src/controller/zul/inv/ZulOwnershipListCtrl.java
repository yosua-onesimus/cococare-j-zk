package controller.zul.inv;

import model.bo.inv.InvRecalculationBo;
import model.obj.inv.InvOwnership;
import cococare.framework.zk.CFZkCtrl;

public class ZulOwnershipListCtrl extends CFZkCtrl {
	private InvRecalculationBo recalculationBo;

	@Override
	protected Class _getEntity() {
		return InvOwnership.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.LIST_FUNCTION;
	}

	@Override
	protected boolean _doDeleteEntity() {
		return super._doDeleteEntity() && tblEntity.saveOrUpdate(recalculationBo.recalcInventory(((InvOwnership) objEntity).getInventory()));
	}
}