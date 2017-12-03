(ns gen-art.Shape.211
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  {:tile-count 20})

;; {:tile-count (q/random 20)}
(defn update-state [state] state)

(defn draw-state [{:keys [tile-count]}]
  (q/background 255)
  (q/smooth)
  (q/no-fill)
  (q/stroke-cap :round)
  (q/random-seed 0)

  (doseq [x (range tile-count)
          y (range tile-count)
          :let [pos-x  (* (/ (q/width) tile-count) x)
                pos-y  (* (/ (q/height) tile-count) y)
                toggle (= 1 (q/random 0 2))]]
    (if toggle
      (do
        (q/stroke-weight (/ (q/mouse-x) 20))
        (q/line pos-x pos-y (+ pos-x (/ (q/width) tile-count)) (+ pos-y (/ (q/height) tile-count))))
      (do
        (q/stroke-weight (/ (q/mouse-y) 20))
        (q/line pos-x (+ pos-y (/ (q/width) tile-count)) (+ pos-x (/ (q/height) tile-count)) pos-y)))))

(q/defsketch gen-art
  :title "Alignment in a grid"
  :size [600 600]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-bind-output]
  :middleware [m/fun-mode])
