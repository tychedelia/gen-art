(ns gen-art.Sketch.lissa-slow
  (:require [quil.core :as q]
            [quil.middleware :as m]))

;; CONSTANTS
(def state
  {:point-count 200
   :freq-x 4
   :freq-y 7
   :mod-freq-x 3
   :mod-freq-y 2
   :line-weight 1
   :line-color 255
   :line-alpha 50
   :phi 15
   :connection-radius 70
   :connection-ramp 6})


;; LIFECYCLE


(defn update-points [{:keys [point-count freq-x freq-y mod-freq-x mod-freq-y phi] :as state}]
  (assoc state
         :points
         (for [i (range point-count)
               :let [angle         (q/map-range i 0 point-count 0 q/TWO-PI)
                     x             (* (q/sin (+ (q/radians phi) (* angle freq-x))) (q/cos (* angle mod-freq-x)))
                     y             (* (q/sin (* angle freq-y)) (q/cos (* angle mod-freq-y)))]]
           [(* x (- (/ (q/width) 2) 30))
            (* y (- (/ (q/height) 2) 30))])))

(defn setup []
  (q/frame-rate 60)
  (let [state (assoc state :points [])]
    (update-points state)))

(defn update-vars [state]
  (assoc state
         :phi (inc (:phi state))
         :mod-freq-x (+ 0.001 (:mod-freq-x state))
         :mod-freq-y (+ 0.001 (:mod-freq-y state))))

(defn update [state]
  (-> state
      (update-vars)
      (update-points)))

(defn lissa [{:keys [line-weight point-count connection-radius line-color line-alpha points]}]
  (q/color-mode :rgb)
  (q/background 0)
  (q/stroke-weight line-weight)
  (q/stroke-cap :round)
  (q/no-fill)

  (q/push-matrix)
  (q/translate (/ (q/width) 2) (/ (q/height) 2))

  ;; LOOP
  (doseq [i1 (range point-count)
          i2 (range i1)
          :let [p1        (nth points i1)
                p2        (nth points i2)
                [[x1 y1]
                 [x2 y2]] [p1 p2]
                d         (q/dist x1 y1 x2 y2)
                a         (q/pow (/ 1 (/ d (+ 1 connection-radius))) 6)]]

    (if (< d connection-radius)
      (do (q/stroke line-color (min Float/MAX_VALUE (* a line-alpha)))
          (q/line x1 y1 x2 y2))))
  (q/pop-matrix))

(defn draw [state]
  (lissa state))

(q/defsketch gen-art
  :title "Lissajous"
  :size [800 800]
  :setup setup
  :update update
  :draw draw
  :features [:keep-on-top :no-bind-output]
  :middleware [m/fun-mode])
