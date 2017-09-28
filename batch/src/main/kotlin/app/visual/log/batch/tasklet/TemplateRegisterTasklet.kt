package app.visual.log.batch.tasklet

import app.visual.log.batch.es.ElasticsearchClient
import app.visual.log.config.AppConfig
import org.elasticsearch.action.admin.indices.template.get.GetIndexTemplatesRequest
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.common.xcontent.XContentFactory
import org.elasticsearch.transport.client.PreBuiltTransportClient
import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component
import java.net.InetAddress

/**
 * マッピングテンプレート作成タスクレットクラスです。
 */
@Component
class TemplateRegisterTasklet(
        val appConfig: AppConfig,
        val client: ElasticsearchClient
) : Tasklet {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        logger.debug("${contribution.toString()} ${chunkContext.toString()}")

        val client = PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(InetSocketTransportAddress(InetAddress.getByName(appConfig.elasticsearch.transportHost), appConfig.elasticsearch.transportPort))

        // @formatter:off
        // インデックスとマッピング作成
        val response = client.admin().indices().getTemplates(GetIndexTemplatesRequest("logstash-*")).actionGet()
        if (response.indexTemplates.isEmpty()) {
            logger.debug("テンプレートがありません")
        }
        client.admin().indices().preparePutTemplate("logstash")
            .setTemplate(
                XContentFactory.jsonBuilder()
                    .startObject()
                        .field("template", "logstash-*")
                        .startObject("mappings")
                            .startObject("log")
                                .startObject("properties")
                                    .startObject("time")
                                        .field("type", "date")
                                    .endObject()
                                .endObject()
                            .endObject()
                        .endObject()
                    .endObject()
                .string()
            ).setSettings(Settings.builder()
                    .put("index.number_of_shards", 10)
                    .put("index.number_of_replicas", 0)
            ).get()
//            client.admin().indices().prepareCreate(appConfig.analyzedTokenIndexName)
//                    .addMapping(appConfig.analyzedTokenTypeName, XContentFactory.jsonBuilder()
//                        .startObject()
//                            .startObject("doc")
//                                .startObject("properties")
//                                    .startObject("token")
//                                        .field("type", "keyword")
//                                    .endObject()
//                                    .startObject("index")
//                                        .field("type", "keyword")
//                                    .endObject()
//                                    .startObject("type")
//                                        .field("type", "keyword")
//                                    .endObject()
//                                    .startObject("id")
//                                        .field("type", "keyword")
//                                    .endObject()
//                                    .startObject("filename")
//                                        .field("type", "keyword")
//                                    .endObject()
//                                    .startObject("categoryCode")
//                                        .field("type", "keyword")
//                                    .endObject()
//                                    .startObject("docId")
//                                        .field("type", "keyword")
//                                    .endObject()
//                                    .startObject("startOffset")
//                                        .field("type", "long")
//                                    .endObject()
//                                    .startObject("endOffset")
//                                        .field("type", "long")
//                                    .endObject()
//                                    .startObject("position")
//                                        .field("type", "long")
//                                    .endObject()
//                                .endObject()
//                            .endObject()
//                        .endObject())
//                    .setSettings(Settings.builder()
//                        .put("index.number_of_shards", 10)
//                        .put("index.number_of_replicas", 0)
//                        .put("index.refresh_interval", -1)
//                    ).get()
//            logger.info("インデックス${appConfig.analyzedTokenIndexName}を作成しました")
//            // @formatter:on
//        } finally {
//            client.close()
        return RepeatStatus.FINISHED
    }
}