(ns engn-web.upcoming
  (:require [engn-web.htmldata :as htmldata]
            [engn-web.prettyprint :as prettyprint]))


;; ==========================================================================
;; Upcoming events page
;; ==========================================================================

(defn upcoming-page []
  [:div
    [:h1 "Upcoming Events"]
    (prettyprint/print-events)])
