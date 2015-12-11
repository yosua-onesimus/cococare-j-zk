package cococare.framework.zk.controller.zul.wf;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.common.CCAccessibleListener;
import static cococare.common.CCClass.*;
import static cococare.common.CCFinal.ROOT;
import static cococare.common.CCLanguage.Process;
import static cococare.common.CCLanguage.turn;
import static cococare.common.CCLogic.isNotNull;
import static cococare.common.CCLogic.isNull;
import cococare.database.CCEntity;
import cococare.database.CCEntityBo;
import cococare.framework.model.bo.wf.WfWorkflowConfiguratorBo;
import cococare.framework.model.obj.wf.WfEnum.ActivityPointType;
import cococare.framework.model.obj.wf.WfFilter.isAction;
import cococare.framework.model.obj.wf.WfFilter.isActivity;
import cococare.framework.model.obj.wf.WfFilter.isProcess;
import cococare.framework.model.obj.wf.*;
import cococare.framework.zk.CFZkCtrl;
import static cococare.framework.zk.CFZkMap.getMenubarV;
import static cococare.zk.CCEditor.requestFocusInWindow;
import cococare.zk.CCTable;
import static cococare.zk.CCZk.*;
import java.util.HashMap;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulWorkflowModuleCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private WfWorkflowConfiguratorBo workflowConfiguratorBo;
    private HashMap<Treeitem, Object> treeitem_object = new HashMap();
    private HashMap<Object, Treeitem> object_treeitem = new HashMap();
    private CCTable treeProcess;
    private Object selectedObject;
