package cococare.framework.zk.controller.zul.note;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.framework.model.bo.note.NoteTrackerBo;
import cococare.framework.model.obj.note.NoteTracker;
import cococare.framework.zk.CFZkCtrl;
import static cococare.zk.CCZk.getTabpanel;
import static cococare.zk.CCZk.showPanel;
import java.util.ArrayList;
import java.util.List;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulTrackerCtrl extends CFZkCtrl {

    private NoteTrackerBo trackerBo;

    @Override
    protected Class _getEntity() {
        return NoteTracker.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.FORM_FUNCTION;
    }

    @Override
    protected void _initComponent() {
        super._initComponent();
        parameter.put("tracker", objEntity);
        parameter.put("tracker_newEntity", newEntity);
        if (newEntity) {
            parameter.put("bookmarks", new ArrayList());
            parameter.put("shortcuts", new ArrayList());
        }
        ZulBookmarkListCtrl bookmarkListCtrl = new ZulBookmarkListCtrl();
        bookmarkListCtrl.with(parameter).init();
        showPanel(getTabpanel(getContainer(), "pnlBookmark"), bookmarkListCtrl.getContainer());
        ZulShortcutListCtrl shortcutListCtrl = new ZulShortcutListCtrl();
        shortcutListCtrl.with(parameter).init();
        showPanel(getTabpanel(getContainer(), "pnlShortcut"), shortcutListCtrl.getContainer());
    }

    @Override
    protected boolean _doSaveEntity() {
        if (newEntity) {
            return trackerBo.saveOrUpdate((NoteTracker) objEntity, (List) parameter.get("bookmarks"), (List) parameter.get("shortcuts"));
        } else {
            return super._doSaveEntity();
        }
    }
}