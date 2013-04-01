package controller.zul.util;

import cococare.framework.model.obj.util.UtilUser;
import cococare.framework.zk.CFZkCtrl;

public class ZulChangePasswordCtrl extends CFZkCtrl {
	@Override
	protected Class _getEntity() {
		return UtilUser.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}
}