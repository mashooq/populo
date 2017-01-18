(ns populo.security.token-validator
  (:require [clojure.data.json :as json]
            [clj-http.client :as client]
            [populo.security.token-store :as store])
  (:import (clojure.lang ExceptionInfo)))

(defn- verify-issued-to [issued-to client-ids]
  (contains? client-ids issued-to))

(defn validate [token config]
  (try
    (if-let [value-in-cache (store/fetch-creds token)]
      (:email value-in-cache)
      (let [user-details
            (json/read-json
             (:body
              (client/get (str (:validation-server-url config) token))))]

        (if (verify-issued-to (:issued_to user-details) (:client-ids config))
          (do
            (store/store token {:email (:email user-details)} (:expires_in user-details))
            (:email user-details))
          false)))
    (catch ExceptionInfo _ false)))
