(ns gen-art.Shape.2123
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  {:tile-count 20
   :color 0
   :alpha 180
   :random-seed 0
   :max-distance 500})

(defn update-state [{:keys [tile-count color alpha random-seed max-distance]}]
  {:tile-count tile-count
   :color color
   :alpha alpha
   :random-seed random-seed
   :max-distance max-distance})

(defn draw-state [{:keys [tile-count color alpha random-seed max-distance]}]
  (q/background 255)
  (q/smooth)
  (q/random-seed random-seed)
  (q/stroke color alpha)
  (q/stroke 3)

  (doseq [grid-x (take-nth 25 (range (q/width)))
          grid-y (take-nth 25 (range (q/height)))
          :let [diameter (* (/ (q/dist (q/mouse-x) (q/mouse-y) grid-x grid-y) max-distance) 50)]]
    (q/fill (q/random 0 255) (q/random 0 255) (q/random 0 255))
    (q/push-matrix)
    (q/translate grid-x grid-y (* diameter 5))
    (q/ellipse 0 0 diameter diameter)
    (q/pop-matrix)))

(q/defsketch gen-art
  :title "Grid 3"
  :size [1000 1000]
  :renderer :p3d
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-bind-output]
  :middleware [m/fun-mode])

