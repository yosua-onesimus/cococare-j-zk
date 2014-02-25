package cococare.framework.zk;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCFormat.*;
import cococare.common.CCLanguage;
import static cococare.common.CCLanguage.*;
import static cococare.common.CCMessage.logp;
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
        throw logp(new UnsupportedOperationException(turn(Not_supported_yet)));
    }

    @Override
    protected CFApplUae _initInitialDataUaeUtility(CFApplUae applUae) {
        applUae.reg(Utility, User_Group, ZulUserGroupListCtrl.class);
        applUae.reg(Utility, User, ZulUserListCtrl.class);
        applUae.reg(Utility, Change_Password, ZulChangePasswordCtrl.class);
        if (!HIBERNATE.getParameterClasses().isEmpty()) {
            applUae.reg(Utility, Parameter, ZulParameterListCtrl.class);
        }
        applUae.reg(Utility, Logger_History, ZulLoggerListCtrl.class);
        if (!HIBERNATE.getCustomizableClasses().isEmpty()) {
            applUae.reg(Utility, Screen_Setting, ZulScreenSettingListCtrl.class);
        }
        applUae.reg(Utility, Application_Setting, ZulApplicationSettingCtrl.class);
        applUae.reg(Utility, Database_Setting, ZulDatabaseSettingCtrl.class);
        if (LICENSE_ACTIVE) {
            //
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
        applUae.addMenuParent(Utility, null, null);
        applUae.addMenuChild(User_Group, null, ZulUserGroupListCtrl.class);
        applUae.addMenuChild(User, null, ZulUserListCtrl.class);
        applUae.addMenuChild(Change_Password, null, ZulChangePasswordCtrl.class);
        applUae.addMenuSeparator();
        if (!HIBERNATE.getParameterClasses().isEmpty()) {
            applUae.addMenuChild(Parameter, null, ZulParameterListCtrl.class);
        }
        applUae.addMenuChild(Logger_History, null, ZulLoggerListCtrl.class);
        applUae.addMenuSeparator();
        if (!HIBERNATE.getCustomizableClasses().isEmpty()) {
            applUae.addMenuChild(Screen_Setting, null, ZulScreenSettingListCtrl.class);
        }
        applUae.addMenuChild(Application_Setting, null, ZulApplicationSettingCtrl.class);
        applUae.addMenuChild(Database_Setting, null, ZulDatabaseSettingCtrl.class);
        if (LICENSE_ACTIVE) {
            applUae.addMenuSeparator();
            //
        }
        applUae.addMenuParent(Log_Out, null, ZulLoginCtrl.class);
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