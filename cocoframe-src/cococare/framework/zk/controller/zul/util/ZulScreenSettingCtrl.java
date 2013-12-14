package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.database.model.obj.cc.CCCustomFieldConfig;
import cococare.framework.zk.CFZkCtrl;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulScreenSettingCtrl extends CFZkCtrl {

    @Override
    protected Class _getEntity() {
        return CCCustomFieldConfig.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.FORM_FUNCTION;
    }
}