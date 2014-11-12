package cococare.framework.zk.controller.zul.note;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.common.CCCustomField;
import static cococare.database.CCHibernate.readTransientByteAValue;
import static cococare.datafile.CCFile.getFileUserTempFile;
import static cococare.datafile.CCFile.writeByteA;
import cococare.framework.model.obj.note.NoteEnum.ReferenceType;
import cococare.framework.model.obj.note.NoteNoteReference;
import cococare.framework.model.obj.note.NoteReference;
import cococare.framework.zk.CFZkCtrl;
import static cococare.zk.CCZk.addListener;
import static cococare.zk.datafile.CCFile.download;
import java.io.File;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.A;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulNoteReferenceListCtrl extends CFZkCtrl {

    @Override
    protected Class _getEntity() {
        return NoteNoteReference.class;
    }

    @Override
    protected BaseFunction _getBaseFunction() {
        return BaseFunction.LIST_FUNCTION;
    }

    @Override
    protected void _initTable() {
        super._initTable();
        tblEntity.initFields("reference.code", "reference.name");
        int column = tblEntity.getColumnCount();
        tblEntity.addField(column, new CCCustomField() {
            @Override
            public String getLabel() {
                return "Value";
            }

            @Override
            public Object getCustomView(Object object) {
                final NoteReference reference = ((NoteNoteReference) object).getReference();
                A a = new A(reference.getValue());
                if (ReferenceType.ATTACHMENT.equals(reference.getReferenceType())) {
                    addListener(a, new EventListener() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            readTransientByteAValue(reference);
                            File file = getFileUserTempFile(reference.getValue());
                            if (writeByteA(reference.getByteA(), file)) {
                                download(file);
                            }
                        }
                    });
                } else if (ReferenceType.LINK.equals(reference.getReferenceType())) {
                    a.setHref(reference.getValue());
                    a.setTarget("_blank");
                } else if (ReferenceType.SHORTCUT.equals(reference.getReferenceType())) {
                }
                return a;
            }
        });
        tblEntity.setColumnLabel("Code", "Name");
    }
}