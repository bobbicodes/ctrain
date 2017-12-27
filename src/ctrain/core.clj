(ns ctrain.core
  (:require [clojure.string :as s]
            [clojure.set])
  (:gen-class))

(declare -main)

(def problems
  (read-string (slurp "problems")))

(def ansi-styles
  {:red   "[31m"
   :green "[32m"
   :reset "[0m"})

(defn ansi [style]
  (str \u001b (style ansi-styles)))

(defn colorize
  [text color]
  (str (ansi color) text (ansi :reset)))

(defn final [results]
  (loop [coll results]
    (if (empty? coll)
      (do
      (spit "prob" (inc (read-string (slurp "prob"))))
      (println (colorize "\nGOOD JOB! Here's the next one:" :green))
       (Thread/sleep 1000)
      (-main)))
   (if (= false (first coll))
       (do
          (println (colorize "\nNope... try again or Ctrl+C to quit" :red))
          (Thread/sleep 1000)
       (-main)))
(recur (rest coll))))

(defn evaluator [answers]
  (loop [totest answers results []]
    (if (empty? totest)
      (final results)
      (recur (rest totest) (conj results (eval (read-string (first totest))))))))

(defn replacer [n]
  (let [ans (read-line)]
    (if (= ans "")
        (do
          (println (colorize "Nice try, but blank answers are not allowed." :red))
          (Thread/sleep 1000)
          (-main)))
(loop [tests (:tests (problems (- n 1))) replaced []]
  (if (empty? tests)
      (evaluator replaced)
      (recur (rest tests) (conj replaced (s/replace (first tests) "__" ans)))))))

(defn problem [n]
  (println (str "\n#" n ": " (:title (nth problems (- n 1)))))
   (println (str "\n" (:description (nth problems (- n 1)))) "\n")
   (run! println (:tests (nth problems (- n 1)))))

(defn -main []
  (let [n (read-string (slurp "prob"))]
    (problem n)
  (replacer n)))
