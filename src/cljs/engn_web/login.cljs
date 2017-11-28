(ns engn-web.login
  (:require [engn-web.htmldata :as html_data]
            [engn-web.prettyprint :as pretty_print]))


;; ==========================================================================
;; Basic login page
;; ==========================================================================
(def username-atom (atom ""))
(def password-atom (atom ""))

(defn input-element
  "An input element which updates its value on change"
  [id name type value]
  [:input {:id id
           :name name
           :class "form-control"
           :type type
           :required ""
           :value @value
           :on-change #(reset! value (-> % .-target .-value))}])

(defn username-input []
  (input-element "username" "username" "text" username-atom))

(defn password-input []
  (input-element "password" "password" "password" password-atom))

(defn print-login []
  [:div
    [:form
      [:h4 "Username: "]
      [username-input]
      [:br]
      [:h4 "Password: "]
      [password-input]]])

(defn login-page []
  [:div
    (print-login)])
