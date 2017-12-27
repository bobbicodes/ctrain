(ns ctrain.core
  (:require [clojure.string :as s])
  (:gen-class))

(declare -main)

(def problems
  (read-string (slurp "problems")))

(def ansi-styles
  {:red   "[31m"
   :green "[32m"
   :blue  "[34m"
   :reset "[0m"})

(defn ansi
  "Produce a string which will apply an ansi style"
  [style]
  (str \u001b (style ansi-styles)))

(defn colorize
  "Apply ansi color to text"
  [text color]
  (str (ansi color) text (ansi :reset)))

(defn final [results]
  (loop [coll results]
    (if (empty? coll)
      (do
      (spit "prob" (inc (read-string (slurp "prob"))))
      (println "")
      (println (colorize "GOOD JOB! Here's the next one:" :green))
      (-main)))
   (if (= false (first coll))
       (do
          (println "")
          (println (colorize "Nope... try again or Ctrl+C to quit" :red))
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
          (-main)))
(loop [tests (:tests (problems (- n 1))) replaced []]
  (if (empty? tests)
      (evaluator replaced)
      (recur (rest tests) (conj replaced (s/replace (first tests) "__" ans)))))))

(defn problem [n]
  (println (str "#" n " " (:title (nth problems (- n 1)))))
  (println "")
   (println (str (:description (nth problems (- n 1)))))
   (println "")
   (run! println (:tests (nth problems (- n 1)))))

(defn -main []
  (println "")
  (let [n (read-string (slurp "prob"))]
    (problem n)
  (replacer n)))
