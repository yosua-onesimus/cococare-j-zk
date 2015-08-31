package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.framework.zk.controller.zul.ZulDefaultCtrl;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulParameterCtrl extends ZulDefaultCtrl {

    @Override
    protected String _getTabTitle() {
        return _getEntityLabel() + ": " + super._getTabTitle();
    }
}