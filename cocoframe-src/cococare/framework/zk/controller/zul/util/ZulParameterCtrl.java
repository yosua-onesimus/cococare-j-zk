package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCClass.getLabel;
import static cococare.common.CCLogic.isNotNull;
import cococare.framework.model.obj.util.UtilConfig;
import cococare.framework.zk.CFZkCtrl;
import static cococare.framework.zk.CFZkMap.newContainer;
import cococare.framework.zk.CFZkView;
import static cococare.zk.CCZk.initComponent;
import static cococare.zk.CCZk.initSpecialComponent;
import org.zkoss.zul.Grid;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulParameterCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private Grid pnlGenerator;
//</editor-fold>

    @Override
    protected Class _getEntity() {
        return isNotNull(objEntity) ? objEntity.getClass() : UtilConfig.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.FORM_FUNCTION;
    }

    @Override
    protected void _initContainer() {
        zkView = new CFZkView(newContainer(ZulParameterCtrl.class));
    }

    @Override
    protected void _initEditor() {
        super._initEditor();
        edtEntity.generateDefaultEditor(pnlGenerator);
        initComponent(getContainer(), this, reinitComponents);
        initSpecialComponent(getContainer(), this);
    }

    @Override
    protected String _getTabTitle() {
        return getLabel(_getEntity()) + ": " + super._getTabTitle();
    }
}