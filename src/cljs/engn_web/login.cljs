(ns engn-web.login)
(in-ns 'engn-web.core)

(defn login-page []
  [ui/MuiThemeProvider
    [:div
      [:h1 "Login page."]]])
