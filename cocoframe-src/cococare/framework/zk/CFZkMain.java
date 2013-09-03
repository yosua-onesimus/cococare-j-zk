package cococare.framework.zk;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCFormat.*;
import cococare.common.CCLanguage;
import static cococare.common.CCLanguage.*;
import static cococare.datafile.CCFile.*;
import cococare.framework.common.CFApplCtrl;
import cococare.framework.common.CFApplUae;
import cococare.framework.model.obj.util.UtilConfAppl;
import static cococare.framework.zk.CFZkMap.*;
import cococare.framework.zk.controller.zul.util.*;
import static cococare.zk.CCSession.getWebRoot;
import static cococare.zk.CCZk.setImageContent;
import cococare.zk.database.CCLoginInfo;
import java.io.File;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public abstract class CFZkMain extends CFApplCtrl {

//<editor-fold defaultstate="collapsed" desc=" public method ">
    @Override
    protected void _loadInternalSetting() {
        PLAT_MODE = PlatformMode.WEB;
        initApplPath(getWebRoot());
        FILE_APPL_CONF = new File(getFileUserConfPath(), S_APPL_CONF);
        FILE_APPL_LCNS = new File(getFileSystConfPath(), S_APPL_LCNS);
        FILE_DTBS_CONF = new File(getFileSystConfPath(), S_DTBS_CONF);
        CCLoginInfo.INSTANCE = new CCLoginInfo();
        //
        super._loadInternalSetting();
    }

    @Override
    protected void _initScreen() {
        setImageContent(getApplLogo(), APPL_LOGO);
        getApplName().setValue(APPL_NAME);
        getApplVer().setValue(APPL_VER);
        _clearUserConfig();
    }

    @Override
    public void end() {
        throw new UnsupportedOperationException(turn(Not_supported_yet));
    }

    @Override
    protected CFApplUae _initInitialDataUaeUtility(CFApplUae applUae) {
        applUae.reg(turn(Utility), turn(User_Group), ZulUserGroupListCtrl.class);
        applUae.reg(turn(Utility), turn(User), ZulUserListCtrl.class);
        applUae.reg(turn(Utility), turn(Change_Password), ZulChangePasswordCtrl.class);
        applUae.reg(turn(Utility), turn(Logger_History), ZulLoggerListCtrl.class);
        applUae.reg(turn(Utility), turn(Application_Setting), ZulApplicationSettingCtrl.class);
        applUae.reg(turn(Utility), turn(Database_Setting), ZulDatabaseSettingCtrl.class);
        if (LICENSE_ACTIVE) {
            //applUae.reg(turn(Utility), turn(Registration), null);
        }
        return applUae;
    }

    @Override
    public boolean showDatabaseSettingScreen() {
        return new ZulDatabaseSettingCtrl().init();
    }

    @Override
    public void updateNonContent(Object object) {
        if (object instanceof UtilConfAppl) {
            UtilConfAppl confAppl = (UtilConfAppl) object;
            load(CCLanguage.LanguagePack.values()[parseInt(confAppl.getApplLanguage())]);
            setImageContent(getCompLogo(), confAppl.getCompanyLogo());
            getCompName().setValue(wordWrap(new String[]{getStringOrBlank(confAppl.getCompanyName()), getStringOrBlank(confAppl.getCompanyAddress())}, false));
        }
    }

    @Override
    protected CFApplUae _applyUserConfigUaeUtility(CFApplUae applUae) {
        applUae.addMenuParent(turn(Utility), null, null);
        applUae.addMenuChild(turn(User_Group), null, ZulUserGroupListCtrl.class);
        applUae.addMenuChild(turn(User), null, ZulUserListCtrl.class);
        applUae.addMenuChild(turn(Change_Password), null, ZulChangePasswordCtrl.class);
        applUae.addMenuSeparator();
        applUae.addMenuChild(turn(Logger_History), null, ZulLoggerListCtrl.class);
        applUae.addMenuChild(turn(Application_Setting), null, ZulApplicationSettingCtrl.class);
        applUae.addMenuChild(turn(Database_Setting), null, ZulDatabaseSettingCtrl.class);
        applUae.addMenuParent(turn(Log_Out), null, ZulLoginCtrl.class);
        return applUae;
    }

    @Override
    protected void _clearUserConfig() {
        getMenubarH().getParent().setVisible(false);
        getMenubarV().setVisible(false);
    }

    @Override
    protected boolean _showLoginScreen() {
        return new ZulLoginCtrl().init();
    }
//</editor-fold>
}