package cococare.framework.zk;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.common.CCAccessibleListener;
import static cococare.common.CCClass.newObject;
import static cococare.common.CCClass.setValue;
import static cococare.common.CCFinal._after_start;
import static cococare.common.CCFinal.btnEdit;
import static cococare.common.CCFormat.getBoolean;
import static cococare.common.CCFormat.getStringOrNull;
import static cococare.common.CCLanguage.turn;
import static cococare.common.CCLogic.*;
import static cococare.common.CCMessage.logp;
import cococare.database.CCHibernateFilter;
import static cococare.database.CCLoginInfo.INSTANCE_isCompAccessible;
import cococare.framework.common.CFViewCtrl;
import static cococare.framework.zk.CFZkMap.*;
import cococare.zk.CCEditor;
import static cococare.zk.CCEditor.requestFocusInWindow;
import static cococare.zk.CCMessage.*;
import cococare.zk.CCTable;
import cococare.zk.CCZk;
import static cococare.zk.CCZk.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;
//</editor-fold>

/**
 * CFZkCtrl is an abstract class which functions as an view controller, in
 * charge of controlling the flow of applications in specific view.
 *
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public abstract class CFZkCtrl extends CFViewCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    //
    protected CFZkView zkView;
    //
    protected List<Component> reinitComponents = new ArrayList();
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
        zkView = new CFZkView(newContainer(_getClass()));
    }

    public Component getContainer() {
        return zkView.getContainer();
    }

    @Override
    public void doShowTab(String sysRef, String title, CFViewCtrl viewCtrl) {
        if (isNull(sysRef_zkCtrl.get(sysRef))) {
            //
            sysRef_tab.put(sysRef, new Tab(turn(title)));
            sysRef_tab.get(sysRef).setParent(zkView.getTabEntity().getTabs());
            //
            sysRef_tabpanel.put(sysRef, new Tabpanel());
            sysRef_zkCtrl.put(sysRef, (CFZkCtrl) viewCtrl);
            sysRef_zkCtrl.get(sysRef).getContainer().setParent(sysRef_tabpanel.get(sysRef));
            sysRef_tabpanel.get(sysRef).setParent(zkView.getTabEntity().getTabpanels());
        } else if (isNotNull(viewCtrl)) {
            ((CFZkCtrl) viewCtrl).getContainer().detach();
        }
        zkView.getTabEntity().setSelectedPanel(sysRef_tabpanel.get(sysRef));
    }

    @Override
    public void doCloseTab(String sysRef) {
        try {
            zkView.getTabEntity().getTabs().removeChild(sysRef_tab.remove(sysRef));
            sysRef_zkCtrl.remove(sysRef);
            Tabpanel tabpanel = sysRef_tabpanel.get(sysRef);
            tabpanel = (Tabpanel) coalesce(tabpanel.getNextSibling(), tabpanel.getPreviousSibling());
            zkView.getTabEntity().getTabpanels().removeChild(sysRef_tabpanel.remove(sysRef));
            zkView.getTabEntity().setSelectedPanel(tabpanel);
        } catch (Exception exception) {
            logp(exception.toString());
        }
    }

    @Override
    protected void _initLanguage() {
        applyLanguage(getContainer());
    }

    @Override
    protected void _initPrivilege() {
        new CFZkUae().isAccessible(_getClass(), getContainer());
    }

    @Override
    protected void _initObject() {
        super._initObject();
        if (BaseFunction.LIST_FUNCTION.equals(_getBaseFunction())) {
            sysRef_tab = new HashMap();
            sysRef_zkCtrl = new HashMap();
            sysRef_tabpanel = new HashMap();
        } else if (BaseFunction.FORM_FUNCTION.equals(_getBaseFunction())) {
            if (_hasEntity()) {
                sysRef = _getSysRef(objEntity);
            }
        }
    }

    @Override
    protected void _initComponent() {
        initComponent(getContainer(), this, reinitComponents);
        super._initComponent();
    }

//<editor-fold defaultstate="collapsed" desc=" LIST_FUNCTION ">
    @Override
    protected void _initTable() {
        if (_hasEntity() && isNotNull(zkView.getTblEntity())) {
            tblEntity = new CCTable(zkView.getTblEntity(), _getEntity());
            requestFocusInWindow(zkView.getTblEntity());
            //parent-childs-screen
            if (isNotNull(parameter.get(toString() + parentValue))) {
                if (getBoolean(parameter.get(toString() + parentNewEntity))) {
                    setVisible(zkView.getTxtKeyword(), false);
                    setVisible(zkView.getBtnFilter(), false);
                    setVisible(zkView.getPgnEntity(), false);
                }
                final Object dummy = this;
                tblEntity.setVisibleField(false, parameter.get(toString() + parentField).toString());
                tblEntity.setHqlFilters(new CCHibernateFilter() {
                    @Override
                    public String getFieldName() {
                        return parameter.get(dummy.toString() + parentField).toString();
                    }

                    @Override
                    public Object getFieldValue() {
                        return parameter.get(dummy.toString() + parentValue);
                    }
                });
            }
        }
    }

    @Override
    protected void _initNaviElements() {
        if (_hasTblEntity()) {
            tblEntity.setNaviElements(zkView.getPgnEntity(), zkView.getTxtKeyword(),
                    zkView.getBtnView(), zkView.getBtnEdit(), zkView.getBtnDelete());
        }
    }

    @Override
    protected void _initFilterElements() {
        if (_hasTblEntity() && isNotNull(zkView.getBtnFilter())) {
            final String popupId = zkView.getBtnFilter().toString();
            showPopup((XulElement) getContainer(), zkView.getBtnFilter(), tblEntity.getFilterContainer(), popupId, _after_start);
            addListener(tblEntity.getFilterBtnFilter(), new EventListener() {
                @Override
                public void onEvent(Event arg0) throws Exception {
                    getPopup(getContainer(), popupId).close();
                }
            });
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" FORM_FUNCTION ">
    @Override
    protected void _initEditor() {
        if (_hasEntity()) {
            edtEntity = new CCEditor(getContainer(), _getEntity());
            edtEntity.getAfterMount().compile().setParent(getContainer());
            if (isNotNull(zkView.getPnlGenerator())) {
                edtEntity.generateDefaultEditor(zkView.getPnlGenerator(), getStringOrNull(parameter.get(toString() + parentField)));
                initComponent(getContainer(), this, reinitComponents);
            }
            if (newEntity) {
                _initObjEntity();
            }
            initSpecialComponent(getContainer(), this);
        }
    }

    @Override
    protected void _initObjEntity() {
        edtEntity.initSequence(objEntity);
        //parent-childs-screen
        if (isNotNull(parameter.get(toString() + parentValue))) {
            setValue(objEntity, parameter.get(toString() + parentField).toString(), parameter.get(toString() + parentValue));
        }
    }
//</editor-fold>

    @Override
    protected void _initAccessible() {
        if (_hasTblEntity()) {
            addAccessibleListener(zkView.getBtnAdd(), accessibleIfEditable);
            addAccessibleListener(zkView.getBtnEdit(), accessibleIfEditable);
            addAccessibleListener(zkView.getBtnDelete(), accessibleIfEditable);
        } else if (_hasEdtEntity()) {
            if (isNotNull(getControllerZul(_getClass())) && !INSTANCE_isCompAccessible(getControllerZul(_getClass()).getName() + "." + btnEdit)) {
                addAccessibleListener(zkView.getBtnEdit(), CCAccessibleListener.nonAccessible);
            }
            addAccessibleListener(zkView.getBtnEdit(), accessibleIfReadonly);
            addAccessibleListener(zkView.getBtnSave(), accessibleIfEditable);
            addAccessibleListener(zkView.getBtnSaveAndNew(), accessibleIfEditable);
            addAccessibleListener(zkView.getBtnCancel(), accessibleIfEditable);
        }
    }

    @Override
    protected void _doUpdateAccessible() {
        if (_hasTblEntity()) {
            applyAccessible(zkView.getBtnAdd(), zkView.getBtnEdit(), zkView.getBtnDelete());
        } else if (_hasEdtEntity()) {
            applyAccessible(zkView.getBtnEdit(), zkView.getBtnSave(), zkView.getBtnSaveAndNew(), zkView.getBtnCancel());
            if (readonly) {
                edtEntity.setAccessible2Readonly();
            } else {
                edtEntity.setAccessible2Default();
            }
        }
    }

    @Override
    protected void _initListener() {
        if (BaseFunction.LIST_FUNCTION.equals(_getBaseFunction())) {
            elAdd = new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    _doAdd();
                }
            };
            elView = new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    _doView();
                }
            };
            elEdit = new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    _doEdit();
                }
            };
            elDelete = new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    _doDelete();
                }
            };
            elExport = new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    _doExport();
                }
            };
            elSearch = new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    _doSearch();
                }
            };
            addListener(zkView.getBtnAdd(), elAdd);
            addListener(zkView.getBtnView(), elView);
            addListener(zkView.getBtnEdit(), elEdit);
            addListener(zkView.getBtnDelete(), elDelete);
            addListener(zkView.getBtnExport(), elExport);
//            addListener(zkView.getTxtKeyword(), elSearch);
        } else if (BaseFunction.FORM_FUNCTION.equals(_getBaseFunction())) {
            elNew = new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    //new data
                }
            };
            elEdit = new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    setReadonly(false);
                }
            };
            elSave = new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    _doSave();
                }
            };
            elSaveAndNew = new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    _doSave();
                    //new data
                }
            };
            elCancel = new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    setReadonly(true);
                }
            };
            elExport = new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    _doExport();
                }
            };
            elClose = new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    _doClose();
                }
            };
            if (ShowMode.DIALOG_MODE.equals(_getShowMode())) {
                addListener(getContainer(), elClose, CCZk.EventName.onCancel);
            }
            addListener(zkView.getBtnNew(), elNew);
            addListener(zkView.getBtnEdit(), elEdit);
            addListener(zkView.getBtnSave(), elSave);
            addListener(zkView.getBtnSaveAndNew(), elSaveAndNew);
            addListener(zkView.getBtnCancel(), elCancel);
            addListener(zkView.getBtnExport(), elExport);
            addListener(zkView.getBtnClose(), elClose);
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
    protected <T> T _getSelectedItem() {
        return tblEntity.getSelectedItem();
    }

    @Override
    protected boolean _isSureDelete() {
        return cococare.zk.CCLogic.isSureDelete();
    }

    @Override
    protected boolean _doDeleteEntity() {
        //parent-childs-screen
        if (getBoolean(parameter.get(toString() + parentNewEntity))) {
            return ((List) parameter.get(toString() + childsValue)).remove(_getSelectedItem());
        } else {
            return tblEntity.deleteBySetting(_getSelectedItem()) > 0;
        }
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
        //return edtEntity.saveOrUpdate(objEntity);
        //parent-childs-screen
        if (getBoolean(parameter.get(toString() + parentNewEntity))) {
            List list = (List) parameter.get(toString() + childsValue);
            return list.contains(objEntity) ? true : list.add(objEntity);
        } else {
            return edtEntity.saveOrUpdate(objEntity, _getEntityChilds());
        }
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

    protected Component _getContent() {
        //parent-childs-screen
        if (isNull(parameter.get(toString() + childContentId))) {
            return getContent();
        } else {
            return getComponent(((CFZkCtrl) callerCtrl).getContainer(), parameter.get(toString() + childContentId).toString());
        }
    }

    @Override
    protected void _doShowScreen() {
        if (ShowMode.PANEL_MODE.equals(_getShowMode())) {
            showPanel(_getContent(), zkView.getContainer());
        } else if (ShowMode.DIALOG_MODE.equals(_getShowMode())) {
            showDialog((Window) zkView.getContainer());
        } else if (ShowMode.TAB_MODE.equals(_getShowMode())) {
            if (isNull(callerCtrl)) {
                showPanel(_getContent(), zkView.getContainer());
            } else {
                sysRef = _getSysRef(objEntity);
                callerCtrl.doShowTab(sysRef, _getTabTitle(), this);
            }
        }
    }

//<editor-fold defaultstate="collapsed" desc=" LIST_FUNCTION ">
    @Override
    protected boolean _doShowEditor(boolean readonly, Object objEntity) {
        sysRef = _getSysRef(objEntity);
        if (isNotNull(sysRef_zkCtrl.get(sysRef))) {
            sysRef_zkCtrl.get(sysRef).setReadonly(readonly);
            doShowTab(sysRef, null, null);
            return false;
        } else {
            return newObject(getControllerZul(_getClass())).with(parameter).with(this).with(readonly).init(objEntity);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" FORM_FUNCTION ">
    @Override
    protected void _doCloseScreen() {
        if (ShowMode.PANEL_MODE.equals(_getShowMode())) {
            if (isNull(callerCtrl)) {
                removePanel(_getContent(), getContainer());
            } else {
                callerCtrl.init();
            }
        } else if (ShowMode.DIALOG_MODE.equals(_getShowMode())) {
            ((Window) zkView.getContainer()).detach();
        } else if (ShowMode.TAB_MODE.equals(_getShowMode())) {
            if (isNull(callerCtrl)) {
                removePanel(_getContent(), getContainer());
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
            //parent-childs-screen
            if (getBoolean(parameter.get(toString() + parentNewEntity))) {
                tblEntity.setList((List) parameter.get(toString() + childsValue));
            } else {
                tblEntity.search();
            }
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