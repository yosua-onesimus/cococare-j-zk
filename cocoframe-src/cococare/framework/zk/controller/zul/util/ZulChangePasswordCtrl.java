package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCMessage.logp;
import static cococare.database.CCLoginInfo.INSTANCE_getUserLogin;
import cococare.framework.model.bo.util.UtilUserBo;
import cococare.framework.model.obj.util.UtilUser;
import cococare.framework.zk.CFZkCtrl;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulChangePasswordCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private UtilUserBo userBo;
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
    protected void _initObjEntity() {
        objEntity = INSTANCE_getUserLogin();
    }

    @Override
    protected void _doUpdateEditor() {
        try {
            ((UtilUser) objEntity).setNewPassword(null);
            ((UtilUser) objEntity).setRetypePassword(null);
        } catch (Exception exception) {
            logp(exception);
        }
        super._doUpdateEditor();
    }

    @Override
    protected boolean _doSaveEntity() {
        return userBo.changePassword((UtilUser) objEntity);
    }

    @Override
    protected boolean _isSavedToClose() {
        return true;
    }
}