package model.mdl.inv;

import org.zkoss.zul.Menubar;

import cococare.common.CCLanguage;
import cococare.datafile.CCFile;
import cococare.framework.common.CFApplCtrl;
import cococare.framework.zk.CFZkMap;
import cococare.framework.zk.CFZkUae;
import cococare.zk.CCMenubar;
import cococare.zk.CCSession;
import cococare.zk.database.CCLoginInfo;
import controller.zul.inv.ZulEmployeeListCtrl;
import controller.zul.inv.ZulInventoryListCtrl;
import controller.zul.util.ZulChangePasswordCtrl;
import controller.zul.util.ZulLoggerListCtrl;
import controller.zul.util.ZulUserGroupListCtrl;
import controller.zul.util.ZulUserListCtrl;

public class InventoryMain extends CFApplCtrl {
	@Override
	protected void _loadInternalSetting() {
		PLAT_MODE = PlatformMode.WEB;
		CCFile.initApplPath(CCSession.getWebRoot());
		super._loadInternalSetting();
		APPL_NAME = "simple-inventory";
		CCLoginInfo.INSTANCE = null;// menghiraukan login
	}

	@Override
	protected void _initScreen() {
	}

	@Override
	protected void _initDatabaseEntity() {
		super._initDatabaseEntity();
		InventoryModule.INSTANCE.init(HIBERNATE);
	}

	@Override
	protected void _applyUserConfig() {
		CFZkUae zkUae = new CFZkUae();
		zkUae.initMenuBar(new CCMenubar((Menubar) CFZkMap.getMenubarH()));
		zkUae.addMenuParent(CCLanguage.Archive, null, null);
		zkUae.addMenuChild("Inventory", null, ZulInventoryListCtrl.class);
		zkUae.addMenuChild("Employee", null, ZulEmployeeListCtrl.class);
		zkUae.changeMenuSide();
		zkUae.addMenuParent(CCLanguage.Utility, null, null);
		zkUae.addMenuChild(CCLanguage.User_Group, null, ZulUserGroupListCtrl.class);
		zkUae.addMenuChild(CCLanguage.User, null, ZulUserListCtrl.class);
		zkUae.addMenuChild(CCLanguage.Change_Password, null, ZulChangePasswordCtrl.class);
		zkUae.addMenuChild(CCLanguage.Logger_History, null, ZulLoggerListCtrl.class);
		zkUae.compileMenu();
	}

	@Override
	public void showScreen() {
		_applyUserConfig();// menghiraukan login
	}
}