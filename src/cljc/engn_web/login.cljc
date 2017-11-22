(ns engn-web.login
  (:require [engn-web.htmldata :as html_data]
            [engn-web.prettyprint :as pretty_print]))


;; ==========================================================================
;; Basic login page
;; ==========================================================================

;for testing html_data functions
(defn login-page []
  [:div
    [:h1 "Login page."]
    (pretty_print/print-login)])
