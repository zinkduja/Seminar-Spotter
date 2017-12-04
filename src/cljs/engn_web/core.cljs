(ns engn-web.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [cljs-time.core :as time]
              [cljs-time.format :as time-format]
              [cljs-time.coerce :as time-coerce]
              [reagent-material-ui.core :as ui]
              [ajax.core :refer [GET POST]]
              [engn-web.login :as login]
              [engn-web.calendar :as calendar]
              [engn-web.upcoming :as upcoming]
              [engn-web.htmldata :as htmldata]))


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

(def page (atom nil))
;(defonce user (js->clj js/user :keywordize-keys true))


;; ==========================================================================
;; Functions to send / receive messages and list channels
;; ==========================================================================

;(defn messages-load [channel]
;  (GET (str "/channel/" channel)
;       {:response-format :json
;        :keywords? true
;        :error-handler error-handler
;        :handler (fn [r] (println r)(reset! msgs r))}))


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

(defn home-page []
  [:div
    [:h1 {:className "home-text font-effect-3d-float"} "Seminar Spotter"]
    [:img {:src "/img/seminar_man.jpg" :className "home-image"}]
    [:h3 {:className "home-text"} "See Vanderbilt's upcoming seminars all in one place."]])

(defn main-page []
  (htmldata/get-data)
  [ui/MuiThemeProvider
      [ui/Tabs
       [ui/Tab {:label "Home"}
        (home-page)]
       [ui/Tab {:label "Upcoming Events"}
        (upcoming/upcoming-page)]
       [ui/Tab {:label "Calendar"}
        (calendar/calendar-page)]
       [ui/Tab {:label "Login"}
        (login/login-page)]]])

;; -------------------------
;; Routes

(reset! page #'main-page)

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
