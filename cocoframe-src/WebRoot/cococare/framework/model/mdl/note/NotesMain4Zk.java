package cococare.framework.model.mdl.note;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.framework.model.mdl.note.NotesLanguage.*;
import cococare.framework.zk.CFZkMain;
import static cococare.framework.zk.CFZkMap.getMenubarH;
import static cococare.framework.zk.CFZkMap.getMenubarV;
import cococare.framework.zk.CFZkUae;
import cococare.framework.zk.controller.zul.note.ZulBookmarkListCtrl;
import cococare.framework.zk.controller.zul.note.ZulObjectiveListCtrl;
import cococare.framework.zk.controller.zul.note.ZulShortcutListCtrl;
import cococare.framework.zk.controller.zul.note.ZulTrackerListCtrl;
import cococare.framework.zk.controller.zul.util.ZulLoginCtrl;
import cococare.zk.CCMenubar;
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
    public boolean initInitialData() {
        super.initInitialData();
        CFZkUae zkUae = new CFZkUae();
        zkUae.reg(Note, Bookmark, ZulBookmarkListCtrl.class);
        zkUae.reg(Note, Shortcut, ZulShortcutListCtrl.class);
        zkUae.reg(Note, Objective, ZulObjectiveListCtrl.class);
        zkUae.reg(Note, Tracker, ZulTrackerListCtrl.class);
        return _initInitialDataUaeUtility(zkUae).compile();
    }

    @Override
    protected void _applyUserConfig() {
        CFZkUae zkUae = new CFZkUae();
        zkUae.initMenuBar(MenuPosition.LEFT_SIDE.equals(MENU_POST) ? new CCMenubar(getMenubarV()) : new CCMenubar(getMenubarH()));
        zkUae.addMenuRoot(ZulLoginCtrl.class);
        zkUae.addMenuParent(Notes, null, null);
        zkUae.addMenuChild(Bookmark, null, ZulBookmarkListCtrl.class);
        zkUae.addMenuChild(Shortcut, null, ZulShortcutListCtrl.class);
        zkUae.addMenuChild(Objective, null, ZulObjectiveListCtrl.class);
        zkUae.addMenuChild(Tracker, null, ZulTrackerListCtrl.class);
        zkUae.changeMenuSide();
        _applyUserConfigUaeUtility(zkUae).compileMenu();
    }
}