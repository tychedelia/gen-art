(ns gen-art.P.1-Color.P.1.2.1
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 1)
  (q/color-mode :hsb 360 100 100))

(defn update-state [state])

(defn rand-color-right [x]
  (q/color (q/random 160 190) 100 (q/random 0 100)))

(defn rand-color-left [x]
  (q/color (q/random 0 60) (q/random 0 100) 100))

(defn draw-state [state ]
  (let [tile-count-x (q/map-range (q/mouse-x) 0 (q/width) 2 100)
        tile-count-y (q/map-range (q/mouse-y) 0 (q/height) 2 10)
        tile-width (/ (q/width) tile-count-x)
        tile-height (/ (q/height) tile-count-y)
        colors-left (vec (map rand-color-left (range 0 tile-count-y)))
        colors-right (vec (map rand-color-right (range 0 tile-count-y)))
        ]
    (doseq [grid-x (range 0 tile-count-x)
            grid-y (range 0 tile-count-y)
            :let [col1 (get colors-left grid-y 360)
                  col2 (get colors-right grid-y 360)
                  ammount (q/map-range grid-x 0 (- tile-count-x 1) 0 1)
                  inter-col (q/lerp-color col1 col2 ammount)]]
      (q/fill inter-col)
      (let [pos-x (* tile-width grid-x)
            pos-y (* tile-height grid-y)]
        (q/rect pos-x pos-y tile-width tile-height)))
    ))

(q/defsketch gen-art
  :title "Color spectrum in a circle"
  :size [720 720]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode m/pause-on-error])

