(ns gen-art.Color.P123
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def tile-count-x 50)
(def tile-count-y 10)

(defn setup []
  (q/color-mode :hsb 360 100 100 100)
  (q/no-stroke)
  (let [hue-values        (mapv (fn [& _] (rand-int 360)) (range tile-count-x))
        saturation-values (mapv (fn [& _] (rand-int 100)) (range tile-count-x))
        brightness-values (mapv (fn [& _] (rand-int 100)) (range tile-count-x))]
    (println hue-values)
    {:h hue-values
     :s saturation-values
     :b brightness-values}))

(defn update-state [state] state)

(defn draw-state [state]
  (q/background 0 0 100)
  (let [current-tile-x (q/map-range (q/mouse-x) 0 (q/width)  1 tile-count-x)
        current-tile-y (q/map-range (q/mouse-y) 0 (q/height) 1 tile-count-y)
        tile-width  (/ (q/width)  current-tile-x)
        tile-height (/ (q/height) current-tile-y)
       !i           (atom 0)]
  (doseq [x (range tile-count-x)
          y (range tile-count-y)
          :let [pos-x (* tile-width  x)
                pos-y (* tile-height y)]]
    (q/fill (nth (:h state) @!i) (nth (:s state) @!i) (nth (:b state) @!i))
    (q/rect pos-x pos-y tile-width tile-height)
    (swap! !i inc))))

(q/defsketch gen-art
  :title "Color palettes from rules"
  :size [600 600]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-bind-output]
  :middleware [m/fun-mode])
