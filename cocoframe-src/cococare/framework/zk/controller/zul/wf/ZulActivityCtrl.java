package cococare.framework.zk.controller.zul.wf;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.common.CCFieldConfig.Accessible;
import static cococare.framework.model.obj.util.UtilFilter.isUserGroupNotRoot;
import cococare.framework.model.obj.wf.WfActivity;
import cococare.framework.model.obj.wf.WfEnum.ActivityPointType;
import cococare.framework.model.obj.wf.WfProcess;
import cococare.framework.zk.controller.zul.ZulDefaultWithChildCtrl;
import cococare.zk.CCBandbox;
import cococare.zk.CCTable;
import static cococare.zk.CCZk.addListener;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulActivityCtrl extends ZulDefaultWithChildCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private Intbox txtDayLimit;
    private CCBandbox bndUserRole;
    private Intbox txtWeight;
    private Combobox cmbPointType;
//</editor-fold>

    @Override
    protected void _initComponent() {
        super._initComponent();
        _addChildScreen2("Tab", "activity", new ZulActivityTabListCtrl());
    }

    @Override
    protected void _initEditor() {
        super._initEditor();
        bndUserRole.getTable().setHqlFilters(isUserGroupNotRoot);
    }

    @Override
    protected void _initObjEntity() {
        super._initObjEntity();
        WfActivity activity = (WfActivity) objEntity;
        activity.setProcess((WfProcess) parameter.get(callerCtrl.toString() + "selectedObject"));
        if (((CCTable) parameter.get(callerCtrl.toString() + "tblEntity")).getRowCount() == 0) {
            activity.setActivityPointType(ActivityPointType.START_POINT);
        }
    }

    @Override
    protected void _initListener() {
        super._initListener();
        addListener(cmbPointType, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doPointType();
            }
        });
    }

    private void _doPointType() {
        boolean isFinalPoint = ActivityPointType.FINAL_POINT.equals(ActivityPointType.values()[cmbPointType.getSelectedIndex()]);
        edtEntity.setAccessible(txtDayLimit, isFinalPoint ? Accessible.READONLY_SET_NULL : Accessible.MANDATORY);
        edtEntity.setAccessible(bndUserRole.getBandbox(), isFinalPoint ? Accessible.READONLY_SET_NULL : Accessible.MANDATORY);
        edtEntity.setAccessible(txtWeight, isFinalPoint ? Accessible.READONLY_SET_NULL : Accessible.MANDATORY);
    }

    @Override
    protected boolean _doSaveEntity() {
        parameter.put(callerCtrl.toString() + "crudObject", objEntity);
        return super._doSaveEntity();
    }

    @Override
    protected void _doUpdateEditor() {
        super._doUpdateEditor();
        _doPointType();
    }
}