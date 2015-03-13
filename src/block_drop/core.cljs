(ns ^:figwheel-always block-drop.core
  (:require [block-drop.views :as views]
            [block-drop.data :as data]
            [om.core :as om :include-macros true]
            [FastClick])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))

; everything in this file will be re-run every time the app reloads, so
; idempotency is a must.

(enable-console-print!)

(.attach js/FastClick (.-body js/document))

(defn main []
  (om/root views/main data/app-state
    {:target (. js/document (getElementById "app"))}))

(main)
