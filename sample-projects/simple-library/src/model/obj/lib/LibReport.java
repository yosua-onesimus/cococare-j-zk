package model.obj.lib;

import static cococare.common.CCLogic.isNull;
import cococare.common.CCFieldConfig;
import cococare.common.CCFieldConfig.Accessible;
import cococare.common.jasperreports.CCReport;
import cococare.common.jasperreports.CCReportEnumInterface;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class LibReport extends CCReport {
	public enum Report implements CCReportEnumInterface {

		BOOK_LIST("Book List", "LibBook.jasper", "RptHeader.jasper"), MEMBER_LIST("Member List", "LibMember.jasper"), BOOK_HISTORY("Book History", "LibBookHistory.jasper"), MEMBER_HISTORY("Member History", "LibMemberHistory.jasper");
		private String string;
		private String jasperFile;
		private String[] reqJasperFiles;

		private Report(String string, String jasperFile, String... reqJasperFiles) {
			this.string = string;
			this.jasperFile = jasperFile;
			this.reqJasperFiles = reqJasperFiles;
		}

		@Override
		public String toString() {
			return string;
		}

		@Override
		public String getJasperFile() {
			return jasperFile;
		}

		@Override
		public String[] getReqJasperFiles() {
			return reqJasperFiles;
		}
	}

	@CCFieldConfig(componentId = "bndBook", accessible = Accessible.MANDATORY, maxLength = 32, uniqueKey = "title")
	private LibBook book;
	@CCFieldConfig(componentId = "bndMember", accessible = Accessible.MANDATORY, maxLength = 32, uniqueKey = "fullName")
	private LibMember member;

	public LibBook getBook() {
		return book;
	}

	public void setBook(LibBook book) {
		this.book = book;
	}

	public LibMember getMember() {
		return member;
	}

	public void setMember(LibMember member) {
		this.member = member;
	}

	@Override
	public Class<? extends CCReportEnumInterface> getReportEnum() {
		return Report.class;
	}

	@Override
	protected void _initDefaultMap() {
		super._initDefaultMap();
		map.put("bookCode", isNull(book) ? "" : book.getCode());
		map.put("bookTitle", isNull(book) ? "" : book.getTitle());
		map.put("bookTypeName", isNull(book) ? "" : book.getBookType().getName());
		map.put("bookAuthorName", isNull(book) ? "" : book.getAuthor().getName());
		map.put("bookPublisherName", isNull(book) ? "" : book.getPublisher().getName());
		map.put("book", isNull(book) ? null : book.getId());
		map.put("memberCode", isNull(member) ? "" : member.getCode());
		map.put("memberFullName", isNull(member) ? "" : member.getFullName());
		map.put("memberPhone", isNull(member) ? "" : member.getPhone());
		map.put("memberKtp", isNull(member) ? "" : member.getKtp());
		map.put("member", isNull(member) ? null : member.getId());
	}
}