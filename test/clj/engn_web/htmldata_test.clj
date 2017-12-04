(ns engn-web.htmldata-test
  (:require [clojure.test :refer :all]
            [engn-web.htmldata :refer :all]))

;; ==========================================================================
;; Test MATH functions
;; ==========================================================================

(deftest test-extract-math-dates
  (testing "Testing correct extracting the date from a math event"
    (is (= "Date here"
            (extract-math-dates "<span class=\"datetime\">Date here</span>")))
    (is (= ""
            (extract-math-dates "<span class=\"datetime\"></span>")))
    ))

;(deftest test-sep-math-date-time
;  (testing "Testing correct separating of date, year, and time for a math event"
;    (is (= ["December 6" "2017" "4:10 pm"]
;            (sep-math-date-time "December 6, 2017 (Wednesday), 4:10 pm")))
;    ))

(deftest test-extract-math-topics
  (testing "Testing correct extracting the topic from a math event"
    (is (= "Colloquium"
            (extract-math-topics "<div class=\"category\"><a href=\"urlhere\">Colloquium</a></div>")))
    (is (= ""
            (extract-math-topics "<div class=\"category\"><a href=\"urlhere\"></a></div>")))
    ))

(deftest test-extract-math-titles
  (testing "Testing correct extracting the title from a math event"
    (is (= "Title"
            (extract-math-titles "<h2>Title</h2>")))
    (is (= ""
            (extract-math-titles "<h2></h2>")))
    ))

(deftest test-extract-math-speakers
  (testing "Testing correct extracting the speaker from a math event"
    (is (= "Eddy Kwessi, Trinity University"
            (extract-math-speakers "<p>Eddy Kwessi, Trinity University<br />Location: Stevenson 1432</p>")))
    (is (= ""
            (extract-math-speakers "<p><br />Location: Stevenson 1432</p>")))
    (is (= ""
            (extract-math-speakers "<p>Location: Stevenson 1432</p>")))
    ))

(deftest test-extract-math-locs
  (testing "Testing correct extracting the location from a math event"
    (is (= "Stevenson 1308"
            (extract-math-locs "<p>Hung Cong Tran, University of Georgia<br />Location: Stevenson 1308</p>")))
    (is (= ""
            (extract-math-locs "<p>Hung Cong Tran, University of Georgia<br />Location: </p>")))
    (is (= "Stevenson 5211"
            (extract-math-locs "<p>Location: Stevenson 5211</p>")))
    ))

(deftest test-extract-math-sums
  (testing "Testing correct extracting the summary from a math event"
    (is (= "Tea at 3:33 pm in SC 1425 ..."
            (extract-math-sums "<p>Location: Stevenson 5211</p><p>Tea at 3:33 pm in SC 1425 &#8230;</p>")))
    (is (= ""
            (extract-math-sums "<p>Location: Stevenson 5211</p><p></p>")))
    (is (= ""
            (extract-math-sums "<p>Location: Stevenson 5211</p>")))
    ))

(deftest test-combine-math
  (testing "Testing correct combinging the info for a math event"
    (is (= {:date ["date"] :topic "topic" :title "title" :speaker "speaker" :location "loc" :summary "summary"}
            (combine-math ["date"] "topic" "title" "speaker" "loc" "summary")))
    (is (= {:date ["date"] :topic "topic" :title "title" :speaker "" :location "loc" :summary ""}
            (combine-math ["date"] "topic" "title" "" "loc" "")))
    ))

;; ==========================================================================
;; Test PSYCH functions
;; ==========================================================================

(deftest test-count-substring
  (testing "Testing correct counting of a substring"
    (is (= 4
            (count-substring "subsubandsub and sub" "sub")))
    (is (= 0
            (count-substring "none" "sub")))
    ))

;(deftest test-extract-psych-dates
;  (testing "Testing correct counting of a substring"
;    (is (= [[" December 05" " 2017"]]
;            (extract-psych-dates "Tuesday, December 05, 2017</strong>html
;            <time datetime=\"somedate\">12:10 PM - 1:00 PM</time> morehtml")))
;    (is (= [[""]]
;            (extract-psych-dates "</strong>html<time datetime=\"somedate\">
;            12:10 PM - 1:00 PM</time> morehtml")))
;    (is (= [[" December 05" " 2017"] [" December 05" " 2017"]]
;            (extract-psych-dates "Wednesday, December 06, 2017</strong>html
;            <time datetime=\"somedate\">12:05 PM - 1:00 PM</time> morehtml
;            <time datetime=\"2017-12-06T04:10:00.0-06:00\"> 4:10 PM - 5:10 PM
;            </time> morehtml")))
;    ))

(deftest test-fix-psych-dates
  (testing "Testing correct counting of a substring"
    (is (= [[" December 05" " 2017"] [" December 05" " 2017"] [" December 07" " 2017"]]
            (fix-psych-dates [[[" December 05" " 2017"] [" December 05" " 2017"]] [[" December 07" " 2017"]]])))
    (is (= [[" December 05" " 2017"] [" December 05" " 2017"] ["" ""]]
            (fix-psych-dates [[[" December 05" " 2017"] [" December 05" " 2017"]] [["" ""]]])))
    ))

(deftest test-sep-psych-times
  (testing "Testing correct counting of a substring"
    (is (= [[" December 05" " 2017"] [" December 05" " 2017"] [" December 07" " 2017"]]
            (sep-psych-times [[[" December 05" " 2017"] [" December 05" " 2017"]] [[" December 07" " 2017"]]])))
    (is (= [[" December 05" " 2017"] [" December 05" " 2017"] ["" ""]]
            (sep-psych-times [[[" December 05" " 2017"] [" December 05" " 2017"]] [["" ""]]])))
    ))
