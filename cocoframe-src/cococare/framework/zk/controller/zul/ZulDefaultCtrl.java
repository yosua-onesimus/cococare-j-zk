package cococare.framework.zk.controller.zul;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.framework.zk.CFZkCtrl;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulDefaultCtrl extends CFZkCtrl {

    @Override
    protected Class _getClass() {
        return ZulDefaultCtrl.class;
    }

    @Override
    protected Class _getEntity() {
        return objEntity.getClass();
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.FORM_FUNCTION;
    }
}