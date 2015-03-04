package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCClass.getUniqueKeyValue;
import static cococare.common.CCLanguage.*;
import static cococare.common.CCLogic.coalesce;
import cococare.framework.model.bo.util.UtilSchedulerBo;
import cococare.framework.model.obj.util.UtilScheduler;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulSchedulerCtrl extends ZulParameterCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private UtilSchedulerBo schedulerBo;
//</editor-fold>

    @Override
    protected boolean _doSaveEntity() {
        return schedulerBo.saveOrUpdate((UtilScheduler) objEntity);
    }

    @Override
    protected String _getTabTitle() {
        return coalesce(getUniqueKeyValue(objEntity), readonly ? turn(View) : turn(Edit)).toString();
    }
}