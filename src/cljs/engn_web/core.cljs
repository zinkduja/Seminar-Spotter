(ns engn-web.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [cljs-time.core :as time]
              [cljs-time.format :as time-format]
              [cljs-time.coerce :as time-coerce]
              [reagent-material-ui.core :as ui]
              [ajax.core :refer [GET POST]]))


;; ==========================================================================
;; Utility functions
;; ==========================================================================

(defn log
  "Log a message to the Javascript console"
  [& msg]
  (.log js/console (apply str msg)))

(defn error-handler
  "Error handler for AJAX calls to print out errors to the console"
  [{:keys [status status-text]}]
  (log (str "something bad happened: " status " " status-text)))

(def el reagent/as-element)
(defn icon-span [nme] [ui/FontIcon {:className "material-icons"} nme])
(defn icon [nme] (el [:i.material-icons nme]))
(defn color [nme] (aget ui/colors nme))

;; ==========================================================================
;; App State
;; ==========================================================================

;(defonce channels (atom []))
;(defonce current-channel (atom ""))
;(defonce add-channel-dialog-open? (atom false))
;(defonce user (js->clj js/user :keywordize-keys true))


;; ==========================================================================
;; Functions to send / receive messages and list channels
;; ==========================================================================

;(GET "/channel" {:response-format :json
;                 :keywords? true
;                 :error-handler error-handler
;                 :handler (fn [r] (reset! channels r)(println "swap done: " @channels))})

;(defn messages-load [channel]
;  (GET (str "/channel/" channel)
;       {:response-format :json
;        :keywords? true
;        :error-handler error-handler
;        :handler (fn [r] (println r)(reset! msgs r))}))

;(defn open-channel [channel]
;   (reset! current-channel channel)
;   (messages-load channel))
;
;(defn push [msgs msg]
;  (conj (seq msgs) msg))


;; ==========================================================================
;; View components
;; ==========================================================================

;(defn set-price-control []
;  [ui/Card
;    [ui/CardText
;      [ui/TextField
;        {:floatingLabelText "What is the price?"
;        :onChange #(swap! state assoc :price-for-server %2)}]
;      [ui/RaisedButton {:label "Set Server Price"
;                        :on-click #(set-price! (:price-for-server @state))}]]])

;(defn price-control []
;  [ui/Card
;    [ui/CardText
;      [ui/TextField
;        {:floatingLabelText "What are we buying?"
;        :onChange #(swap! state assoc :item %2)}]
;      [ui/RaisedButton {:label "Buy"
;                        :on-click #(get-price! (:item @state))}]]])


;(defn main-page []
;  (let [greeting (:greeting @state)
;        hello    (:hello @state)
;        price (:price @state)
;        item-name (:item-name @state)]
;    [ui/MuiThemeProvider ;theme-defaults
;      [:div
;        [set-greeting-control]
;        [greet-control]
;        [ui/Card
;          [ui/CardText
;            [:h1 (str greeting " " hello)]]]
;        [set-price-control]
;        [price-control]
;        [ui/Card
;          [ui/CardText
;            [:h1 (str item-name " costs " price)]]]]]))

(defn main-page []
  [ui/MuiThemeProvider
    [:div
      [:h1 "Landing page."]]])

;; -------------------------
;; Routes

(def page (atom #'main-page))

(defn current-page []
  [:div [@page]])

(secretary/defroute "/" []
  (reset! page #'main-page))


;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))


;(load "login")
