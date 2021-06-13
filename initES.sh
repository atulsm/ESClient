curl -XPUT localhost:9200/logindata

curl -XPUT -H 'Content-Type: application/json' localhost:9200/logindata\_settings -d  '
{
    "index.refresh_interval": "60s",
    "index.number_of_replicas": "0"
}
'

curl -XPOST -H 'Content-Type: application/json' localhost:9200/logindata/_mapping/login -d  '
{
   "login":{
      "properties":{
         "country":{
            "type":"text",
            "fields":{
               "keyword":{
                  "type":"keyword",
                  "ignore_above":256
               }
            }
         },
         "ipaddress":{
            "type":"ip"
         },
         "location":{
            "type":"geo_point"
         },
         "login_date":{
            "type":"date"
         },
         "sellerid":{
            "type":"text",
            "fields":{
               "keyword":{
                  "type":"keyword",
                  "ignore_above":256
               }
            }
         },
         "userloginid":{
            "type":"text",
            "fields":{
               "keyword":{
                  "type":"keyword",
                  "ignore_above":256
               }
            }
         },
         "userlogintype":{
            "type":"text",
            "fields":{
               "keyword":{
                  "type":"keyword",
                  "ignore_above":256
               }
            }
         }
      }
   }
}'

