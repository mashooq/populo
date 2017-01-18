(ns populo.security.config
  (:require [environ.core :refer [env]]
            [friend-oauth2.util :refer [format-config-uri]]
            [populo.security.token-workflow :as token]
            [friend-oauth2.workflow :as oauth2]
            [populo.security.token-validator :as validator]))

(def validator-config
  {:client-ids #{"395088799531-mflprmkfk0sr23ap6frab7965ts3ill3.apps.googleusercontent.com"
                 "395088799531-9fn07r0q11i6d5bt43vtfo64fpg43r1i.apps.googleusercontent.com"
                 "395088799531-6gkbmk5sre3orfj1id40nul4jk4eedqo.apps.googleusercontent.com"}
   :validation-server-url "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token="})

(def client-config
  {:client-id     "395088799531-mflprmkfk0sr23ap6frab7965ts3ill3.apps.googleusercontent.com"
   :client-secret (env :auth-client-secret)
   :callback      {:domain (env :auth-callback-domain) :path "/oauth2callback"}})

(def uri-config
  {:authentication-uri {:url   "https://accounts.google.com/o/oauth2/auth"
                        :query {:client_id     (:client-id client-config)
                                :response_type "code"
                                :redirect_uri  (format-config-uri client-config)
                                :scope         "email"}}

   :access-token-uri   {:url   "https://accounts.google.com/o/oauth2/token"
                        :query {:client_id     (:client-id client-config)
                                :client_secret (:client-secret client-config)
                                :grant_type    "authorization_code"
                                :redirect_uri  (format-config-uri client-config)}}})

(defn get-user [id] {:id id :name "Fake User" :roles {:admin :user}})

(defn validate-credential [token]
  (if-let [user-id (validator/validate (:access-token token) validator-config)]
    (if-let [user (get-user user-id)]
      (assoc user :identity token :userid user-id)
      false)
    false))

(def auth-config {:allow-anon?          true
                  :unauthorized-handler (fn [request] {:status 403
                                                       :body   "Unauthorized"})
                  :workflows            [(token/workflow
                                          validate-credential)
                                         (oauth2/workflow
                                          {:client-config client-config
                                           :uri-config    uri-config
                                           :credential-fn validate-credential})]})
