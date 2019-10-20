(ns gen-art.Shape.223-01
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [gen-art.util :as util]))

(def form-resolution 15)
(def step-size 2)
(def distortion-factor 1)
(def init-radius 150)

(defn setup []
  (q/stroke 0 50)
  (q/background 255)
  (q/stroke-weight 0.75)

  (let [state {:x (float-array form-resolution)
               :y (float-array form-resolution)
               :center-x 0
               :center-y 0
               :filled false}
        angle (q/radians (/ 360 (float form-resolution)))]
    (doseq [i (range form-resolution)]
      (aset (:x state) i (float (* (q/cos (* angle i)) init-radius)))
      (aset (:y state) i (float (* (q/sin (* angle i)) init-radius))))
    state))

(defn update-center [{:keys [center-x center-y] :as state}]
  (if (or (not= (q/mouse-x) 0) (not= (q/mouse-y) 0))
    (assoc state
           :center-x (+ center-x (* (- (q/mouse-x) center-x) 0.01))
           :center-y (+ center-y (* (- (q/mouse-y) center-y) 0.01)))
    state))

(defn update-points [{:keys [x y] :as state}]
  (doseq [i (range form-resolution)]
    (aset x i (float (+ (aget x i) (q/random (- step-size) step-size))))
    (aset y i (float (+ (aget y i) (q/random (- step-size) step-size)))))
  state)

(defn update-mouse-press [state]
  (if (q/mouse-pressed?)
    (do
      (doseq [i (range form-resolution)
              :let [angle (q/radians (/ 360 (float form-resolution)))
                    radius (* init-radius (q/random 0.5 1.0))]]
        (aset (:x state) i (float (* (q/cos (* angle i)) radius)))
        (aset (:y state) i (float (* (q/sin (* angle i)) radius))))
      (assoc state
             :center-x (q/mouse-x)
             :center-y (q/mouse-y))))
    state)

(defn update-state [state]
  (-> state
      (update-center)
      (update-points)
      (update-mouse-press)))

(defn draw-state [{:keys [x y center-x center-y filled]}]
  (if filled
    (q/fill (q/random 255))
    (q/no-fill))

  (q/begin-shape)
  (q/curve-vertex (+ (aget x (- form-resolution 1)) center-x) (+ (aget y (- form-resolution 1)) center-y))

  (doseq [i (range form-resolution)]
    (q/curve-vertex (+ (aget x i) center-x) (+ (aget y i) center-y)))

  (q/curve-vertex (+ (aget x 0) center-x) (+ (aget y 0) center-y))
  (q/curve-vertex (+ (aget x 1) center-x) (+ (aget y 1) center-y))
  (q/end-shape))

(q/defsketch gen-art
  :title "Morphing agents"
  :size [800 800]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-bind-output]
  :middleware [m/fun-mode])


