(ns gen-art.Shape.212
(:require [quil.core :as q]
          [quil.middleware :as m]))

(defn setup []
  {:random-seed 0
   :circle-color 0
   :circle-alpha 180
   :tile-count 20})

(defn update-state [{:keys [random-seed circle-color circle-alpha tile-count]}]
  {:random-seed random-seed
   :circle-color circle-color
   :circle-alpha circle-alpha
   :tile-count tile-count})

(defn draw-state [{:keys [random-seed circle-color circle-alpha tile-count]}]
  (q/background 255)
  (q/smooth)
  (q/no-fill)
  (q/random-seed random-seed)
  (q/stroke circle-color circle-alpha)
  (q/stroke-weight (/ (q/mouse-y) 60))
  (doseq [x (range (+ tile-count 1))
          y (range (+ tile-count 1))
          :let [pos-x   (* (/ (q/width) tile-count) x)
                pos-y   (* (/ (q/height) tile-count) y)
                shift-x (/ (q/random (- (q/mouse-x)) (q/mouse-x)) 20)
                shift-y (/ (q/random (- (q/mouse-x)) (q/mouse-x)) 20)]]
    (q/ellipse (+ pos-x shift-x) (+ pos-y shift-y) (/ (q/mouse-y) 15) (/ (q/mouse-y) 15))))

(q/defsketch gen-art
  :title "Movment in a grid"
  :size [720 720]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-bind-output]
  :middleware [m/fun-mode])

