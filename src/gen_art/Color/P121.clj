(ns gen-art.Color.P121
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 30)
  (q/stroke-weight 0)
  (q/no-stroke)
  (q/color-mode :hsb 360 100 100))

(defn update-state [state])

(defn rand-color-right [x]
  (q/color (q/random 0 1090) 190 (q/random 0 100)))

(defn rand-color-left [x]
  (q/color (q/random 0 60) (q/random 0 100) 255))

(defn draw-state [state ]
  (let [tile-count-x (q/map-range (q/mouse-x) 0 (q/width) 2 50)
        tile-count-y (q/map-range (q/mouse-y) 0 (q/height) 2 10)
        tile-width (/ (q/width) tile-count-x)
        tile-height (/ (q/height) tile-count-y)
        colors-left (vec (map rand-color-left (range 0 tile-count-y)))
        colors-right (vec (map rand-color-right (range 0 tile-count-y)))]
    (doseq [grid-x (range 0 tile-count-x)
            grid-y (range 0 tile-count-y)
            :let [col1 (get colors-left grid-y 360)
                  col2 (get colors-right grid-y 360)
                  ammount (q/map-range grid-x 0 (- tile-count-x 1) 0 1)
                  inter-col (q/lerp-color col1 col2 ammount)]]
      (q/fill inter-col)
      (let [pos-x (* tile-width grid-x)
            pos-y (* tile-height grid-y)]
        (q/rect pos-x pos-y tile-width tile-height)))))

(q/defsketch gen-art
  :title "hue, saturation..."
  :size [1920 720]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-bind-output :keep-on-top]
  :middleware [m/fun-mode m/pause-on-error])

