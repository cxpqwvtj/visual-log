package app.visual.log.batch.tasklet

import app.visual.log.config.AppConfig
import app.visual.log.extentions.dateConvert
import com.fasterxml.jackson.databind.ObjectMapper
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.transport.client.PreBuiltTransportClient
import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component
import java.io.File
import java.net.InetAddress
import java.text.SimpleDateFormat

/**
 * Elasticsearchへログ登録するタスクレットクラスです。
 */
@Component
class LogPostTasklet(
        val appConfig: AppConfig
) : Tasklet {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val client = PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(InetSocketTransportAddress(InetAddress.getByName(appConfig.elasticsearch.transportHost), appConfig.elasticsearch.transportPort))
        val bulkRequest = client.prepareBulk()

        val lineCount = File(appConfig.batch.logFile).useLines { lineSequences: Sequence<String> -> lineSequences.count() }
        logger.debug("${lineCount}行のファイルです")

        File(appConfig.batch.logFile).forEachLine { line ->
            val map = mutableMapOf<String, Any>()
            line.split("\t").forEach { item ->
                val key = item.split(":")[0]
                val value = item.split(":").filterNot { key == it }.joinToString(":")
                if ((key == "runtime" || key == "apptime") && value == "-") {
                    // 無視
                } else if (key == "time") {
                    map.put(key, value.dateConvert())
                } else {
                    map.put(key, value)
                }
            }

            val indexName = "logstash-${SimpleDateFormat("yyyyMMdd").format(map.get("time"))}"
            val type = "log"
            bulkRequest.add(client.prepareIndex(indexName, type).setSource(ObjectMapper().writeValueAsBytes(map), XContentType.SMILE))

            if (bulkRequest.numberOfActions() >= 10000) {
                val response = bulkRequest.get()
                if (response.hasFailures()) {
                    throw Exception(response.buildFailureMessage())
                }
            }
        }

        return RepeatStatus.FINISHED
    }
}