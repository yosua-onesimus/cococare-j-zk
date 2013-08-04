package model.mdl.bljr;

import cococare.common.CCLanguage;
import cococare.database.CCLoginInfo;
import cococare.framework.zk.CFZkMain;
import cococare.framework.zk.CFZkMap;
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
		CFZkUae zkUae = new CFZkUae();
		zkUae.reg("Bljr", "Employee", ZulEmployeeListCtrl.class);
		return _initInitialDataUaeUtility(zkUae).compile();
	}

	@Override
	protected void _applyUserConfig() {
		CFZkUae zkUae = new CFZkUae();
		CFZkMap.getMenubarH().setVisible(true);
		zkUae.initMenuBar(new CCMenubar(CFZkMap.getMenubarH()));
		zkUae.addMenuRoot(ZulLoginCtrl.class);
		zkUae.addMenuParent(CCLanguage.Archive, null, null);
		zkUae.addMenuChild("Employee", null, ZulEmployeeListCtrl.class);
		zkUae.changeMenuSide();
		_applyUserConfigUaeUtility(zkUae).compileMenu();
	}

	@Override
	protected void _clearUserConfig() {
		CFZkUae zkUae = new CFZkUae();
		CFZkMap.getMenubarH().setVisible(true);
		zkUae.initMenuBar(new CCMenubar(CFZkMap.getMenubarH()));
		zkUae.compileMenu();
	}

	@Override
	public void showScreen() {
		// super.showScreen();// with login
		_applyUserConfig();// without login
	}
}