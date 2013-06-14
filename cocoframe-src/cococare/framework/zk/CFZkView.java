package cococare.framework.zk;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCFinal.*;
import static cococare.zk.CCZk.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.MeshElement;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class CFZkView {

// <editor-fold defaultstate="collapsed" desc=" private object ">
    private Component container;
// </editor-fold>

//<editor-fold defaultstate="collapsed" desc=" CFZkView ">
    public CFZkView(Component container) {
        this.container = container;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" getter ">
    public Component getContainer() {
        return container;
    }

    public Button getBtnAdd() {
        return getButton(getContainer(), btnAdd);
    }

    public Button getBtnView() {
        return getButton(getContainer(), btnView);
    }

    public Button getBtnEdit() {
        return getButton(getContainer(), btnEdit);
    }

    public Button getBtnDelete() {
        return getButton(getContainer(), btnDelete);
    }

    public Button getBtnExport() {
        return getButton(getContainer(), btnExport);
    }

    public Button getBtnNew() {
        return getButton(getContainer(), btnNew);
    }

    public Button getBtnSave() {
        return getButton(getContainer(), btnSave);
    }

    public Button getBtnSaveAndNew() {
        return getButton(getContainer(), btnSaveAndNew);
    }

    public Button getBtnCancel() {
        return getButton(getContainer(), btnCancel);
    }

    public Button getBtnClose() {
        return getButton(getContainer(), btnClose);
    }

    public Textbox getTxtKeyword() {
        return getTextbox(getContainer(), txtKeyword);
    }

    public Tabbox getTabEntity() {
        return getTabbox(getContainer(), tabEntity);
    }

    public MeshElement getTblEntity() {
        return getMeshElement(getContainer(), tblEntity);
    }

    public Paging getPgnEntity() {
        return getPaging(getContainer(), pgnEntity);
    }

    public Component getPnlEntity() {
        return getComponent(getContainer(), pnlEntity);
    }

    public Button getBtnFirst() {
        return getButton(getContainer(), btnFirst);
    }

    public Button getBtnPrev() {
        return getButton(getContainer(), btnPrev);
    }

    public Button getBtnNext() {
        return getButton(getContainer(), btnNext);
    }

    public Button getBtnLast() {
        return getButton(getContainer(), btnLast);
    }
//</editor-fold>
}