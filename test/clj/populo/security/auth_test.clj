(ns populo.security.auth-test
  (:use midje.sweet)
  (:require [populo.security.auth :as a]
            [environ.core :refer [env]]
            [cemerick.friend :as friend]))

(def user-email "fake-user@codurance.com")
(def token "AUTH-TOKEN")
(def token-map {:access-token token})

(fact "always performs the action if auth is not enabled"
      (a/auth nil (fn [] "action performed")) => "action performed"

      (against-background (env :enabled-auth) => "false"))

(fact "authorizes againt the admin role"
      (against-background (friend/authorized? #{:admin} anything) => true)

      (a/auth #{:admin} (fn [] "admin action performed")) => "admin action performed"

      (against-background (env :enabled-auth) => "true"))

(fact "authorizes against user role"
      (against-background (friend/authorized? #{:user} anything) => true)

      (a/auth #{:user} (fn [] "user action performed")) => "user action performed"

      (against-background (env :enabled-auth) => "true"))

(fact "gets user details from current authentication minus the auth token"
      (a/user-details) => {:userid user-email :roles #{:admin}}

      (provided
        (friend/current-authentication) => {:identity {:access-token token-map} :userid user-email :roles #{:admin}})

      (against-background (env :enabled-auth) => "true") )

(fact "gets fake user details if auth is not enabled"
      (a/user-details) => {:userid user-email :roles #{:admin}}

      (against-background (env :enabled-auth) => "false")
      )
