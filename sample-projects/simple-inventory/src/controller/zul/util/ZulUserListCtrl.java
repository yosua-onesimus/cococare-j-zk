package controller.zul.util;

import cococare.framework.model.obj.util.UtilUser;
import cococare.framework.zk.CFZkCtrl;

public class ZulUserListCtrl extends CFZkCtrl {
	@Override
	protected Class _getEntity() {
		return UtilUser.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.LIST_FUNCTION;
	}
}