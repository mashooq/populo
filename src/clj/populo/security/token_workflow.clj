(ns populo.security.token-workflow
  (:require
   [cemerick.friend :as friend]))

(defn- from-request [request]
  (when-let [headers (:headers request)]
    (headers "authorization")))

(defn- verify-token [session-token credential-fn]
  (if-let [credentials (credential-fn {:access-token session-token})]

    (vary-meta credentials merge
               {::friend/workflow          :oauth2
                ::friend/redirect-on-auth? false
                :type                      ::friend/auth})

    {:status 401 :headers {"Content-Type" "text/plain"}}))

(defn workflow [credential-fn]
  (fn [request]
    (if-let [session-token (from-request request)]
      (verify-token session-token credential-fn)
      false)))
