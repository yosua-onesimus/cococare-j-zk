package controller.zul.inv;

import model.obj.inv.InvInventory;
import cococare.framework.zk.CFZkCtrl;

public class ZulInventoryCtrl extends CFZkCtrl {
	@Override
	protected Class _getEntity() {
		return InvInventory.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}
}