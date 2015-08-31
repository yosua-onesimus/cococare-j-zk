package cococare.framework.zk.controller.zul;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.framework.zk.CFZkCtrl;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public abstract class ZulDefaultListCtrl extends CFZkCtrl {

    @Override
    protected Class _getClass() {
        return ZulDefaultListCtrl.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.LIST_FUNCTION;
    }
}