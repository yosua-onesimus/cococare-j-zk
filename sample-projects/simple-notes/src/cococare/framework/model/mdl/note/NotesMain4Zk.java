package cococare.framework.model.mdl.note;

import static cococare.common.CCLanguage.init;
import static cococare.common.CCLanguage.turn;
import static cococare.framework.model.mdl.note.NotesLanguage.Bookmark;
import static cococare.framework.model.mdl.note.NotesLanguage.Note;
import static cococare.framework.model.mdl.note.NotesLanguage.Notes;
import static cococare.framework.model.mdl.note.NotesLanguage.Objective;
import static cococare.framework.model.mdl.note.NotesLanguage.Shortcut;
import static cococare.framework.model.mdl.note.NotesLanguage.Tracker;
import cococare.framework.zk.CFZkMain;
import cococare.framework.zk.CFZkMap;
import cococare.framework.zk.CFZkUae;
import cococare.framework.zk.controller.zul.note.ZulBookmarkListCtrl;
import cococare.framework.zk.controller.zul.note.ZulObjectiveListCtrl;
import cococare.framework.zk.controller.zul.note.ZulShortcutListCtrl;
import cococare.framework.zk.controller.zul.note.ZulTrackerListCtrl;
import cococare.framework.zk.controller.zul.util.ZulLoginCtrl;
import cococare.zk.CCMenubar;

public class NotesMain4Zk extends CFZkMain {
	@Override
	protected void _loadInternalSetting() {
		APPL_CODE = "cccr-nts";
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
		CFZkUae zkUae = new CFZkUae();
		zkUae.reg(turn(Note), turn(Bookmark), ZulBookmarkListCtrl.class);
		zkUae.reg(turn(Note), turn(Shortcut), ZulShortcutListCtrl.class);
		zkUae.reg(turn(Note), turn(Objective), ZulObjectiveListCtrl.class);
		zkUae.reg(turn(Note), turn(Tracker), ZulTrackerListCtrl.class);
		// zkUae.reg("Setup", "Javax.Comm Setup", PnlJavaxCommSetupCtrl.class);
		// zkUae.reg("Setup", "List Files Setup", PnlListFilesSetupCtrl.class);
		// zkUae.reg("Setup", "Language Setup", PnlLanguageSetupCtrl.class);
		return _initInitialDataUaeUtility(zkUae).compile();
	}

	@Override
	protected void _applyUserConfig() {
		CFZkUae zkUae = new CFZkUae();
		zkUae.initMenuBar(new CCMenubar(CFZkMap.getMenubarH()));
		zkUae.addMenuRoot(ZulLoginCtrl.class);
		zkUae.addMenuParent(turn(Notes), null, null);
		zkUae.addMenuChild(turn(Bookmark), null, ZulBookmarkListCtrl.class);
		zkUae.addMenuChild(turn(Shortcut), null, ZulShortcutListCtrl.class);
		zkUae.addMenuChild(turn(Objective), null, ZulObjectiveListCtrl.class);
		zkUae.addMenuChild(turn(Tracker), null, ZulTrackerListCtrl.class);
		// zkUae.addMenuParent("Setup", null, null);
		// zkUae.addMenuChild("Javax.Comm Setup", null,
		// PnlJavaxCommSetupCtrl.class);
		// zkUae.addMenuChild("List Files Setup", null,
		// PnlListFilesSetupCtrl.class);
		// zkUae.addMenuChild("Language Setup", null,
		// PnlLanguageSetupCtrl.class);
		zkUae.changeMenuSide();
		_applyUserConfigUaeUtility(zkUae).compileMenu();
	}

	@Override
	protected void _clearUserConfig() {
		CFZkUae zkUae = new CFZkUae();
		CFZkMap.getMenubarH().setVisible(true);
		zkUae.initMenuBar(new CCMenubar(CFZkMap.getMenubarH()));
		zkUae.compileMenu();
	}
}