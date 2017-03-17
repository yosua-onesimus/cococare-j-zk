package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.common.CCCustomField;
import static cococare.common.CCFinal.FORMAT_MYSQL;
import static cococare.common.CCFormat.getString;
import static cococare.common.CCLanguage.Logger_History;
import static cococare.common.CCLanguage.turn;
import cococare.database.CCHibernateFilter;
import cococare.framework.model.obj.util.UtilLogger;
import cococare.framework.zk.CFZkCtrl;
import static cococare.zk.CCZk.addListener;
import static cococare.zk.CCZk.fillUp;
import org.zkoss.zul.Combobox;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulLoggerListCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private Combobox cmbUsername;
    private Combobox cmbScreen;
    private Combobox cmbAction;
//</editor-fold>

    @Override
    protected Class _getEntity() {
        return UtilLogger.class;
    }

    @Override
    protected String _getEntityLabel() {
        return turn(Logger_History);
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.LIST_FUNCTION;
    }

    @Override
    protected void _initComponent() {
        super._initComponent();
        fillUp(cmbUsername, loggerBo.getUsernames(), true, false);
        fillUp(cmbScreen, loggerBo.getScreens(), true, false);
        fillUp(cmbAction, loggerBo.getActions(), true, false);
    }

    @Override
    protected void _initTable() {
        super._initTable();
        tblEntity.addField(0, new CCCustomField() {
            @Override
            public String getLabel() {
                return "Date Time";
            }

            @Override
            public Object getCustomView(Object object) {
                return getString(((UtilLogger) object).getLogCreatedOn(), FORMAT_MYSQL);
            }
        });
        tblEntity.setColumnWidth(150, 100, 100, 100, 150, 100, null);
        tblEntity.setHqlFilters(new CCHibernateFilter() {
            @Override
            public String getFieldName() {
                return "username";
            }

            @Override
            public Object getFieldValue() {
                return cmbUsername.getSelectedIndex() < 1 ? null : cmbUsername.getText();
            }
        }, new CCHibernateFilter() {
            @Override
            public String getFieldName() {
                return "screen";
            }

            @Override
            public Object getFieldValue() {
                return cmbScreen.getSelectedIndex() < 1 ? null : cmbScreen.getText();
            }
        }, new CCHibernateFilter() {
            @Override
            public String getFieldName() {
                return "action";
            }

            @Override
            public Object getFieldValue() {
                return cmbAction.getSelectedIndex() < 1 ? null : cmbAction.getText();
            }
        });
        tblEntity.setHqlOrderSyntax("id DESC");
    }

    @Override
    protected void _initListener() {
        super._initListener();
        addListener(cmbUsername, elSearch);
        addListener(cmbScreen, elSearch);
        addListener(cmbAction, elSearch);
    }
}