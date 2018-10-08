(ns ctrain.core
  (:require [clojure.string :as s]
                      [clojure.set :refer :all])
  (:gen-class))

(declare -main)

(def problems
  (read-string (slurp "progress.edn")))

(defn tester [results]
  (loop [coll results]
    (if (empty? coll)
      (do
       (println "\nNICE! Here's the next one:")
       (Thread/sleep 1500)
       (-main)))
    (if (= false (first coll))
       (do
          (println "\nSorry, try again...")
          (Thread/sleep 1500)
          (-main)))
    (recur (rest coll))))

(defn evaluate [answers]
  (loop [answers answers results []]
    (if (empty? answers)
      results
      (recur
        (rest answers)
        (conj results
          (try
             (eval (read-string (first answers)))
             (catch Exception e
               (println (str "Error evaluating: " (class e) ":" (.getMessage e)))
               (.printStackTrace e)
               false)))))))

(defn get-answer [problems n]
  (:answer (nth problems (dec n))))

(defn prob-num [m]
    (loop [n 1]
      (if (get-answer m n)
          (recur (inc n))
          (dec n))))

(defn get-current-answer [problems]
    (get-answer problems (prob-num problems)))

(defn get-tests [problems]
  (:tests (nth problems (dec (prob-num problems)))))

(defn replacer [problems]  
    (s/replace (get-tests problems)
               "__"
               (get-current-answer problems)))

(defn get-problem [n]
  (nth problems (dec n))) 

(defn answer [n]
  (spit "progress.edn"
        (assoc-in problems [(dec n) :answer]
                  (read-line))))

(defn print-problem [n]
  (println (str "\n#" n ": " (:title (get-problem n))))
  (println (str "\n" (:description (get-problem n)) "\n"))
  (run! println (:tests (get-problem n))))

(defn -main []
  (let [n (prob-num (read-string (slurp "progress.edn")))]
    (print-problem n)
    (answer n)
    (replacer n)))

; (get-current-answer (read-string (slurp "progress.edn")))