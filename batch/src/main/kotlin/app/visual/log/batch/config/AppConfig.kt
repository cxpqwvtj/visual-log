package app.visual.log.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * アプリケーション設定管理クラスです。
 */
@Component
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = true)
data class AppConfig(
        var batch: Batch = Batch(),
        var elasticsearch: Elasticsearch = Elasticsearch()
) {
    data class Batch(
            var execJob: String = ""
    )

    data class Elasticsearch(
            var url: String = "http://localhost:9200",
            var transportHost: String = "localhost",
            var transportPort: Int = 9300,
            var defaultSearchSize: Int = 10000,
            var keepAliveTime: Long = 30000L
    )
}
