package masterplanb.masterplanbbe.user.repository

import org.jooq.generated.tables.pojos.User


interface UserRepositoryCustom {
    fun findByUserId(userId: String): User?
}