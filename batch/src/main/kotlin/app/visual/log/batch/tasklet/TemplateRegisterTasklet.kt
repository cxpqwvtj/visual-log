package app.visual.log.batch.tasklet

import app.visual.log.config.AppConfig
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

/**
 * マッピングテンプレート作成タスクレットクラスです。
 */
@Component
class TemplateRegisterTasklet(
        val appConfig: AppConfig
) : Tasklet {
    override fun execute(contribution: StepContribution?, chunkContext: ChunkContext?): RepeatStatus {
        return RepeatStatus.FINISHED
    }
}