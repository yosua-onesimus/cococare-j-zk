package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.framework.model.obj.util.UtilFilter;
import cococare.framework.model.obj.util.UtilUser;
import cococare.framework.zk.CFZkCtrl;
import static cococare.zk.CCZk.addListener;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulUserListCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private Button btnChangePassword;
//</editor-fold>

    @Override
    protected Class _getEntity() {
        return UtilUser.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.LIST_FUNCTION;
    }

    @Override
    protected void _initTable() {
        super._initTable();
        tblEntity.setHqlFilters(UtilFilter.isUserNotRoot);
    }

    @Override
    protected void _initNaviElements() {
        tblEntity.setNaviElements(zkView.getPgnEntity(), zkView.getTxtKeyword(),
                zkView.getBtnView(), zkView.getBtnEdit(), btnChangePassword, zkView.getBtnDelete());
    }

    @Override
    protected void _initListener() {
        super._initListener();
        addListener(btnChangePassword, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doChangePassword();
            }
        });
    }

    private void _doChangePassword() {
        new ZulChangePasswordCtrl().with(this).init(tblEntity.getSelectedItem());
    }

    @Override
    protected boolean _doDeleteEntity() {
        return tblEntity.deleteBySetting(_getSelectedItem()) > 0;
    }
}