package app.visual.log.batch

import app.visual.log.config.AppConfig
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
@EnableBatchProcessing
open class BatchMain(
        val appConfig: AppConfig,
        val jobBuilderFactory: JobBuilderFactory,
        val stepBuilderFactory: StepBuilderFactory
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Bean
    open fun job(): Job {
        if (appConfig.batch.execJob == "pushlog") {

        }
        return jobBuilderFactory
                .get("help")
                .incrementer(RunIdIncrementer())
                .flow(stepBuilderFactory.get("help").tasklet { _, _ ->
                    val resource = ClassPathResource("/version")
                    val contents = if (resource.exists()) {
                        resource.inputStream.bufferedReader().use { it.readLines() }.joinToString("<br />")
                    } else {
                        "version:undefined"
                    }
                    logger.info("version:$contents")
                    RepeatStatus.FINISHED
                }.build())
                .end()
                .build()

    }
}
