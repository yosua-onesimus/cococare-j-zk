package cococare.framework.zk.controller.zul.wf;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.framework.model.obj.wf.WfAction;
import cococare.framework.model.obj.wf.WfActivity;
import static cococare.framework.model.obj.wf.WfFilter.*;
import cococare.framework.zk.controller.zul.ZulDefaultCtrl;
import cococare.zk.CCBandbox;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulActionCtrl extends ZulDefaultCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private CCBandbox bndActionVisibility;
    private CCBandbox bndAdditionalInput;
    private CCBandbox bndRouteValidation;
//</editor-fold>

    @Override
    protected void _initEditor() {
        super._initEditor();
        bndActionVisibility.getTable().setHqlFilters(isTypeIsActionVisibility);
        bndAdditionalInput.getTable().setHqlFilters(isTypeIsAdditionalInput);
        bndRouteValidation.getTable().setHqlFilters(isTypeIsRouteValidation);
    }

    @Override
    protected void _initObjEntity() {
        super._initObjEntity();
        ((WfAction) objEntity).setActivity((WfActivity) parameter.get(callerCtrl.toString() + "selectedObject"));
    }

    @Override
    protected boolean _doSaveEntity() {
        parameter.put(callerCtrl.toString() + "crudObject", objEntity);
        return super._doSaveEntity();
    }
}