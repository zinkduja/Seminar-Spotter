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
  (testing "Testing correct combining of the info for a math event"
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

(deftest test-fix-psych-dates
  (testing "Testing correct flattening of psych dates"
    (is (= [[" December 05" " 2017"] [" December 05" " 2017"] [" December 07" " 2017"]]
            (fix-psych-dates [[[" December 05" " 2017"] [" December 05" " 2017"]] [[" December 07" " 2017"]]])))
    (is (= [[" December 05" " 2017"] [" December 05" " 2017"] ["" ""]]
            (fix-psych-dates [[[" December 05" " 2017"] [" December 05" " 2017"]] [["" ""]]])))
    ))

(deftest test-sep-psych-times
  (testing "Testing correct extracting of a time for a psych event"
    (is (= "12:10 PM - 1:00 PM"
            (sep-psych-times " datetime=\"time\">12:10 PM - 1:00 PM</time> - html")))
    (is (= "4:10 PM - 5:10 PM"
            (sep-psych-times "datetime=\"time\">4:10 PM - 5:10 PM</time> - html")))
    (is (= ""
            (sep-psych-times "datetime=\"time\"></time> - html")))
    ))

(deftest test-sep-psych-titles
  (testing "Testing correct extracting of a title for a psych event"
    (is (= "Clinical Science Brown Bag Series"
            (sep-psych-titles "<a href=link>Clinical Science Brown Bag Series</a>")))
    (is (= ""
            (sep-psych-titles "<a href=link></a>")))
    ))

(deftest test-combine-psych
  (testing "Testing correct combining of the info for a psych event"
    (is (= {:date ["date"] :time "time" :title "title"}
            (combine-psych ["date"] "time" "title")))
    ))

;; ==========================================================================
;; Test NEURO functions
;; ==========================================================================

(deftest test-extract-neuro-dates
  (testing "Testing correct extracting of a date for a neuro event"
    (is (= "Date here"
            (extract-neuro-dates "<td>Date here</td>")))
    (is (= ""
            (extract-neuro-dates "<td></td>")))
    ))

(deftest test-extract-neuro-topics
  (testing "Testing correct extracting of a topic for a neuro event"
    (is (= "Topic"
            (extract-neuro-topics "<td>Date here</td> <td>Topic</td>")))
    (is (= ""
            (extract-neuro-topics "<td>Date here</td> <td></td>")))
    ))

(deftest test-remove-link
  (testing "Testing correct removing of a starting link for a neuro event"
    (is (= "link text"
            (remove-link "<stuff><a href=link>link text</a>")))
    (is (= "<no link here>"
            (remove-link "<no link here>")))
    ))

(deftest test-remove-speaker-strong
  (testing "Testing correct removing of a starting <strong> for a neuro event"
    (is (= "Speaker Name"
            (remove-speaker-strong "<stuff><strong>Speaker Name</strong>")))
    (is (= "<no work here>"
            (remove-speaker-strong "<no work here>")))
    ))

(deftest test-remove-title-p
  (testing "Testing correct removing of a <p> for a neuro event"
    (is (= "Title"
            (remove-title-p " <p>Title</p>")))
    (is (= "<no work here>"
            (remove-title-p "<no work here>")))
    ))

(deftest test-combine-neuro
  (testing "Testing correct combining of the info for a neuro event"
    (is (= {:date ["date"] :time "4:10 p.m." :topic "topic" :title "title" :speaker "speaker"
            :location "1220 Medical Research Building III"}
            (combine-neuro ["date"] "topic" "title" "speaker")))
    (is (= {:date ["date"] :time "4:10 p.m." :topic "" :title "title" :speaker ""
            :location "1220 Medical Research Building III"}
            (combine-neuro ["date"] "" "title" "")))
    ))
