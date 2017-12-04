(ns engn-web.htmldata
  (:require [ajax.core :refer [GET POST]]
            [clojure.string :as string]))

;; ==========================================================================
;; Define atoms
;; ==========================================================================

(defonce math (atom ""))
(defonce psych (atom ""))
(defonce neuro (atom ""))

(defn get-math-atom []
  @math)
(defn get-psych-atom []
  @psych)
(defn get-neuro-atom []
  @neuro)

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
;; PSYCH functions
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
      (reset! psych events))
    (reset! psych []))) ;no psych events

;; ==========================================================================
;; NEURO functions
;; ==========================================================================

;for neuro dept, get the dates
(defn extract-neuro-dates [event]
  (let [index1 (string/index-of event "<td>")
        len (count "<td>")
        index2 (string/index-of event "</td>")
        date (subs event (+ index1 len) index2)]
    date))

;for neuro dept, get the topics
(defn extract-neuro-topics [event]
  (let [index1 (string/index-of event "</td>")
        len (count "</td>")
        sub1 (subs event (+ index1 len))
        index2 (string/index-of sub1 "<td>")
        len2 (count "<td>")
        index3 (string/index-of sub1 "</td>")
        topic (subs sub1 (+ index2 len2) index3)]
    topic))

;for neuro dept, remove a href html from beginning
(defn remove-link [html]
  (if (string/includes? html "a href")
    (let [sub1 (subs html (string/index-of html "a href"))
          index1 (string/index-of sub1 ">")
          index2 (string/index-of sub1 "</a>")]
      (subs sub1 (+ index1 1) index2))
    html))

;for neuro dept, remove strong html from begging
(defn remove-speaker-strong [spkr-html]
  (if (string/includes? spkr-html "strong")
    (let [sub1 (subs spkr-html (string/index-of spkr-html "<strong>"))
          len (count "<strong>")
          index1 (string/index-of sub1 "</strong>")]
      (subs sub1 len index1))
    spkr-html))

;for neuro dept, get the speakers
(defn extract-neuro-speakers [event]
  (let [split (string/split event "<td>")
        spkr (get split 3)
        sub1 (subs spkr 0 (string/index-of spkr "</td>"))
        no-link (remove-link sub1)
        no-strong (remove-speaker-strong no-link)]
    no-strong))

;for neuro dept, remove <p> html from beginning
(defn remove-title-p [title-html]
  (if (string/includes? title-html "<p>")
    (let [len (count " <p>")
          index1 (string/index-of title-html "</p>")]
      (subs title-html len index1))
    title-html))

;for neuro dept, get the titles
(defn extract-neuro-titles [event]
  (let [split (string/split event "<td>")
        title (get split 4)
        no-p (remove-title-p title)
        no-link (remove-link no-p)]
    (if (string/includes? no-link "TBA")
      "TBA"
      no-link)))

;for neuro dept, combine info into dictionary
(defn combine-neuro [date topic title speaker]
  {:date date :time "4:10 p.m." :topic topic :title title :speaker speaker
    :location "1220 Medical Research Building III"})

;for neuro dept, get dates, topics, speakers, titles
;final - return list of dictionaries
(defn handle-neuro [html]
  (if (string/includes? html "<tr>") ;there are neuro events
    (let [events (subvec (string/split html "<tr>") 1)
          dates (into [] (map extract-neuro-dates events))
          topics (into [] (map extract-neuro-topics events))
          speakers (into [] (map extract-neuro-speakers events))
          titles (into [] (map extract-neuro-titles events))
          combined (into [] (map combine-neuro dates topics titles speakers))]
      (reset! neuro combined))
    (reset! neuro []))) ;no neuro events

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
                      :handler handle-psych})
    (= name "neuro") (GET (str "/webpage/" name)
                      {:keywords? true
                      :keywordize-keys true
                      :handler handle-neuro})))

;gets called when the app starts up to get all data
;from the selected departmenets
(defn get-data []
  (get-webpage "math")
  (get-webpage "psych")
  (get-webpage "neuro"))

(defn get-printing []
  (concat (get-math-atom) (get-psych-atom)))
