package model.mdl.bljr;

import model.obj.bljr.Employee;
import cococare.database.CCHibernate;
import cococare.database.CCHibernateModule;

public class BljrModule extends CCHibernateModule {
	public static BljrModule INSTANCE = new BljrModule();

	@Override
	public void init(CCHibernate hibernate) {
		super.init(hibernate);
		hibernate.addAnnotatedClass(Employee.class);
	}
}