(ns engn-web.htmldata
  (:require [ajax.core :refer [GET POST]]
            [clojure.string :as string]))

;; ==========================================================================
;; Define atoms
;; ==========================================================================

(defonce math (atom ""))
(defonce psych (atom ""))

(defn get-math-atom []
  @math)
(defn get-psych-atom []
  @psych)

;; ==========================================================================
;; Functions to get the data from the websites
;; ==========================================================================

;; ==========================================================================
;; MATH functions
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
  {:date date :topic topic :title title :speaker speaker :location loc :summary summary :titleColor "#841917"})

;for math dept, get dates, times, location, speaker, topic, title, summary
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

;; ==========================================================================
;; psych functions
;; ==========================================================================

;count the number of times a substring occurs in a string
(defn count-substring [txt sub]
  (count (re-seq (re-pattern sub) txt)))

;for psych dept, get the dates and separate date and year
;may need to return same date again for multiple events
;return: [[month/date year]...]
(defn extract-psych-dates [event]
  (let [num-events (count-substring event "datetime")
        index1 (string/index-of event "</strong>")
        dateyear (subs event 0 index1)
        sep (string/split dateyear ",")
        sep-no-day (into [] (concat [(get sep 1)] [(get sep 2)]))]
    (into [] (repeat num-events sep-no-day))))

;for psych dept, remove outer [] on updates
(defn fix-psych-dates [dates]
  (let [flat (flatten dates)
        new-dates (into [] (partition 2 flat))]
    new-dates))

;for psych dept, separate multiple times on same date
(defn sep-psych-times [html-time]
  (let [index1 (string/index-of html-time ">")
        index2 (string/index-of html-time "</time>")
        time (subs html-time (+ index1 1) index2)]
    time))

;for psych dept, get the times
;return list of times (may be multiple events)
(defn extract-psych-times [event]
  (let [split (subvec (string/split event "<time") 1)
        times (into [] (map sep-psych-times split))]
    times))

;for psych dept, separate multiple titles on same date
(defn sep-psych-titles [html-title]
  (let [index1 (string/index-of html-title ">")
        index2 (string/index-of html-title "</a>")
        title (subs html-title (+ index1 1) index2)]
    title))

;for psych dept, get the titles
;return list of titles (may be multiple events)
(defn extract-psych-titles [event]
  (let [split (subvec (string/split event "<a") 1)
        titles (into [] (map sep-psych-titles split))]
    titles))

;for psych dept, combine info into dictionary
(defn combine-psych [date time title]
  {:date (into [] date) :time time :title title :titleColor "#311784"})

;for psych dept, get dates, times, titles
;final - return list of dictionaries
(defn handle-psych [html]
  (if (string/includes? html "<div id=\"searchform\">") ;there are psych events
    (let [events (subvec (string/split html "<strong>") 1)
          dates (into [] (map extract-psych-dates events))
          times (into [] (map extract-psych-times events))
          titles (into [] (map extract-psych-titles events))
          combined (into [] (map combine-psych (fix-psych-dates dates) (flatten times) (flatten titles)))]
      (reset! psych combined))
    (reset! psych []))) ;no psych events

;; ==========================================================================
;; GET HTML functions
;; ==========================================================================

;fetch html from correct webpage
(defn get-webpage [name]
  (cond
    (= name "math") (GET (str "/webpage/" name)
                      {:keywords? true
                       :keywordize-keys true
                       :handler handle-math})
    (= name "psych") (GET (str "/webpage/" name)
                      {:keywords? true
                       :keywordize-keys true
                       :handler handle-psych})))

;gets called when the app starts up to get all data
;from the selected departmenets
(defn get-data []
  (get-webpage "math")
  (get-webpage "psych"))

(defn get-printing []
  (concat (get-math-atom) (get-psych-atom)))
