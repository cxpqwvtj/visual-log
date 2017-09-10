package app.visual.log.batch.es

import app.visual.log.config.AppConfig
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.transport.client.PreBuiltTransportClient
import org.springframework.stereotype.Component
import java.net.InetAddress

/**
 * Elasticsearch接続クライアントクラスです。
 */
@Component
class ElasticsearchClient(
        val appConfig: AppConfig
) : PreBuiltTransportClient(Settings.EMPTY) {

    init {
        addTransportAddress(InetSocketTransportAddress(InetAddress.getByName(appConfig.elasticsearch.transportHost), appConfig.elasticsearch.transportPort))
    }
}