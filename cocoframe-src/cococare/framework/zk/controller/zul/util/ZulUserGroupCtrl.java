package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCClass.extract;
import cococare.common.CCCustomField;
import static cococare.common.CCFinal._padding_left_10px;
import static cococare.common.CCLanguage.Privilege;
import static cococare.common.CCLanguage.turn;
import static cococare.common.CCLogic.*;
import static cococare.common.CCMessage.IS_NOT_IP;
import cococare.framework.model.bo.util.UtilUserGroupBo;
import cococare.framework.model.obj.util.UtilFilter.isIdNotInIds;
import cococare.framework.model.obj.util.*;
import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCBandbox;
import static cococare.zk.CCMessage.showInformation;
import cococare.zk.CCTable;
import static cococare.zk.CCZk.*;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
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
    private Textbox txtIp;
    private Button btnIpAdd;
    private Button btnIpRemove;
    private CCTable tblIp;
    private CCBandbox bndArea;
    private Button btnAreaAdd;
    private Button btnAreaRemove;
    private CCTable tblArea;
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
        _initTblIp();
        _initTblArea();
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

            @Override
            public Integer getColumnWidth() {
                return 40;
            }
        });
        tblPrivilege.addField(1, new CCCustomField() {
            @Override
            public String getLabel() {
                return turn(Privilege);
            }

            @Override
            public Object getCustomView(Object object) {
                UtilPrivilege privilege = (UtilPrivilege) object;
                Label label = new Label(privilege.getCode() + "-" + privilege.getName());
                if (isNotNull(privilege.getParent())) {
                    setStyle(label, _padding_left_10px);
                }
                return label;
            }
        });
        tblPrivilege.setCheckboxColumn(true, 0);
    }

    private void _initTblIp() {
        tblIp = newCCTable(getContainer(), "tblIp", UtilUserGroupIp.class);
        tblIp.setNaviElements(null, null, btnIpRemove);
    }

    private void _initTblArea() {
        bndArea = newCCBandbox(getContainer(), "bndArea", UtilArea.class, "name");
        bndArea.getTable().setHqlFilters(new isIdNotInIds() {
            @Override
            public Object getFieldValue() {
                return extract(tblArea.getList(), "area.id");
            }
        });
        tblArea = newCCTable(getContainer(), "tblArea", UtilUserGroupArea.class);
        tblArea.setNaviElements(null, null, btnAreaRemove);
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
        addEventListenerOnClick(btnIpAdd, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doIpAdd();
            }
        });
        addEventListenerOnClick(btnIpRemove, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doIpRemove();
            }
        });
        addEventListenerOnClick(btnAreaAdd, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doAreaAdd();
            }
        });
        addEventListenerOnClick(btnAreaRemove, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doAreaRemove();
            }
        });
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

    private void _doIpAdd() {
        String ip = txtIp.getText();
        if (isIp(ip)) {
            userGroupBo.addUserGroupIp(ip);
            txtIp.setText("");
            _doUpdateTblIp();
        } else {
            showInformation(IS_NOT_IP("IP"));
        }
    }

    private void _doIpRemove() {
        txtIp.setText("");
        userGroupBo.removeUserGroupIp(tblIp.getSelectedRow());
        _doUpdateTblIp();
    }

    private void _doAreaAdd() {
        UtilArea area = bndArea.getObject();
        if (isNotNull(area)) {
            userGroupBo.addUserGroupArea(area);
            bndArea.setObject(null);
            bndArea.getTable().search();
            _doUpdateTblArea();
        }
    }

    private void _doAreaRemove() {
        bndArea.setObject(null);
        bndArea.getTable().search();
        userGroupBo.removeUserGroupArea(tblArea.getSelectedRow());
        _doUpdateTblArea();
    }

    @Override
    protected void _doUpdateComponent() {
        super._doUpdateComponent();
        //privilege
        _doUpdateTblPrivilege();
        _doUpdateTblIp();
        _doUpdateTblArea();
    }

    private void _doUpdateTblPrivilege() {
        tblPrivilege.setList(userGroupBo.getPrivileges());
    }

    private void _doUpdateTblIp() {
        tblIp.setList(userGroupBo.getUserGroupIps());
    }

    private void _doUpdateTblArea() {
        tblArea.setList(userGroupBo.getUserGroupAreas());
    }
}