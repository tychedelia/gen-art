(ns gen-art.P.1-Color.P.1.1.1
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  (q/background 0)
  (q/frame-rate 30)
  (q/color-mode :hsb (q/width) (q/height) 100)
  {:x 2
   :y 2})

(defn update-state [state]
  {:x (+ (q/mouse-x) 2) :y (+ (q/mouse-y) 2)})

(defn print-state [state]
  (q/text-size 20)
  (q/text (str state) 20 20))

(defn draw-state [state]
  (q/no-stroke)
  (doseq
      [grid-x (range 0 (q/height) (:x state))
       grid-y (range 0 (q/width)  (:y state))]
    (q/fill grid-x (- (q/height) grid-y) 100)
    (q/rect grid-x grid-y (:x state) (:y state))
    )
  (print-state state))


(q/defsketch gen-art
  :title "Color spectrum in a grid"
  :size [1000 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode m/pause-on-error])



