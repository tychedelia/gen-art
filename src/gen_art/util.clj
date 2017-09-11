(ns gen-art.util
  (:require [quil.core :as q])
  (:require [clojure.core.match :refer [match]]))

(defn print-state [state]
  (q/text-size 20)
  (q/text (str state) 20 20))

(keyword "sort-mode" "red")
(keyword "sort-mode" "green")
(keyword "sort-mode" "blue")
(keyword "sort-mode" "hue")
(keyword "sort-mode" "saturation")
(keyword "sort-mode" "brightness")
(keyword "sort-mode" "grayscale")
(keyword "sort-mode" "alpha")

(defn sort-colors [mode colors]
  (match [mode]
         :sort-mode/red colors
         :sort-mode/green colors
         :sort-mode/blue colors
         :sort-mode/hue colors
         :sort-mode/saturation colors
         :sort-mode/brightness colors
         :sort-mode/grayscale colors
         :sort-mode/alpha colors
         :else colors))

