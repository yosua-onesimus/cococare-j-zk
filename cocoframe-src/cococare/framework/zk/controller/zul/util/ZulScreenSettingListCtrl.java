package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.database.CCHibernateFilter;
import cococare.database.model.bo.cc.CCCustomFieldConfigBo;
import cococare.database.model.obj.cc.CCCustomFieldConfig;
import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCCombobox;
import static cococare.zk.CCZk.addListener;
import static cococare.zk.CCZk.newCCCombobox;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulScreenSettingListCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private CCCustomFieldConfigBo customFieldConfigBo;
    private CCCombobox cmbEntity;
//</editor-fold>

    @Override
    protected Class _getEntity() {
        return CCCustomFieldConfig.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.LIST_FUNCTION;
    }

    @Override
    protected void _initComponent() {
        super._initComponent();
        cmbEntity = newCCCombobox(getContainer(), "cmbEntity", null, CCCustomFieldConfig.class, "entityName");
        cmbEntity.setList(customFieldConfigBo.getListDistinctEntity());
    }

    @Override
    protected void _initTable() {
        super._initTable();
        tblEntity.setHqlFilters(new CCHibernateFilter() {
            @Override
            public String getFieldName() {
                return "entity";
            }

            @Override
            public Object getFieldValue() {
                return ((CCCustomFieldConfig) cmbEntity.getSelectedObject()).getEntity();
            }
        });
    }

    @Override
    protected void _initListener() {
        super._initListener();
        addListener(cmbEntity.getCombobox(), new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doSearch();
            }
        });
    }
}