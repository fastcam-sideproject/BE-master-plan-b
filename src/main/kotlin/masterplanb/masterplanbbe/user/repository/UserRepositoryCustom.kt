package masterplanb.masterplanbbe.user.repository

import masterplanb.masterplanbbe.domain.User

interface UserRepositoryCustom {
    fun findByUserId(userId: String): User?
}