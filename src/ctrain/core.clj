(ns ctrain.core
  (:require [clojure.string :as s])
  (:gen-class))

(declare -main)

(def problems
  (read-string (slurp "problems")))

(defn evaluator [n]
  (let [ans (read-line)]
    (if (= ans "")
        (-main))  
  (if (= true
    (eval (read-string (s/replace
      (first (:tests (nth problems (- n 1)))) "__" ans))))
    (do
      (spit "prob" (inc n))
      (println "")
      (str "GOOD JOB")))))

(defn problem [n]
  (println (str "#" n " " (:title (nth problems (- n 1)))))
  (println "")
   (println (str (:description (nth problems (- n 1)))))
   (println "")
   (run! println (:tests (nth problems (- n 1))))
  (println (evaluator n)))

(defn -main []
  (println "")
  (problem (read-string (slurp "prob")))
  (-main))
