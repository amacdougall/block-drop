(ns block-drop.views
  (:require [om.core :as om :include-macros true]
            [sablono.core :as html :refer-macros [html]]
            [FastClick]))

(defn main [app owner]
  (reify
    om/IDisplayName
    (display-name [_] "Main")
    om/IRender
    (render [_]
      (html [:div
             [:div {:class "header"} "BLOCK DROP"]
             (om/build game app)
             [:div {:class "instructions"}
              "Click to drop blocks. Make rows of the same color."]]))))

(defn- game [app owner]
  (reify
    om/IDisplayName
    (display-name [_] "Game")
    om/IRender
    (render [_]
      (html [:div {:class "game"} "Columns Will Go Here"]))))
