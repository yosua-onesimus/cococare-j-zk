package model.dao.inv;

import model.mdl.inv.InventoryDao;
import model.obj.inv.InvEmployee;

public class InvEmployeeDao extends InventoryDao {

	@Override
	protected Class getEntity() {
		return InvEmployee.class;
	}
}