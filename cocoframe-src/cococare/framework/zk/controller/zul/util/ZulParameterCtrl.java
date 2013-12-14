package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCLogic.isNotNull;
import cococare.framework.model.obj.util.UtilConfig;
import cococare.framework.zk.CFZkCtrl;
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
        if (isNotNull(objEntity)) {
            return objEntity.getClass();
        } else {
            return UtilConfig.class;
        }
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.FORM_FUNCTION;
    }

    @Override
    protected void _initEditor() {
        super._initEditor();
        //
        edtEntity.generateDefaultEditor(pnlGenerator);
    }
}