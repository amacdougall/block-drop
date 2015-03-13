(ns block-drop.views
  (:require [block-drop.data :as data]
            [om.core :as om :include-macros true]
            [sablono.core :as html :refer-macros [html]]
            [clojure.string :as string]))

;; Returns the supplied string class names, without nils, as a space-separated
;; string suitable for use as a #js className. Class names may already include
;; spaces, naturally; (classes "inner button" "selected") will return "inner
;; button selected".
(defn classes [& cs]
  (string/join " " (remove nil? cs)))


;; A helper which produces a Sablono-like vector. Provide a container in
;; Sablono format, and a vector of children produces by om/build-all.
;;
;; Example:
;; (html-container
;;   [:div {:class 'parent'}] (om/build-all component data))
(defn- html-container [container children]
  (html (vec (concat container children))))

;; A single block in the puzzle grid.
(defn- block
  [data owner]
  (reify
    om/IDisplayName
    (display-name [_] "Block")
    om/IRender
    (render [_]
      (html
        [:div {:class (classes "block animated fadeInDown" (name (:color data)))}]))))

;; A column of blocks in the puzzle grid.
(defn- column [blocks owner]
  (reify
    om/IDisplayName
    (display-name [_] "Column")
    om/IRender
    (render [_]
      (html-container
        [:div {:class "column" :on-click #(data/place-block blocks)}]
        (om/build-all block blocks)))))

;; The entire puzzle grid, containing data/column-count columns.
(defn- grid [columns owner]
  (reify
    om/IDisplayName
    (display-name [_] "Grid")
    om/IRender
    (render [_]
      (html-container
        [:div {:class "grid"}]
        (om/build-all column columns)))))

;; The root application component.
(defn main [app owner]
  (reify
    om/IDisplayName
    (display-name [_] "Main")
    om/IRender
    (render [_]
      (html
        [:div {:class "main"}
         [:h1 "BLOCK DROP"]
         (if (data/game-over?)
           [:div {:class "win-screen"}
            [:h2 "YOU WON THE GAME"]
            [:h3 "Play Again!?"]
            [:button {:class "reset-button" :on-click data/reset} "OBVIOUSLY"]]
           (om/build grid (:columns app)))
         [:div {:class "instructions"}
          "Click to drop blocks. ...that's basically it."]]))))
