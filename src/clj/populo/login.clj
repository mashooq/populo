(ns populo.login
  (:use [hiccup.core :only (h)]
        [hiccup.form :refer :all])
  (:require [populo.layout :refer [common]]))

(defn login []
  (common nil "Login"
          [:div {:class "container content-sm"}
           [:div {:class "row margin-bottom-40"}
            [:div {:class "row margin-bottom-50"}
             [:div {:class "col-sm-4 col-sm-offset-4"}
              [:div {:ng-show "showMessage" :class "alert alert-danger fade in"}
               [:h4 "SOME MESSAGE"]]
              [:form {:name "signInForm" :class "reg-page"}
               [:div {:class "reg-header"}
                [:h2 "Sig"]]
               [:div {:class "input-group margin-bottom-20"}
                [:span {:class "input-group-addon"} [:i {:class "fa fa-user"}]]
                [:input {:class "form-control" :type "email" :placeholder "Email" :ng-model "user.email" :autoFocus "" :required ""}]]
               [:div {:class "input-group margin-bottom-20"}
                [:span {:class "input-group-addon"} [:i {:class "fa fa-lock"}]]
                [:input {:class "form-control" :type "password" :placeholder "Password" :ng-model "user.password" :required ""}]]
               [:div {:captcha ""}]
               [:div {:class "row"}
                [:div {:class "text-center"}
                 [:button {:ng-disabled "disableSubmit || strength == 'note-error'"
                           :ng-click "signInForm.$valid && signIn(user)" :class "btn btn-u"} "Submit"]]]
               [:hr]
               [:h4 "Forgot your Password ?"]
               [:p "No worries, " [:a {:href "/password-reset"} "click here"] " to reset your password."]]]]]]))
