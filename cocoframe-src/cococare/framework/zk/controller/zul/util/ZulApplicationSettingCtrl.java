package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.common.CCFieldConfig.Accessible;
import cococare.database.CCEntity;
import static cococare.datafile.CCFile.writeObject;
import cococare.framework.common.CFApplCtrl;
import cococare.framework.model.bo.util.UtilConfigBo;
import cococare.framework.model.obj.util.UtilConfAppl;
import cococare.framework.model.obj.util.UtilConfServ;
import cococare.framework.zk.controller.zul.ZulDefaultCtrl;
import static cococare.zk.CCZk.addListener;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulApplicationSettingCtrl extends ZulDefaultCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private UtilConfigBo configBo;
    //
    private Combobox txtApplLookAndFeel;
    //
    private Checkbox txtFileTransferEnable;
    private Textbox txtFileTransferHostname;
    private Intbox txtFileTransferPort;
    private Textbox txtFileTransferUsername;
    private Textbox txtFileTransferPassword;
    //
    private Checkbox txtMailSendMailEnable;
    private Checkbox txtMailBugReportEnable;
    private Textbox txtMailMailSmtpHost;
    private Textbox txtMailGmailUsername;
    private Textbox txtMailGmailPassword;
//</editor-fold>

    @Override
    protected void _initListener() {
        super._initListener();
        if (objEntity instanceof UtilConfServ) {
            EventListener alUpdateAccessible = new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    _doUpdateAccessible(event.getTarget());
                }
            };
            addListener(txtFileTransferEnable, alUpdateAccessible);
            addListener(txtMailSendMailEnable, alUpdateAccessible);
            addListener(txtMailBugReportEnable, alUpdateAccessible);
        }
    }

    private void _doUpdateAccessible(Component component) {
        if (component.equals(txtFileTransferEnable)) {
            Accessible accessible = txtFileTransferEnable.isChecked() ? Accessible.MANDATORY : Accessible.NORMAL;
            edtEntity.setAccessible(txtFileTransferHostname, accessible);
            edtEntity.setAccessible(txtFileTransferPort, accessible);
            edtEntity.setAccessible(txtFileTransferUsername, accessible);
            edtEntity.setAccessible(txtFileTransferPassword, accessible);
        } else if (component.equals(txtMailSendMailEnable) || component.equals(txtMailBugReportEnable)) {
            Accessible accessible = txtMailSendMailEnable.isChecked() || txtMailBugReportEnable.isChecked() ? Accessible.MANDATORY : Accessible.NORMAL;
            edtEntity.setAccessible(txtMailMailSmtpHost, accessible);
            edtEntity.setAccessible(txtMailGmailUsername, accessible);
            edtEntity.setAccessible(txtMailGmailPassword, accessible);
        }
    }

    @Override
    protected boolean _doSaveEntity() {
        if (updateCaller = configBo.saveConf(objEntity)) {
            if (objEntity instanceof UtilConfAppl) {
                updateCaller = writeObject((CCEntity) objEntity, CFApplCtrl.FILE_APPL_CONF);
            }
            if ((objEntity instanceof UtilConfAppl) || (objEntity instanceof UtilConfServ)) {
                CFApplCtrl.INSTANCE.updateNonContent(objEntity);
            }
        }
        return updateCaller;
    }

    @Override
    protected String _getTabTitle() {
        return _getEntityLabel();
    }

    @Override
    protected void _doUpdateEditor() {
        super._doUpdateEditor();
        if (objEntity instanceof UtilConfAppl) {
            edtEntity.setAccessible(txtApplLookAndFeel, Accessible.READONLY_SET_NULL);
        }
        if (objEntity instanceof UtilConfServ) {
            _doUpdateAccessible(txtFileTransferEnable);
            _doUpdateAccessible(txtMailSendMailEnable);
        }
    }
}