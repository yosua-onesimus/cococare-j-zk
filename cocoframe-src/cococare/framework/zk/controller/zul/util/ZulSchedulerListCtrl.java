package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.common.CCCustomField;
import cococare.framework.model.obj.util.UtilScheduler;
import cococare.framework.zk.CFZkCtrl;
import static cococare.zk.CCZk.addListener;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.A;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulSchedulerListCtrl extends CFZkCtrl {

    @Override
    protected Class _getEntity() {
        return UtilScheduler.class;
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
    protected void _initTable() {
        super._initTable();
        tblEntity.setVisibleField(false, "code");
        tblEntity.addField(0, new CCCustomField() {
            @Override
            public String getLabel() {
                return "Code";
            }

            @Override
            public Object getCustomView(Object object) {
                final UtilScheduler scheduler = (UtilScheduler) object;
                A a = new A(scheduler.getCode());
                addListener(a, new EventListener() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        _doShowEditor(readonly, scheduler);
                    }
                });
                return a;
            }

            @Override
            public Integer getColumnWidth() {
                return 100;
            }
        });
        tblEntity.setEditableColumn(true, 0);
    }

    @Override
    protected String _getTabTitle() {
        return _getEntityLabel();
    }
}