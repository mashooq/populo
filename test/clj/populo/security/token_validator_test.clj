(ns populo.security.token-validator-test
  (:use midje.sweet)
  (:require [populo.security.token-validator :as validator]
            [clojure.data.json :as json]
            [clj-http.client :as client]
            [populo.security.token-store :as store]
            [clj-time.core :as time]
            [clj-time.coerce :as tc]))

(def token "AUTH-TOKEN")
(def user-email "fake-user@codurance.com")
(def validation-server-url "https://server/tokeninfo?access_token=")
(def token-url (str validation-server-url token))
(def expires-in-seconds 1000)
(def current-time-ms 1446740411000)
(def token-url-response (json/write-str {:email user-email :issued_to "clientid.apps.googleusercontent.com" :expires_in expires-in-seconds}))
(def token-url-response-not-issued-to-me (json/write-str {:email user-email :issued_to "some-other-client.apps.googleusercontent.com"}))
(def token-url-invalid-response (json/write-str {:error "invalid_token" :error_description "Invalid Value"}))
(def validation-config
  {:client-ids            #{"clientid.apps.googleusercontent.com"}
   :validation-server-url validation-server-url})

(fact "if the token is not in cache, return false if response from Google OAuth server is invalid"
      (validator/validate token validation-config) => false

      (provided
        (store/fetch-creds token) => false
        (client/get token-url) => {:body token-url-invalid-response}))

(fact "if the token is not in cache, return false if response from Google OAuth server is invalid"
      (validator/validate token validation-config) => false

      (provided
        (store/fetch-creds token) => false
        (client/get token-url) => {:body token-url-invalid-response}))

(fact "if the token is not in cache, return false if response from Google OAuth server is an error"
      (validator/validate token validation-config) => false

      (provided
        (store/fetch-creds token) => false
        (client/get token-url) =throws=> (ex-info "Something wrong happened" {})))

(fact "if the token is not in cache, return false if the specified token is not issued to me"
      (validator/validate token validation-config) => false

      (provided
        (store/fetch-creds token) => false
        (client/get token-url) => {:body token-url-response-not-issued-to-me}))

(fact "if the token is not in cache and is valid, return the user's email"
      (validator/validate token validation-config) => user-email

      (provided
        (store/fetch-creds token) => false
        (store/store token {:email user-email} expires-in-seconds) => anything
        (client/get token-url) => {:body token-url-response}))

(fact "if the token is in cache, do not contact the server and return data from cache"
      (validator/validate token validation-config) => user-email

      (provided
        (store/fetch-creds token) => {:email user-email}
        (client/get anything) => nil :times 0))

(fact "if the token is not in cache and is valid, save it in cache"
      (validator/validate token validation-config) => anything

      (provided
        (store/fetch-creds token) => false
        (store/store token {:email user-email} expires-in-seconds) => anything
        (client/get token-url) => {:body token-url-response}))
