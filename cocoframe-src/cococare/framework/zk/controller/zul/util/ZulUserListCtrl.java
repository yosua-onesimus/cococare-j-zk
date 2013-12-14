package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.framework.model.obj.util.UtilFilter;
import cococare.framework.model.obj.util.UtilUser;
import cococare.framework.zk.CFZkCtrl;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulUserListCtrl extends CFZkCtrl {

    @Override
    protected Class _getEntity() {
        return UtilUser.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.LIST_FUNCTION;
    }

    @Override
    protected void _initTable() {
        super._initTable();
        tblEntity.setHqlFilters(UtilFilter.isUserNotRoot);
    }

    @Override
    protected boolean _doDeleteEntity() {
        return tblEntity.deleteBySetting(_getSelectedItem()) > 0;
    }
}