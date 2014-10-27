package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCClass.getCCFields;
import cococare.common.CCCustomField;
import cococare.common.CCField;
import static cococare.database.CCEntityConfig.EX_FIELD;
import cococare.database.CCEntityModule;
import cococare.database.CCHibernateFilter;
import cococare.database.model.obj.cc.CCAuditData;
import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCTable;
import static cococare.zk.CCZk.*;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulAuditTrailCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private Button btnApprove;
    private CCTable tblAuditTrail;
    private CCTable tblOldVsNew;
//</editor-fold>

    @Override
    protected Class _getEntity() {
        return CCAuditData.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.FORM_FUNCTION;
    }

    @Override
    protected void _initObject() {
        super._initObject();
        readonly = ((CCAuditData) objEntity).isApproved();
    }

    @Override
    protected String _getSysRef(Object objEntity) {
        return ((CCAuditData) objEntity).getSysRef();
    }

    @Override
    protected void _initComponent() {
        super._initComponent();
        tblAuditTrail = newCCTable(getContainer(), "tblAuditTrail", CCAuditData.class);
        int column = 0;
        tblAuditTrail.addField(column++, new CCCustomField() {
            @Override
            public Object getCustomView(Object object) {
                return ((CCAuditData) object).getLabel();
            }
        });
        tblAuditTrail.addField(column++, new CCCustomField() {
            @Override
            public Object getCustomView(Object object) {
                return ((CCAuditData) object).getProcessType().toString();
            }
        });
        tblAuditTrail.addField(column++, new CCCustomField() {
            @Override
            public Object getCustomView(Object object) {
                return ((CCAuditData) object).getCreator();
            }
        });
        tblAuditTrail.addField(column++, new CCCustomField() {
            @Override
            public Object getCustomView(Object object) {
                return ((CCAuditData) object).getApprover();
            }
        });
        tblAuditTrail.setColumnLabel("Class Name", "Process", "Creator", "Approver");
        tblAuditTrail.setColumnWidth(80, 80, 180, 180, 60);
        tblAuditTrail.setHqlFilters(new CCHibernateFilter() {
            @Override
            public String getFieldName() {
                return "sysRef";
            }

            @Override
            public Object getFieldValue() {
                return ((CCAuditData) objEntity).getSysRef();
            }
        });
        tblAuditTrail.setHqlOrderSyntax("id DESC");
        tblAuditTrail.search();
        tblOldVsNew = new CCTable(getMeshElement(getContainer(), "tblOldVsNew"), "Field Name", "Old Value", "New Value");
    }

    @Override
    protected void _initAccessible() {
        super._initAccessible();
        addAccessibleListener(btnApprove, accessibleIfEditable);
    }

    @Override
    protected void _doUpdateAccessible() {
        super._doUpdateAccessible();
        applyAccessible(btnApprove);
    }

    @Override
    protected void _initListener() {
        super._initListener();
        tblAuditTrail.addEventListenerOnSelect(new EventListener() {
            public void onEvent(Event event) throws Exception {
                tblOldVsNew.removeRows();
                if (tblAuditTrail.isSelected()) {
                    CCAuditData auditData = tblAuditTrail.getSelectedItem();
                    Object valueOld = auditData.getValueOldObject();
                    Object valueNew = auditData.getValueNewObject();
                    for (CCField field : getCCFields(auditData.getValueObject())) {
                        if (!EX_FIELD.contains(field.getFieldName())) {
                            tblOldVsNew.addRow(field.getFieldName(), field.getValue4View(valueOld), field.getValue4View(valueNew));
                        }
                    }
                }
            }
        });
        addEventListenerOnClick(btnApprove, elSave);
    }

    @Override
    protected boolean _doSaveEntity() {
        return CCEntityModule.INSTANCE.getCCHibernate().approve((CCAuditData) objEntity);
    }
}