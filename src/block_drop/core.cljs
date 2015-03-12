(ns block-drop.core
  (:require [block-drop.views :as views]
            [om.core :as om :include-macros true]
            [cljs.core.async :refer [>! <! chan put! timeout alts!]]
            [FastClick])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))

; everything in this file will be re-run every time the app reloads, so
; idempotency is a must.

(enable-console-print!)

(def column-count 4)

;; Abstract color names to be styled in CSS.
(def colors [:alpha :beta :gamma :delta])

(defonce app-state
  (atom
    {:columns (map (constantly []) (range column-count))}))

(.attach js/FastClick (.-body js/document))

(om/root views/main app-state
  {:target (. js/document (getElementById "app"))})
