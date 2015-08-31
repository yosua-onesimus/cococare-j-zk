package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.framework.model.bo.util.UtilSchedulerBo;
import cococare.framework.model.obj.util.UtilScheduler;
import cococare.framework.zk.controller.zul.ZulDefaultCtrl;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulSchedulerCtrl extends ZulDefaultCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private UtilSchedulerBo schedulerBo;
//</editor-fold>

    @Override
    protected boolean _doSaveEntity() {
        return schedulerBo.saveOrUpdate((UtilScheduler) objEntity);
    }
}