package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.common.CCCustomField;
import static cococare.common.CCFinal._padding_left_10px;
import static cococare.common.CCLogic.isNotNull;
import static cococare.common.CCLogic.isNotNullAndNotEmpty;
import cococare.framework.model.bo.util.UtilUserGroupBo;
import cococare.framework.model.obj.util.UtilPrivilege;
import cococare.framework.model.obj.util.UtilUserGroup;
import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCTable;
import static cococare.zk.CCZk.*;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulUserGroupCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private UtilUserGroupBo userGroupBo;
    private CCTable tblPrivilege;
    private EventListener elSelect = new EventListener() {
        @Override
        public void onEvent(Event event) throws Exception {
            selectRowElement(event.getTarget());
            _doSelect(((Checkbox) event.getTarget()).isChecked());
        }
    };
//</editor-fold>

    @Override
    protected Class _getEntity() {
        return UtilUserGroup.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.FORM_FUNCTION;
    }

    @Override
    protected void _initObject() {
        super._initObject();
        //privilege
        userGroupBo.load((UtilUserGroup) objEntity);
    }

    @Override
    protected void _initEditor() {
        super._initEditor();
        //privilege
        _initTblPrivilege();
    }

    private void _initTblPrivilege() {
        tblPrivilege = newCCTable(getContainer(), "tblPrivilege", UtilPrivilege.class);
        tblPrivilege.setVisibleField(false, "name");
        tblPrivilege.addField(0, new CCCustomField() {
            @Override
            public Object getCustomView(Object object) {
                UtilPrivilege privilege = (UtilPrivilege) object;
                Checkbox checkbox = new Checkbox();
                checkbox.setChecked(privilege.isSelected());
                addEventListenerOnClick(checkbox, elSelect);
                return checkbox;
            }
        });
        tblPrivilege.addField(1, new CCCustomField() {
            @Override
            public String getLabel() {
                return "Privilege";
            }

            @Override
            public Object getCustomView(Object object) {
                UtilPrivilege privilege = (UtilPrivilege) object;
                Label label = new Label(privilege.getName());
                if (isNotNull(privilege.getParent())) {
                    setStyle(label, _padding_left_10px);
                }
                return label;
            }
        });
        tblPrivilege.setCheckboxColumn(true, 0);
        tblPrivilege.setColumnWidth(0, 40);
    }

    @Override
    protected void _doUpdateAccessible() {
        super._doUpdateAccessible();
        //privilege
        tblPrivilege.setEditableColumn(!readonly, 0);
    }

    @Override
    protected boolean _doSaveEntity() {
        return userGroupBo.saveOrUpdate();
    }

    private void _doSelect(boolean selected) {
        UtilPrivilege privilege = tblPrivilege.getSelectedItem();
        privilege.setSelected(selected);
        tblPrivilege.reloadSelectedItem();
        if (selected) {
            if (isNotNull(privilege.getParent())) {
                UtilPrivilege parent = privilege.getParent();
                parent.setSelected(selected);
                tblPrivilege.reloadItem(userGroupBo.getPrivilegeIndex(parent));
            }
        } else {
            if (isNotNullAndNotEmpty(privilege.getChilds())) {
                for (UtilPrivilege child : privilege.getChilds()) {
                    child.setSelected(selected);
                    tblPrivilege.reloadItem(userGroupBo.getPrivilegeIndex(child));
                }
            }
        }
    }

    @Override
    protected void _doUpdateComponent() {
        super._doUpdateComponent();
        //privilege
        _doUpdateTblPrivilege();
    }

    private void _doUpdateTblPrivilege() {
        tblPrivilege.setList(userGroupBo.getPrivileges());
    }
}