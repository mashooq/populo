(ns populo.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [hiccup.page :refer [include-js include-css html5]]
            [populo.middleware :refer [wrap-middleware]]
            [populo.layout :refer [common]]
            [config.core :refer [env]]))

(defroutes routes
  (GET "/" [] (common nil "Main"))
  (GET "/users" [] (common nil "Users"))
  
  (resources "/")
  (not-found "Not Found"))

(def app (wrap-middleware #'routes))
