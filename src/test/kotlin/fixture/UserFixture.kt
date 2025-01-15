package fixture

import masterplanb.masterplanbbe.domain.Role.*
import masterplanb.masterplanbbe.domain.User
import java.util.*

class UserFixture {
    companion object {
        fun createUser(): User {
            return User(
                userId = UUID.randomUUID().toString(),
                email = "test@test.com",
                name = "test",
                nickname = "test",
                password = "test-password",
                role = USER
            )
        }
    }
}
