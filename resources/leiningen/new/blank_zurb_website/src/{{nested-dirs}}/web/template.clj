(ns {{namespace}}.web.template
  (:use [net.cgrand.enlive-html :only [defsnippet]])
  (:require
    [net.cgrand.enlive-html :as h]))

(defsnippet link-tmpl "../resources/public/templates/main.html"
  [:#headerMenu :ul :> h/first-child]
  [{text :text link :link}]
  [:a] (h/do->
        (h/content text)
        (h/set-attr :href link)))

(defn ^:template page-tmpl [data]
  (h/template "../resources/public/templates/main.html" []
   [:#headerMenu :ul]  (h/content
                        (map link-tmpl (:nav-links data)))

   [:#sidebarMenu :ul] (h/content
                        (map link-tmpl  (:nav-links data)))

   [:#headerEnd] (h/content (:header-end data))

   [:#contentMain] (h/content (:content data))))

(defn ^:snippet home-contents []
  (h/snippet "../resources/public/templates/_home.html" [:contents] []))

(defn ^:snippet about-contents []
  (h/snippet "../resources/public/templates/_about.html" [:contents] []))