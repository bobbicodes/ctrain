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
        (do
          (println "You must enter something.")
          (Thread/sleep 1000)
          (-main)))
    (loop [tests (:tests (problems (- n 1))) replaced []]
        (if (empty? tests)
          (evaluator replaced)
          (recur (rest tests) (conj replaced (s/replace (first tests) "__" ans)))))))

(defn get-problem [n]
  (nth problems (dec n))) 

(defn answer [n]
  (spit "progress.edn" (assoc-in problems [(dec n) :answer] (read-line))))

(defn problem [n]
  (println (str "\n#" n ": " (:title (get-problem n))))
  (println (str "\n" (:description (get-problem n)) "\n"))
  (run! println (:tests (get-problem n)))
  (spit (str "ans-" n)(read-line)))

(defn -main []
  (let [n (read-string (slurp "prob-num"))]
    (problem n)
    (replacer n)))
