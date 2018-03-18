(ns goose.core
  (:require [compojure.core :refer [GET POST PUT DELETE routes defroutes context]]
            [compojure.route :refer [resources not-found]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.reload :refer [wrap-reload]]
            [cheshire.core :refer [generate-string parse-string]]
            [clojure.java.io :as io]
            [org.httpkit.server :as http]
            [korma.core :refer [defentity select insert values where]]
            [korma.db :refer [sqlite3 defdb transaction]])
  (:gen-class))

(def db (sqlite3 {:db "db/db"}))

(defdb sqlite-db db)

(defentity flying_ability)

(defn store-ability [map]
  (transaction (insert flying_ability (values map))))

(defentity geese)

(defn store-record [map]
  (transaction (insert geese (values map))))

(defn generate-html-response [data]
  {:status 200
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body data})

(defn generate-json-response [data]
  {:status 200
   :headers {"Access-Control-Allow-Origin" "*" "Content-Type" "application/json"}
   :body data})

(defn update-record [bird body]
  body)

(defn delete-record [bird]
  bird)

(defroutes app-routes
  (GET "/" [] (generate-html-response "Hello html"))
  (context "/:ability" [ability]
           (defroutes ability-routes
             (GET "/" [] (generate-json-response
                          (generate-string (select geese (where {:ability_id ability})))))
             (POST "/" {body :body} (store-record body)))
             (context "/:bird" [bird ability]
                      (defroutes bird-routes
                        (GET "/" [ability] (generate-json-response
                                     (generate-string (select geese (where (and
                                                                         (= :bird_id bird)
                                                                         (= :ability_id ability)))))))
                        (PUT "/" {body :body} (update-record bird body))
                        (DELETE "/" [] (delete-record bird)))))
  (not-found "Not Found")
  (resources "/"))

(def reloadable-app
  (wrap-reload #'app-routes))

(def app
  (wrap-defaults app-routes site-defaults))
   
(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn -main [& args]
  (reset! server (http/run-server
                  ;;reloadable-app
                  app
                  {:port 8080})))

;;(-main)

;;(stop-server)



