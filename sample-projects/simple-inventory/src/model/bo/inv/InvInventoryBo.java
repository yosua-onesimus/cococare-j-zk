package model.bo.inv;

//<editor-fold defaultstate="collapsed" desc=" import ">
import model.dao.inv.InvInventoryDao;
import model.obj.inv.InvInventory;
import cococare.database.CCHibernateBo;

public class InvInventoryBo extends CCHibernateBo {

	// Bo
	private InvRecalculationBo recalculationBo = new InvRecalculationBo();
	// Dao
	private InvInventoryDao inventoryDao;
	// Collection & Object
	private InvInventory inventory;

	public synchronized void load(InvInventory inventory) {
		//
		this.inventory = inventory;
	}

	public synchronized boolean save() {
		return inventoryDao.saveOrUpdate(recalculationBo.recalcInventory(inventory));
	}
}