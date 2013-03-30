package controller.zul;

import model.obj.InvInventory;
import cococare.framework.zk.CFZkCtrl;

public class ZulInventoryListCtrl extends CFZkCtrl {
	@Override
	protected Class _getEntity() {
		return InvInventory.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.LIST_FUNCTION;
	}
}