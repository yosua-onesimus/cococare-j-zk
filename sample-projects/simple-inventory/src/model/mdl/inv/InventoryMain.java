package model.mdl.inv;

import cococare.common.CCLanguage;
import cococare.framework.zk.CFZkMain;
import cococare.framework.zk.CFZkMap;
import cococare.framework.zk.CFZkUae;
import cococare.framework.zk.controller.zul.util.ZulLoginCtrl;
import cococare.framework.zk.controller.zul.util.ZulUserListCtrl;
import cococare.zk.CCMenubar;
import controller.zul.inv.ZulEmployeeListCtrl;
import controller.zul.inv.ZulInventoryListCtrl;

public class InventoryMain extends CFZkMain {
	@Override
	protected void _loadInternalSetting() {
		APPL_CODE = "smpl-invntry-zul";
		APPL_NAME = "simple-inventory";
		// CCLoginInfo.INSTANCE = null;//without login
		super._loadInternalSetting();
	}

	@Override
	protected void _initDatabaseEntity() {
		super._initDatabaseEntity();
		InventoryModule.INSTANCE.init(HIBERNATE);
	}

	@Override
	public boolean initInitialData() {
		CFZkUae zkUae = new CFZkUae();
		zkUae.reg("Inventory", "Inventory", ZulInventoryListCtrl.class);
		zkUae.reg("Inventory", "Employee", ZulEmployeeListCtrl.class);
		// swingUae.reg("Inventory", "Ownership",
		// ZulOwnershipListCtrl.class);
		return _initInitialDataUaeUtility(zkUae).compile();
	}

	@Override
	protected void _applyUserConfig() {
		super._applyUserConfig();
		CFZkUae zkUae = new CFZkUae();
		CFZkMap.getMenubarH().setVisible(true);
		zkUae.initMenuBar(new CCMenubar(CFZkMap.getMenubarH()));
		zkUae.addMenuRoot(ZulLoginCtrl.class, ZulUserListCtrl.class);
		zkUae.addMenuParent(CCLanguage.Archive, null, null);
		zkUae.addMenuChild("Inventory", null, ZulInventoryListCtrl.class);
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
		super.showScreen();// with login
		// _applyUserConfig();//without login
	}
}