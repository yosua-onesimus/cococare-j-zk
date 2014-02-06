package model.mdl.bljr;

import static cococare.framework.zk.CFZkMap.getMenubarH;
import static cococare.framework.zk.CFZkMap.getMenubarV;
import cococare.common.CCLanguage;
import cococare.database.CCLoginInfo;
import cococare.framework.zk.CFZkMain;
import cococare.framework.zk.CFZkUae;
import cococare.framework.zk.controller.zul.util.ZulLoginCtrl;
import cococare.zk.CCMenubar;
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
	public boolean initInitialData() {
		super.initInitialData();
		CFZkUae uae = new CFZkUae();
		uae.reg("Bljr", "Employee", ZulEmployeeListCtrl.class);
		return _initInitialDataUaeUtility(uae).compile();
	}

	@Override
	protected void _applyUserConfig() {
		CFZkUae uae = new CFZkUae();
		uae.initMenuBar(MenuPosition.LEFT_SIDE.equals(MENU_POST) ? new CCMenubar(getMenubarV()) : new CCMenubar(getMenubarH()));
		uae.addMenuRoot(ZulLoginCtrl.class);
		uae.addMenuParent(CCLanguage.Archive, null, null);
		uae.addMenuChild("Employee", null, ZulEmployeeListCtrl.class);
		uae.changeMenuSide();
		_applyUserConfigUaeUtility(uae).compileMenu();
	}

	@Override
	public void showScreen() {
		// super.showScreen();// with login
		_applyUserConfig();// without login
	}
}