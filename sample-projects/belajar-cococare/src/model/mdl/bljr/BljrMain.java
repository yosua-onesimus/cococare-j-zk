package model.mdl.bljr;

import cococare.common.CCLanguage;
import cococare.database.CCLoginInfo;
import cococare.framework.common.CFApplUae;
import cococare.framework.zk.CFZkMain;
import controller.zul.bljr.ZulEmployeeListCtrl;

public class BljrMain extends CFZkMain {
	@Override
	protected void _loadInternalSetting() {
		super._loadInternalSetting();
		CCLoginInfo.INSTANCE = null;// without login
	}

	@Override
	protected void _initDatabaseEntity() {
		super._initDatabaseEntity();
		BljrModule.INSTANCE.init(HIBERNATE);
	}

	@Override
	protected void _initInitialUaeBody(CFApplUae uae) {
		uae.reg("Bljr", "Employee", ZulEmployeeListCtrl.class);
	}

	@Override
	protected void _applyUserConfigUaeBody(CFApplUae uae) {
		uae.addMenuParent(CCLanguage.Archive, "/img/icon-menu-parent.png", null);
		uae.addMenuChild("Employee", "/img/icon-menu-child.png", ZulEmployeeListCtrl.class);
	}

	@Override
	public void showScreen() {
		// super.showScreen();// with login
		_applyUserConfig();// without login
	}
}