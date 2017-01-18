(ns populo.security.token-workflow-test
  (:use midje.sweet)
  (:require
    [populo.security.token-workflow :as token]
    [cemerick.friend :as friend]))

(def token "AUTH-TOKEN")
(def user-email "fake-user@codurance.com")
(def request-without-token {:headers {"some header" "value"}})
(def user-info {:userid user-email})
(def credential-fn)

(fact "return false if token is not provided"
      ((token/workflow nil) request-without-token) => false)

(fact "return 401 HTTP response if the credential-fn does not validate the token"
      ((token/workflow credential-fn) {:headers {"authorization" token}}) => {:status 401 :headers {"Content-Type" "text/plain"}}

      (provided (credential-fn {:access-token "AUTH-TOKEN"}) => false))

(fact "return credential function result with friend metadata"
      (let [workflow-output ((token/workflow credential-fn) {:headers {"authorization" token}})]
        {:object workflow-output :metadata (meta workflow-output)})
      => {:object user-info
          :metadata {::friend/workflow          :oauth2
                      ::friend/redirect-on-auth? false
                      :type                      ::friend/auth}}

      (provided (credential-fn {:access-token "AUTH-TOKEN"}) => user-info))
