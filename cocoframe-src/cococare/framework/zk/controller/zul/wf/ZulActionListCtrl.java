package cococare.framework.zk.controller.zul.wf;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.framework.model.obj.wf.WfAction;
import cococare.framework.zk.controller.zul.ZulDefaultListCtrl;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulActionListCtrl extends ZulDefaultListCtrl {

    @Override
    protected Class _getEntity() {
        return WfAction.class;
    }
}