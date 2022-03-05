(ns hobbit.exercises
  (:require
   [clojure.string :as string]))

(defn add-hundred
  [num]
  (+ num 100))

(add-hundred 250) ; 350

(defn dec-maker
  [dec-by]
  #(- % dec-by))

(def dec9 (dec-maker 9))

(dec9 10) ; 1

(defn mapset
  [f coll]
  (apply hash-set (map f coll)))

; Testing
(apply hash-set [1 2 3 2]) ; #{1 3 2}

(mapset inc [1 1 2 2]) ; #{3 2}

