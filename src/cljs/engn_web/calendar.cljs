(ns engn-web.calendar
  (:require [reagent.core :as reagent]
            [engn-web.prettyprint :as prettyprint]
            [reagent-material-ui.core :as ui]
            [cljs-time.core :as time]
            [cljs-time.format :as time-format]
            [cljs-time.coerce :as time-coerce]))

(def el reagent/as-element)
(defn icon-span [nme] [ui/FontIcon {:className "material-icons"} nme])
(defn icon [nme] (el [:i.material-icons nme]))
(defn color [nme] (aget ui/colors nme))

(defn calendar []
  [ui/GridList {:cols "7"}
   [ui/GridTile {:title "November 1" :subtitle "Math Event @ 5pm" :titleBackground "rgba(255,0,0,1)"}]
   [ui/GridTile {:title "November 2" :actionIcon (icon "star_outline")}]
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
    [:h1 {:className "home-text"} "Calendar page."]
   [:h2 {:className "home-text font-effect-3d-float"} "November"]
   [calendar]])
