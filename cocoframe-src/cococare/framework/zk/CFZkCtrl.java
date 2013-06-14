package cococare.framework.zk;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.common.CCAccessibleListener;
import static cococare.common.CCClass.getUniqueKeyValue;
import static cococare.common.CCClass.newObject;
import static cococare.common.CCFinal.btnEdit;
import static cococare.common.CCLanguage.*;
import static cococare.common.CCLogic.*;
import static cococare.database.CCLoginInfo.INSTANCE_isCompAccessible;
import cococare.framework.common.CFViewCtrl;
import static cococare.framework.zk.CFZkMap.*;
import cococare.zk.CCEditor;
import static cococare.zk.CCMessage.*;
import cococare.zk.CCTable;
import static cococare.zk.CCZk.*;
import java.util.HashMap;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public abstract class CFZkCtrl extends CFViewCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    //
    protected CFZkView zkView;
    //
    protected CCTable tblEntity;
    protected EventListener elAdd;
    protected EventListener elView;
    protected EventListener elEdit;
    protected EventListener elDelete;
    protected EventListener elExport;
    protected EventListener elSearch;
    protected HashMap<String, Tab> sysRef_tab;
    protected HashMap<String, CFZkCtrl> sysRef_zkCtrl;
    protected HashMap<String, Tabpanel> sysRef_tabpanel;
    //
    protected CCEditor edtEntity;
    protected EventListener elNew;
    protected EventListener elSave;
    protected EventListener elSaveAndNew;
    protected EventListener elCancel;
    protected EventListener elClose;
//</editor-fold>

    @Override
    protected void _initContainer() {
        zkView = new CFZkView(newContainer(getClass()));
    }

    public Component getContainer() {
        return zkView.getContainer();
    }

    @Override
    public void doShowTab(String sysRef, String title, CFViewCtrl viewCtrl) {
        //
        Tab tab = new Tab(title);
        sysRef_tab.put(sysRef, tab);
        tab.setParent(zkView.getTabEntity().getTabs());
        //
        Tabpanel tabpanel = new Tabpanel();
        sysRef_zkCtrl.put(sysRef, ((CFZkCtrl) viewCtrl));
        ((CFZkCtrl) viewCtrl).getContainer().setParent(tabpanel);
        sysRef_tabpanel.put(sysRef, tabpanel);
        tabpanel.setParent(zkView.getTabEntity().getTabpanels());
        //
        zkView.getTabEntity().setSelectedPanel(sysRef_tabpanel.get(sysRef));
    }

    @Override
    public void doCloseTab(String sysRef) {
        zkView.getTabEntity().getTabs().removeChild(sysRef_tab.remove(sysRef));
        sysRef_zkCtrl.remove(sysRef);
        Tabpanel tabpanel = sysRef_tabpanel.get(sysRef);
        tabpanel = (Tabpanel) (isNotNull(tabpanel.getNextSibling())
                ? tabpanel.getNextSibling()
                : tabpanel.getPreviousSibling());
        zkView.getTabEntity().getTabpanels().removeChild(sysRef_tabpanel.remove(sysRef));
        zkView.getTabEntity().setSelectedPanel(tabpanel);
    }

    @Override
    protected void _initLanguage() {
        applyLanguage(getContainer());
    }

    @Override
    protected void _initPrivilege() {
        new CFZkUae().isAccessible(getClass(), getContainer());
    }

    @Override
    protected void _initObject() {
        super._initObject();
        if (_hasEntity()) {
            if (BaseFunction.LIST_FUNCTION.equals(_getBaseFunction())) {
                sysRef_tab = new HashMap();
                sysRef_zkCtrl = new HashMap();
                sysRef_tabpanel = new HashMap();
            } else if (BaseFunction.FORM_FUNCTION.equals(_getBaseFunction())) {
                sysRef = _getSysRef(objEntity);
            }
        }
    }

    @Override
    protected void _initComponent() {
        initComponent(getContainer(), this);
        super._initComponent();
    }

