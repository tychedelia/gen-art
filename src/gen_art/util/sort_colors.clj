(ns gen-art.util.sort-colorss
  (:require [quil.core :as q]
            [clojure.core.match :refer [match]]))


;; -----------------------
;; color modes
;; -----------------------
(keyword "color-mode" "hsb")
(keyword "color-mode" "rgb")

(defn current-color-mode []
  (let [color-mode (.-colorMode (.-g (quil.applet/current-applet)))]
    (if (= color-mode 3)
      (:color-mode/hsb)
      (:color-mode/rgb))))

;; -----------------------
;; sort modes
;; -----------------------
(keyword "sort-mode" "red")
(keyword "sort-mode" "green")
(keyword "sort-mode" "blue")
(keyword "sort-mode" "hue")
(keyword "sort-mode" "saturation")
(keyword "sort-mode" "brightness")
(keyword "sort-mode" "grayscale")
(keyword "sort-mode" "alpha")

;; -----------------------
;; comparators
;; -----------------------
(defn alpha-comparator [a b]
  (compare (q/alpha a) (q/alpha b)))

(defn hue-comparator [a b]
  (compare (q/hue a) (q/hue b)))

(defn saturation-comparator [a b]
  (compare (q/saturation a) (q/saturation b)))

(defn brightness-comparator [a b]
  (compare (q/brightness a) (q/brightness b)))

(defn red-comparator [a b]
  (compare (q/red a) (q/red b)))

(defn green-comparator [a b]
  (compare (q/green a) (q/green b)))

(defn blue-comparator [a b]
  (compare (q/blue a) (q/blue b)))

;; RGB / HSB -> grayscale is complicated
(defn to-grayscale [color-mode c]
  (if (= color-mode :color-mode/hsb)
    0 ;; TODO: figure out how to create hex -> int after reading rgb
    (+ (* (q/red c) 0.3) (* (q/green c) 0.59) (* (q/blue c) 0.11))))

(defn grayscale-comparator [a b]
  (compare (to-grayscale a) (to-grayscale b)))

;; -----------------------
;; handlers
;; -----------------------
(defn red [colors] (sort red-comparator colors))
(defn green [colors] (sort green-comparator colors))
(defn blue [colors] (sort blue-comparator colors))
(defn hue [colors] (sort hue-comparator colors))
(defn saturation [colors] (sort saturation-comparator colors))
(defn brightness [colors] (sort brightness-comparator colors))
(defn grayscale [colors] (sort grayscale-comparator colors))

(defn alpha [colors] (sort alpha-comparator colors))

;; -----------------------
;; sort
;; -----------------------
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


(sort-colors :sort-mode/red [1 2 3])
