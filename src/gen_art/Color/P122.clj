(ns gen-art.Color.P122
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  ;; (def img (q/load-image "a.jpg"))
  ;; (q/no-cursor)
  ;; (q/no-stroke)
  ;; (q/color-mode :hsb 360 100 100)
  ;; (q/frame-rate 30)
  )

(defn update-state [state] state)

(defn draw-state [state]
  (q/background 0)
  (let [img (q/load-image "a.jpg")]
    (println (.toString img)))
  ;; (let [tile-count (/ (q/width) (max (q/mouse-x) 5))
  ;;       rect-size (/ (q/width) tile-count)])
  )

(q/defsketch gen-art
  :title "Color palettes from images"
  :size [600 600]
  :setup setup
  :draw draw-state
  ;; We need no-bind-output not to break things, this also allows us to write to std out in cider
  :features [:no-bind-output]
  :middleware [m/fun-mode])
