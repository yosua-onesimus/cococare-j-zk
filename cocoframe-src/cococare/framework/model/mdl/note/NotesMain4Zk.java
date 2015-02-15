package cococare.framework.model.mdl.note;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.common.CCLanguage;
import cococare.framework.common.CFApplUae;
import static cococare.framework.model.mdl.note.NotesLanguage.*;
import cococare.framework.zk.CFZkMain;
import cococare.framework.zk.controller.zul.note.ZulNoteListCtrl;
import cococare.framework.zk.controller.zul.note.ZulReferenceListCtrl;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class NotesMain4Zk extends CFZkMain {

    @Override
    protected void _loadInternalSetting() {
        APPL_CODE = "cccr-nts-zk";
        APPL_NAME = "Coco Notes";
        super._loadInternalSetting();
    }

    @Override
    protected void _loadExternalSetting() {
        CCLanguage.init(false, NotesLanguage.class);
        super._loadExternalSetting();
    }

    @Override
    protected void _initDatabaseEntity() {
        super._initDatabaseEntity();
        NotesModule.INSTANCE.init(HIBERNATE);
    }

    @Override
    protected void _initInitialUaeBody(CFApplUae uae) {
        uae.reg(Note, Reference, ZulReferenceListCtrl.class);
        uae.reg(Note, Note, ZulNoteListCtrl.class);
    }

    @Override
    protected void _applyUserConfigUaeBody(CFApplUae uae) {
        uae.addMenuParent(Notes, "/img/icon-menu-parent.png", null);
        uae.addMenuChild(Reference, "/img/icon-menu-child.png", ZulReferenceListCtrl.class);
        uae.addMenuChild(Note, "/img/icon-menu-child.png", ZulNoteListCtrl.class);
    }
}