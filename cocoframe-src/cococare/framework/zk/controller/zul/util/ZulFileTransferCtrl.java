package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCFinal.*;
import static cococare.common.CCLogic.isNotNull;
import cococare.common.ftp.CCFtp;
import static cococare.datafile.CCFile.getFileUserTempFile;
import cococare.framework.model.bo.util.UtilConfigBo;
import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCAttachment;
import cococare.zk.CCTable;
import static cococare.zk.CCZk.*;
import static cococare.zk.datafile.CCFile.download;
import java.io.File;
import org.apache.commons.net.ftp.FTPFile;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listcell;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulFileTransferCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private UtilConfigBo configBo;
    private CCFtp ftp;
    private CCAttachment attFileTransfer;
    private Button btnStoreFile;
    private Button btnRetrieveFile;
    private CCTable tblFileTransfer;
//</editor-fold>

    @Override
    protected Class _getEntity() {
        return null;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.FORM_FUNCTION;
    }

    @Override
    protected ShowMode _getShowMode() {
        return ShowMode.DIALOG_MODE;
    }

    @Override
    protected void _initComponent() {
        super._initComponent();
        ftp = new CCFtp();
        ftp.connect(configBo.getConfServ().getFileTransferHostname(), configBo.getConfServ().getFileTransferPort());
        ftp.login(configBo.getConfServ().getFileTransferUsername(), configBo.getConfServ().getFileTransferPassword());
        ftp.changeWorkingDirectory(configBo.getConfServ().getFileTransferDirectory());
        attFileTransfer = newCCAttachment(getContainer(), "attFileTransfer");
        tblFileTransfer = newCCTable(getContainer(), "tblFileTransfer", "File Name", "File Size", "Date");
        tblFileTransfer.setColumnHorizontalAlignment(_left, _right, _center);
        tblFileTransfer.setColumnWidth(null, 100, 100);
    }

    @Override
    protected void _initListener() {
        super._initListener();
        addListener(btnStoreFile, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doStoreFile();
            }
        });
        addListener(btnRetrieveFile, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doRetrieveFile();
            }
        });
    }

    private void _doStoreFile() {
        if (isNotNull(attFileTransfer.getInputStream())) {
            ftp.storeFile(attFileTransfer.getFileName(), attFileTransfer.getInputStream());
            doUpdateTable();
        }
    }

    private void _doRetrieveFile() {
        if (tblFileTransfer.isSelected()) {
            String fileName = ((Listcell) tblFileTransfer.getCellElement(tblFileTransfer.getSelectedRow(), 0)).getLabel();
            File file = getFileUserTempFile(fileName);
            updateCaller = ftp.retrieveFile(file) && download(file);
        }
    }

    @Override
    protected void _doUpdateComponent() {
        super._doUpdateComponent();
        doUpdateTable();
    }

    @Override
    public void doUpdateTable() {
        tblFileTransfer.removeRows();
        for (FTPFile fTPFile : ftp.listFiles()) {
            if (fTPFile.isFile()) {
                tblFileTransfer.addRow(fTPFile.getName(), fTPFile.getSize(), fTPFile.getTimestamp().getTime());
            }
        }
    }
}