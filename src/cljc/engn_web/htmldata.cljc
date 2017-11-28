(ns engn-web.htmldata
  (:require [ajax.core :refer [GET POST]]
            [clojure.string :as string]))

;; ==========================================================================
;; Define atoms
;; ==========================================================================

(defonce math (atom ""))
(defonce pysch (atom ""))

(defn get-math-atom []
  @math)
(defn get-psych-atom []
  @pysch)

;; ==========================================================================
;; Functions to get the data from the websites
;; ==========================================================================

;for math dept, get the dates and times
(defn extract-math-dates [event]
  (let [html-class "<span class=\"datetime\">"
        length (count html-class)
        index1 (string/index-of event html-class)
        index2 (string/index-of event "</span>")]
    (subs event (+ index1 length) index2)))

;for math dept, separate dates and times into date, year (remove day), time
;return: [month/date year time]
(defn sep-math-date-time [datetime]
  (let [sep (string/split datetime ",")
        sep-no-day (concat [(get sep 0)] [(subs (get sep 1) 0 5)] [(get sep 2)])]
   (into [] sep-no-day)))

;for math dept, get the topics
(defn extract-math-topics [event]
  (let [html-class "<div class=\"category\">"
        length (count html-class)
        index1 (string/index-of event html-class)
        result1 (subs event (+ index1 length))
        index2 (string/index-of result1 ">")
        index3 (string/index-of result1 "</a>")
        result2 (subs result1 (+ index2 1) index3)]
    result2))

;for math dept, get titles of seminars
(defn extract-math-titles [event]
  (let [length (count "<h2>")
        index1 (string/index-of event "<h2>")
        index2 (string/index-of event "</h2>")
        result1 (subs event (+ index1 length) index2)]
    result1))

;for math dept, get speakers (if they exist, else "")
(defn extract-math-speakers [event]
  (let [length (count "<p>")
        index1 (string/index-of event "<p>")
        index2 (string/index-of event "</p>")
        result1 (subs event (+ index1 length) index2)]
    (if (string/includes? result1 "<br />") ;speaker exists
      (subs result1 0 (string/index-of result1 "<br />"))
      "")))

;for math dept, get locations
(defn extract-math-locs [event]
  (let [length (count  "<p>")
        index1 (string/index-of event "<p>")
        index2 (string/index-of event "</p>")
        result1 (subs event (+ index1 length) index2)]
    (if (string/includes? result1 "<br />") ;speaker exists
      (let [start (string/index-of result1 "<br />")
            full (subs result1 (+ start (count "<br />")))
            loc (subs full (count "Location: "))]
        loc)
      (subs result1 (count "Location: ")))))

;for math dept, get summaries
(defn extract-math-sums [event]
  (let [length (count "</p>")
        index1 (string/index-of event "</p>")
        result1 (subs event (+ index1 length))]
    (if (string/includes? result1 "<p>") ;summary exists
      (let [len2 (count "<p>")
            start (string/index-of result1 "<p>")
            end (string/index-of result1 "</p>")
            result2 (subs result1 (+ start len2) end)
            result3 (string/replace result2 "&#8230;" "...")
            result4 (string/replace result3 "&#8217;" "'")
            result5 (string/replace result4 "&#8220;" "'")
            result6 (string/replace result5 "&#8221;" "'")]
        result6)
      "")))

;for math dept, combine info into dictionary
(defn combine-math [date topic title speaker loc summary]
  {:date date :topic topic :title title :speaker speaker :location loc :summary summary})

;for math dept, get dates, times, location, speaker, topic, title, summary
;(get (get separated 0) 0) - inner get gets list, outer gets date/year/time
;final - return list of dictionaries
(defn handle-math [html]
  (if (string/includes? html "<div class=\"eventitem\">") ;there are math events
    (let [events (subvec (string/split html "<div class=\"eventitem\">") 1)
          dates (into [] (map extract-math-dates events))
          separated (into [] (map sep-math-date-time dates))
          topics (into [] (map extract-math-topics events))
          titles (into [] (map extract-math-titles events))
          speakers (into [] (map extract-math-speakers events))
          locs (into [] (map extract-math-locs events))
          sums (into [] (map extract-math-sums events))
          combined (into [] (map combine-math separated topics titles speakers locs sums))]
      (reset! math combined))
    (reset! math []))) ;no math events

;fetch html from correct webpage
;(defn get-handler [name]
;  (cond
;    ("h")))


; TODO - split handler
(defn get-webpage [name]
  (GET (str "/webpage/" name)
       {:keywords? true
        :keywordize-keys true
        :handler handle-math}))

;gets called when the app starts up to get all data
;from the selected departmenets
(defn get-data []
  (get-webpage "math"))
