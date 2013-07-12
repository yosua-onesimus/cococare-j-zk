package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.common.CCCustomField;
import static cococare.common.CCLogic.isNotNull;
import static cococare.common.CCLogic.isNotNullAndNotEmpty;
import cococare.framework.model.bo.util.UtilUserBo;
import cococare.framework.model.obj.util.UtilFilter;
import cococare.framework.model.obj.util.UtilPrivilege;
import cococare.framework.model.obj.util.UtilUser;
import cococare.framework.model.obj.util.UtilUserGroup;
import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCBandbox;
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
public class ZulUserCtrl extends CFZkCtrl {

    private UtilUserBo userBo;
    private CCBandbox bndUserGroup;
    private CCTable tblPrivilege;
    private EventListener elSelect = new EventListener() {
        @Override
        public void onEvent(Event event) throws Exception {
            selectRowElement(event.getTarget());
            _doSelect(((Checkbox) event.getTarget()).isChecked());
        }
    };

    @Override
    protected Class _getEntity() {
        return UtilUser.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.FORM_FUNCTION;
    }

    @Override
    protected void _initObject() {
        super._initObject();
        //privilege
        userBo.load((UtilUser) objEntity);
    }

    @Override
    protected void _initEditor() {
        super._initEditor();
        //
        if (!newEntity) {
            edtEntity.unreg("txtPassword");
            edtEntity.unreg("xPassword");
            edtEntity.unreg("txtRetypePassword");
            edtEntity.unreg("xRetypePassword");
        }
        bndUserGroup = getCCBandbox(getContainer(), "bndUserGroup");
        bndUserGroup.getTable().setHqlFilters(UtilFilter.IsUserGroupNotRoot);
        //privilege
        _initTblPrivilege();
    }

    protected void _initTblPrivilege() {
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
                    setStyle(label, "padding-left:10px;");
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
    protected void _initListener() {
        super._initListener();
        //privilege
        bndUserGroup.addEventListenerOnSelect(new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                userBo.getPrivileges((UtilUserGroup) bndUserGroup.getObject());
                tblPrivilege.reloadItems();
            }
        });
    }

    @Override
    protected boolean _doSaveEntity() {
        return userBo.saveOrUpdate();
    }

    protected void _doSelect(boolean selected) {
        UtilPrivilege privilege = (UtilPrivilege) tblPrivilege.getSelectedItem();
        privilege.setSelected(selected);
        tblPrivilege.reloadSelectedItem();
        if (selected) {
            if (isNotNull(privilege.getParent())) {
                UtilPrivilege parent = privilege.getParent();
                parent.setSelected(selected);
                tblPrivilege.reloadItem(userBo.getPrivilegeIndex(parent));
            }
        } else {
            if (isNotNullAndNotEmpty(privilege.getChilds())) {
                for (UtilPrivilege child : privilege.getChilds()) {
                    child.setSelected(selected);
                    tblPrivilege.reloadItem(userBo.getPrivilegeIndex(child));
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

    protected void _doUpdateTblPrivilege() {
        tblPrivilege.setList(userBo.getPrivileges());
    }
}