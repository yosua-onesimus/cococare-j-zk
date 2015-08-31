package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.common.CCAccessibleListener;
import static cococare.common.CCClass.*;
import static cococare.common.CCLogic.isNotNull;
import static cococare.common.CCLogic.isNullOrEmpty;
import static cococare.common.CCMessage.logp;
import cococare.database.CCEntityModule;
import cococare.framework.model.obj.util.UtilConfig;
import cococare.framework.zk.CFZkCtrl;
import static cococare.zk.CCZk.*;
import org.zkoss.zul.Combobox;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulParameterListCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private Combobox cmbEntity;
//</editor-fold>

    @Override
    protected Class _getEntity() {
        if (isNotNull(cmbEntity) && cmbEntity.getSelectedIndex() > -1) {
            return CCEntityModule.INSTANCE.getCCHibernate().getParameterClasses().get(cmbEntity.getSelectedIndex());
        } else {
            return UtilConfig.class;
        }
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.LIST_FUNCTION;
    }

    @Override
    protected void _initComponent() {
        super._initComponent();
        for (Class clazz : CCEntityModule.INSTANCE.getCCHibernate().getParameterClasses()) {
            cmbEntity.appendItem(getLabel(clazz));
        }
        cmbEntity.setSelectedIndex(0);
    }

    @Override
    protected void _initNaviElements() {
        super._initNaviElements();
        CCAccessibleListener notUtilConfig = new CCAccessibleListener() {
            @Override
            public boolean isAccessible() {
                return !UtilConfig.class.equals(_getEntity());
            }
        };
        addAccessibleListener(zkView.getBtnAdd(), notUtilConfig);
        addAccessibleListener(zkView.getBtnEdit(), notUtilConfig);
        addAccessibleListener(zkView.getBtnDelete(), notUtilConfig);
    }

    @Override
    protected void _initListener() {
        super._initListener();
        addListener(cmbEntity, elSearch);
    }

    @Override
    protected boolean _doShowEditor(boolean readonly, Object objEntity) {
        String controllerClass = getCCTypeConfig(_getEntity()).controllerClass();
        if (isNullOrEmpty(controllerClass)) {
            return super._doShowEditor(readonly, objEntity);
        } else {
            try {
                return ((CFZkCtrl) newObject(controllerClass)).with(parameter).with(this).with(readonly).init(objEntity);
            } catch (NullPointerException nullPointerException) {
                logp(nullPointerException);
                return false;
            }
        }
    }

    @Override
    public void doUpdateTable() {
        applyAccessible(zkView.getBtnAdd());
        tblEntity.setEntity(_getEntity());
        super.doUpdateTable();
    }
}