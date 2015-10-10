package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCClass.getCCTypeConfig;
import cococare.common.CCCustomField;
import static cococare.common.CCFinal._padding_left_20px;
import static cococare.common.CCLanguage.Privilege;
import static cococare.common.CCLanguage.turn;
import static cococare.common.CCLogic.*;
import static cococare.common.CCMessage.IS_NOT_IP;
import cococare.common.CCTypeConfig;
import cococare.framework.model.bo.util.UtilUserGroupBo;
import cococare.framework.model.obj.util.UtilPrivilege;
import cococare.framework.model.obj.util.UtilUserGroup;
import cococare.framework.model.obj.util.UtilUserGroupIp;
import cococare.framework.zk.CFZkCtrl;
import static cococare.zk.CCMessage.showInformation;
import cococare.zk.CCOptionbox;
import cococare.zk.CCTable;
import static cococare.zk.CCZk.*;
import java.util.HashMap;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.*;
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
    private HashMap<Class, CCOptionbox> clazz_optionBox = new HashMap();
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
        _initAdditionalTab();
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
                addListener(checkbox, elSelect);
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
                    setStyle(label, _padding_left_20px);
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

    private void _initAdditionalTab() {
        for (Class clazz : userGroupBo.getUtilAdditionalTabClass()) {
            CCTypeConfig typeConfig = getCCTypeConfig(clazz);
            Tab tab = new Tab(typeConfig.label());
            tab.setParent(zkView.getTabEntity().getTabs());
            Tabpanel tabpanel = new Tabpanel();
            tabpanel.setParent(zkView.getTabEntity().getTabpanels());
            Grid grid = new Grid();
            grid.setParent(tabpanel);
            CCOptionbox optionbox = newCCOptionbox(grid, false, clazz, typeConfig.uniqueKey());
            optionbox.setList(userGroupBo.getListBy(clazz));
            optionbox.setSelectedItem(true, userGroupBo.getSelectedItem(clazz).toArray());
            clazz_optionBox.put(clazz, optionbox);
        }
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
        addListener(btnIpAdd, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doIpAdd();
            }
        });
        addListener(btnIpRemove, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doIpRemove();
            }
        });
    }

    @Override
    protected void _getValueFromEditor() {
        super._getValueFromEditor();
        for (Class clazz : userGroupBo.getUtilAdditionalTabClass()) {
            userGroupBo.addChild(clazz, clazz_optionBox.get(clazz).getSelectedItems());
        }
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

    @Override
    protected void _doUpdateComponent() {
        super._doUpdateComponent();
        //privilege
        _doUpdateTblPrivilege();
        _doUpdateTblIp();
    }

    private void _doUpdateTblPrivilege() {
        tblPrivilege.setList(userGroupBo.getPrivileges());
    }

    private void _doUpdateTblIp() {
        tblIp.setList(userGroupBo.getUserGroupIps());
    }
}