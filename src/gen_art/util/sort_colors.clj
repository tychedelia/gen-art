(ns gen-art.util.sort-colorss
  (:require [clojure.core.match :refer [match]]))


(keyword "sort-mode" "red")
(keyword "sort-mode" "green")
(keyword "sort-mode" "blue")
(keyword "sort-mode" "hue")
(keyword "sort-mode" "saturation")
(keyword "sort-mode" "brightness")
(keyword "sort-mode" "grayscale")
(keyword "sort-mode" "alpha")

(defn red [colors] colors)
(defn green [colors] colors)
(defn blue [colors] colors)
(defn hue [colors] colors)
(defn saturation [colors] colors)
(defn brightness [colors] colors)
(defn grayscale [colors] colors)
(defn alpha [colors] colors)

(defn sort-colors [mode colors]
  (match [mode]
         [:sort-mode/red] (red colors)
         [:sort-mode/green] (green colors)
         [:sort-mode/blue] (blue colors)
         [:sort-mode/hue] (hue colors)
         [:sort-mode/saturation] (saturation colors)
         [:sort-mode/brightness] (brightness colors)
         [:sort-mode/grayscale] (grayscale colors)
         [:sort-mode/alpha] (alpha colors)
         :else colors))


(sort-colors :sort-mode/red [1])
