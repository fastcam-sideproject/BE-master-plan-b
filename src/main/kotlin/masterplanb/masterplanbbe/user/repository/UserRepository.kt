package masterplanb.masterplanbbe.user.repository

import masterplanb.masterplanbbe.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long>, UserRepositoryCustom {
    fun existsByPhoneNumberIs(phoneNumber : String) : Boolean
}