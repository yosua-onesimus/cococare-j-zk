package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCClass.getLabel;
import static cococare.common.CCLogic.isNotNull;
import cococare.framework.model.obj.util.UtilConfig;
import cococare.framework.zk.CFZkCtrl;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulParameterCtrl extends CFZkCtrl {

    @Override
    protected Class _getClass() {
        return ZulParameterCtrl.class;
    }

    @Override
    protected Class _getEntity() {
        return isNotNull(objEntity) ? objEntity.getClass() : UtilConfig.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.FORM_FUNCTION;
    }

    @Override
    protected String _getTabTitle() {
        return getLabel(_getEntity()) + ": " + super._getTabTitle();
    }
}