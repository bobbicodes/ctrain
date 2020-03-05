(ns ctrain.core2
  (:require [clojure.edn :as edn]
            [clojure.string :as str]))

(def problems 
  (edn/read-string (slurp "resources/problems.edn")))

(defn prompt [n]
    (println (str "\n#" n ": " (:title (nth problems (dec n)))))
    (println (str "\n" (:description (nth problems (dec n))) "\n"))
    (run! println (:tests (nth problems (dec n)))))

(defn ans [n]
    (prompt n)
    (edn/read-string (str/replace (:tests (nth problems (dec n))) "__" (read-line))))

(defn evaluate [tests]
     (try
       (into [] (map eval (edn/read-string tests)))
       (catch Exception e
         false)))

(defn -main []
  (loop [n 1]
    (if (every? true? (evaluate (ans n)))
      (recur (inc n))
      (recur n))))

(comment
  problems
  (ans 3))