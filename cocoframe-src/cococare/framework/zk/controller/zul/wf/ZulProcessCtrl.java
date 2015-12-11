package cococare.framework.zk.controller.zul.wf;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.framework.model.obj.wf.WfFilter.isTypeIsPostRouteProcess;
import cococare.framework.zk.controller.zul.ZulDefaultCtrl;
import cococare.zk.CCBandbox;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulProcessCtrl extends ZulDefaultCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private CCBandbox bndPostRouteProcess;
//</editor-fold>

    @Override
    protected void _initEditor() {
        super._initEditor();
        bndPostRouteProcess.getTable().setHqlFilters(isTypeIsPostRouteProcess);
    }

    @Override
    protected boolean _doSaveEntity() {
        parameter.put(callerCtrl.toString() + "crudObject", objEntity);
        return super._doSaveEntity();
    }
}