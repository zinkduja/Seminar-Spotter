(ns engn-web.upcoming
  (:require [engn-web.htmldata :as html_data]
            [engn-web.prettyprint :as pretty_print]))


;; ==========================================================================
;; Upcoming events page
;; ==========================================================================

;for testing html_data functions
(defn upcoming-page []
  [:div
    [:h1 "Upcoming Events"]
    (pretty_print/print-events)])
