package cococare.framework.zk.controller.zul;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCClass.newObject;
import cococare.common.jasperreports.CCReport;
import static cococare.datafile.CCFile.getFileUserTempFile;
import cococare.framework.zk.CFZkCtrl;
import static cococare.framework.zk.CFZkMap.getControllerZul;
import static cococare.zk.CCZk.addListener;
import static cococare.zk.CCZk.fillUp;
import static cococare.zk.datafile.CCFile.download;
import java.io.File;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public abstract class ZulReportDefaultListCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    protected Button _btnView;
    protected Button _btnExport;
    protected Combobox cmbReport;
//</editor-fold>

    @Override
    protected Class _getClass() {
        return ZulReportDefaultListCtrl.class;
    }

    protected abstract Class _getReportEnum();

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.LIST_FUNCTION;
    }

    @Override
    protected void _initComponent() {
        _initEditor();
        super._initComponent();
        fillUp(cmbReport, _getReportEnum(), false, false);
    }

    @Override
    protected void _initListener() {
        super._initListener();
        addListener(cmbReport, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doUpdateAccessible();
            }
        });
        addListener(_btnView, elView);
        addListener(_btnExport, elExport);
    }

    @Override
    protected void _doView() {
        if (edtEntity.isValueValid()) {
            CFZkCtrl reportCtrl = newObject(getControllerZul(_getClass()));
            parameter.put(reportCtrl.toString(), zkView.getTabEntity().getTabs().getChildren().size());
            reportCtrl.with(parameter).with(this).init(edtEntity.getValueFromEditor());
        }
    }

    @Override
    protected void _doExport() {
        if (edtEntity.isValueValid()) {
            CCReport report = edtEntity.getValueFromEditor();
            File file = getFileUserTempFile(report.getReportName() + ".xls");
            if (report.newReport().exportAsXlsFile(file.getPath())) {
                download(file);
            }
        }
    }

    @Override
    protected void _doUpdateComponent() {
        edtEntity.setValueToEditor(edtEntity.newItem());
    }
}