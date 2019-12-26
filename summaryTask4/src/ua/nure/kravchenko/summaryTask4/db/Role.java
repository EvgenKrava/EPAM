package ua.nure.kravchenko.summaryTask4.db;

import ua.nure.kravchenko.summaryTask4.db.entity.User;

/**
 * Role entity.
 * 
 * @author E. Kravchenko
 * 
 */

public enum Role {
	CLIENT, ADMIN;
	
	public static Role getRole(User user) {
		int roleId = user.getRoleId();
		return Role.values()[roleId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
}
