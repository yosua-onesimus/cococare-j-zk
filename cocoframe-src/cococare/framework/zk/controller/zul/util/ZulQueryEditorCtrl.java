package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCConfig.HBN_MAX_RESULTS;
import static cococare.common.CCLogic.isNotEmpty;
import static cococare.common.CCLogic.isNull;
import static cococare.common.CCMessage.logp;
import cococare.database.CCDatabase;
import cococare.database.CCEntityModule;
import cococare.database.CCHibernate;
import cococare.framework.zk.CFZkCtrl;
import static cococare.zk.CCMessage.showError;
import cococare.zk.CCTable;
import static cococare.zk.CCZk.addListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulQueryEditorCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private CCDatabase database;
    private Combobox cmbDatabaseConfig;
    private Button _btnRun;
    private Textbox txtSql;
//</editor-fold>

    @Override
    protected Class _getEntity() {
        return null;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.LIST_FUNCTION;
    }

    @Override
    protected void _initObject() {
        super._initObject();
        database = new CCDatabase();
    }

    @Override
    protected void _initComponent() {
        super._initComponent();
        for (CCHibernate hibernate : CCEntityModule.getHibernates()) {
            cmbDatabaseConfig.appendItem(hibernate.getDatabaseConfig().getDatabase());
        }
        cmbDatabaseConfig.setSelectedIndex(0);
    }

    @Override
    protected void _initListener() {
        super._initListener();
        addListener(_btnRun, elSearch);
    }

    @Override
    public void doUpdateTable() {
        int selectedIndex = cmbDatabaseConfig.getSelectedIndex();
        String sql = txtSql.getText();
        if (selectedIndex > -1 && isNotEmpty(sql)) {
            try {
                CCHibernate hibernate = CCEntityModule.getHibernates().get(selectedIndex);
                //get columnNames
                database.getConnection(hibernate.getDatabaseConfig(), false);
                ResultSet resultSet = database.executeQuery(sql, null, HBN_MAX_RESULTS);
                if (isNull(resultSet)) {
                    showError();
                    return;
                }
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                String[] columnNames = new String[resultSetMetaData.getColumnCount()];
                for (int i = 0; i < columnNames.length; i++) {
                    columnNames[i] = resultSetMetaData.getColumnName(i + 1);
                }
                //create tblEntity
                tblEntity = new CCTable(zkView.getTblEntity(), columnNames);
                tblEntity.setSpan(false);
                tblEntity.setSizedByContent(true);
                tblEntity.setPageSize(10);
                tblEntity.removeRows();
                //add rowData
                while (resultSet.next()) {
                    Object[] rowData = new Object[resultSetMetaData.getColumnCount()];
                    for (int i = 0; i < rowData.length; i++) {
                        rowData[i] = resultSet.getObject(i + 1);
                    }
                    tblEntity.addRow(rowData);
                }
            } catch (Exception exception) {
                logp(exception.toString());
                showError();
            }
        }
    }
}