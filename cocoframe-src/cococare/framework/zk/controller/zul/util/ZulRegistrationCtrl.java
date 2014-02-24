package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCClass.getValue;
import cococare.common.CCFieldConfig.Accessible;
import cococare.common.CCFieldConfig.CompareRule;
import cococare.common.CCFieldConfig.Type;
import static cococare.common.CCFormat.getString;
import static cococare.common.CCFormat.getStringOrBlank;
import static cococare.common.CCLanguage.*;
import static cococare.framework.common.CFApplCtrl.LICENSE;
import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCEditor;
import static cococare.zk.CCEditor.requestFocusInWindow;
import static cococare.zk.CCMessage.showInformation;
import static cococare.zk.CCZk.addEventListenerOnClick;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulRegistrationCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    //if hasRegister then user may re-register..
    private boolean hasRegister = LICENSE.hasRegister();
    private Button btnRegister;
    private Textbox txtLock;
    private Textbox txtRegTo;
    private Textbox txtPass;
    private Textbox txtRunFirst;
    private Textbox txtRunTime;
    private Textbox txtMaxData;
    private Textbox txtRunLast;
    private Textbox txtRunUntil;
//</editor-fold>

    @Override
    protected Class _getEntity() {
        return null;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.FORM_FUNCTION;
    }

    @Override
    protected void _initEditor() {
        edtEntity = new CCEditor(getContainer());
        edtEntity.reg(null, null, txtLock, Accessible.READONLY, Type.TEXT, 0, null, CompareRule.NONE, null);
        edtEntity.reg("Register To", "Register To", txtRegTo, hasRegister ? Accessible.MANDATORY : Accessible.MANDATORY, Type.TEXT, 0, null, CompareRule.NONE, null);
        edtEntity.reg("Pass", "Pass", txtPass, hasRegister ? Accessible.MANDATORY : Accessible.MANDATORY, Type.TEXT, 0, null, CompareRule.NONE, null);
        edtEntity.reg(null, null, txtRunFirst, Accessible.READONLY, Type.TEXT, 0, null, CompareRule.NONE, null);
        edtEntity.reg(null, null, txtRunTime, Accessible.READONLY, Type.NUMERIC, 0, null, CompareRule.NONE, null);
        edtEntity.reg(null, null, txtMaxData, Accessible.READONLY, Type.NUMERIC, 0, null, CompareRule.NONE, null);
        edtEntity.reg(null, null, txtRunLast, Accessible.READONLY, Type.TEXT, 0, null, CompareRule.NONE, null);
        edtEntity.reg(null, null, txtRunUntil, Accessible.READONLY, Type.TEXT, 0, null, CompareRule.NONE, null);
    }

    @Override
    protected void _initListener() {
        super._initListener();
        if (hasRegister) {
            addEventListenerOnClick(btnRegister, new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    _doRegister();
                }
            });
        } else {
            addEventListenerOnClick(btnRegister, new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    _doRegister();
                }
            });
        }
    }

    private void _doRegister() {
        if (edtEntity.isValueValid()) {
            if (hasRegister = LICENSE.register(txtRegTo.getText(), txtPass.getText())) {
                _doUpdateEditor();
            }
            showInformation(turn(hasRegister ? Registration_success : Invalid_serial_number));
        }
    }

    @Override
    protected void _doUpdateEditor() {
        txtLock.setText(getString(getValue(LICENSE, "applLock")));
        txtRegTo.setText(getString(getValue(LICENSE, "applRegTo")));
        txtPass.setText(getStringOrBlank(getValue(LICENSE, "applPass")));
        txtRunFirst.setText(getString(getValue(LICENSE, "runFirst")));
        txtRunTime.setText(getString(getValue(LICENSE, "runTime")));
        txtMaxData.setText(getStringOrBlank(getValue(LICENSE, "maxData")));
        txtRunLast.setText(getString(getValue(LICENSE, "runLast")));
        txtRunUntil.setText(getStringOrBlank(getValue(LICENSE, "runUntil")));
        if (hasRegister) {
        } else {
            requestFocusInWindow(txtRegTo);
        }
    }
}