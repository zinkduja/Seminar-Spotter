(ns engn-web.prettyprint
  (:require [clojure.string :as string]
            [engn-web.htmldata :as htmldata]
            [reagent-material-ui.core :as ui]
            [cljs-time.core :as time]))

; FOR THE UPCOMING EVENTS PAGE

;(defn add-month []
;  (time/plus (time/now) (time/months 1)))

(defn print-events []
 (for [item (htmldata/get-printing)]
     ^{:key item}  [ui/Card
                    [ui/CardHeader {:titleColor (:titleColor item) :title [(:title item) " (" (:speaker item) ")"]
                                    :showExpandableButton true
                                    :subtitle [(:location item) " - " (:date item)]}]
                    [ui/CardText {:expandable true}
                      (:topic item) [:br][:br] (:summary item)]]))
