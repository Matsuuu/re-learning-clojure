(ns fwpd.core
  (:require
   [clojure.string :as string]))

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(string/split % #",")
       (string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Foo\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

;(first (mapify (parse (slurp filename)))) ; {:name "Edward Cullen", :glitter-index 10}

(def mapped-people (mapify (parse (slurp filename))))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(glitter-filter 3 mapped-people)
; ({:name "Edward Cullen", :glitter-index 10}
;  {:name "Jacob Black", :glitter-index 3}
;  {:name "Carlisle Cullen", :glitter-index 6})

; List names
(map :name (glitter-filter 3 mapped-people))

(defn append
  [suspect]
  (spit filename
        (str (:name suspect) "," (:glitter-index suspect) "\n")
        :append true))

;; Append to file of suspects
;(append {:name "Matias Huhta" :glitter-index 2})

(defn has-field?
  [field-keyword suspect]
  (contains? suspect field-keyword))

(defn validate
  [req-fields suspect]
  (every? #(has-field? % suspect) req-fields))

(def matias {:name "Matias Huhta" :glitter-index 2})
(def invalid-user {:foo "Bar" :glitter-index 2})

; Validate that all fields are present
(validate vamp-keys matias) ; true
(validate vamp-keys invalid-user) ; false

(defn map->string
  [suspect]
  (str (:name suspect) "," (:glitter-index suspect)))

(defn map->csv-string
  [suspect-map]
  (string/join "\n" (map map->string suspect-map)))

(map->csv-string mapped-people)

(spit "test.csv" (map->csv-string mapped-people))
