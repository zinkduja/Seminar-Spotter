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
  [ui/GridList {:cols 7}
   [ui/GridTile {:title "November 26" :titlePosition "top"}
                [:img {:src "https://triumphstrength.net/wp-content/uploads/2017/02/b72aaa11fefe5d19dfb995176b811f53_calendar-clip-art-sundayy-calendar-clipart_172-194.jpeg"}]]
   [ui/GridTile {:title "November 27" :titlePosition "top"}
                [:img {:src "https://thecrystalranch.ca/tcr/wp-content/uploads/2016/01/monday.jpg"}]]
   [ui/GridTile {:title "November 28" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-7615.jpg"}]]
   [ui/GridTile {:title "November 29" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/TN_day-week-calendar-wednesday-7615A.jpg"}]]
   [ui/GridTile {:title "November 30" :titlePosition "top" :actionIcon (icon "star_outline")}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-2.jpg"}]]
   [ui/GridTile {:title "December 1" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-friday-7615.jpg"}]]
   [ui/GridTile {:title "December 2" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-saturday-7615.jpg"}]]
   [ui/GridTile {:title "December 3" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://triumphstrength.net/wp-content/uploads/2017/02/b72aaa11fefe5d19dfb995176b811f53_calendar-clip-art-sundayy-calendar-clipart_172-194.jpeg"}]]
   [ui/GridTile {:title "December 4" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://thecrystalranch.ca/tcr/wp-content/uploads/2016/01/monday.jpg"}]]
   [ui/GridTile {:title "December 5" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-7615.jpg"}]]
   [ui/GridTile {:title "December 6" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/TN_day-week-calendar-wednesday-7615A.jpg"}]]
   [ui/GridTile {:title "December 7" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-2.jpg"}]]
   [ui/GridTile {:title "December 8" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-friday-7615.jpg"}]]
   [ui/GridTile {:title "December 9" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-saturday-7615.jpg"}]]
   [ui/GridTile {:title "December 10" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://triumphstrength.net/wp-content/uploads/2017/02/b72aaa11fefe5d19dfb995176b811f53_calendar-clip-art-sundayy-calendar-clipart_172-194.jpeg"}]]
   [ui/GridTile {:title "December 11" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://thecrystalranch.ca/tcr/wp-content/uploads/2016/01/monday.jpg"}]]
   [ui/GridTile {:title "December 12" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-7615.jpg"}]]
   [ui/GridTile {:title "December 13" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/TN_day-week-calendar-wednesday-7615A.jpg"}]]
   [ui/GridTile {:title "December 14" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-2.jpg"}]]
   [ui/GridTile {:title "December 15" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-friday-7615.jpg"}]]
   [ui/GridTile {:title "December 16" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-saturday-7615.jpg"}]]
   [ui/GridTile {:title "December 17" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://triumphstrength.net/wp-content/uploads/2017/02/b72aaa11fefe5d19dfb995176b811f53_calendar-clip-art-sundayy-calendar-clipart_172-194.jpeg"}]]
   [ui/GridTile {:title "December 18" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://thecrystalranch.ca/tcr/wp-content/uploads/2016/01/monday.jpg"}]]
   [ui/GridTile {:title "December 19" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-7615.jpg"}]]
   [ui/GridTile {:title "December 20" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/TN_day-week-calendar-wednesday-7615A.jpg"}]]
   [ui/GridTile {:title "December 21" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-2.jpg"}]]
   [ui/GridTile {:title "December 22" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-friday-7615.jpg"}]]
   [ui/GridTile {:title "December 23" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-saturday-7615.jpg"}]]
   [ui/GridTile {:title "December 24" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://triumphstrength.net/wp-content/uploads/2017/02/b72aaa11fefe5d19dfb995176b811f53_calendar-clip-art-sundayy-calendar-clipart_172-194.jpeg"}]]
   [ui/GridTile {:title "December 25" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://thecrystalranch.ca/tcr/wp-content/uploads/2016/01/monday.jpg"}]]
   [ui/GridTile {:title "December 26" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-7615.jpg"}]]
   [ui/GridTile {:title "December 27" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/TN_day-week-calendar-wednesday-7615A.jpg"}]]
   [ui/GridTile {:title "December 28" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-2.jpg"}]]
   [ui/GridTile {:title "December 29" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-friday-7615.jpg"}]]
   [ui/GridTile {:title "December 30" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-saturday-7615.jpg"}]]
   [ui/GridTile {:title "December 31" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://triumphstrength.net/wp-content/uploads/2017/02/b72aaa11fefe5d19dfb995176b811f53_calendar-clip-art-sundayy-calendar-clipart_172-194.jpeg"}]]])

(defn calendar-page []
  [:div
    [:h1 {:className "home-text"} "Calendar page."]
   [:h1 {:className "home-text font-effect-3d-float"} "December"]
   [calendar]])
