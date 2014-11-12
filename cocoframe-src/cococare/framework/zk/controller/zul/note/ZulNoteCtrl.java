package cococare.framework.zk.controller.zul.note;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.framework.model.bo.note.NoteNoteBo;
import cococare.framework.model.obj.note.NoteNote;
import cococare.framework.model.obj.note.NoteTag;
import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCOptionbox;
import static cococare.zk.CCZk.newCCOptionbox;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulNoteCtrl extends CFZkCtrl {

//<editor-fold defaultstate="collapsed" desc=" private object ">
    private NoteNoteBo noteBo;
    private CCOptionbox optTag;
//</editor-fold>

    @Override
    protected Class _getEntity() {
        return NoteNote.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.FORM_FUNCTION;
    }

    @Override
    protected void _initObject() {
        super._initObject();
        noteBo.load((NoteNote) objEntity);
    }

    @Override
    protected void _initComponent() {
        super._initComponent();
        _addChildScreen("note", new ZulNoteReferenceListCtrl(), "zulNoteReference");
    }

    @Override
    protected void _initEditor() {
        super._initEditor();
        optTag = newCCOptionbox(getContainer(), "optTag", false, NoteTag.class, "name");
        optTag.setColumnCount(1);
        optTag.autoList();
    }

    @Override
    protected boolean _doSaveEntity() {
        return noteBo.saveOrUpdate(_getEntityChilds());
    }

    @Override
    protected void _doUpdateEditor() {
        super._doUpdateEditor();
        optTag.setSelectedItem(true, noteBo.getSelectedTags().toArray());
    }

    @Override
    protected void _getValueFromEditor() {
        super._getValueFromEditor();
        noteBo.setSelectedTags(optTag.getSelectedItems());
    }
}