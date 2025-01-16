package masterplanb.masterplanbbe.user.repository

import fixture.UserFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserRepositoryTest @Autowired constructor(
    private val userRepository: UserRepository
) {
    @Test
    fun `user can be found by userId`() {
        val user = userRepository.save(UserFixture.createUser())

        val foundUser = userRepository.findByUserId(user.userId)

        assertThat(foundUser!!.id).isNotNull
        assertThat(foundUser.userId).isEqualTo(user.userId)
        assertThat(foundUser.email).isEqualTo(user.email)
        assertThat(foundUser.name).isEqualTo(user.name)
        assertThat(foundUser.nickname).isEqualTo(user.nickname)
        assertThat(foundUser.password).isEqualTo(user.password)
    }
}