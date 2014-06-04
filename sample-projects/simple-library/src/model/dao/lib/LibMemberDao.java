package model.dao.lib;

import model.mdl.lib.LibraryDao;
import model.obj.lib.LibMember;

public class LibMemberDao extends LibraryDao {
	@Override
	protected Class getEntity() {
		return LibMember.class;
	}
}