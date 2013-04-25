package controller.zul.util;

import static cococare.common.CCLogic.isNull;
import cococare.database.CCDatabaseConfig;
import cococare.framework.common.CFApplCtrl;
import cococare.framework.model.mdl.util.UtilityModule;
import cococare.framework.zk.CFZkCtrl;

public class ZulDatabaseSettingCtrl extends CFZkCtrl {
	@Override
	protected Class _getEntity() {
		return CCDatabaseConfig.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}

	@Override
	protected void _initObjEntity() {
		CCDatabaseConfig databaseConfig = UtilityModule.INSTANCE.getCCHibernate().getDatabaseConfig();
		if (isNull(databaseConfig)) {
			databaseConfig = new CCDatabaseConfig();
		}
		objEntity = databaseConfig;
	}

	@Override
	protected boolean _doSaveEntity() {
		CCDatabaseConfig databaseConfig = (CCDatabaseConfig) objEntity;
		updateCaller = CFApplCtrl.INSTANCE.openDatabaseConnection(databaseConfig, databaseConfig.isAutoCreateDatabase());
		if (updateCaller && !databaseConfig.isFirstRun() && databaseConfig.isInitInitialData()) {
			updateCaller = CFApplCtrl.INSTANCE.initInitialData();
		}
		return updateCaller;
	}

	@Override
	protected void _doCloseScreen() {
		super._doCloseScreen();
		if (updateCaller) {
			new ZulLoginCtrl().init();
		} else {
			CFApplCtrl.INSTANCE.reloadDatabaseConfig();
		}
	}
}