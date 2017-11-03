(ns gen-art.Shape.2002
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup [])

(defn update-state [state] state)

(defn draw-state [state]
  (if (q/mouse-pressed?)
    (do
      (q/push-matrix)
      (q/translate ( / (q/width) 2) (/ (q/height) 2)))
    (let [circ-res (q/map-range (+ (q/mouse-y) 100) 0 (q/height) 2 10)
          radius   (+ (- (q/mouse-x) (/ (q/width) 2)) 0.5)
          angle    (/ q/TWO-PI circ-res)]
      (q/stroke-weight 2)
      (q/stroke 0 25)
      (q/begin-shape)
      (doseq [i (range circ-res)
              :let [x (* (q/cos (+ angle i)) radius)
                    y (* (q/sin(+ angle i)) radius)]]
        (q/vertex x y)
        )
      (q/end-shape)
      (q/pop-matrix))))


(q/defsketch gen-art
  :title "Hello, shape"
  :size [720 720]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-bind-output]
  :middleware [m/fun-mode])

