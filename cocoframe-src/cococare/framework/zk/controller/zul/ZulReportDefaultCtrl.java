package cococare.framework.zk.controller.zul;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.common.jasperreports.CCReport;
import cococare.framework.zk.CFZkCtrl;
import static cococare.zk.CCZk.setIframeContent;
import org.zkoss.zul.Iframe;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulReportDefaultCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private Iframe frmReport;
//</editor-fold>

    @Override
    protected Class _getClass() {
        return ZulReportDefaultCtrl.class;
    }

    @Override
    protected Class _getEntity() {
        return objEntity.getClass();
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.FORM_FUNCTION;
    }

    @Override
    protected ShowMode _getShowMode() {
        return (callerCtrl instanceof ZulReportDefaultListCtrl) ? ShowMode.TAB_MODE : ShowMode.DIALOG_MODE;
    }

    @Override
    protected void _initComponent() {
        super._initComponent();
        setIframeContent(frmReport, ((CCReport) objEntity).newReport().getPdfStream());
    }

    @Override
    protected String _getSysRef(Object objEntity) {
        return (callerCtrl instanceof ZulReportDefaultListCtrl) ? parameter.get(toString()).toString() : "";
    }

    @Override
    protected String _getTabTitle() {
        return ((CCReport) objEntity).getReportName();
    }
}