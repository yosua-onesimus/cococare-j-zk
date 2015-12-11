package cococare.framework.zk.controller.zul.wf;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCLogic.isNotNull;
import cococare.framework.model.obj.wf.WfActivityTab;
import cococare.framework.model.obj.wf.WfTab;
import cococare.framework.zk.controller.zul.ZulDefaultCtrl;
import cococare.zk.CCBandbox;
import static cococare.zk.CCZk.addListener;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Textbox;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulActivityTabCtrl extends ZulDefaultCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private CCBandbox bndTab;
    private Textbox txtTabName;
//</editor-fold>

    @Override
    protected Class _getEntity() {
        return WfActivityTab.class;
    }

    @Override
    protected void _initListener() {
        super._initListener();
        addListener(bndTab.getBandbox(), new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doUpdateTxtTabName();
            }
        });
    }

    private void _doUpdateTxtTabName() {
        WfTab tab = bndTab.getObject();
        if (newEntity && isNotNull(tab)) {
            txtTabName.setText(tab.getName());
        }
    }
}