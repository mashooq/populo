(ns populo.security.token-store-test
  (:use midje.sweet populo.security.token-store)
  (:require [clj-time.core :as t]))

(fact "stored credentials are maintained along with the expiry time"
      (store "a-token" {:email "me@somewhere.com"} 3000)
      (fetch-creds "a-token") => {:email "me@somewhere.com" :expires-at (t/date-time 2015 07 01 00 50 00)}

      (against-background (t/now) => (t/date-time 2015 07 01 00 00 00)))

(fact "expired credentials are not returned"
      (store "another-token" {:email "me@somewhere.com"} 3000)
      (fetch-creds "another-token") => nil

      (against-background (t/now) =streams=> [(t/date-time 2015 07 01 00 00 00) (t/date-time 2015 07 01 00 52 00)]))

(fact "return nil if no token is found"
      (fetch-creds "yet-another-token") => nil)
