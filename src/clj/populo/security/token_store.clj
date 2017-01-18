(ns populo.security.token-store
  (:require [clj-time.core :as t]))

(def token-store (atom {}))

(defn store [token, creds, expires-in]
  (swap! token-store
         assoc token (assoc creds :expires-at (t/plus (t/now) (t/seconds expires-in)))))

(defn fetch-creds [token]
  (when-let [creds (@token-store token)]
    (when (t/before? (t/now) (:expires-at creds)) creds)))
