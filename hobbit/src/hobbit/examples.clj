(ns hobbit.examples)

(let [x 3]
  x)

(def dalmatian-list
  ["Pongo" "Perdita" "Puppy 1" "Puppy 2"])

(let [[first-dog & rest-of-the-dogs] dalmatian-list]
  [first-dog rest-of-the-dogs]) ; ["Pongo" ("Perdita" "Puppy 1" "Puppy 2")]

(into [] (set [:a :a])) ; [:a]
(into [] (set [:a :b])) ; [:b :a]

(loop [iteration 0]
  (println (str "Iteration " iteration))
  (if (> iteration 3)
    (println "Goodbye!")
    (recur (inc iteration))))

(re-find #"^left-" "left-eye") ; "left-"

(re-find #"^left-" "cleft-chin") ; nil

(reduce + [1 2 3 4]) ; 10

(reduce + 15 [1 2 3 4]) ; 25

(defn my-reduce
  ([f initial coll]
   (loop [result initial
          remaining coll]
     (if (empty? remaining)
       result
       (recur (f result (first remaining)) (rest remaining)))))
  ([f [head & tail]]
   (my-reduce f head tail)))
