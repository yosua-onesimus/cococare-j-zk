package cococare.framework.zk.controller.zul.wf;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCLogic.nvl2;
import cococare.framework.model.obj.wf.WfActivityTab;
import cococare.framework.zk.controller.zul.ZulDefaultListCtrl;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulActivityTabListCtrl extends ZulDefaultListCtrl {

    @Override
    protected Class _getClass() {
        return nvl2(zkView, getClass(), super._getClass());
    }

    @Override
    protected Class _getEntity() {
        return WfActivityTab.class;
    }
}