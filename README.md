# visual-log

## 実行に必要なソフトウェア

- Docker
- Java

## Elasticsearch - Kibana 起動

```
cd docker
docker-compose up -d
```

## ログ取り込み

- `batch/logs/access.log` にファイルを配置
- `./gradlew batch:bootRun` を実行

## nginxのログ形式

```
  log_format ltsv "time:$time_local"
                  "\thost:$remote_addr"
                  "\tforwardedfor:$http_x_forwarded_for"
                  "\tuser:$remote_user"
                  "\treq:$request"
                  "\tmethod:$request_method"
                  "\tscheme:$scheme"
                  "\turi:$request_uri"
                  "\tprotocol:$server_protocol"
                  "\tstatus:$status"
                  "\tsize:$body_bytes_sent"
                  "\treqsize:$request_length"
                  "\treferer:$http_referer"
                  "\tua:$http_user_agent"
                  "\tvhost:$host"
                  "\treqtime:$request_time"
                  "\tcache:$upstream_http_x_cache"
                  "\truntime:$upstream_http_x_runtime"
                  "\tapptime:$upstream_response_time";
```
