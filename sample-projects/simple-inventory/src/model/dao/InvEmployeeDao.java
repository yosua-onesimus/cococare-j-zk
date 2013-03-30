package model.dao;

import model.mdl.InventoryDao;
import model.obj.InvEmployee;

public class InvEmployeeDao extends InventoryDao {

	@Override
	protected Class getEntity() {
		return InvEmployee.class;
	}
}