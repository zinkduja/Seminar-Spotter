(ns engn-web.handler
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.route :refer [not-found resources]]
            [config.core :refer [env]]
            [clj-http.client :as client]
            [engn-web.middleware :refer [wrap-middleware]]
            [hiccup.page :refer [include-js include-css html5]]
            [ring.middleware.json :as json]
            [ring.middleware.cookies :refer [wrap-cookies]]
            [ring.middleware.params :refer [wrap-params]]
            [clojure.string :as string]))

;; ==========================================================================
;; Utility functions for serving up a JSON REST API
;; ==========================================================================

(def json-header
  "Utility function to set the appropriate headers in an
   HTTP response to return JSON to the client"
  {"Content-Type" "application/json"})

(defn json
  "Utility function to return JSON to the client"
  [data]
  {:status 200 :headers json-header :body data})

;; ==========================================================================
;; Functions to render the HTML for the single-page application
;; ==========================================================================

(def mount-target
  "This is the page that is displayed before figwheel
   compiles your application"
  [:div#app
      [:h3 "ClojureScript has not been compiled!"]
      [:p "please run "
       [:b "lein figwheel"]
       " in order to start the compiler (this page may self-destruct)"]])

(defn head []
  "Function to generate the <head> tag in the main HTML page that is
   returned to the browser"
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css "https://fonts.googleapis.com/icon?family=Material+Icons")
   (include-css "https://fonts.googleapis.com/css?family=Roboto:300,400,500")
   (include-css (if (env :dev) "/css/site.css" "/css/site.min.css"))])

(defn main-page
   "Generates the main HTML page for the single-page application"
   []
   (html5
     (head)
     [:body
       mount-target
       (include-js "/js/app.js")]))

;; ==========================================================================
;; Functions to setup the list of URI routes for the application
;; and setup the appropriate middleware wrappers
;; ==========================================================================


(defn get-math-html []
  (let [html (get (client/get "https://as.vanderbilt.edu/math/category/events/") :body)
        index1 (string/index-of html "<div class=\"eventitem\">")
        index2 (string/index-of html "</div><!-- /secmain -->")
        new-html (subs html index1 index2)]
   new-html))

(defn get-psych-html []
  (let [html (get (client/get "https://events.vanderbilt.edu/index.php?com=searchresult&t=364") :body)
        index1 (string/index-of html "<div id=\"searchform\">")
        index2 (string/index-of html "<!-- Right Column -->")
        new-html (subs html index1 index2)]
    new-html))

(defn get-neuro-html []
  (let [html (get (client/get "https://medschool.vanderbilt.edu/brain-institute/neuroscience-seminar-series-student-nanosymposiumforums") :body)
        index1 (string/index-of html "<tbody>")
        index2 (string/index-of html "</tbody>")
        new-html (subs html index1 index2)]
    new-html))

(defn get-html-handler [dept]
  (cond
    (= dept "math") (get-math-html)
    (= dept "psych") (get-psych-html)
    (= dept "neuro") (get-neuro-html)
    :else "not a handled department"))

;; ==========================================================================
;; Functions to setup the list of URI routes for the application
;; and setup the appropriate middleware wrappers
;; ==========================================================================

(defroutes routes
  (GET "/" request (main-page))
  (GET "/webpage/:dept" [dept] (get-html-handler dept))
  ;; Routes down here handle static files and not found
  (resources "/")
  (not-found "Not Found"))


(def app (->  (wrap-middleware #'routes)
              (json/wrap-json-response)
              (json/wrap-json-params)
              wrap-params
              wrap-cookies))
