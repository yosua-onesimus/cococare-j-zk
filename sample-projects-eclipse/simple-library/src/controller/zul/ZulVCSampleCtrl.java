package controller.zul;

import model.bo.lib.LibBookBo;
import model.obj.lib.LibBook;

import org.zkoss.zul.Cell;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Group;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listgroup;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import cococare.zk.CCEditor;
import cococare.zk.CCTable;
import cococare.zk.CCZk;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulVCSampleCtrl extends Window {
	private LibBookBo bookBo;
	private Grid grid1;
	private Listbox listbox1;
	private Tree tree1;
	private CCTable grid2;
	private CCTable listbox2;
	private CCTable tree2;
	private CCTable grid3;
	private CCTable listbox3;
	private CCTable tree3;
	private CCTable grid4;
	private CCTable listbox4;
	private CCTable tree4;
	private CCTable grid5;
	private CCTable listbox5;
	private CCTable tree5;
	private CCTable tblEntity;
	private CCEditor edtEntity;
	private CCEditor edtEntityAutoGen;

	public void onCreate() {
		CCZk.initComponent(this, this, null);
		_ListSample();
		_FormSample();
	}

	private void _ListSample() {
		_PureZKAtCtrl();
		_WithCCTable1();
		_WithCCTable2();
		_WithCCTable3();
		_WithCCTable4();
		_WithCCTable5();
	}

	private void _FormSample() {
		_WithCCEditor();
		_WithCCEditorAutoGen();
	}

	private void _PureZKAtCtrl() {
		new Columns().setParent(grid1);
		new Column("Column A", null, "100px").setParent(grid1.getColumns());
		new Column("Column B", null, "100px").setParent(grid1.getColumns());
		new Column("Column C", null, "100px").setParent(grid1.getColumns());
		new Column("Column D", null, null).setParent(grid1.getColumns());
		new Rows().setParent(grid1);
		new Row().setParent(grid1.getRows());
		new Label("Row 1 A").setParent(grid1.getRows().getLastChild());
		new Label("Row 1 B").setParent(grid1.getRows().getLastChild());
		new Label("Row 1 C").setParent(grid1.getRows().getLastChild());
		new Label("Row 1 D").setParent(grid1.getRows().getLastChild());
		new Row().setParent(grid1.getRows());
		new Label("Row 2 A").setParent(grid1.getRows().getLastChild());
		new Label("Row 2 B").setParent(grid1.getRows().getLastChild());
		new Label("Row 2 C").setParent(grid1.getRows().getLastChild());
		new Label("Row 2 D").setParent(grid1.getRows().getLastChild());
		new Group("Group Sample (Span)").setParent(grid1.getRows());
		new Row().setParent(grid1.getRows());
		Cell cell = new Cell();
		cell.setColspan(2);
		new Label("Row 3 A - Row 3 B").setParent(cell);
		cell.setParent(grid1.getRows().getLastChild());
		new Label("Row 3 C").setParent(grid1.getRows().getLastChild());
		cell = new Cell();
		cell.setRowspan(2);
		new Label("Row 3 D - Row 4 D").setParent(cell);
		cell.setParent(grid1.getRows().getLastChild());
		new Row().setParent(grid1.getRows());
		new Label("Row 4 A").setParent(grid1.getRows().getLastChild());
		new Label("Row 4 B").setParent(grid1.getRows().getLastChild());
		new Label("Row 4 C").setParent(grid1.getRows().getLastChild());
		new Listhead().setParent(listbox1);
		new Listheader("Column A", null, "100px").setParent(listbox1.getListhead());
		new Listheader("Column B", null, "100px").setParent(listbox1.getListhead());
		new Listheader("Column C", null, "100px").setParent(listbox1.getListhead());
		new Listheader("Column D", null, null).setParent(listbox1.getListhead());
		new Listitem().setParent(listbox1);
		new Listcell("Row 1 A").setParent(listbox1.getLastChild());
		new Listcell("Row 1 B").setParent(listbox1.getLastChild());
		new Listcell("Row 1 C").setParent(listbox1.getLastChild());
		new Listcell("Row 1 D").setParent(listbox1.getLastChild());
		new Listitem().setParent(listbox1);
		new Listcell("Row 2 A").setParent(listbox1.getLastChild());
		new Listcell("Row 2 B").setParent(listbox1.getLastChild());
		new Listcell("Row 2 C").setParent(listbox1.getLastChild());
		new Listcell("Row 2 D").setParent(listbox1.getLastChild());
		new Listgroup("Group Sample").setParent(listbox1);
		new Listitem().setParent(listbox1);
		new Listcell("Row 3 A").setParent(listbox1.getLastChild());
		new Listcell("Row 3 B").setParent(listbox1.getLastChild());
		new Listcell("Row 3 C").setParent(listbox1.getLastChild());
		new Listcell("Row 3 D").setParent(listbox1.getLastChild());
		new Listitem().setParent(listbox1);
		new Listcell("Row 4 A").setParent(listbox1.getLastChild());
		new Listcell("Row 4 B").setParent(listbox1.getLastChild());
		new Listcell("Row 4 C").setParent(listbox1.getLastChild());
		new Listcell("Row 4 D").setParent(listbox1.getLastChild());
		new Treecols().setParent(tree1);
		new Treecol("Column A", null, "100px").setParent(tree1.getTreecols());
		new Treecol("Column B", null, "100px").setParent(tree1.getTreecols());
		new Treecol("Column C", null, "100px").setParent(tree1.getTreecols());
		new Treecol("Column D", null, null).setParent(tree1.getTreecols());
		new Treechildren().setParent(tree1);
		new Treeitem().setParent(tree1.getTreechildren());
		new Treerow().setParent(tree1.getTreechildren().getLastChild());
		new Treecell("Row 1 A").setParent(tree1.getTreechildren().getLastChild().getLastChild());
		new Treecell("Row 1 B").setParent(tree1.getTreechildren().getLastChild().getLastChild());
		new Treecell("Row 1 C").setParent(tree1.getTreechildren().getLastChild().getLastChild());
		new Treecell("Row 1 D").setParent(tree1.getTreechildren().getLastChild().getLastChild());
		new Treeitem().setParent(tree1.getTreechildren());
		new Treerow().setParent(tree1.getTreechildren().getLastChild());
		new Treecell("Row 2 A").setParent(tree1.getTreechildren().getLastChild().getLastChild());
		new Treecell("Row 2 B").setParent(tree1.getTreechildren().getLastChild().getLastChild());
		new Treecell("Row 2 C").setParent(tree1.getTreechildren().getLastChild().getLastChild());
		new Treecell("Row 2 D").setParent(tree1.getTreechildren().getLastChild().getLastChild());
		new Treechildren().setParent(tree1.getTreechildren().getLastChild());
		new Treeitem().setParent(tree1.getTreechildren().getLastChild().getLastChild());
		new Treerow().setParent(tree1.getTreechildren().getLastChild().getLastChild().getLastChild());
		new Treecell("3 A").setParent(tree1.getTreechildren().getLastChild().getLastChild().getLastChild().getLastChild());
		new Treecell("3 B").setParent(tree1.getTreechildren().getLastChild().getLastChild().getLastChild().getLastChild());
		new Treecell("3 C").setParent(tree1.getTreechildren().getLastChild().getLastChild().getLastChild().getLastChild());
		new Treecell("3 D").setParent(tree1.getTreechildren().getLastChild().getLastChild().getLastChild().getLastChild());
		new Treechildren().setParent(tree1.getTreechildren().getLastChild().getLastChild().getLastChild());
		new Treeitem().setParent(tree1.getTreechildren().getLastChild().getLastChild().getLastChild().getLastChild());
		new Treerow().setParent(tree1.getTreechildren().getLastChild().getLastChild().getLastChild().getLastChild().getLastChild());
		new Treecell("4 A").setParent(tree1.getTreechildren().getLastChild().getLastChild().getLastChild().getLastChild().getLastChild().getLastChild());
		new Treecell("4 B").setParent(tree1.getTreechildren().getLastChild().getLastChild().getLastChild().getLastChild().getLastChild().getLastChild());
		new Treecell("4 C").setParent(tree1.getTreechildren().getLastChild().getLastChild().getLastChild().getLastChild().getLastChild().getLastChild());
		new Treecell("4 D").setParent(tree1.getTreechildren().getLastChild().getLastChild().getLastChild().getLastChild().getLastChild().getLastChild());
	}

	private void _WithCCTable1() {
		grid2 = new CCTable(CCZk.getMeshElement(this, "grid2"), "Column A", "Column B", "Column C", "Column D");
		grid2.setColumnWidth(100, 100, 100, null);
		grid2.addRow("Row 1 A", "Row 1 B", "Row 1 C", "Row 1 D");
		grid2.addRow("Row 2 A", "Row 2 B", "Row 2 C", "Row 2 D");
		grid2.addGroup("Group Sample (Span)");
		grid2.setNextColspans(2, 1, 1);
		grid2.setNextRowspans(1, 1, 2);
		grid2.addRow("Row 3 A - Row 3 B", "Row 3 C", "Row 3 D - Row 4 D");
		grid2.addRow("Row 4 A", "Row 4 B", "Row 4 C");
		listbox2 = new CCTable(CCZk.getMeshElement(this, "listbox2"), "Column A", "Column B", "Column C", "Column D");
		listbox2.setColumnWidth(100, 100, 100, null);
		listbox2.addRow("Row 1 A", "Row 1 B", "Row 1 C", "Row 1 D");
		listbox2.addRow("Row 2 A", "Row 2 B", "Row 2 C", "Row 2 D");
		listbox2.addGroup("Group Sample");
		listbox2.addRow("Row 3 A", "Row 3 B", "Row 3 C", "Row 3 D");
		listbox2.addRow("Row 4 A", "Row 4 B", "Row 4 C", "Row 4 D");
		tree2 = new CCTable(CCZk.getMeshElement(this, "tree2"), "Column A", "Column B", "Column C", "Column D");
		tree2.setColumnWidth(100, 100, 100, null);
		tree2.addRow("Row 1 A", "Row 1 B", "Row 1 C", "Row 1 D");
		tree2.addRow("Row 2 A", "Row 2 B", "Row 2 C", "Row 2 D");
		tree2.addChildRow(1, "3 A", "3 B", "3 C", "3 D");
		tree2.addChildRow(2, "4 A", "4 B", "4 C", "4 D");
	}

	private void _WithCCTable2() {
		bookBo = new LibBookBo();
		grid3 = new CCTable(CCZk.getMeshElement(this, "grid3"), "Code", "Title", "Book Type", "Author", "Publisher");
		grid3.setColumnWidth(100, 100, 100, 100, null);
		for (LibBook book : bookBo.getList()) {
			grid3.addRow(book.getCode(), book.getTitle(), book.getBookType().getName(), book.getAuthor().getName(), book.getPublisher().getName());
		}
		listbox3 = new CCTable(CCZk.getMeshElement(this, "listbox3"), "Code", "Title", "Book Type", "Author", "Publisher");
		listbox3.setColumnWidth(100, 100, 100, 100, null);
		for (LibBook book : bookBo.getList()) {
			listbox3.addRow(book.getCode(), book.getTitle(), book.getBookType().getName(), book.getAuthor().getName(), book.getPublisher().getName());
		}
		tree3 = new CCTable(CCZk.getMeshElement(this, "tree3"), "Code", "Title", "Book Type", "Author", "Publisher");
		tree3.setColumnWidth(100, 100, 100, 100, null);
		for (LibBook book : bookBo.getList()) {
			tree3.addRow(book.getCode(), book.getTitle(), book.getBookType().getName(), book.getAuthor().getName(), book.getPublisher().getName());
		}
	}

	private void _WithCCTable3() {
		bookBo = new LibBookBo();
		grid4 = new CCTable(CCZk.getMeshElement(this, "grid4"), LibBook.class);
		grid4.setGroupBy("bookType");
		for (LibBook book : bookBo.getList()) {
			grid4.addItem(book);
		}
		listbox4 = new CCTable(CCZk.getMeshElement(this, "listbox4"), LibBook.class);
		listbox4.setGroupBy("bookType");
		for (LibBook book : bookBo.getList()) {
			listbox4.addItem(book);
		}
		tree4 = new CCTable(CCZk.getMeshElement(this, "tree4"), LibBook.class);
		for (LibBook book : bookBo.getList()) {
			tree4.addItem(book);
		}
	}

	private void _WithCCTable4() {
		grid5 = new CCTable(CCZk.getMeshElement(this, "grid5"), LibBook.class);
		grid5.setGroupBy("bookType");
		grid5.search();
		listbox5 = new CCTable(CCZk.getMeshElement(this, "listbox5"), LibBook.class);
		listbox5.setGroupBy("bookType");
		listbox5.search();
		tree5 = new CCTable(CCZk.getMeshElement(this, "tree5"), LibBook.class);
		tree5.search();
	}

	private void _WithCCTable5() {
		tblEntity = new CCTable(CCZk.getMeshElement(this, "tblEntity"), LibBook.class);
		tblEntity.setNaviElements("pgnEntity", "txtKeyword", "btnEdit", "btnDelete");
		tblEntity.search();
	}

	private void _WithCCEditor() {
		edtEntity = new CCEditor(CCZk.getWindow(this, "winEditor"), LibBook.class);
	}

	private void _WithCCEditorAutoGen() {
		edtEntityAutoGen = new CCEditor(CCZk.getWindow(this, "winEditor2"), LibBook.class);
		edtEntityAutoGen.generateDefaultEditor(CCZk.getGrid(CCZk.getWindow(this, "winEditor2"), "pnlGenerator"));
	}
}