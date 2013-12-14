package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.framework.common.CFApplCtrl;
import cococare.framework.model.obj.util.UtilUser;
import cococare.framework.zk.CFZkCtrl;
import static cococare.zk.CCMessage.showError;
import static cococare.zk.CCZk.addEventListenerOnClick;
import static cococare.zk.CCZk.addEventListenerOnOk;
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
public class ZulLoginCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private Textbox txtUsername;
    private Textbox txtPassword;
    private Button btnLogin;
//</editor-fold>

    @Override
    protected Class _getEntity() {
        return UtilUser.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.FORM_FUNCTION;
    }

    @Override
    public boolean init() {
        CFApplCtrl.INSTANCE.logout();
        return super.init();
    }

    @Override
    protected void _initListener() {
        super._initListener();
        EventListener elLogin = new EventListener() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                _doLogin();
            }
        };
        addEventListenerOnOk(txtUsername, elLogin);
        addEventListenerOnOk(txtPassword, elLogin);
        addEventListenerOnClick(btnLogin, elLogin);
    }

    private void _doLogin() {
        if (_hasEdtEntity()) {
            if (_isValueValid()) {
                _getValueFromEditor();
                UtilUser user = (UtilUser) objEntity;
                if (updateCaller = CFApplCtrl.INSTANCE.login(user.getUsername(), user.getPassword())) {
                    _logger(objEntity);
                    _doCloseScreen();
                } else {
                    showError();
                }
            }
        }
    }
}