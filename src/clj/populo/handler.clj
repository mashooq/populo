(ns populo.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [hiccup.page :refer [include-js include-css html5]]
            [populo.middleware :refer [wrap-middleware]]
            [populo.views.users :refer [users]]
            [config.core :refer [env]]))

(defroutes routes
  (GET "/" [] (users nil))
  (GET "/users" [] (users nil))
  
  (resources "/")
  (not-found "Not Found"))

(def app (wrap-middleware #'routes))
