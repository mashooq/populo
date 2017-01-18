(ns populo.security.auth
  (:require
   [cemerick.friend :as friend]
   [environ.core :refer [env]]))

(defn- auth-enabled? [] (= (env :enabled-auth) "true"))

(defn auth [roles f] (if (auth-enabled?) (friend/authorize roles (f)) (f)))

(defn user-id [] (if (auth-enabled?) (:userid (friend/current-authentication)) "fake-user@codurance.com"))

(defn user-details []
  (if (auth-enabled?) (dissoc (friend/current-authentication) :identity)
      {:userid "fake-user@codurance.com" :roles #{:admin}}))