//<editor-fold defaultstate="collapsed" desc=" LIST_FUNCTION ">
    @Override
    protected void _initTable() {
        if (_hasEntity() && isNotNull(zkView.getTblEntity())) {
            tblEntity = new CCTable(zkView.getTblEntity(), _getEntity());
            zkView.getTblEntity().setFocus(true);
        }
    }

    @Override
    protected void _initNaviElements() {
        if (_hasTblEntity()) {
            tblEntity.setNaviElements(zkView.getPgnEntity(), zkView.getTxtKeyword(),
                    zkView.getBtnView(), zkView.getBtnEdit(), zkView.getBtnDelete());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" FORM_FUNCTION ">
    @Override
    protected void _initEditor() {
        if (_hasEntity()) {
            edtEntity = new CCEditor(getContainer(), _getEntity());
            if (newEntity) {
                _initObjEntity();
            }
        }
    }

    @Override
    protected void _initObjEntity() {
        edtEntity.initSequence(objEntity);
    }

    @Override
    protected void _initAccessible() {
        if (isNotNull(getControllerZul(getClass())) && !INSTANCE_isCompAccessible(getControllerZul(getClass()).getName() + "." + btnEdit)) {
            addAccessibleListener(zkView.getBtnEdit(), CCAccessibleListener.nonAccessible);
        }
        addAccessibleListener(zkView.getBtnEdit(), accessibleIfReadonly);
        addAccessibleListener(zkView.getBtnSave(), accessibleIfEditable);
        addAccessibleListener(zkView.getBtnSaveAndNew(), accessibleIfEditable);
        addAccessibleListener(zkView.getBtnCancel(), accessibleIfEditable);
    }

    @Override
    protected void _doUpdateAccessible() {
        if (_hasEdtEntity()) {
            applyAccessible(zkView.getBtnEdit(), zkView.getBtnSave(), zkView.getBtnSaveAndNew(), zkView.getBtnCancel());
            if (readonly) {
                edtEntity.setAccessible2Readonly();
            } else {
                edtEntity.setAccessible2Default();
            }
        }
    }
//</editor-fold>

    @Override
    protected void _initListener() {
        if (BaseFunction.LIST_FUNCTION.equals(_getBaseFunction())) {
            elAdd = new EventListener() {
                @Override
                public void onEvent(Event t) throws Exception {
                    _doAdd();
                }
            };
            elView = new EventListener() {
                @Override
                public void onEvent(Event t) throws Exception {
                    _doView();
                }
            };
            elEdit = new EventListener() {
                @Override
                public void onEvent(Event t) throws Exception {
                    _doEdit();
                }
            };
            elDelete = new EventListener() {
                @Override
                public void onEvent(Event t) throws Exception {
                    _doDelete();
                }
            };
            elExport = new EventListener() {
                @Override
                public void onEvent(Event t) throws Exception {
                    _doExport();
                }
            };
            elSearch = new EventListener() {
                @Override
                public void onEvent(Event t) throws Exception {
                    _doSearch();
                }
            };
            addEventListenerOnClick(zkView.getBtnAdd(), elAdd);
            addEventListenerOnClick(zkView.getBtnView(), elView);
            addEventListenerOnClick(zkView.getBtnEdit(), elEdit);
            addEventListenerOnClick(zkView.getBtnDelete(), elDelete);
            addEventListenerOnClick(zkView.getBtnExport(), elExport);
            addEventListenerOnChange_OnOk(zkView.getTxtKeyword(), elSearch);
        } else if (BaseFunction.FORM_FUNCTION.equals(_getBaseFunction())) {
            if (ShowMode.DIALOG_MODE.equals(_getShowMode())) {
                addEventListenerOnCancel(getContainer(), elClose);
            }
            elNew = new EventListener() {
                @Override
                public void onEvent(Event t) throws Exception {
                    //new data
                }
            };
            elEdit = new EventListener() {
                @Override
                public void onEvent(Event t) throws Exception {
                    setReadonly(false);
                }
            };
            elSave = new EventListener() {
                @Override
                public void onEvent(Event t) throws Exception {
                    _doSave();
                }
            };
            elSaveAndNew = new EventListener() {
                @Override
                public void onEvent(Event t) throws Exception {
                    _doSave();
                    //new data
                }
            };
            elCancel = new EventListener() {
                @Override
                public void onEvent(Event t) throws Exception {
                    setReadonly(true);
                }
            };
            elClose = new EventListener() {
                @Override
                public void onEvent(Event t) throws Exception {
                    _doClose();
                }
            };
            addEventListenerOnClick(zkView.getBtnNew(), elNew);
            addEventListenerOnClick(zkView.getBtnEdit(), elEdit);
            addEventListenerOnClick(zkView.getBtnSave(), elSave);
            addEventListenerOnClick(zkView.getBtnSaveAndNew(), elSaveAndNew);
            addEventListenerOnClick(zkView.getBtnCancel(), elCancel);
            addEventListenerOnClick(zkView.getBtnClose(), elClose);
        }
    }

//<editor-fold defaultstate="collapsed" desc=" LIST_FUNCTION ">
    @Override
    protected boolean _hasTblEntity() {
        return isNotNull(tblEntity);
    }

    @Override
    protected boolean _isSelected() {
        return _hasTblEntity() && tblEntity.isSelected();
    }

    @Override
    protected Object _getSelectedItem() {
        return tblEntity.getSelectedItem();
    }

    @Override
    protected boolean _isSureDelete() {
        return cococare.zk.CCLogic.isSureDelete();
    }

    @Override
    protected boolean _doDeleteEntity() {
        return tblEntity.deleteById(_getSelectedItem()) > 0;
    }

    @Override
    protected void _showDeleted(boolean success) {
        showDeleted(success);
    }

    @Override
    protected boolean _doExportMulti() {
        return tblEntity.export2Excel();
    }

    @Override
    protected boolean _doExportSingle() {
        return true;
    }

    @Override
    protected void _showExported(boolean success) {
        if (!success) {
            showError();
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" FORM_FUNCTION ">
    @Override
    protected boolean _hasEdtEntity() {
        return isNotNull(edtEntity);
    }

    @Override
    protected boolean _isValueValid() {
        return edtEntity.isValueValid();
    }

    @Override
    protected boolean _isValueCompare() {
        return edtEntity.isValueCompare();
    }

    @Override
    protected boolean _isValueUnique() {
        return edtEntity.isValueUnique();
    }

    @Override
    protected boolean _isSureSave() {
        return cococare.zk.CCLogic.isSureSave();
    }

    @Override
    protected void _getValueFromEditor() {
        objEntity = edtEntity.getValueFromEditor();
    }

    @Override
    protected boolean _doSaveEntity() {
        return edtEntity.saveOrUpdate(objEntity);
    }

    @Override
    protected void _showSaved(boolean success) {
        showSaved(success);
    }

    @Override
    protected boolean _isValueChanges() {
        return edtEntity.isValueChanges();
    }

    @Override
    protected boolean _isSureDataNotSaved() {
        return cococare.zk.CCLogic.isSureDataNotSaved();
    }
//</editor-fold>

    @Override
    protected void _doShowScreen() {
        if (ShowMode.PANEL_MODE.equals(_getShowMode())) {
            showPanel(getContent(), zkView.getContainer());
        } else if (ShowMode.DIALOG_MODE.equals(_getShowMode())) {
            showDialog((Window) zkView.getContainer());
        } else if (ShowMode.TAB_MODE.equals(_getShowMode())) {
            if (isNull(callerCtrl)) {
                showPanel(getContent(), zkView.getContainer());
            } else {
                callerCtrl.doShowTab(sysRef, newEntity ? turn(New) : coalesce(getUniqueKeyValue(objEntity), readonly ? turn(View) : turn(Edit)).toString(), this);
            }
        }
    }

//<editor-fold defaultstate="collapsed" desc=" LIST_FUNCTION ">
    @Override
    protected boolean _doShowEditor(boolean readonly, Object objEntity) {
        sysRef = _getSysRef(objEntity);
        if (isNotNull(sysRef_zkCtrl.get(sysRef))) {
            sysRef_zkCtrl.get(sysRef).setReadonly(readonly);
            zkView.getTabEntity().setSelectedPanel(sysRef_tabpanel.get(sysRef));
            return false;
        } else {
            return ((CFZkCtrl) newObject(getControllerZul(getClass()))).with(parameter).with(this).with(readonly).init(objEntity);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" FORM_FUNCTION ">
    @Override
    protected void _doCloseScreen() {
        if (ShowMode.PANEL_MODE.equals(_getShowMode())) {
            if (isNull(callerCtrl)) {
                showPanel(getContent(), null);
            } else {
                callerCtrl.init();
            }
        } else if (ShowMode.DIALOG_MODE.equals(_getShowMode())) {
            ((Window) zkView.getContainer()).detach();
        } else if (ShowMode.TAB_MODE.equals(_getShowMode())) {
            if (isNull(callerCtrl)) {
                showPanel(getContent(), null);
            } else {
                callerCtrl.doCloseTab(sysRef);
                if (updateCaller) {
                    if (newEntity) {
                        callerCtrl.doUpdateTable();
                    } else {
                        callerCtrl.doUpdateTablePartial(objEntity);
                    }
                }
            }
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" LIST_FUNCTION ">
    @Override
    public void doUpdateTable() {
        if (_hasTblEntity()) {
            tblEntity.search();
        }
    }

    @Override
    public void doUpdateTablePartial(Object objEntity) {
        if (_hasTblEntity()) {
            tblEntity.reloadItem(objEntity);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" FORM_FUNCTION ">
    @Override
    protected void _doUpdateEditor() {
        if (_hasEdtEntity()) {
            edtEntity.setValueToEditor(objEntity);
        }
    }
//</editor-fold>
}