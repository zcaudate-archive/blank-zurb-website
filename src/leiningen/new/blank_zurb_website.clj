(ns leiningen.new.blank-zurb-website
  (:use [leiningen.newnew.templates :only [project-name sanitize-ns year name-to-path]]))

(defn blank-zurb-website
  "A blank template for working with the zurb-foundation framework."
  [name]
  {:template true
   :data 
     {:raw-name name
      :name (project-name name)
      :namespace (sanitize-ns name)
      :nested-dirs (name-to-path name)
      :year (year)}
   :directives
     {:render-dirs [["" :except ["resources"]]]
      :copy-dirs ["resources"]}})