(ns gen-art.Color.P122
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn path [x] (clojure.java.io/as-relative-path x))

(def img (q/load-image (path "./resources/a.jpg")))

(defn setup []
  (q/no-cursor)
  (q/no-stroke)
  (q/color-mode :hsb 360 100 100)
  (q/frame-rate 30))

(defn update-state [])

(defn draw-state []
  (let [tile-count (/ (q/width) (max (q/mouse-x) 5))
        rect-size (/ (q/width) tile-count)]

    )

(q/defsketch gen-art
  :title "Color palettes from images"
  :size [600 600]
  :setup setup
  :draw: draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])
