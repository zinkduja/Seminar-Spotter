(ns engn-web.calendar
  (:require [engn-web.prettyprint :as prettyprint]
            [reagent-material-ui.core :as ui]))


(defn calendar []
  [ui/GridList {:cols "7"}
   [ui/GridTile {:title "November 1" :subtitle "Math Event @ 5pm" :titleBackground "rgba(255,0,0,1)" :height ".5"}]
   [ui/GridTile {:title "November 2"}]
   [ui/GridTile {:title "November 3"}]
   [ui/GridTile {:title "November 4"}]
   [ui/GridTile {:title "November 5"}]
   [ui/GridTile {:title "November 6"}]
   [ui/GridTile {:title "November 7"}]
   [ui/GridTile {:title "November 8"}]
   [ui/GridTile {:title "November 9"}]
   [ui/GridTile {:title "November 10"}]
   [ui/GridTile {:title "November 11"}]
   [ui/GridTile {:title "November 12"}]
   [ui/GridTile {:title "November 13"}]
   [ui/GridTile {:title "November 14"}]
   [ui/GridTile {:title "November 15"}]
   [ui/GridTile {:title "November 16"}]
   [ui/GridTile {:title "November 17"}]
   [ui/GridTile {:title "November 18"}]
   [ui/GridTile {:title "November 19"}]
   [ui/GridTile {:title "November 20"}]
   [ui/GridTile {:title "November 21"}]
   [ui/GridTile {:title "November 22"}]
   [ui/GridTile {:title "November 23"}]])



(defn calendar-page []
  [:div
    [:h1 "Calendar page."]
   [calendar]])
