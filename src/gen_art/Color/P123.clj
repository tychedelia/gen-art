(ns gen-art.Color.P123
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def tile-count-x 50)
(def tile-count-y 10)

(defn setup []
  (q/color-mode :hsb 360 100 100 100)
  (q/no-stroke)
  (let [hue-values        (map (partial q/random 0 360) (range tile-count-x))
        saturation-values (map (partial q/random 0 100) (range tile-count-x))
        brightness-values (map (partial q/random 0 100) (range tile-count-x))]
    {:h hue-values
     :s saturation-values
     :b brightness-values}))

(defn update-state [state])

(defn draw-state [state]
  (q/background 0 0 100)
  (println (:h state))
  )

(q/defsketch gen-art
  :title "Color palettes from rules"
  :size [600 600]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-bind-output]
  :middleware [m/fun-mode])
