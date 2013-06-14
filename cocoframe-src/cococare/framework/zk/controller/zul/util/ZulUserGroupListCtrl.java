package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.framework.model.obj.util.UtilFilter;
import cococare.framework.model.obj.util.UtilUserGroup;
import cococare.framework.zk.CFZkCtrl;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulUserGroupListCtrl extends CFZkCtrl {

    @Override
    protected Class _getEntity() {
        return UtilUserGroup.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.LIST_FUNCTION;
    }

    @Override
    protected void _initTable() {
        super._initTable();
        tblEntity.setHqlFilters(UtilFilter.IsUserGroupNotRoot);
    }
}