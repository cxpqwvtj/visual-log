package app.visual.log.batch.es

import app.visual.log.config.AppConfig
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.transport.client.PreBuiltTransportClient

/**
 * Elasticsearch接続クライアントクラスです。
 */
class ElasticsearchClient(
        val appConfig: AppConfig
): PreBuiltTransportClient(settings:Settings) {
    
    init {
settings = Settings.builder().put("cluster.name", appConfig.elasticsearch.clusterName).build()
        super(settings)
    }
}