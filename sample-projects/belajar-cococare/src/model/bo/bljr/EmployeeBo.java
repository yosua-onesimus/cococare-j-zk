package model.bo.bljr;

import model.dao.bljr.EmployeeDao;
import model.obj.bljr.Employee;
import cococare.database.CCHibernateBo;

public class EmployeeBo extends CCHibernateBo {
	private EmployeeDao employeeDao;

	public synchronized boolean saveOrUpdate(Employee employee) {
		return employeeDao.saveOrUpdate(employee);
	}
}