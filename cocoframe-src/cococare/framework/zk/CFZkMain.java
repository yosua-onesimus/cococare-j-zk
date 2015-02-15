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
    public void end() {
        throw logp(new UnsupportedOperationException(turn(Not_supported_yet)));
    }

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
            uae.reg(Utility, Export_Import, ZulExportImportCtrl.class);
        }
        if (!HIBERNATE.getAuditableClasses().isEmpty()) {
            uae.reg(Utility, Audit_Trail, ZulAuditTrailListCtrl.class);
        }
        uae.reg(Utility, Logger_History, ZulLoggerListCtrl.class);
        if (!HIBERNATE.getCustomizableClasses().isEmpty()) {
            uae.reg(Utility, Screen_Setting, ZulScreenSettingListCtrl.class);
        }
        uae.reg(Utility, Application_Setting, ZulApplicationSettingListCtrl.class);
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
            getCompName().setValue(wordWrap(false, getStringOrBlank(confAppl.getCompanyName()), getStringOrBlank(confAppl.getCompanyAddress())));
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
        uae.changeMenuSide();
        uae.addMenuParent(Utility, "/img/icon-menu-parent.png", null);
        uae.addMenuChild(User_Group, "/img/icon-menu-user-group.png", ZulUserGroupListCtrl.class);
        uae.addMenuChild(User, "/img/icon-menu-user.png", ZulUserListCtrl.class);
        uae.addMenuChild(Change_Password, "/img/icon-menu-change-password.png", ZulChangePasswordCtrl.class);
        uae.addMenuSeparator();
        if (!HIBERNATE.getParameterClasses().isEmpty()) {
            uae.addMenuChild(Parameter, "/img/icon-menu-parameter.png", ZulParameterListCtrl.class);
            uae.addMenuChild(Export_Import, "/img/icon-menu-export-import.png", ZulExportImportCtrl.class);
        }
        if (!HIBERNATE.getAuditableClasses().isEmpty()) {
            uae.addMenuChild(Audit_Trail, "/img/icon-menu-audit-trail.png", ZulAuditTrailListCtrl.class);
        }
        uae.addMenuChild(Logger_History, "/img/icon-menu-logger-history.png", ZulLoggerListCtrl.class);
        uae.addMenuSeparator();
        if (!HIBERNATE.getCustomizableClasses().isEmpty()) {
            uae.addMenuChild(Screen_Setting, "/img/icon-menu-screen-setting.png", ZulScreenSettingListCtrl.class);
        }
        uae.addMenuChild(Application_Setting, "/img/icon-menu-application-setting.png", ZulApplicationSettingListCtrl.class);
        uae.addMenuChild(Database_Setting, "/img/icon-menu-database-setting.png", ZulDatabaseSettingCtrl.class);
        if (LICENSE_ACTIVE) {
            uae.addMenuSeparator();
            uae.addMenuChild(Registration, "/img/icon-menu-registration.png", ZulRegistrationCtrl.class);
        }
        uae.addMenuParent(Log_Out, "/img/icon-menu-log-out.png", ZulLoginCtrl.class);
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