package app.visual.log

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy
open class Application {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            System.exit(
                    SpringApplication.exit(
                            SpringApplication.run(Application::class.java, *args)
                    )
            )
        }
    }
}
