(ns engn-web.html_data
  (:require [ajax.core :refer [GET POST]]
            [clojure.string :as string]))

  ;; ==========================================================================
  ;; Define atoms
  ;; ==========================================================================

(defonce math (atom ""))

(defn get-math-atom []
  @math)

;; ==========================================================================
;; Functions to get the data from the websites
;; ==========================================================================

(defn handle-webpage [result]
  (let [events (string/split result "<div class=\"eventitem\">")]
    (reset! math (get events 1))))


(defn get-webpage [name]
  (GET (str "/webpage/" name)
        {:keywords? true
        :keywordize-keys true
        :handler handle-webpage}))


(defn get-data []
  (get-webpage "math"))

;; ==========================================================================
;; Basic login page
;; ==========================================================================

(defn html-page []
  [:div
    [:h1 "Login page."]
    [:h4 @math]
    ])
