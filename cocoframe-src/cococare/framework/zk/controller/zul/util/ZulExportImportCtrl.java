package cococare.framework.zk.controller.zul.util;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.common.CCAccessibleListener;
import static cococare.common.CCClass.getLabel;
import static cococare.common.CCLogic.isNotNull;
import cococare.database.CCEntityBo;
import cococare.database.CCEntityModule;
import static cococare.datafile.CCFile.getFileUserTempFile;
import cococare.datafile.jxl.CCExcel;
import cococare.framework.zk.CFZkCtrl;
import static cococare.zk.CCLogic.isSureExport;
import static cococare.zk.CCLogic.isSureImport;
import static cococare.zk.CCMessage.showExport;
import static cococare.zk.CCMessage.showImport;
import cococare.zk.CCOptionbox;
import static cococare.zk.CCZk.*;
import static cococare.zk.datafile.CCFile.download;
import static cococare.zk.datafile.CCFile.upload;
import java.io.File;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulExportImportCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private Button btnTemplate;
    private Button btnImport;
    private CCOptionbox optParameter;
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
    protected void _initComponent() {
        optParameter = newCCOptionbox(getContainer(), "optParameter");
        super._initComponent();
        for (Class clazz : CCEntityModule.INSTANCE.getCCHibernate().getParameterClasses()) {
            optParameter.addItem(getLabel(clazz));
        }
    }

    @Override
    protected void _initAccessible() {
        super._initAccessible();
        CCAccessibleListener isSelected = new CCAccessibleListener() {
            @Override
            public boolean isAccessible() {
                return optParameter.isSelected();
            }
        };
        addAccessibleListener(zkView.getBtnExport(), isSelected);
        addAccessibleListener(btnTemplate, isSelected);
        addAccessibleListener(btnImport, isSelected);
    }

    @Override
    protected void _doUpdateAccessible() {
        super._doUpdateAccessible();
        applyAccessible(zkView.getBtnExport(), btnTemplate, btnImport);
    }

    @Override
    protected void _initListener() {
        super._initListener();
        addListener(zkView.getBtnExport(), new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doExport(true);
            }
        });
        addListener(btnTemplate, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doExport(false);
            }
        });
        addListener(btnImport, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doImport();
            }
        });
        optParameter.addListenerOnClick(new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doUpdateAccessible();
            }
        });
    }

    private void _doExport(boolean withData) {
        if (isSureExport()) {
            File file = getFileUserTempFile("Parameter.xls");
            CCExcel excel = new CCExcel();
            excel.newWorkbook();
            for (int index : optParameter.getSelectedIndexes()) {
                Class clazz = CCEntityModule.INSTANCE.getCCHibernate().getParameterClasses().get(index);
                excel.newSheet(clazz.getSimpleName());
                excel.initEntity(clazz, false);
                excel.writeRowEntityHeader();
                if (withData) {
                    excel.writeRowEntity(CCEntityBo.INSTANCE.getListBy(clazz, null, null, null, 0, null));
                }
            }
            showExport(updateCaller = (excel.saveAndCloseWorkbook(file) && download(file)));
        }
    }

    private void _doImport() {
        if (isSureImport()) {
            Media media;
            if (isNotNull(media = upload())) {
                CCExcel excel = new CCExcel();
                excel.openWorkbook(media.getStreamData());
                for (int index : optParameter.getSelectedIndexes()) {
                    Class clazz = CCEntityModule.INSTANCE.getCCHibernate().getParameterClasses().get(index);
                    if (isNotNull(excel.getSheet(clazz.getSimpleName()))) {
                        excel.initEntity(clazz, false);
                        if (!(updateCaller = CCEntityModule.INSTANCE.getCCHibernate().restore(excel.readRowEntity(1, excel.getRowCount() - 1)))) {
                            break;
                        }
                    }
                }
                showImport(updateCaller);
            }
        }
    }
}