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
import cococare.framework.model.obj.util.UtilConfAppl.MenuPosition;
import static cococare.framework.zk.CFZkMap.*;
import cococare.framework.zk.controller.zul.util.*;
import cococare.zk.CCMenubar;
import static cococare.zk.CCSession.getWebRoot;
import static cococare.zk.CCZk.setImageContent;
import cococare.zk.database.CCLoginInfo;
import java.io.File;
//</editor-fold>

/**
 * CFZkMain is an abstract class which functions as an application controller,
 * in charge of controlling the flow of applications in general.
 *
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
    protected CFApplUae _initInitialUaeBegin() {
        return new CFZkUae();
    }

    @Override
    protected boolean _initInitialUaeEnd(CFApplUae uae) {
        uae.reg(Utility, User_Group, ZulUserGroupListCtrl.class);
        uae.reg(Utility, User, ZulUserListCtrl.class);
        uae.reg(Utility, Change_Password, ZulChangePasswordCtrl.class);
        if (!HIBERNATE.getParameterClasses().isEmpty()) {
            uae.reg(Utility, Parameter, ZulParameterListCtrl.class);
        }
        uae.reg(Utility, Logger_History, ZulLoggerListCtrl.class);
        if (!HIBERNATE.getCustomizableClasses().isEmpty()) {
            uae.reg(Utility, Screen_Setting, ZulScreenSettingListCtrl.class);
        }
        uae.reg(Utility, Application_Setting, ZulApplicationSettingCtrl.class);
        uae.reg(Utility, Database_Setting, ZulDatabaseSettingCtrl.class);
        if (LICENSE_ACTIVE) {
            uae.reg(Utility, Registration, ZulRegistrationCtrl.class);
        }
        return uae.compile();
    }

    @Override
    public boolean showDatabaseSettingScreen() {
        return new ZulDatabaseSettingCtrl().init();
    }

    @Override
    public void updateNonContent(Object object) {
        if (object instanceof UtilConfAppl) {
            load(CCLanguage.LanguagePack.values()[parseInt(confAppl.getApplLanguage())]);
            setImageContent(getCompLogo(), confAppl.getCompanyLogo());
            getCompName().setValue(wordWrap(new String[]{getStringOrBlank(confAppl.getCompanyName()), getStringOrBlank(confAppl.getCompanyAddress())}, false));
        }
    }

    @Override
    protected CFApplUae _applyUserConfigUaeBegin() {
        CFZkUae uae = new CFZkUae();
        uae.initMenuBar(new CCMenubar(MenuPosition.LEFT_SIDE.ordinal() == confAppl.getApplMenuPosition().intValue() ? getMenubarV() : getMenubarH()));
        uae.addMenuRoot(ZulLoginCtrl.class);
        return uae;
    }

    @Override
    protected void _applyUserConfigUaeEnd(CFApplUae uae) {
        uae.addMenuParent(Utility, null, null);
        uae.addMenuChild(User_Group, null, ZulUserGroupListCtrl.class);
        uae.addMenuChild(User, null, ZulUserListCtrl.class);
        uae.addMenuChild(Change_Password, null, ZulChangePasswordCtrl.class);
        uae.addMenuSeparator();
        if (!HIBERNATE.getParameterClasses().isEmpty()) {
            uae.addMenuChild(Parameter, null, ZulParameterListCtrl.class);
        }
        uae.addMenuChild(Logger_History, null, ZulLoggerListCtrl.class);
        uae.addMenuSeparator();
        if (!HIBERNATE.getCustomizableClasses().isEmpty()) {
            uae.addMenuChild(Screen_Setting, null, ZulScreenSettingListCtrl.class);
        }
        uae.addMenuChild(Application_Setting, null, ZulApplicationSettingCtrl.class);
        uae.addMenuChild(Database_Setting, null, ZulDatabaseSettingCtrl.class);
        if (LICENSE_ACTIVE) {
            uae.addMenuSeparator();
            uae.addMenuChild(Registration, null, ZulRegistrationCtrl.class);
        }
        uae.addMenuParent(Log_Out, null, ZulLoginCtrl.class);
        uae.compileMenu();
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