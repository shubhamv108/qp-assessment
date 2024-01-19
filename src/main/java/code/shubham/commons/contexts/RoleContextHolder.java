package code.shubham.commons.contexts;

import code.shubham.core.iammodels.RoleName;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RoleContextHolder {

	private static final ThreadLocal<Set<String>> CONTEXT = new ThreadLocal<>();

	public static void set(final Set<String> roles) {
		CONTEXT.set(new HashSet<>(roles));
	}

	public static Set<String> get() {
		return CONTEXT.get();
	}

	public static void clear() {
		Optional.ofNullable(CONTEXT.get()).ifPresent(Set::clear);
		CONTEXT.remove();
	}

	public static boolean isAdmin() {
		return has(RoleName.ADMIN.name());
	}

	public static boolean isUser() {
		return has(RoleName.USER.name());
	}

	public static boolean has(final String role) {
		return Optional.ofNullable(CONTEXT.get()).map(roles -> roles.contains(role)).orElse(false);
	}

}
