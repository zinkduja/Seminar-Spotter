(ns engn-web.prettyprint
  (:require [clojure.string :as string]
            [engn-web.htmldata :as htmldata]
            [reagent-material-ui.core :as ui]))

; FOR THE UPCOMING EVENTS PAGE

(defn print-events []
 (for [item (htmldata/get-math-atom)]
   ^{:key item} [ui/Card
                 [ui/CardHeader {:title [(:title item) " (" (:speaker item) ")"]
                                 :subtitle [(:location item) " - " (:date item)]
                                 :showExpandableButton true}]
                 [ui/CardText {:expandable true}
                              (:topic item) [:br][:br] (:summary item)]]))
