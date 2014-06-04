package controller.zul.lib;

import static cococare.common.CCClass.copy;
import model.bo.lib.LibConfigBo;
import model.obj.lib.LibBook;
import cococare.framework.zk.CFZkCtrl;

public class ZulBookCtrl extends CFZkCtrl {
	private LibConfigBo configBo;

	@Override
	protected Class _getEntity() {
		return LibBook.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}

	@Override
	protected void _initObjEntity() {
		super._initObjEntity();
		copy(configBo.loadLibConfig(), objEntity);
	}
}