(ns populo.users
  (:require [populo.layout :refer [common]]))

(defn users []
  (common nil "Users"
    [:div#app
        [:h3 "ClojureScript has not been compiled!"]
        [:p "please run "
        [:b "lein figwheel"]
        " in order to start the compiler"]] 

    [:script {:src "js/app.js"}]))
