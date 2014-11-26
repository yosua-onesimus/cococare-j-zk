package model.obj.lib;

import cococare.database.CCHibernateFilter;

public class LibFilter {
	public static CCHibernateFilter isSuspendFalse = new CCHibernateFilter() {
		@Override
		public String getFieldName() {
			return "suspend";
		}

		@Override
		public Object getFieldValue() {
			return false;
		}
	};
	public static CCHibernateFilter isReturnedFalse = new CCHibernateFilter() {
		@Override
		public String getFieldName() {
			return "returned";
		}

		@Override
		public Object getFieldValue() {
			return false;
		}
	};
}