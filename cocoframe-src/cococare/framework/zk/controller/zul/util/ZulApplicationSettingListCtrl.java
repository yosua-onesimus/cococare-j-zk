package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCClass.getCCTypeConfig;
import static cococare.common.CCClass.newObject;
import static cococare.common.CCConfig.COM_SHOW_JAVAX_COMM_MODULE;
import static cococare.common.CCConfig.HBN_WORKFLOW_MODULE_INCLUDED;
import cococare.common.CCCustomField;
import static cococare.common.CCLogic.isNullOrEmpty;
import static cococare.common.CCMessage.logp;
import cococare.common.CCTypeConfig;
import cococare.framework.model.bo.util.UtilConfigBo;
import cococare.framework.model.obj.util.UtilConfAppl;
import cococare.framework.model.obj.util.UtilConfServ;
import cococare.framework.model.obj.util.UtilScheduler;
import cococare.framework.model.obj.wf.WfWorkflow;
import cococare.framework.zk.CFZkCtrl;
import static cococare.zk.CCZk.addListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.A;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulApplicationSettingListCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private UtilConfigBo configBo;
    private List<Class> settingClasses;
    private HashMap<CCTypeConfig, Class> typeConfig_settingClass;
//</editor-fold>

    @Override
    protected Class _getEntity() {
        return CCTypeConfig.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.LIST_FUNCTION;
    }

    @Override
    protected void _initObject() {
        super._initObject();
        settingClasses = new ArrayList();
        settingClasses.add(UtilConfAppl.class);
        if (COM_SHOW_JAVAX_COMM_MODULE) {
        }
        settingClasses.add(UtilConfServ.class);
        settingClasses.addAll(configBo.getConfAppl().getUtilAdditionalSettingClass());
        settingClasses.add(UtilScheduler.class);
        if (HBN_WORKFLOW_MODULE_INCLUDED) {
            settingClasses.add(WfWorkflow.class);
        }
        typeConfig_settingClass = new LinkedHashMap();
        for (Class settingClass : settingClasses) {
            typeConfig_settingClass.put(getCCTypeConfig(settingClass), settingClass);
        }
    }

    @Override
    protected void _initTable() {
        super._initTable();
        tblEntity.initFields();
        tblEntity.addField(0, new CCCustomField() {
            @Override
            public String getLabel() {
                return "Module Name";
            }

            @Override
            public Integer getColumnWidth() {
                return 200;
            }

            @Override
            public Object getCustomView(Object object) {
                try {
                    final CCTypeConfig typeConfig = (CCTypeConfig) object;
                    A a = new A(typeConfig.label());
                    addListener(a, new EventListener() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            _doEdit(typeConfig);
                        }
                    });
                    return a;
                } catch (NullPointerException nullPointerException) {
                    logp(nullPointerException);
                    return null;
                }
            }
        });
        tblEntity.addField(1, new CCCustomField() {
            @Override
            public String getLabel() {
                return "Description";
            }

            @Override
            public Object getCustomView(Object object) {
                return ((CCTypeConfig) object).tooltiptext();
            }
        });
        tblEntity.setEditableColumn(true, 0);
    }

    @Override
    public void doUpdateTable() {
        tblEntity.removeItems();
        for (CCTypeConfig typeConfig : typeConfig_settingClass.keySet()) {
            tblEntity.addItem(typeConfig);
        }
    }

    private void _doEdit(CCTypeConfig typeConfig) {
        String controllerClass = typeConfig.controllerClass();
        objEntity = configBo.loadHash(typeConfig_settingClass.get(typeConfig));
        if (isNullOrEmpty(controllerClass)) {
            _doShowEditor(readonly, objEntity);
        } else {
            try {
                ((CFZkCtrl) newObject(controllerClass)).with(parameter).with(this).with(readonly).init(objEntity);
            } catch (NullPointerException nullPointerException) {
                logp(nullPointerException);
            }
        }
    }
}