(ns engn-web.upcoming
  (:require [engn-web.htmldata :as htmldata]
            [engn-web.prettyprint :as prettyprint]
            [reagent-material-ui.core :as ui]))


;; ==========================================================================
;; Upcoming events page
;; ==========================================================================

(defn upcoming-page []
  [:div
    [:h1 {:className "home-text"} "Upcoming Events"]
    [ui/Chip {:labelColor "#000000" :backgroundColor "#841917" :size "30"}
          "Mathematics"]
    [ui/Chip {:labelColor "#000000" :backgroundColor "#311784" :size "30"}
         "Psychology"]
    [:br]
    (prettyprint/print-events)])
