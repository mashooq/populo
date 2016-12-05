(ns populo.views.users
  (:use [hiccup.core :only (h)]
        [hiccup.form :refer :all])
  (:require [populo.views.layout :as layout]))

(defn users [userDetails]
  (layout/common nil "Users"
    [:div {:class "container content profile"}
        [:div {:class "row"}
            [:div {:class "col-md-3 margin-top-20 md-margin-bottom-40"}
                [:ul {:class "list-group sidebar-nav-v1 margin-bottom-40" :id "sidebar-nav-1"}
                    [:li {:class "list-group-item list-toggle"}
												[:a {:data-toggle "collapse" :data-parent "#sidebar-nav-1" :href "#collapse-apps" :class "collapsed" :aria-expanded "false"} 
														[:i {:class "fa fa-cubes"}] "Applications"]
													[:ul {:id "collapse-apps" :class "collapse" :aria-expanded "false" :style "height: 0px;"}
															[:li [:a {:href "shortcode_btn_general.html"} [:i {:class "fa fa-cube"}] "Memento"]]
															[:li [:a {:href "shortcode_btn_general.html"} [:i {:class "fa fa-cube"}] "Monitor"]]]]
										[:li {:class "list-group-item list-toggle"}
												[:a {:data-toggle "collapse" :data-parent "#sidebar-nav-1" :href "#collapse-users" :class "colapsed" :aria-expanded "false"} 
														[:i {:class "fa fa-users"}] "Users"]
													[:ul {:id "collapse-users" :class "collapse" :aria-expanded "false" :style "height: 0px;"}
															[:li [:a {:href "shortcode_btn_general.html"} [:i {:class "fa fa-user"}] "All"]]
															[:li [:a {:href "shortcode_btn_general.html"} [:i {:class "fa fa-filter"}] "Active"]]
															[:li [:a {:href "shortcode_btn_general.html"} [:i {:class "fa fa-filter"}] "Archived"]]
															[:li [:a {:href "shortcode_btn_general.html"} [:i {:class "fa fa-search"}] "Search"]]]] ]]


            [:div {:class "col-md-9"}
                [:div {:class "profile-body margin-bottom-20"}
                  [:div#app
                      [:h3 "ClojureScript has not been compiled!"]
                      [:p "please run "
                      [:b "lein figwheel"]
                      " in order to start the compiler"]]
                    ]]]]
      ))
