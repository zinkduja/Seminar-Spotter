(ns engn-web.login
  (:require [engn-web.html_data :as html_data]
            [engn-web.pretty_print :as pretty_print]))


;; ==========================================================================
;; Basic login page
;; ==========================================================================

;for testing html_data functions
(defn login-page []
  [:div
    [:h1 "Login page."]
    (pretty_print/print-events)])

;(defn login-page [])
;  [:div])
    ;[:h1 "Login page."]])
