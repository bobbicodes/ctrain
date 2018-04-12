(ns ctrain.core
  (:require [clojure.string :as s]
            [clojure.set])
  (:gen-class))

(declare -main)

(def problems
  (read-string (slurp "problems")))
  
(defn final [results]
  (loop [coll results]
    (if (empty? coll)
      (do
      (spit "prob" (inc (read-string (slurp "prob"))))
      (println "\nGOOD JOB! Here's the next one:")
       (Thread/sleep 1000)
      (-main)))
   (if (= false (first coll))
       (do
          (println "\nNope... try again or Ctrl+C to quit")
          (Thread/sleep 1000)
       (-main)))
(recur (rest coll))))

(defn evaluator [answers]
  (loop [totest answers results []]
    (if (empty? totest)
      (final results)
      (recur (rest totest) (conj results (eval (read-string (first totest))))))))

(defn replacer [n]
  (let [ans (slurp (str "ans-" n))]
    (if (= ans "")
        (do
          (println "Nice try, but blank answers are not allowed.")
          (Thread/sleep 1000)
          (-main)))
(loop [tests (:tests (problems (- n 1))) replaced []]
  (if (empty? tests)
      (evaluator replaced)
      (recur (rest tests) (conj replaced (s/replace (first tests) "__" ans)))))))


(defn problem [n]
  (println (str "\n#" n ": " (:title (nth problems (- n 1)))))
   (println (str "\n" (:description (nth problems (- n 1)))) "\n")
   (run! println (:tests (nth problems (- n 1))))
   (spit (str "ans-" n)(read-line)))

(defn -main []
  (let [n (read-string (slurp "prob"))]
    (problem n)
  (replacer n)))
