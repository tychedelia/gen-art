(ns gen-art.Shape.211
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  {:tile-count 20 :seed 0 :stroke-cap :round})

;; {:tile-count (q/random 20)}
(defn update-state [{:keys [tile-count seed]}]
  {:tile-count tile-count
   :seed (if (q/mouse-pressed?) (q/random 100000) seed)
   :stroke-cap :square})

(defn draw-state [{:keys [tile-count seed stroke-cap]}]
  (q/background 255)
  (q/smooth)
  (q/no-fill)
  (q/stroke-cap stroke-cap)
  (q/random-seed seed)

  (doseq [x (range tile-count)
          y (range tile-count)
          :let [pos-x  (* (/ (q/width) tile-count) x)
                pos-y  (* (/ (q/height) tile-count) y)
                toggle (= 1 (int (q/random 0 2)))]]
    (println toggle)
    (if toggle
      (do
        (q/stroke-weight (/ (q/mouse-y) 20))
        (q/line pos-x (+ pos-y (/ (q/width) tile-count)) (+ pos-x (/ (q/height) tile-count)) pos-y))
      (do
        (q/stroke-weight (/ (q/mouse-x) 20))
        (q/line pos-x pos-y (+ pos-x (/ (q/width) tile-count)) (+ pos-y (/ (q/height) tile-count)))))))

(q/defsketch gen-art
  :title "Alignment in a grid"
  :size [600 600]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-bind-output]
  :middleware [m/fun-mode])
