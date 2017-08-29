(ns gen-art.Color.P122
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [gen-art.util :as u]))

(defn setup []
  (q/no-cursor)
  (q/no-stroke)
  (q/color-mode :hsb 360 100 100)
  (q/frame-rate 10)
  {:img (q/load-image "a.jpg")})

(defn update-state [state]
  (let [img        (:img state)
        tile-count (/ (q/width) (max (q/mouse-x) 5))
        rect-size  (/ (q/width) tile-count)
        colors  (if (= rect-size (:rect-size state))
                  (:colors state)
                  (for [grid-x (range tile-count)
                        grid-y (range tile-count)
                        :let [px (* grid-x rect-size)
                              py (* grid-y rect-size)]]
                    (q/get-pixel img px py)))
        ]
    (assoc state
           :tile-count tile-count
           :rect-size rect-size
           :colors colors)))

(defn draw-state [state]
  (let [{ tile-count  :tile-count
          rect-size   :rect-size
          colors      :colors } state]
    (doseq [grid-x (range tile-count)
            grid-y (range tile-count)
            ]
      (q/fill (q/random 0 255))
      (q/rect (* grid-x rect-size) (* grid-y rect-size) rect-size rect-size)
      (u/print-state (q/mouse-x))
      )))

(q/defsketch gen-art
  :title "Color palettes from images"
  :size [600 600]
  :setup setup
  :update update-state
  :draw draw-state
  ;; We need no-bind-output not to break things, this also allows us to write to std out in cider
  :features [:no-bind-output]
  :middleware [m/fun-mode])
