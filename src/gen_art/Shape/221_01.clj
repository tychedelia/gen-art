(ns gen-art.Shape.221-01
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [gen-art.util :as util]))

::north
::north-east
::east
::south-east
::south
::south-west
::west
::north-west

(def step-size 2)

(def diameter 3)

(defn update-direction [direction pos-x pos-y]
  (case direction
    ::north {:pos-x pos-x :pos-y (- pos-y step-size)}
    ::north-east {:pos-x (+ pos-x step-size) :pos-y (- pos-y step-size)}
    ::east {:pos-x (+ pos-x step-size) :pos-y pos-y}
    ::south-east {:pos-x (+ pos-x step-size) :pos-y (+ pos-y step-size)}
    ::south {:pos-x pos-x :pos-y (+ pos-y step-size)}
    ::south-west {:pos-x (- pos-x step-size) :pos-y (+ pos-y step-size)}
    ::west {:pos-x (- pos-x step-size) :pos-y pos-y}
    ::north-west {:pos-x (- pos-x step-size) :pos-y (- pos-y step-size)}))

(defn setup []
  (q/background 255)
  ;; (q/frame-rate 1)
  (q/smooth)
  (q/no-stroke)
  { :pos {:pos-x (/ (q/width) 2) :pos-y (/ (q/height) 2)}})

(defn update-pos [{:keys [pos-x pos-y]}]
  (let [direction (rand-nth [::north ::north-east ::east ::south-east ::south ::south-west ::west ::north-west])
        {pos-x :pos-x pos-y :pos-y} (update-direction direction pos-x pos-y)
        {pos-x :pos-x pos-y :pos-y} (if (> pos-x (q/width)) {:pos-x 0 :pos-y pos-y} {:pos-x pos-x :pos-y pos-y})
        {pos-x :pos-x pos-y :pos-y} (if (< pos-x 0) {:pos-x (q/width) :pos-y pos-y} {:pos-x pos-x :pos-y pos-y})
        {pos-x :pos-x pos-y :pos-y} (if (< pos-y 0) {:pos-x pos-x :pos-y (q/height)} {:pos-x pos-x :pos-y pos-y})
        {pos-x :pos-x pos-y :pos-y} (if (> pos-y (q/height)) {:pos-x pos-x :pos-y 0} {:pos-x pos-x :pos-y pos-y})]

        {:pos-x pos-x :pos-y pos-y}))


(defn positions [{:keys [pos seq len]}]
  (if (= len (count seq))
    (util/rmap pos seq len)
    (let [pos (update-pos pos)
          seq (conj seq pos)]
      (positions (util/rmap pos seq len)))))

(defn update-state [state]
  (positions (assoc state :len (q/mouse-x) :seq [])))

(defn draw-state [{:keys [seq]}]
  (doseq [{pos-x :pos-x pos-y :pos-y} seq]
    (q/fill 0 40)
    (q/ellipse (+ pos-x (/ step-size 2)) (+ pos-y (/ step-size 2)) diameter diameter)))

(q/defsketch gen-art
  :title "Dumb agents"
  :size [800 800]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-bind-output]
  :middleware [m/fun-mode])

