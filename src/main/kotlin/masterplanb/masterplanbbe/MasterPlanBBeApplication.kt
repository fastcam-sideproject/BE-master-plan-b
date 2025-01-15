package masterplanb.masterplanbbe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class MasterPlanBBeApplication

fun main(args: Array<String>) {
    runApplication<MasterPlanBBeApplication>(*args)
}
