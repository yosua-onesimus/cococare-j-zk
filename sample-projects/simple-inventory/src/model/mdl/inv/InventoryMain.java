package model.mdl.inv;

import java.io.File;

import cococare.common.CCLanguage;
import cococare.datafile.CCFile;
import cococare.framework.common.CFApplCtrl;
import cococare.framework.model.bo.util.UtilConfigBo;
import cococare.framework.model.obj.util.UtilConfAppl;
import cococare.framework.zk.CFZkMap;
import cococare.framework.zk.CFZkUae;
import cococare.zk.CCMenubar;
import cococare.zk.CCSession;
import cococare.zk.database.CCLoginInfo;
import controller.zul.inv.ZulEmployeeListCtrl;
import controller.zul.inv.ZulInventoryListCtrl;
import controller.zul.util.ZulChangePasswordCtrl;
import controller.zul.util.ZulDatabaseSettingCtrl;
import controller.zul.util.ZulLoggerListCtrl;
import controller.zul.util.ZulLoginCtrl;
import controller.zul.util.ZulUserGroupListCtrl;
import controller.zul.util.ZulUserListCtrl;

public class InventoryMain extends CFApplCtrl {
	@Override
	protected void _loadInternalSetting() {
		PLAT_MODE = PlatformMode.WEB;
		CCFile.initApplPath(CCSession.getWebRoot());
		CCLoginInfo.INSTANCE = new CCLoginInfo();
		super._loadInternalSetting();
		APPL_NAME = "simple-inventory";
		// CCLoginInfo.INSTANCE = null;// without login
	}

	@Override
	protected void _loadExternalSetting() {
		super._loadExternalSetting();
		File file = CCFile.getFileSystConfFile(S_APPL_CONF);
		if (file.exists()) {
			updateNonContent((UtilConfAppl) CCFile.readObject(file));
		}
	}

	@Override
	protected void _initScreen() {
		_clearUserConfig();
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
		// swingUae.reg("Inventory", "Ownership", ZulOwnershipListCtrl.class);
		zkUae.reg(CCLanguage.Utility, CCLanguage.User_Group, ZulUserGroupListCtrl.class);
		zkUae.reg(CCLanguage.Utility, CCLanguage.User, ZulUserListCtrl.class);
		zkUae.reg(CCLanguage.Utility, CCLanguage.Change_Password, ZulChangePasswordCtrl.class);
		zkUae.reg(CCLanguage.Utility, CCLanguage.Logger_History, ZulLoggerListCtrl.class);
		// swingUae.reg(CCLanguage.Utility, CCLanguage.Application_Setting,
		// ZulApplicationSettingCtrl.class);
		zkUae.reg(CCLanguage.Utility, CCLanguage.Database_Setting, ZulDatabaseSettingCtrl.class);
		return zkUae.compile();
	}

	@Override
	public boolean showDatabaseSettingScreen() {
		return super.showDatabaseSettingScreen();
	}

	@Override
	public void updateNonContent(Object object) {
		super.updateNonContent(object);
	}

	@Override
	protected void _applyUserConfig() {
		UtilConfAppl confAppl = new UtilConfigBo().loadConfAppl();
		updateNonContent(confAppl);

		CFZkUae zkUae = new CFZkUae();
		CFZkMap.getMenubarH().setVisible(true);
		zkUae.initMenuBar(new CCMenubar(CFZkMap.getMenubarH()));
		zkUae.addMenuRoot(ZulLoginCtrl.class, ZulUserListCtrl.class);
		zkUae.addMenuParent(CCLanguage.Archive, null, null);
		zkUae.addMenuChild("Inventory", null, ZulInventoryListCtrl.class);
		zkUae.addMenuChild("Employee", null, ZulEmployeeListCtrl.class);
		zkUae.changeMenuSide();
		zkUae.addMenuParent(CCLanguage.Utility, null, null);
		zkUae.addMenuChild(CCLanguage.User_Group, null, ZulUserGroupListCtrl.class);
		zkUae.addMenuChild(CCLanguage.User, null, ZulUserListCtrl.class);
		zkUae.addMenuChild(CCLanguage.Change_Password, null, ZulChangePasswordCtrl.class);
		zkUae.addMenuChild(CCLanguage.Logger_History, null, ZulLoggerListCtrl.class);
		zkUae.addMenuChild(CCLanguage.Log_Out, null, ZulLoginCtrl.class);
		zkUae.compileMenu();
	}

	@Override
	protected void _clearUserConfig() {
		CFZkUae zkUae = new CFZkUae();
		CFZkMap.getMenubarH().setVisible(true);
		zkUae.initMenuBar(new CCMenubar(CFZkMap.getMenubarH()));
		zkUae.addMenuRoot(ZulLoginCtrl.class);
		zkUae.addMenuParent(CCLanguage.Home, null, ZulLoginCtrl.class);
		zkUae.compileMenu();
	}

	@Override
	protected boolean _showLoginScreen() {
		return new ZulLoginCtrl().init();
	}

	@Override
	public void showScreen() {
		super.showScreen();// with login
		// _applyUserConfig();//without login
	}
}