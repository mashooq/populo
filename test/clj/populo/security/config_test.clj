(ns populo.security.config-test
  (:use midje.sweet)
  (:require [populo.security.token-validator :as validator]
            [populo.security.config :as c]))

(def token "AUTH-TOKEN")
(def token-map {:access-token token})
(def user-email "fake-user@codurance.com")

(fact "retrieve user roles and user-id for the user email returned by the validator"
      (c/validate-credential token-map) => {:identity token-map :userid user-email :roles #{:admin}}

      (provided
        (validator/validate token c/validator-config) => user-email
        (c/get-user user-email) => {:roles #{:admin}}))

(fact "return false if validator returns false"
      (c/validate-credential token-map) => false

      (provided
        (validator/validate token c/validator-config) => false))

(fact "returns false if user is not known"
      (c/validate-credential token-map) => false

      (provided
        (validator/validate token c/validator-config) => user-email
        (c/get-user user-email) => nil))

(fact "create credentials from the user role"
      (c/validate-credential token-map) => {:identity token-map :userid user-email :roles #{:admin}}

      (provided
        (validator/validate token c/validator-config) => user-email
        (c/get-user user-email) => {:roles #{:admin}}))
