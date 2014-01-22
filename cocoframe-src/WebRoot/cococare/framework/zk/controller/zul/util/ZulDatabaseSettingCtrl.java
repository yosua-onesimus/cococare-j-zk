package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCLogic.coalesce;
import cococare.database.CCDatabaseConfig;
import cococare.framework.common.CFApplCtrl;
import cococare.framework.model.mdl.util.UtilityModule;
import cococare.framework.zk.CFZkCtrl;
import static cococare.zk.CCZk.addEventListenerOnChange_OnOk;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulDatabaseSettingCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private Combobox cmbDriver;
    private Textbox txtPort;
    private Textbox txtUsername;
//</editor-fold>

    @Override
    protected Class _getEntity() {
        return CCDatabaseConfig.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.FORM_FUNCTION;
    }

    @Override
    protected void _initObjEntity() {
        objEntity = coalesce(UtilityModule.INSTANCE.getCCHibernate().getDatabaseConfig(), new CCDatabaseConfig());
    }

    @Override
    protected void _initListener() {
        super._initListener();
        addEventListenerOnChange_OnOk(cmbDriver, new EventListener() {
            @Override
            public void onEvent(Event t) throws Exception {
                _doCmbDriver();
            }
        });
    }

    @Override
    protected boolean _doSaveEntity() {
        CCDatabaseConfig databaseConfig = (CCDatabaseConfig) objEntity;
        updateCaller = CFApplCtrl.INSTANCE.openDatabaseConnection(databaseConfig, databaseConfig.isAutoCreateDatabase());
        if (updateCaller && !databaseConfig.isFirstRun() && databaseConfig.isInitInitialData()) {
            updateCaller = CFApplCtrl.INSTANCE.initInitialData();
        }
        return updateCaller;
    }

    @Override
    protected void _doCloseScreen() {
        super._doCloseScreen();
        if (updateCaller) {
            new ZulLoginCtrl().init();
        } else {
            CFApplCtrl.INSTANCE.reloadDatabaseConfig();
        }
    }

    private void _doCmbDriver() {
        CCDatabaseConfig.SupportedDatabase supportedDatabase = CCDatabaseConfig.SupportedDatabase.values()[cmbDriver.getSelectedIndex()];
        txtPort.setText(supportedDatabase.getDefaultPort());
        txtUsername.setText(supportedDatabase.getDefaultUsername());
    }
}