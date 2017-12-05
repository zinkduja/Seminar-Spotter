(ns engn-web.handler-integration-test
  (:require [ajax.core :refer [GET POST]]
            [cheshire.core :as cheshire]
            [clojure.test :refer :all]
            [clojure.string :as string]
            [engn-web.handler :refer :all]
            [engn-web.server :refer [-main]]
            [engn-web.util :refer [sync]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.mock.request :refer [request]]))

(defmacro with-server [t]
  `(let [server# (-main)]
    (try
      ~t
      (finally
        (.stop server#)))))

;;======================================================================


(deftest ^:integration math-html-test
  (testing "Test that we can fetch the math html from the HTTP endpoint"
    (with-server
        (let [html (sync GET "https://as.vanderbilt.edu/math/category/events/")]
          (is (string/includes? html "<div class=\"eventitem\">"))))))

(deftest ^:integration psych-html-test
  (testing "Test that we can fetch the psych html from the HTTP endpoint"
    (with-server
        (let [html (sync GET "https://events.vanderbilt.edu/index.php?com=searchresult&t=364")]
          (is (string/includes? html "<div id=\"searchform\">"))))))

(deftest ^:integration neuro-html-test
  (testing "Test that we can fetch the neuro html from the HTTP endpoint"
    (with-server
        (let [html (sync GET "https://medschool.vanderbilt.edu/brain-institute/neuroscience-seminar-series-student-nanosymposiumforums")]
          (is (string/includes? html "<tbody>"))))))
