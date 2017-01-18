(ns populo.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [hiccup.page :refer [include-js include-css html5]]
            [populo.middleware :refer [wrap-middleware]]
            [populo.users :refer [users]]
            [populo.login :refer [login]]
            [config.core :refer [env]]))

(defroutes routes
  (GET "/users" [] (users))
  (GET "/login" [] (login))
  
  (resources "/")
  (not-found "Not Found"))

(def app (wrap-middleware #'routes))
