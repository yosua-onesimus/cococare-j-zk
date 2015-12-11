package cococare.framework.zk.controller.zul.wf;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.framework.model.obj.util.UtilFilter.isIdNotId;
import cococare.framework.model.obj.wf.WfAction;
import static cococare.framework.model.obj.wf.WfFilter.isTypeIsPostRouteProcess;
import static cococare.framework.model.obj.wf.WfFilter.isTypeIsRouteAvailability;
import cococare.framework.model.obj.wf.WfTransition;
import cococare.framework.zk.controller.zul.ZulDefaultCtrl;
import cococare.zk.CCBandbox;
import cococare.zk.CCTable;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulTransitionCtrl extends ZulDefaultCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private CCBandbox bndRouteAvailability;
    private CCBandbox bndDestination;
    private CCBandbox bndPostRouteProcess;
//</editor-fold>

    @Override
    protected void _initEditor() {
        super._initEditor();
        bndRouteAvailability.getTable().setHqlFilters(isTypeIsRouteAvailability);
        bndDestination.getTable().setHqlFilters(new isIdNotId() {
            @Override
            public Object getFieldValue() {
                return ((WfTransition) objEntity).getAction().getActivity().getId();
            }
        });
        bndPostRouteProcess.getTable().setHqlFilters(isTypeIsPostRouteProcess);
    }

    @Override
    protected void _initObjEntity() {
        super._initObjEntity();
        WfTransition transition = (WfTransition) objEntity;
        transition.setAction((WfAction) parameter.get(callerCtrl.toString() + "selectedObject"));
        transition.setSequence(((CCTable) parameter.get(callerCtrl.toString() + "tblEntity")).getRowCount());
        transition.setName(transition.getAction().getName());
    }

    @Override
    protected boolean _doSaveEntity() {
        parameter.put(callerCtrl.toString() + "crudObject", objEntity);
        return super._doSaveEntity();
    }
}