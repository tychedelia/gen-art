(ns gen-art.Shape.201
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup [])

(defn update-state [state] state)

(defn draw-state [state]
  (q/stroke-cap :square)
  (q/smooth)
  (q/no-fill)
  (q/background 255)
  (q/translate (/ (q/width) 2) (/ (q/height) 2))
  (q/stroke-weight (q/abs (/ (q/mouse-y) 20)))
  (let [circle-resolution (q/map-range (q/mouse-x) 0 (q/height) 2 80)
        radius (+ (- (q/mouse-x) (/ (q/width) 2)) 0.5)
        angle  (/ q/TWO-PI circle-resolution)]
    (q/begin-shape)
    (doseq [i (range circle-resolution)
            :let [x (* (q/cos (* angle i)) radius)
                  y (* (q/sin (* angle i)) radius)]]
      (q/line 0 0 x y))
    (q/end-shape)))

(q/defsketch gen-art
  :title "Hello, shape"
  :size [600 600]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-bind-output]
  :middleware [m/fun-mode])

