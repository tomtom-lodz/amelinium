package amelinium1.grails

import org.apache.commons.lang.builder.HashCodeBuilder

class SecUserSecRole implements Serializable {

	private static final long serialVersionUID = 1

	SecUser secUser
	SecRole secRole

	boolean equals(other) {
		if (!(other instanceof SecUserSecRole)) {
			return false
		}

		other.secUser?.id == secUser?.id &&
			other.secRole?.id == secRole?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (secUser) builder.append(secUser.id)
		if (secRole) builder.append(secRole.id)
		builder.toHashCode()
	}

	static SecUserSecRole get(long secUserId, long secRoleId) {
		SecUserSecRole.where {
			secUser == SecUser.load(secUserId) &&
			secRole == SecRole.load(secRoleId)
		}.get()
	}

	static SecUserSecRole create(SecUser secUser, SecRole secRole, boolean flush = false) {
		new SecUserSecRole(secUser: secUser, secRole: secRole).save(flush: flush, insert: true)
	}

	static boolean remove(SecUser u, SecRole r, boolean flush = false) {

		int rowCount = SecUserSecRole.where {
			secUser == SecUser.load(u.id) &&
			secRole == SecRole.load(r.id)
		}.deleteAll()

		rowCount > 0
	}

	static void removeAll(SecUser u) {
		SecUserSecRole.where {
			secUser == SecUser.load(u.id)
		}.deleteAll()
	}

	static void removeAll(SecRole r) {
		SecUserSecRole.where {
			secRole == SecRole.load(r.id)
		}.deleteAll()
	}

	static mapping = {
		id composite: ['secRole', 'secUser']
		version false
	}
}
