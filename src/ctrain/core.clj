(ns ctrain.core
  (:require [clojure.string :as s])
  (:gen-class))

(def problems
  (read-string (slurp "problems")))

(defn evaluator [n]
 (if (= true
        (eval (read-string (s/replace
        (first (:tests (nth problems (- n 1)))) "__" (read-line)))))
     (do
       (spit "prob" (inc n))
       (println "")
       (str "GOOD JOB"))))

(defn problem [n]
  (println (str (:title (nth problems (- n 1)))))
  (println "")
   (println (str (:description (nth problems (- n 1)))))
   (println "")
  (println (first (:tests (nth problems (- n 1)))))
  (println (evaluator n))
  (println ""))

(defn -main []
  (println "")
  (problem (read-string (slurp "prob")))
  (-main))
