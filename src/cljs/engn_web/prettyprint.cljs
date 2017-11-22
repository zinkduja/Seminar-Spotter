(ns engn-web.prettyprint
  (:require [clojure.string :as string]
            [engn-web.htmldata :as html_data]
            [reagent-material-ui.core :as ui]))

; FOR THE UPCOMING EVENTS PAGE

(defn print-events []
 (for [item (html_data/get-math-atom)]
   [ui/Card
    [ui/CardHeader {:title [(:title item) " (" (:speaker item) ")"]
                    :subtitle [(:location item) " - " (:date item)]
                    :showExpandableButton true}]
    [ui/CardText {:expandable true}
                 (:topic item) [:br][:br] (:summary item)]]))

; FOR THE LOGIN PAGE
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
