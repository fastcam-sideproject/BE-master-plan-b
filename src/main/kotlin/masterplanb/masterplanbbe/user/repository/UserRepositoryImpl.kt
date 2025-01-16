package masterplanb.masterplanbbe.user.repository

import org.jooq.DSLContext
import org.jooq.generated.tables.JUser.*
import org.jooq.generated.tables.pojos.User

class UserRepositoryImpl(
    private val dslContext: DSLContext
): UserRepositoryCustom {
    override fun findByUserId(userId: String): User? {
        val query = dslContext.select(*USER.fields())
            .from(USER)
            .where(USER.USER_ID.eq(userId))

        val result = query.fetchOneInto(User::class.java)
        println(result)
        return result
    }
}