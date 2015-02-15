package model.mdl.lib;

import static cococare.common.CCLanguage.Archive;
import static cococare.common.CCLanguage.Report;
import static cococare.common.CCLanguage.Transaction;
import static cococare.common.CCLogic.isNotNull;
import static cococare.database.CCLoginInfo.INSTANCE_getUserLogin;
import static model.mdl.lib.LibraryLanguage.Book;
import static model.mdl.lib.LibraryLanguage.Borrowing;
import static model.mdl.lib.LibraryLanguage.Lib;
import static model.mdl.lib.LibraryLanguage.Member;
import static model.mdl.lib.LibraryLanguage.Returning;

import java.util.Arrays;

import model.obj.lib.LibConfig;
import model.obj.lib.LibReport;
import cococare.common.CCLanguage;
import cococare.framework.common.CFApplUae;
import cococare.framework.model.bo.util.UtilConfigBo;
import cococare.framework.model.obj.util.UtilUser;
import cococare.framework.zk.CFZkMain;
import controller.zul.lib.ZulBookListCtrl;
import controller.zul.lib.ZulBorrowingListCtrl;
import controller.zul.lib.ZulMemberListCtrl;
import controller.zul.lib.ZulReportListCtrl;
import controller.zul.lib.ZulReturningListCtrl;
import controller.zul.sample.ZulBook2ListCtrl;
import controller.zul.sample.ZulBook3ListCtrl;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class LibraryMain extends CFZkMain {
	@Override
	protected void _loadInternalSetting() {
		APPL_LOGO = "/cococare/resource/icon-cococare.jpg";
		APPL_VER = "1.0.130317";
		APPL_CODE = "simple-library";
		APPL_NAME = "Simple Library";
		super._loadInternalSetting();
		LibReport.setupReportFile();
	}

	@Override
	protected void _loadExternalSetting() {
		CCLanguage.init(false, LibraryLanguage.class);
		super._loadExternalSetting();
	}

	@Override
	protected void _initDatabaseEntity() {
		super._initDatabaseEntity();
		LibraryModule.INSTANCE.init(HIBERNATE);
	}

	@Override
	protected boolean _initInitialData() {
		UtilConfigBo configBo = new UtilConfigBo();
		confAppl = configBo.loadConfAppl();
		confAppl.setUtilAdditionalSettingClass(Arrays.asList(//
				LibConfig.class.getName()));
		return super._initInitialData()//
				&& configBo.saveConf(confAppl);
	}

	@Override
	protected void _initInitialUaeBody(CFApplUae uae) {
		uae.reg(Lib, Book, ZulBookListCtrl.class);
		uae.reg(Lib, Member, ZulMemberListCtrl.class);
		uae.reg(Lib, Borrowing, ZulBorrowingListCtrl.class);
		uae.reg(Lib, Returning, ZulReturningListCtrl.class);
		uae.reg(Lib, Report, ZulReportListCtrl.class);
	}

	@Override
	protected void _applyUserConfigUaeBody(CFApplUae uae) {
		if (isNotNull(INSTANCE_getUserLogin()) && ((UtilUser) INSTANCE_getUserLogin()).getUserGroup().isRoot()) {
			// login with root-root to access the following two examples
			uae.addMenuRoot(ZulBook2ListCtrl.class, ZulBook3ListCtrl.class);
		}
		uae.addMenuParent(Archive, "/img/Archive.png", null);
		uae.addMenuChild(Book, "/img/Book.png", ZulBookListCtrl.class);
		uae.addMenuChild(Member, "/img/Member.png", ZulMemberListCtrl.class);
		uae.addMenuParent(Transaction, "/img/Transaction.png", null);
		uae.addMenuChild(Borrowing, "/img/Borrowing.png", ZulBorrowingListCtrl.class);
		uae.addMenuChild(Returning, "/img/Returning.png", ZulReturningListCtrl.class);
		uae.addMenuParent(Report, "/img/Report.png", ZulReportListCtrl.class);
		uae.addMenuParent("Other Flow Sample", "/img/Sample.png", null);
		uae.addMenuChild("Dialog Flow Sample", "/img/Sample.png", ZulBook2ListCtrl.class);
		uae.addMenuChild("Panel Flow Sample", "/img/Sample.png", ZulBook3ListCtrl.class);
	}
}