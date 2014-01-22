package cococare.framework.zk.controller.zul.note;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.framework.model.obj.note.NoteBookmark;
import cococare.framework.zk.CFZkCtrl;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulBookmarkListCtrl extends CFZkCtrl {

    @Override
    protected Class _getEntity() {
        return NoteBookmark.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.LIST_FUNCTION;
    }
}