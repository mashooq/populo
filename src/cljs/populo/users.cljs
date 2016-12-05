(ns populo.users
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]))

(defonce users (atom  (sorted-map "1" {:id "1" :name "Mashooq Badar" :position "Software Craftsman"}
                                   "2" {:id "2" :name "Sandro Mancuso" :position "Software Craftsman"}
                                   "3" {:id "3" :name "Pedro Santos" :position "Software Craftsman"}
                                   
                                   )))

(defn add-user [user]
  (swap! users assoc (:id user) user))

(defn users-page []
  [:div 
   (for [user-row (partition-all 2 (vals @users))]
      [:div {:class "row margin-bottom-20"}
      (for [user user-row]
        ^{:key (:id user)}
          [:div {:class "col-sm-6 sm-margin-bottom-20"}
              [:div {:class "profile-blog"}
                  [:img {:class "rounded-x" :src "images/person.png"}]
                  [:div {:class "name-location"}
                      [:strong (:name user)]
                      [:span (:position user)]]
                  [:div {:class "clearfix margin-bottom-20"}]
                  [:hr]
                  [:ul {:class "list-inline share-list"}
                      [:li [:i {:class "fa fa-edit"}][:a {:href "#"} "Edit"]]
                      [:li [:i {:class "fa fa-book"}][:a {:href "#"} "Archive"]]]]])])])


