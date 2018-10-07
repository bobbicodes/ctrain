(ns ctrain.core
  (:require [clojure.string :as s]
                      [clojure.set :refer :all])
  (:gen-class))

(declare -main)

(def problems
  (read-string (slurp "problems.edn")))

(defn tester [results]
  (loop [coll results]
    (if (empty? coll)
      (do
       (spit "prob-num" (inc (read-string (slurp "prob-num"))))
       (println "\nNICE! Here's the next one:")
       (Thread/sleep 1500)
       (-main)))
    (if (= false (first coll))
       (do
          (println "\nSorry, try again...")
          (Thread/sleep 1500)
          (-main)))
    (recur (rest coll))))

(defn evaluator [answers]
  (loop [totest answers results []]
    (if (empty? totest)
      (tester results)
      (recur
        (rest totest)
        (conj results
          (try
             (eval (read-string (first totest)))
             (catch Exception e
               (println (str "Error evaluating: " (class e) ":" (.getMessage e)))
               (.printStackTrace e)
               false)))))))

(defn replacer [n]
  (let [ans (slurp (str "ans-" n))]
    (if (= ans "")
          (-main))
    (loop [tests (:tests (problems (- n 1))) replaced []]
        (if (empty? tests)
          (evaluator replaced)
          (recur (rest tests) (conj replaced (s/replace (first tests) "__" ans)))))))

(defn get-problem [n]
  (nth problems (dec n))) 

(defn answer [n]
  (spit "progress.edn" (assoc-in problems [(dec n) :answer] (read-line))))

(defn get-answer [n]
  (:answer (nth (read-string (slurp "progress.edn")) (dec n))))


(defn problem [n]
  (println (str "\n#" n ": " (:title (get-problem n))))
  (println (str "\n" (:description (get-problem n)) "\n"))
  (run! println (:tests (get-problem n)))
  (spit (str "ans-" n)(read-line)))

(defn -main []
  (let [n (read-string (slurp "prob-num"))]
    (problem n)
    (replacer n)))

(defn prob-num []
  (let [problems (read-string (slurp "progress.edn"))]
    (loop [n 1]
      (if (get-answer n)
          (recur (inc n))
          n))))