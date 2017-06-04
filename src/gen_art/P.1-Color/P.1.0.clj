(ns gen-art.P.1-Color.P.1.0
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  (q/rect-mode :center)
  (q/no-cursor)
  (q/frame-rate 30)
  (q/color-mode :hsb 360 100 100)
  (q/no-stroke)
  {:state 1})

(defn update-state [state]
  ;; we could read vals here
  state)

(defn draw-state [state]
   (q/background (/ (q/mouse-y) 2) 100 100)
  (q/fill (/ (- 360 (q/mouse-y)) 2) 100 100)
  (q/rect 360 360 (+ (q/mouse-x) 1) (+ (q/mouse-x) 1))
  )

(q/defsketch gen-art
  :title "Hello, color"
  :size [720 720]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode m/pause-on-error])
