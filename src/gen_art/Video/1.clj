(ns gen-art.Video.1
  (:require [quil.core :as q]
            [quil.middleware :as m])
  (:import [processing.video Capture]))

(defn setup []
  (q/fill 255 255 255 90)
  (q/no-stroke)
  (q/frame-rate 1)
  (println "Initializing Camera...")
  (let [camera (Capture. (quil.applet/current-applet))]
    (.start camera)
    {:camera camera}))

(defn update-state [state]
  (println (:camera state))
  (if (.available (:camera state))
    (do
      (println "avail")
      (.read (:camera state))
      (.loadPixels (:camera state))
      (assoc state :pixels (.pixels (:camera state))))
    state))

(defn draw-state [state]
  (q/background 2 89 15)
  (q/image (:camera state) 100 100)
  )

(q/defsketch videotest
  :size [1280 720]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-bind-output :keep-on-top]
  :middleware [m/fun-mode])
