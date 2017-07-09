# Elasticsearch

## template

Kibana Dev Tools にて以下を実行

```
PUT /_template/logstash 
{
  "template": "logstash-*",
  "mappings": {
    "log": {
      "properties": {
        "time": {
          "type": "date"
        },
        "host": {
          "type": "keyword"
        },
        "forwardedfor": {
          "type": "keyword"
        },
        "user": {
          "type": "keyword"
        },
        "req": {
          "type": "keyword"
        },
        "method": {
          "type": "keyword"
        },
        "scheme": {
          "type": "keyword"
        },
        "uri": {
          "type": "keyword"
        },
        "protocol": {
          "type": "keyword"
        },
        "status": {
          "type": "long"
        },
        "size": {
          "type": "long"
        },
        "reqsize": {
          "type": "long"
        },
        "referer": {
          "type": "keyword"
        },
        "ua": {
          "type": "keyword"
        },
        "vhost": {
          "type": "keyword"
        },
        "reqtime": {
          "type": "double"
        },
        "cache": {
          "type": "keyword"
        },
        "runtime": {
          "type": "double"
        },
        "apptime": {
          "type": "double"
        }
      }
    }
  }
}
```
