(ns {{namespace}}.web.server
  (:use [ring.middleware.file :only [wrap-file]]
        [ring.middleware.file-info :only [wrap-file-info]]
        [ring.middleware.params :only [wrap-params]]
        [ring.middleware.nested-params :only [wrap-nested-params]]
        [ring.middleware.keyword-params :only [wrap-keyword-params]]
        [compojure.core :only [defroutes routes GET POST ANY]])
  (:require
    [{{name}}.web.template :as tmpl]
    [ring.adapter.jetty :as jetty] :reload))

(defn ^:template site-page [body-contents & body-args]
  (tmpl/page-tmpl
   {:nav-links [{:text "home"   :link "/"}
                {:text "about"  :link "/about"}
                {:text "link1"  :link "#"}
                {:text "link2"  :link "#"}
                {:text "link3"  :link "#"}]
    :header-end ""
    :content     (apply body-contents body-args)}))

(defroutes base-handler
  (GET "/"      request ((site-page (tmpl/home-contents))))
  (GET "/about" request ((site-page (tmpl/about-contents))))
  (GET "/test"  request (str request))
  (ANY "*" a "page not found"))


;; Server controls

(defn start [& [port]]
  (jetty/run-jetty
   (-> #'base-handler
       (wrap-file "resources/public")
       wrap-file-info
       wrap-keyword-params
       wrap-nested-params
       wrap-params) {:port (or port 8421) :join? false}))

(defn stop [serv]
  (.stop serv))

(defn restart [serv]
  (stop serv)
  (.start serv))

(defn started? [serv]
  (or (.isRunning serv) (.isStarting serv) (.isStarted serv)))

(defn stopped? [serv]
  (or (.isStopped serv) (.isStopping serv) (.isFailed serv)))


(comment

  (def a (start))
  (stop a)

  )