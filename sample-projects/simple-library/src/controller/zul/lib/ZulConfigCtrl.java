package controller.zul.lib;

import model.bo.lib.LibConfigBo;
import model.obj.lib.LibConfig;
import cococare.framework.zk.CFZkCtrl;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulConfigCtrl extends CFZkCtrl {
	private LibConfigBo configBo;

	@Override
	protected Class _getEntity() {
		return LibConfig.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}

	@Override
	protected void _initObjEntity() {
		objEntity = configBo.loadLibConfig();
	}

	@Override
	protected boolean _doSaveEntity() {
		return configBo.saveConf(objEntity);
	}
}