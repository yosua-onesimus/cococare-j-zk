package model.mdl.inv;

import static cococare.framework.zk.CFZkMap.getMenubarH;
import static cococare.framework.zk.CFZkMap.getMenubarV;
import cococare.common.CCLanguage;
import cococare.framework.zk.CFZkMain;
import cococare.framework.zk.CFZkUae;
import cococare.framework.zk.controller.zul.util.ZulLoginCtrl;
import cococare.zk.CCMenubar;
import controller.zul.inv.ZulEmployeeListCtrl;
import controller.zul.inv.ZulInventoryListCtrl;
import controller.zul.inv.ZulOwnershipListCtrl;

public class InventoryMain extends CFZkMain {
	@Override
	protected void _loadInternalSetting() {
		APPL_CODE = "smpl-invntry-zk";
		APPL_NAME = "simple-inventory";
		super._loadInternalSetting();
		// CCLoginInfo.INSTANCE = null;//without login
	}

	@Override
	protected void _initDatabaseEntity() {
		super._initDatabaseEntity();
		InventoryModule.INSTANCE.init(HIBERNATE);
	}

	@Override
	public boolean initInitialData() {
		super.initInitialData();
		CFZkUae zkUae = new CFZkUae();
		zkUae.reg("Inventory", "Inventory", ZulInventoryListCtrl.class);
		zkUae.reg("Inventory", "Employee", ZulEmployeeListCtrl.class);
		zkUae.reg("Inventory", "Ownership", ZulOwnershipListCtrl.class);
		return _initInitialDataUaeUtility(zkUae).compile();
	}

	@Override
	protected void _applyUserConfig() {
		CFZkUae zkUae = new CFZkUae();
		zkUae.initMenuBar(MenuPosition.LEFT_SIDE.equals(MENU_POST) ? new CCMenubar(getMenubarV()) : new CCMenubar(getMenubarH()));
		zkUae.addMenuRoot(ZulLoginCtrl.class);
		zkUae.addMenuParent(CCLanguage.Archive, null, null);
		zkUae.addMenuChild("Inventory", null, ZulInventoryListCtrl.class);
		zkUae.addMenuChild("Employee", null, ZulEmployeeListCtrl.class);
		zkUae.addMenuChild("Ownership", null, ZulOwnershipListCtrl.class);
		zkUae.changeMenuSide();
		_applyUserConfigUaeUtility(zkUae).compileMenu();
	}

	@Override
	public void showScreen() {
		super.showScreen();// with login
		// _applyUserConfig();//without login
	}
}