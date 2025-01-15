package masterplanb.masterplanbbe.user.repository

import masterplanb.masterplanbbe.domain.User
import org.jooq.DSLContext
import org.jooq.generated.tables.JUser.*

class UserRepositoryImpl(
    private val dslContext: DSLContext
): UserRepositoryCustom {
    override fun findByUserId(userId: String): User? {
        val query = dslContext.selectFrom(USER)
            .where(USER.USER_ID.eq(userId))

        return query.fetchOneInto(User::class.java)
    }
}