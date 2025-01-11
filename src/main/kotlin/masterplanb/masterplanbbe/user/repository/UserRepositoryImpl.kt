package masterplanb.masterplanbbe.user.repository

import jakarta.persistence.EntityManager
import masterplanb.masterplanbbe.domain.User

class UserRepositoryImpl(
    em: EntityManager
): UserRepositoryCustom {
    override fun findByUserId(userId: String): User? {
        TODO("Not yet implemented")
    }
}