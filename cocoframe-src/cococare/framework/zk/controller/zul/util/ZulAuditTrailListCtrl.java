package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCClass.getLabel;
import cococare.common.CCCustomField;
import cococare.database.CCEntityModule;
import cococare.database.CCHibernateFilter;
import cococare.database.model.obj.cc.CCAuditData;
import cococare.framework.zk.CFZkCtrl;
import static cococare.zk.CCZk.addListener;
import org.zkoss.zul.Combobox;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulAuditTrailListCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private Combobox cmbEntity;
//</editor-fold>

    @Override
    protected Class _getEntity() {
        return CCAuditData.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.LIST_FUNCTION;
    }

    @Override
    protected String _getSysRef(Object objEntity) {
        return ((CCAuditData) objEntity).getSysRef();
    }

    @Override
    protected void _initComponent() {
        super._initComponent();
        cmbEntity.appendItem("");
        for (Class clazz : CCEntityModule.getAuditableClasses()) {
            cmbEntity.appendItem(getLabel(clazz));
        }
    }

    @Override
    protected void _initTable() {
        super._initTable();
        int column = 0;
        tblEntity.addField(column++, new CCCustomField() {
            @Override
            public Object getCustomView(Object object) {
                return ((CCAuditData) object).getLabel();
            }
        });
        tblEntity.addField(column++, new CCCustomField() {
            @Override
            public Object getCustomView(Object object) {
                return ((CCAuditData) object).getProcessType().toString();
            }
        });
        tblEntity.addField(column++, new CCCustomField() {
            @Override
            public Object getCustomView(Object object) {
                return ((CCAuditData) object).getCreator();
            }
        });
        tblEntity.addField(column++, new CCCustomField() {
            @Override
            public Object getCustomView(Object object) {
                return ((CCAuditData) object).getApprover();
            }
        });
        tblEntity.setColumnLabel("Class Name", "Process", "Creator", "Approver");
        tblEntity.setColumnWidth(80, 80, 180, 180, 60);
        tblEntity.setHqlFilters(new CCHibernateFilter() {
            @Override
            public String getFieldName() {
                return "className";
            }

            @Override
            public Object getFieldValue() {
                if (cmbEntity.getSelectedIndex() > 0) {
                    return CCEntityModule.getAuditableClasses().get(cmbEntity.getSelectedIndex() - 1).getName();
                } else {
                    return null;
                }
            }
        });
        tblEntity.setHqlOrderSyntax("id DESC");
    }

    @Override
    protected void _initListener() {
        super._initListener();
        addListener(cmbEntity, elSearch);
    }
}