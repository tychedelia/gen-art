(ns gen-art.P.1-Color.P.1.1.2
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsb 360 (q/width) (q/height))
  7)

(defn update-state [state]
  state)

(defn draw-state [state]
  (q/background 360)
  (let [angle-step (/ 360 8)
        radius 300]
    (q/begin-shape :triangle-fan)
    (q/vertex (/ (q/width) 2) (/ (q/height) 2))
    (doseq [angle (range 0 360 angle-step)
            :let [vx (+ (/ (q/width) 2) (* (q/cos (q/radians angle)) radius))
                  vy (+ (/ (q/height) 2) (* (q/sin (q/radians angle)) radius))]]
      (q/vertex vx vy)
      (q/fill angle (q/mouse-x) (q/mouse-y))
      )
    (q/end-shape)))

(q/defsketch gen-art
  :title "Color spectrum in a circle"
  :size [720 720]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode m/pause-on-error])
