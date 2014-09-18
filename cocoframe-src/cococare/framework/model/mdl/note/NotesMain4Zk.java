package cococare.framework.model.mdl.note;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.framework.common.CFApplUae;
import static cococare.framework.model.mdl.note.NotesLanguage.*;
import cococare.framework.zk.CFZkMain;
import cococare.framework.zk.controller.zul.note.ZulBookmarkListCtrl;
import cococare.framework.zk.controller.zul.note.ZulObjectiveListCtrl;
import cococare.framework.zk.controller.zul.note.ZulShortcutListCtrl;
import cococare.framework.zk.controller.zul.note.ZulTrackerListCtrl;
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
        init(false, NotesLanguage.class);
        super._loadExternalSetting();
    }

    @Override
    protected void _initDatabaseEntity() {
        super._initDatabaseEntity();
        NotesModule.INSTANCE.init(HIBERNATE);
    }

    @Override
    protected void _initInitialUaeBody(CFApplUae uae) {
        uae.reg(Note, Bookmark, ZulBookmarkListCtrl.class);
        uae.reg(Note, Shortcut, ZulShortcutListCtrl.class);
        uae.reg(Note, Objective, ZulObjectiveListCtrl.class);
        uae.reg(Note, Tracker, ZulTrackerListCtrl.class);
    }

    @Override
    protected void _applyUserConfigUaeBody(CFApplUae uae) {
        uae.addMenuParent(Notes, null, null);
        uae.addMenuChild(Bookmark, null, ZulBookmarkListCtrl.class);
        uae.addMenuChild(Shortcut, null, ZulShortcutListCtrl.class);
        uae.addMenuChild(Objective, null, ZulObjectiveListCtrl.class);
        uae.addMenuChild(Tracker, null, ZulTrackerListCtrl.class);
    }
}