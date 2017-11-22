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
(defn password-atom (atom ""))

(defn print-login []
  [:div
    [:form
      [username-input]
      [password-input]])

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
