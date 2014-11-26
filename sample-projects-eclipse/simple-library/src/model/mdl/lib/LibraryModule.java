package model.mdl.lib;

import model.obj.lib.LibAuthor;
import model.obj.lib.LibBook;
import model.obj.lib.LibBookType;
import model.obj.lib.LibBorrowing;
import model.obj.lib.LibBorrowingItem;
import model.obj.lib.LibMember;
import model.obj.lib.LibPublisher;
import model.obj.lib.LibReturning;
import model.obj.lib.LibReturningItem;
import cococare.database.CCHibernate;
import cococare.database.CCHibernateModule;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class LibraryModule extends CCHibernateModule {
	public static LibraryModule INSTANCE = new LibraryModule();

	@Override
	public void init(CCHibernate hibernate) {
		super.init(hibernate);
		// parameter
		hibernate.addAnnotatedClass(LibBookType.class);
		hibernate.addAnnotatedClass(LibAuthor.class);
		hibernate.addAnnotatedClass(LibPublisher.class);
		// archive
		hibernate.addAnnotatedClass(LibBook.class);
		hibernate.addAnnotatedClass(LibMember.class);
		// transaction
		hibernate.addAnnotatedClass(LibBorrowing.class);
		hibernate.addAnnotatedClass(LibBorrowingItem.class);
		hibernate.addAnnotatedClass(LibReturning.class);
		hibernate.addAnnotatedClass(LibReturningItem.class);
	}
}