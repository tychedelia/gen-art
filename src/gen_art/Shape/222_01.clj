;; P.2.2.2.01:
;;
;; in update:
;;
;; in a seq of mouse-x length, we calculate a "point", including whether it has reached a border
;; or not.
;;
;; in draw:
;;
;; we process the points to draw the lines.

(ns gen-art.Shape.222-01
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [gen-art.util :as util]))

::north
::east
::south
::west

(def angle-count 7)
(def step-size 3)
(def min-length 10)
(def init-y 5)
(def init-x 0)

(defn get-random-angle [direction]
  (let [a (* (+ (q/floor (q/random (- angle-count) angle-count)) 0.5) (/ 90 angle-count))]
    (case direction
      ::north (- a 90)
      ::east a
      ::south (+ a 90)
      ::west (+ a 180)
      :else 0)))

(defn setup []
  (q/frame-rate 1)
  (q/color-mode :hsb 360 100 100 100)
  (q/smooth)
  (q/background 360)

  [{:direction ::south
    :angle (get-random-angle ::south)
    :x init-x
    :y init-y
    :cross-x init-x
    :cross-y init-y}])

(defn update-cross
  [{:keys [cross border x y] :as pos}]
  ;;
  (if (or cross border)
    (assoc pos
           :cross-x x
           :cross-y y)
    pos))

(defn step-position
  ;;
  [{:keys [x y angle] :as pos}]
  (assoc pos
         :x (+ x (* (q/cos (q/radians angle)) step-size))
         :y (+ y (* (q/sin (q/radians angle)) step-size))))

(defn check-border
  ;;
  [{:keys [x y direction] :as pos}]
  (let [[border direction]
         (cond
           (<= y 5) [true ::south]
           (>= x (- (q/width) 5)) [true ::west]
           (>= y (- (q/height) 5)) [true ::north]
           (<=  x 5) [true ::east]
           :else [false direction])]
    (assoc pos
           :border border
           :direction direction)))

(defn check-cross
  ;;
  [{:keys [x y] :as pos}]
  (let [px (int x)
        py (int y)
        cross (not= (q/get-pixel px py) (q/color 360))]
    (assoc pos
           :cross cross)))

(defn check-angle [{:keys [border cross direction] :as pos}]
  (if (or border cross)
    (assoc pos :angle (get-random-angle direction))
    pos))


(defn make-step
  ;; compute a incremental step to x and y
  ;; angle is not mutated
  [pos]
  (let [pos (step-position pos)
        pos (check-border pos)
        pos (check-cross pos)
        pos (check-angle pos)]
    pos))

(defn do-pos [pos]
  (let [pos (update-cross pos)
        pos (make-step pos)]
    pos))

(defn positions [pos vals len]
  (if (<= len (count vals))
    vals
    (let [pos (do-pos pos)]
      (positions pos (cons pos vals) len))))

(defn update-state [[pos]]
  (positions pos [pos] (q/mouse-x)))

(defn draw-state [xs]
  (doseq [{:keys [x y cross-x cross-y]} xs]
    (let [d (q/dist x y cross-x cross-y)]
      (if (>= d min-length)
        (do (q/stroke-weight 3)
            (q/stroke 0)
            (q/line x y cross-x cross-y))
        )
      )
    ))

(q/defsketch gen-art
  :title "Dumb agents"
  :size [800 800]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-bind-output]
  :middleware [m/fun-mode])


