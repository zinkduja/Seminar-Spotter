(ns engn-web.login
  (:require [ajax.core :refer [GET POST]]))

(defonce history (atom ""))

(defn handle-webpage [result]
  (reset! history result))


(defn get-webpage [name]
  (GET (str "/webpage/" name)
        {:keywords? true
        :keywordize-keys true
        :handler handle-webpage}))


(defn get-data []
  (get-webpage "math"))

(defn login-page []
  [:div
    [:h1 "Login page."]
    [:h4 @history]
    ])

;(get-webpage "https://as.vanderbilt.edu/history/calendar.php")
