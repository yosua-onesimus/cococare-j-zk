package cococare.framework.zk.controller.zul.note;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.framework.model.obj.note.NoteEnum.ReferenceType;
import cococare.framework.model.obj.note.NoteReference;
import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCAttachment;
import static cococare.zk.CCZk.addListener;
import static cococare.zk.CCZk.getCCAttachment;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulReferenceCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private Combobox cmbType;
    private CCAttachment attByteA;
    private Textbox txtValue;
//</editor-fold>

    @Override
    protected Class _getEntity() {
        return NoteReference.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.FORM_FUNCTION;
    }

    @Override
    protected void _initEditor() {
        super._initEditor();
        attByteA = getCCAttachment(getContainer(), "attByteA");
    }

    @Override
    protected void _initListener() {
        super._initListener();
        addListener(cmbType, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                _doUpdateVisibility();
            }
        });
    }

    @Override
    protected void _doUpdateEditor() {
        super._doUpdateEditor();
        _doUpdateVisibility();
    }

    private void _doUpdateVisibility() {
        ReferenceType referenceType = ReferenceType.values()[cmbType.getSelectedIndex()];
        attByteA.getDiv().setVisible(ReferenceType.ATTACHMENT.equals(referenceType));
        txtValue.setVisible(!ReferenceType.ATTACHMENT.equals(referenceType));
    }

    @Override
    protected void _getValueFromEditor() {
        super._getValueFromEditor();
        ReferenceType referenceType = ReferenceType.values()[cmbType.getSelectedIndex()];
        if (ReferenceType.ATTACHMENT.equals(referenceType)) {
            ((NoteReference) objEntity).setValue(attByteA.getFileName());
        }
    }
}