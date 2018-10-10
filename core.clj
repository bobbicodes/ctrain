(ns ctrain.core
  (:require [clojure.string :as s]
                      [clojure.set :refer :all]))

(def problems (read-string (slurp "problems.edn")))

  (defn print-prob [n]
    (println (str "\n#" n ": " (:title (nth problems (dec n)))))
    (println (str "\n" (:description (nth problems (dec n))) "\n"))
    (run! println (:tests (nth problems (dec n)))))

  (defn ans [n]
    (print-prob n)
    (read-string (s/replace (:tests (nth problems (dec n))) "__" (read-line))))

  (defn evaluate [tests]
    (loop [tests tests results []]
      (if (empty? tests)
        results
        (recur
         (rest tests)
         (conj results
               (try
                 (eval (read-string (first tests)))
                 (catch Exception e
                   false)))))))

(defn -main []
  (loop [n 1]
    (if (every? true? (evaluate (ans n)))
      (recur (inc n))
      (recur n))))