package controller.zul.inv;

import model.bo.inv.InvRecalculationBo;
import model.obj.inv.InvOwnership;
import cococare.framework.zk.CFZkCtrl;

public class ZulOwnershipCtrl extends CFZkCtrl {
	private InvRecalculationBo recalculationBo;

	@Override
	protected Class _getEntity() {
		return InvOwnership.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}

	@Override
	protected boolean _doSaveEntity() {
		return super._doSaveEntity() && edtEntity.saveOrUpdate(recalculationBo.recalcInventory(((InvOwnership) objEntity).getInventory()));
	}
}