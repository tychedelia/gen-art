(ns gen-art.Sketch.1
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [clojure.core.match :refer [match]]))

(def background-colors
  [[255 255 0   0.25]
   [0   255 0   0.25]
   [0   0   255 0.25]
   [255 0   0   0.25]
   [0   255 255 0.25]
   [255 0   255 0.25]])

(defn set-gradient [x y w h c1 c2 axis]
  (match [axis]
         [:y-axis]
         (doseq [i (range y h)
                 :let [inter (q/map-range i y (+ y h) 0 1)
                       c     (q/lerp-color c1 c2 inter)]]
           (q/stroke (* c 10))
           (q/line x i (+ x w) i))
         [:x-axis]
         (doseq [i (range x w)
                 :let [inter (q/map-range i x (+ x w) 0 1)
                       c     (q/lerp-color c1 c2 inter)]]
           (q/stroke c)
           (q/line i y i (+ y h)))))


(defn inc-wrap [n]
  (let [num (if (= 0 (Math/round (Math/random)))
               (+ n (Math/floor (* (Math/random) 100)))
               (- n (Math/floor (* (Math/random) 100))))]
    (cond
      (> num 254) 254
      (<= num 0)  1
      (or (< num 255) (>= 0 num)) num
      :else (inc-wrap num))))

(defn mutate [[r g b a]]
  (let [i (int (+ (Math/floor (* (Math/random) 3)) 1))]
  (case i
    1 [(inc-wrap r) g b a]
    2 [r (inc-wrap g) b a]
    3 [r g (inc-wrap b) a])))

(defn setup []
  (q/frame-rate 25)
  {:bg-color (rand-nth background-colors)
   :mutator  (rand-nth background-colors)})

(defn update-state [state]
  {:bg-color (mutate (:bg-color state))
   :mutator (mutate (:mutator state))})

(defn draw-state [state]
  (let [[r1 g1 b1 a1] (:bg-color state)
        [r2 g2 b2 a2] (:mutator state)
        c1 (q/color r1 g1 b1 a1)
        c2 (q/color r2 g2 b2 a2)]
    (q/no-fill)
    ;; (q/background c2)
    (q/stroke-weight 10)
    (set-gradient 0 0 (q/width) (q/height) c1 c2 :y-axis)))

(q/defsketch gen-art
  :title "Hello, shape"
  :size [1000 1000]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-bind-output]
  :middleware [m/fun-mode])




