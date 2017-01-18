(ns populo.layout
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css)]))

(defn common [userDetails title & body]
  (html5
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1, maximum-scale=1"}]
     [:title (str "Populo : " title)]

     [:link {:rel "shortcut icon" :href "images/codurance-logo.ico"}]

    (include-css "css/custom.css") 
    (include-css "https://fonts.googleapis.com/css?family=Open+Sans:400,300,700&amp;subset=cyrillic,latin") 
    (include-css "assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css")
    (include-css "assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css") 
    (include-css "assets/plugins/bootstrap/css/bootstrap.min.css")
    (include-css "assets/css/style.css")
    (include-css "assets/plugins/animate.css")
    (include-css "assets/plugins/line-icons/line-icons.css")
    (include-css "assets/plugins/font-awesome/css/font-awesome.min.css")
    (include-css "assets/plugins/brand-buttons/brand-buttons-inversed.css")
    (include-css "assets/css/theme-colors/orange.css")
    (include-css "assets/css/headers/header-default.css")
    (include-css "assets/css/pages/page_log_reg_v3.css")
    (include-css "assets/css/pages/page_log_reg_v1.css")
    (include-css "assets/css/pages/page_404_error.css")
    (include-css "assets/css/pages/profile.css")
    (include-css "assets/css/app.css") 
    ]

    [:script {:src "assets/plugins/jquery/jquery.min.js"}]
    [:script {:src "assets/plugins/jquery/jquery-migrate.min.js"}]
    [:script {:src "assets/plugins/bootstrap/js/bootstrap.min.js"}]
    [:script {:src "assets/plugins/sky-forms-pro/skyforms/js/jquery-ui.min.js"}]
    [:script {:src "assets/plugins/sky-forms-pro/skyforms/js/jquery.form.min.js"}]
    [:script {:src "assets/plugins/sky-forms-pro/skyforms/js/jquery.validate.min.js"}]

    [:body  body]))