//</editor-fold>

    @Override
    protected Class _getClass() {
        if (isNotNull(selectedObject)) {
            Class selectedClass = selectedObject.getClass();
            if (String.class.equals(selectedClass)) {
                return ZulProcessListCtrl.class;
            } else if (WfProcess.class.equals(selectedClass)) {
                return ZulActivityListCtrl.class;
            } else if (WfActivity.class.equals(selectedClass)) {
                return ZulActionListCtrl.class;
            } else if (WfAction.class.equals(selectedClass)) {
                return ZulTransitionListCtrl.class;
            }
        }
        return ZulWorkflowModuleCtrl.class;
    }

    @Override
    protected Class _getEntity() {
        if (isNotNull(selectedObject)) {
            Class selectedClass = selectedObject.getClass();
            if (String.class.equals(selectedClass)) {
                return WfProcess.class;
            } else if (WfProcess.class.equals(selectedClass)) {
                return WfActivity.class;
            } else if (WfActivity.class.equals(selectedClass)) {
                return WfAction.class;
            } else if (WfAction.class.equals(selectedClass)) {
                return WfTransition.class;
            }
        }
        return WfWorkflow.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.LIST_FUNCTION;
    }

    @Override
    protected ShowMode _getShowMode() {
        return ShowMode.TAB_MODE;
    }

    @Override
    protected void _initComponent() {
        getMenubarV().setOpen(false);
        super._initComponent();
        _initTreeProcess();
    }

    @Override
    protected void _initTable() {
        super._initTable();
        parameter.put(toString() + "tblEntity", tblEntity);
    }

    private Treeitem _addRow(Treeitem treeitem, String label, Object object) {
        if (isNull(treeitem)) {
            treeProcess.addRow(label);
            treeitem = (Treeitem) treeProcess.getRowElement(treeProcess.getLastRow());
        } else {
            treeProcess.addChildRow(treeitem, label);
            treeitem = (Treeitem) treeitem.getTreechildren().getLastChild();
        }
        treeitem_object.put(treeitem, object);
        object_treeitem.put(getSysRef(object), treeitem);
        return treeitem;
    }

    private void _initTreeProcess() {
        treeProcess = newCCTable(getContainer(), "treeProcess", 1);
        Treeitem treeitemRoot = _addRow(null, turn(Process), ROOT);
        for (WfProcess process : workflowConfiguratorBo.getProcesses()) {
            Treeitem treeitemProcess = _addRow(treeitemRoot, process.getName(), process);
            for (WfActivity activity : workflowConfiguratorBo.getActivitesBy(process)) {
                Treeitem treeitemActivity = _addRow(treeitemProcess, activity.getName(), activity);
                for (WfAction action : workflowConfiguratorBo.getActionsBy(activity)) {
                    _addRow(treeitemActivity, action.getName(), action);
                }
            }
        }
    }

    @Override
    protected void _initAccessible() {
        super._initAccessible();
        addAccessibleListener(zkView.getBtnAdd(), new CCAccessibleListener() {
            @Override
            public boolean isAccessible() {
                return !(selectedObject instanceof WfActivity)
                        || !ActivityPointType.FINAL_POINT.equals(((WfActivity) selectedObject).getActivityPointType());
            }
        });
    }

    @Override
    protected void _initListener() {
        super._initListener();
        addListener(treeProcess.getMeshElement(), new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doTreeProcess();
            }
        });
    }

    private void _doTreeProcess() {
        selectedObject = treeitem_object.get((Treeitem) treeProcess.getRowElement(treeProcess.getSelectedRow()));
        if (isNull(selectedObject)) {
            treeProcess.setSelectedRow(true, 0);
            _doTreeProcess();
        } else {
            for (String string : sysRef_zkCtrl.keySet()) {
                doCloseTab(string);
            }
            applyAccessible(zkView.getBtnAdd());
            parameter.put(toString() + "selectedObject", selectedObject);
            Class entity = _getEntity();
            ((Tab) zkView.getTabEntity().getTabs().getFirstChild()).setLabel(workflowConfiguratorBo.newBreadcrumbsTitle(selectedObject) + getLabel(entity));
            tblEntity.setEntity(entity);
            if (WfActivity.class.equals(entity)) {
                tblEntity.setHqlFilters(new isProcess() {
                    @Override
                    public Object getFieldValue() {
                        return selectedObject;
                    }
                });
            } else if (WfAction.class.equals(entity)) {
                tblEntity.setHqlFilters(new isActivity() {
                    @Override
                    public Object getFieldValue() {
                        return selectedObject;
                    }
                });
            } else if (WfTransition.class.equals(entity)) {
                tblEntity.setHqlFilters(new isAction() {
                    @Override
                    public Object getFieldValue() {
                        return selectedObject;
                    }
                });
            }
            tblEntity.search();
        }
    }

    @Override
    protected boolean _doDeleteEntity() {
        parameter.put(toString() + "crudObject", _getSelectedItem());
        return super._doDeleteEntity();
    }

    @Override
    protected String _getTabTitle() {
        return _getEntityLabel();
    }

    @Override
    protected void _doUpdateComponent() {
        _doTreeProcess();
        super._doUpdateComponent();
    }

    @Override
    public void doUpdateTable() {
        _doUpdateTreeProcess();
        super.doUpdateTable();
        requestFocusInWindow(treeProcess.getMeshElement());
    }

    @Override
    public void doUpdateTablePartial(Object objEntity) {
        _doUpdateTreeProcess();
        super.doUpdateTablePartial(objEntity);
    }

    private void _doUpdateTreeProcess() {
        Object crudObject = parameter.get(toString() + "crudObject");
        if (crudObject instanceof WfProcess
                || crudObject instanceof WfActivity
                || crudObject instanceof WfAction) {
            Treeitem selectedTreeitem = object_treeitem.get(getSysRef(selectedObject));
            Treeitem crudTreeitem = object_treeitem.get(getSysRef(crudObject));
            if (isNotNull(crudObject = CCEntityBo.INSTANCE.reLoad((CCEntity) crudObject))) {
                String label = getUniqueKeyValue(crudObject).toString();
                if (isNotNull(crudTreeitem)) {
                    ((Treecell) crudTreeitem.getFirstChild().getFirstChild()).setLabel(label);
                } else {
                    _addRow(selectedTreeitem, label, crudObject);
                }
            } else {
                selectedTreeitem.getTreechildren().getChildren().remove(crudTreeitem);
            }
            parameter.put(toString() + "crudObject", null);
        }
    }
}