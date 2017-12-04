(ns engn-web.handler-test
  (:require
   [cheshire.core :as cheshire]
   [clojure.test :refer :all]
   [engn-web.handler :refer :all]
   [ring.mock.request :as mock]
   [clojure.string :as string]))


(defn parse-body [body]
  (cheshire/parse-string body true))

;--------------------------------------------------------------

(deftest bad-webpage-test
  (testing "Test that we cannot fetch a bad department"
    (let [response (app (-> (mock/request :get "/webpage/foo")))
          msg (:body response)]
      (is (= (:status response) 200))
      (is (= "not a handled department" msg)))))

(deftest math-webpage-test
  (testing "Test that we can fetch and clean up the math department events"
    (let [response (app (-> (mock/request :get "/webpage/math")))
          msg (:body response)]
      (is (= (:status response) 200))
      (is (string/includes? msg "eventitem"))
      (is (not (string/includes? msg "</div><!-- /secmain -->"))))))

(deftest psych-webpage-test
  (testing "Test that we can fetch and clean up the psych department events"
    (let [response (app (-> (mock/request :get "/webpage/psych")))
          msg (:body response)]
      (is (= (:status response) 200))
      (is (string/includes? msg "searchform"))
      (is (not (string/includes? msg "<!-- Right Column -->"))))))

(deftest neuro-webpage-test
  (testing "Test that we can fetch and clean up the neuro department events"
    (let [response (app (-> (mock/request :get "/webpage/neuro")))
          msg (:body response)]
      (is (= (:status response) 200))
      (is (string/includes? msg "<tbody>"))
      (is (not (string/includes? msg "</tbody>"))))))
