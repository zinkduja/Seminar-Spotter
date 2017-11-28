(ns engn-web.upcoming
  (:require [engn-web.htmldata :as html_data]
            [engn-web.prettyprint :as pretty_print]))


;; ==========================================================================
;; Upcoming events page
;; ==========================================================================

(defn upcoming-page []
  [:div
    [:h1 "Upcoming Events"]
    (pretty_print/print-events)])
