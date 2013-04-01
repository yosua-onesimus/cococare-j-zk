package controller.zul.util;

import cococare.framework.model.obj.util.UtilUserGroup;
import cococare.framework.zk.CFZkCtrl;

public class ZulUserGroupListCtrl extends CFZkCtrl {
	@Override
	protected Class _getEntity() {
		return UtilUserGroup.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.LIST_FUNCTION;
	}
}