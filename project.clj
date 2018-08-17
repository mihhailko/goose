(defproject goose "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.6.0"]
                 [ring/ring-defaults "0.3.1"]
                 [ring-cors "0.1.11"]
                 [korma "0.4.3"]
                 [cheshire "5.8.0"]
                 [ring "1.6.3"]
                 [http-kit "2.2.0"]
                 [org.xerial/sqlite-jdbc "3.21.0.1"]]
  :plugins [[lein-ring "0.12.2"]]
  :ring {:handler goose.core/app}
  :main goose.core
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]
                        [clj-gatling "0.11.0"]]}})


