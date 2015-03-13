(ns block-drop.data
  (:require [om.core :as om :include-macros true]
            [cljs.core.async :refer [>! <! chan put! timeout alts!]]))


(def column-count 4)
(def row-count 4)

;; Abstract color names to be styled in CSS.
(def colors [:alpha :beta :gamma :delta])

;; Returns a block of a random color.
(defn random-block []
  {:color (rand-nth colors)})

;; Returns a grid of blank columns.
(defn blank-columns []
  (mapv (constantly []) (range column-count)))

(defonce app-state
  (atom
    {:columns (blank-columns)
     :on-deck (random-block)}))

;; Returns a reference cursor for the app state. Pure convenience.
(defn app-cursor []
  (om/ref-cursor (om/root-cursor app-state)))

;; True if the supplied column is full.
(defn column-full? [column]
  (>= (count column) row-count))

;; True if the user has filled every column.
(defn game-over? []
  (empty? (remove column-full? (:columns @app-state))))

(defn reset []
  (om/update! (app-cursor) [:columns] (blank-columns)))

;; Adds the supplied block to the supplied blocks cursor.
(defn place-block [column]
  (when-not (column-full? column)
    (let [block (:on-deck @app-state)
          next-block (random-block)]
      (om/transact! column #(conj % block))
      (om/transact! (app-cursor) #(assoc % :on-deck next-block)))))

