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
		CFZkUae uae = new CFZkUae();
		uae.reg("Inventory", "Inventory", ZulInventoryListCtrl.class);
		uae.reg("Inventory", "Employee", ZulEmployeeListCtrl.class);
		uae.reg("Inventory", "Ownership", ZulOwnershipListCtrl.class);
		return _initInitialDataUaeUtility(uae).compile();
	}

	@Override
	protected void _applyUserConfig() {
		CFZkUae uae = new CFZkUae();
		uae.initMenuBar(MenuPosition.LEFT_SIDE.equals(MENU_POST) ? new CCMenubar(getMenubarV()) : new CCMenubar(getMenubarH()));
		uae.addMenuRoot(ZulLoginCtrl.class);
		uae.addMenuParent(CCLanguage.Archive, null, null);
		uae.addMenuChild("Inventory", null, ZulInventoryListCtrl.class);
		uae.addMenuChild("Employee", null, ZulEmployeeListCtrl.class);
		uae.addMenuChild("Ownership", null, ZulOwnershipListCtrl.class);
		uae.changeMenuSide();
		_applyUserConfigUaeUtility(uae).compileMenu();
	}

	@Override
	public void showScreen() {
		super.showScreen();// with login
		// _applyUserConfig();//without login
	}
}