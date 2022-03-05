(ns core-functions.core
  (:gen-class)
  (:require
   [clojure.string :as string]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(map inc [1 2 3]) ; (2 3 4)

(map str ["a" "b" "c"] ["A", "B", "C"]) ; ("aA" "bB" "cC")

(def human-consumption [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0, 0.2, 0.3, 1.1])

(defn unify-diet-data
  [human critter]
  {:human human
   :critter critter})

(map unify-diet-data human-consumption critter-consumption)
; ({:human 8.1, :critter 0.0}
;  {:human 7.3, :critter 0.2}
;  {:human 6.6, :critter 0.3}
;  {:human 5.0, :critter 1.1})

(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))

(stats [3 4 10]) ; (17 3 17/3)

(stats [80 1 44 13 6]) ; (144 5 144/5)

(reduce (fn [new-map [key val]]
          (assoc new-map key (inc val)))
        {}
        {:max 30 :min 10}) ; {:max 31, :min 11}

(assoc (assoc {} :max (inc 30))
       :min (inc 10)) ; {:max 31, :min 11}

(reduce (fn [new-map [key val]]
          (if (> val 4)
            (assoc new-map key val)
            new-map))
        {}
        {:human 4.1
         :critter 3.9}) ; {:human 4.1}

(take 3 [1 2 3 4 5 6 7 8 9 10]) ; (1 2 3)
(drop 3 [1 2 3 4 5 6 7 8 9 10])
; (4 5 6 7 8 9 10)

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

(take-while #(< (:month %) 3) food-journal)
; ({:month 1, :day 1, :human 5.3, :critter 2.3}
;  {:month 1, :day 2, :human 5.1, :critter 2.0}
;  {:month 2, :day 1, :human 4.9, :critter 2.1}
;  {:month 2, :day 2, :human 5.0, :critter 2.5})

(drop-while #(< (:month %) 3) food-journal)
; ({:month 3, :day 1, :human 4.2, :critter 3.3}
;  {:month 3, :day 2, :human 4.0, :critter 3.8}
;  {:month 4, :day 1, :human 3.7, :critter 3.9}
;  {:month 4, :day 2, :human 3.7, :critter 3.6})

(take-while #(< (:month %) 4)
            (drop-while #(< (:month %) 2) food-journal))
; ({:month 2, :day 1, :human 4.9, :critter 2.1}
;  {:month 2, :day 2, :human 5.0, :critter 2.5}
;  {:month 3, :day 1, :human 4.2, :critter 3.3}
;  {:month 3, :day 2, :human 4.0, :critter 3.8})

(filter #(< (:human %) 5) food-journal)
; ({:month 2, :day 1, :human 4.9, :critter 2.1}
;  {:month 3, :day 1, :human 4.2, :critter 3.3}
;  {:month 3, :day 2, :human 4.0, :critter 3.8}
;  {:month 4, :day 1, :human 3.7, :critter 3.9}
;  {:month 4, :day 2, :human 3.7, :critter 3.6})

(some #(> (:critter %) 5) food-journal) ; nil

(some #(> (:critter %) 3) food-journal) ; true

(sort [3 1 2]) ; (1 2 3)

(sort-by count ["aaa" "c" "bb"]) ; ("c" "bb" "aaa")

(concat [1 2] [3 4]) ; (1 2 3 4)

(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true  :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true  :name "McMackson"}
   2 {:makes-blood-puns? true,  :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true,  :has-pulse? true  :name "Mickey Mouse"}})

(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 1000)
  (get vampire-database social-security-number))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))
       record))

(defn identify-vampire
  [social-security-numbers]
  (first (filter vampire?
                 (map vampire-related-details social-security-numbers))))

(time (vampire-related-details 0)) ; {:makes-blood-puns? false, :has-pulse? true, :name "McFishwich"}

(def mapped-details (map vampire-related-details (range 0 10000000)))

;(time (identify-vampire (range 0 100000)))

(concat (take 8 (repeat "na")) ["Batman!"])

(take 3 (repeatedly (fn [] (rand-int 10))))

(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))

(take 20 (even-numbers)) ; (0 2 4 6 8 10 12 14 16 18 20 22 24 26 28 30 32 34 36 38)

(map identity {:sunlight-reaction "Glitter!"})

(into {} (map identity {:sunlight-reaction "Glitter!"}))

(into {:favorite-emotion "gloomy"} [[:sunlight-reaction "Glitter!"]]) ; {:favorite-emotion "gloomy", :sunlight-reaction "Glitter!"}

(into ["cherry"] '("pine" "spruce")) ; ["cherry" "pine" "spruce"]

(conj [0] [1]) ; [0 [1]]
(into [0] [1]) ; [0 1]

(conj [0] 1) ; [0 1]

(max 1 2 3) ; 3

(apply max [0 1 2]) ; 2

(def add10 (partial + 10))

(add10 3) ; 13
(add10 5) ; 15

(def add-missing-elements
  (partial conj ["water" "earth" "air"]))

(add-missing-elements "unobtainium" "adamantium")

(defn lousy-logger
  [log-level message]
  (condp = log-level
    :warn (string/lower-case message)
    :emergency (string/upper-case message)))

(def warn (partial lousy-logger :warn))
(def emergency (partial lousy-logger :emergency))

(warn "Red light ahead") ; "red light ahead"
(emergency "Red light ahead") ; "RED LIGHT AHEAD"

(def not-vampire? (complement vampire?))

(defn identify-humans
  [social-security-numbers]
  (filter not-vampire?
          (map vampire-related-details social-security-numbers)))


