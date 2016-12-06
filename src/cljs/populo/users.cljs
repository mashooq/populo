(ns populo.users
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]))

(defonce users (atom  (sorted-map "1" {:id "1" :name "Mashooq Badar" :position "Software Craftsman" :archived false}
                                   "2" {:id "2" :name "Sandro Mancuso" :position "Software Craftsman" :archived false}
                                   "3" {:id "3" :name "Pedro Santos" :position "Software Craftsman" :archived true})))

(def search-term (atom ""))

(defn toggle-archived [id user] 
  (swap! user update  :archived not))

(defn save  [id user] (swap! users assoc id user))

(defn add-user [user]
  (swap! users assoc (:id user) user))

(defn has-term [user] 
  (seq (filter #(re-matches (re-pattern (str "(?i).*" @search-term ".*")) (str %)) (vals user)))
)

(defn search-users [filt]
  (if (clojure.string/blank? @search-term) 
    (filter filt (vals @users))
    (doall (filter has-term (filter filt (vals @users))))
    ))

(defn text-input [user key]
  [:input {:type "text" :value (@user key)
           :on-change #(swap! user assoc key (-> % .-target .-value)) 
          } ])

(defn checkbox-archived [user]
  (let [{:keys [id archived]} @user]
   [:input.toggle {:type "checkbox" :checked archived
                  :on-change #(toggle-archived id user)}
   ]))

(defn user-tile [user]
  (let [tile-mode (atom false)]
    (fn [user]
      (case @tile-mode 
        :editing
          (let [edited-user (atom user)]
            [:div {:class "col-sm-4 sm-margin-bottom-20"}
              [:div {:class "profile-blog"}
                  [:div {:class "sky-form"}
                      [:label {:class "input"} [text-input edited-user :name]]  
                      [:label {:class "input"} [text-input edited-user :position]]  
                      [:label {:class "checkbox"} [checkbox-archived edited-user] [:i] " Archived" ]  
                      ]
                  [:div {:class "clearfix margin-bottom-20"}]
                  [:hr]
                  [:ul {:class "list-inline"}
                      [:li [:button { :on-click #(do (reset! tile-mode :display) (save (:id @edited-user) @edited-user))
                                      :href "#" :class "btn-u rounded btn-u-sm btn-u-default"} 
                            [:i {:class "fa fa-save"}]]]
                      [:li [:button { :on-click #(do (reset! tile-mode :display) (reset! edited-user user))
                                      :href "#" :class "btn-u rounded btn-u-sm btn-u-red"} 
                            [:i {:class "fa fa-times"}]]]
                      ]
                  ]])

          
          [:div {:class "col-sm-4 sm-margin-bottom-20"}
            [:div {:class "profile-blog" }
                [:img {:class "rounded-x" :src "images/person.png"}]
                [:div {:class "name-location"}
                    [:a {:style {:float "right"} :on-click #(reset! tile-mode :editing) :href "#"} [:i {:class "fa fa-edit"}]  ] 
                    [:strong (:name user)]
                    [:span (:position user)]]
                ]]))))

(defn panel-title [title]
  [:div {:class "panel-heading overflow-h margin-top-20"}
      [:h2 {:class "panel-title heading-sm pull-left"}
       	[:i {:class "fa fa-users"}] title]]
  )

(defn user-list [title filt]
  [:div {:class "panel panel-profile" }
      (panel-title title) 
      [:div {:class "profile-body"}
      (for [user-row (partition-all 3 (search-users filt )  )]
          ^{:key (str "row-" (:id (first user-row)))}
          [:div {:class "row margin-bottom-10"}
          (for [user user-row] ^{:key (:id user)} [user-tile user])])]]
  )

(defn users-page []
  [:div {:class "wrapper"}
   [:div {:class "container content"} ]
    (let [term (atom "")] 
      [:div {:class "container profile"}
      [:div {:class "row"} 
        [:div {:class "col-md-6 col-md-offset-3"}
                  [:div {:class "input-group"}
                      [:input {:type "text" :class "form-control" :placeholder "Search people ..."
                              :on-change #(reset! search-term (-> % .-target .-value))}]
                      [:span {:class "input-group-btn"}
                          [:button {:class "btn-u btn-u-default disabled" :disabled "disabled" :type "button"}[:i {:class "fa fa-search"}]]]]]]
        [:div {:class "panel-group" :id "user-lists"}
                    (user-list "Active" #(not  (:archived %)))              
                    (user-list "Archived" #(:archived %))]])])


