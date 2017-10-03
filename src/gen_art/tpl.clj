(ns gen-art.tpl
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup [])

(defn update-state [state] state)

(defn draw-state [state])

(q/defsketch gen-art
  :title "quil template"
  :size [600 600]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-bind-output]
  :middleware [m/fun-mode])
