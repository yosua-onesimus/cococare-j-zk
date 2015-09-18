package model.obj.lib;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cococare.common.CCFieldConfig;
import cococare.common.CCFieldConfig.Accessible;
import cococare.common.CCFieldConfig.Type;
import cococare.common.CCTypeConfig;
import cococare.database.CCEntity;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
@Entity
@Table(name = "lib_returning_items")
@CCTypeConfig(label = "Returning Item", uniqueKey = "borrowingItem.book.title")
public class LibReturningItem extends CCEntity {
	@ManyToOne
	@CCFieldConfig(componentId = "bndReturning", accessible = Accessible.MANDATORY, maxLength = 12, uniqueKey = "code")
	private LibReturning returning;
	@ManyToOne
	@CCFieldConfig(label = "Book", componentId = "bndBorrowingItem", accessible = Accessible.MANDATORY, maxLength = 12, uniqueKey = "book.title", requestFocus = true)
	private LibBorrowingItem borrowingItem;
	@CCFieldConfig(label = "Fine", tooltiptext = "Borrowing Fine", componentId = "txtBorrowingFine", accessible = Accessible.MANDATORY_READONLY, type = Type.NUMBER_FORMAT, maxLength = 24)
	private Double borrowingFine;

	public LibReturning getReturning() {
		return returning;
	}

	public void setReturning(LibReturning returning) {
		this.returning = returning;
	}

	public LibBorrowingItem getBorrowingItem() {
		return borrowingItem;
	}

	public void setBorrowingItem(LibBorrowingItem borrowingItem) {
		this.borrowingItem = borrowingItem;
	}

	public Double getBorrowingFine() {
		return borrowingFine;
	}

	public void setBorrowingFine(Double borrowingFine) {
		this.borrowingFine = borrowingFine;
	}
}