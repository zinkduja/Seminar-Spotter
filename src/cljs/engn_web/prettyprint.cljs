(ns engn-web.prettyprint
  (:require [clojure.string :as string]
            [engn-web.htmldata :as html_data]
            [reagent-material-ui.core :as ui]))

(defn print-events []
 (for [item (html_data/get-math-atom)]
   [ui/Card
    [ui/CardHeader {:title [(:title item) " (" (:speaker item) ")"]
                    :subtitle [(:location item) " - " (:date item)]
                    :showExpandableButton true}]
    [ui/CardText {:expandable true}
                 (:topic item) [:br][:br] (:summary item)]]))
