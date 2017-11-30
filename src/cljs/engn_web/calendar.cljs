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
   [ui/GridTile {:title "October 29" :titlePosition "top"}
                [:img {:src "https://triumphstrength.net/wp-content/uploads/2017/02/b72aaa11fefe5d19dfb995176b811f53_calendar-clip-art-sundayy-calendar-clipart_172-194.jpeg"}]]
   [ui/GridTile {:title "October 30" :titlePosition "top"}
                [:img {:src "https://thecrystalranch.ca/tcr/wp-content/uploads/2016/01/monday.jpg"}]]
   [ui/GridTile {:title "October 31" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-7615.jpg"}]]
   [ui/GridTile {:title "November 1" :subtitle "Math Event @ 5pm" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/TN_day-week-calendar-wednesday-7615A.jpg"}]]
   [ui/GridTile {:title "November 2" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-2.jpg"}]]
   [ui/GridTile {:title "November 3" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-friday-7615.jpg"}]]
   [ui/GridTile {:title "November 4" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-saturday-7615.jpg"}]]
   [ui/GridTile {:title "November 5" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://triumphstrength.net/wp-content/uploads/2017/02/b72aaa11fefe5d19dfb995176b811f53_calendar-clip-art-sundayy-calendar-clipart_172-194.jpeg"}]]
   [ui/GridTile {:title "November 6" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://thecrystalranch.ca/tcr/wp-content/uploads/2016/01/monday.jpg"}]]
   [ui/GridTile {:title "November 7" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-7615.jpg"}]]
   [ui/GridTile {:title "November 8" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/TN_day-week-calendar-wednesday-7615A.jpg"}]]
   [ui/GridTile {:title "November 9" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-2.jpg"}]]
   [ui/GridTile {:title "November 10" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-friday-7615.jpg"}]]
   [ui/GridTile {:title "November 11" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-saturday-7615.jpg"}]]
   [ui/GridTile {:title "November 12" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://triumphstrength.net/wp-content/uploads/2017/02/b72aaa11fefe5d19dfb995176b811f53_calendar-clip-art-sundayy-calendar-clipart_172-194.jpeg"}]]
   [ui/GridTile {:title "November 13" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://thecrystalranch.ca/tcr/wp-content/uploads/2016/01/monday.jpg"}]]
   [ui/GridTile {:title "November 14" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-7615.jpg"}]]
   [ui/GridTile {:title "November 15" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/TN_day-week-calendar-wednesday-7615A.jpg"}]]
   [ui/GridTile {:title "November 16" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-2.jpg"}]]
   [ui/GridTile {:title "November 17" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-friday-7615.jpg"}]]
   [ui/GridTile {:title "November 18" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-saturday-7615.jpg"}]]
   [ui/GridTile {:title "November 19" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://triumphstrength.net/wp-content/uploads/2017/02/b72aaa11fefe5d19dfb995176b811f53_calendar-clip-art-sundayy-calendar-clipart_172-194.jpeg"}]]
   [ui/GridTile {:title "November 20" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://thecrystalranch.ca/tcr/wp-content/uploads/2016/01/monday.jpg"}]]
   [ui/GridTile {:title "November 21" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-7615.jpg"}]]
   [ui/GridTile {:title "November 22" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/TN_day-week-calendar-wednesday-7615A.jpg"}]]
   [ui/GridTile {:title "November 23" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-2.jpg"}]]
   [ui/GridTile {:title "November 24" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-friday-7615.jpg"}]]
   [ui/GridTile {:title "November 25" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-saturday-7615.jpg"}]]
   [ui/GridTile {:title "November 26" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://triumphstrength.net/wp-content/uploads/2017/02/b72aaa11fefe5d19dfb995176b811f53_calendar-clip-art-sundayy-calendar-clipart_172-194.jpeg"}]]
   [ui/GridTile {:title "November 27" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://thecrystalranch.ca/tcr/wp-content/uploads/2016/01/monday.jpg"}]]
   [ui/GridTile {:title "November 28" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-7615.jpg"}]]
   [ui/GridTile {:title "November 29" :titleBackground "rgba(255,0,0,1)" :titlePosition "top"}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/TN_day-week-calendar-wednesday-7615A.jpg"}]]
   [ui/GridTile {:title "November 30" :titleBackground "rgba(255,0,0,1)" :titlePosition "top" :actionIcon (icon "star_outline")}
                [:img {:src "https://classroomclipart.com/images/gallery/Clipart/Calendar/day-week-calendar-thursday-2.jpg"}]]])

(defn calendar-page []
  [:div
    [:h1 {:className "home-text"} "Calendar page."]
   [:h1 {:className "home-text font-effect-3d-float"} "November"]
   [calendar]])
