package model.mdl.inv;

//<editor-fold defaultstate="collapsed" desc=" import ">
import model.obj.inv.InvEmployee;
import model.obj.inv.InvInventory;
import model.obj.inv.InvOwnership;
import cococare.database.CCHibernate;
import cococare.database.CCHibernateModule;

public class InventoryModule extends CCHibernateModule {

	public static InventoryModule INSTANCE = new InventoryModule();

	// <editor-fold defaultstate="collapsed" desc=" public method ">
	@Override
	public void init(CCHibernate hibernate) {
		super.init(hibernate);
		//
		hibernate.addAnnotatedClass(InvEmployee.class);
		hibernate.addAnnotatedClass(InvInventory.class);
		hibernate.addAnnotatedClass(InvOwnership.class);
	}
	// </editor-fold>
}