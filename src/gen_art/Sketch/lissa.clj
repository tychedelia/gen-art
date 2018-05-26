(ns gen-art.Sketch.lissa
  (:require [quil.core :as q]
            [quil.middleware :as m]))

;; CONSTANTS
(def settings { :point-count 200
               :freq-x 4
               :freq-y 7
               :mod-freq-x 3
               :mod-freq-y 2
               :line-weight 1
               :line-color 0
               :line-alpha 50
               :connection-radius 80
               :connection-ramp  6 })

;; VARS
(def !phi         (atom 15))

;; UTIL
(defn point [x y] [x y])

;; LIFECYCLE
(defn calc-points [{:keys [point-count freq-x freq-y mod-freq-x mod-freq-y]}]
  (for [i (range point-count)
        :let [angle (q/map-range i 0 point-count 0 q/TWO-PI)
              x     (* (q/sin (+ (q/radians @!phi) (* angle freq-x))) (q/cos (* angle mod-freq-x)))
              y     (* (q/sin (* angle freq-y)) (q/cos (* angle mod-freq-y)))]]
    (point
     (* x (- (/ (q/width) 2 ) 30))
     (* y (- (/ (q/height) 2) 30)))))

(defn update-vars []
  (swap! !phi (fn [x] (q/random 0 360))))

(defn setup []
  (calc-points settings))

(defn update [points]
  (update-vars)
  (calc-points settings))

(defn lissa [{:keys [line-weight point-count connection-radius line-color line-alpha]} points]
  (q/color-mode :rgb)
  (q/background 255)
  (q/stroke-weight line-weight)
  (q/stroke-cap :round)
  (q/no-fill)

  (q/push-matrix)
  (q/translate (/ (q/width) 2) (/ (q/height) 2))

  ;; LOOP
  (doseq [i1 (range point-count)
          i2 (range i1)
          :let [[x1 y1] (nth points i1)
                [x2 y2] (nth points i2)
                d       (q/dist x1 y1 x2 y2)
                a       (q/pow (/ 1 (/ d (+ 1 connection-radius))) 6)]]

    (if (< d connection-radius)
      (do (q/stroke line-color (* a line-alpha))
          (q/line x1 y1 x2 y2)
          (q/pop-matrix)))))

(defn draw [points]
  (lissa settings points))

(q/defsketch gen-art
  :title "Lissajous"
  :size [800 800]
  :setup setup
  :update update
  :draw draw
  :features [:keep-on-top :no-bind-output]
  :middleware [m/fun-mode])



