package controller.zul.util;

import static cococare.database.CCLoginInfo.INSTANCE_getUserLogin;
import cococare.framework.model.bo.util.UtilUserBo;
import cococare.framework.model.obj.util.UtilUser;
import cococare.framework.zk.CFZkCtrl;

public class ZulChangePasswordCtrl extends CFZkCtrl {
	private UtilUserBo userBo;

	@Override
	protected Class _getEntity() {
		return UtilUser.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}

	@Override
	protected void _initObjEntity() {
		objEntity = INSTANCE_getUserLogin();
		((UtilUser) objEntity).setNewPassword(null);
		((UtilUser) objEntity).setRetypePassword(null);
	}

	@Override
	protected boolean _doSaveEntity() {
		return userBo.changePassword((UtilUser) objEntity);
	}

	@Override
	protected boolean _isSavedToClose() {
		return true;
	}
}