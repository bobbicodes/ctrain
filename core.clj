(ns ctrain.core
  (:require [clojure.string :as s]
                      [clojure.set :refer :all])
  (:gen-class))

(declare -main)

(def problems
  (read-string (slurp "progress.edn")))

(defn next-prob [results]
    (if (empty? results)
      (do
       (println "\nNICE! Here's the next one:")
       (Thread/sleep 1500)
       (-main)))
    (if (= false (first results))
       (do
          (println "\nSorry, try again...")
          (Thread/sleep 1500)
          (-main)))
    (recur (rest results)))

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

(defn get-last-answer [problems]
    (get-answer problems (prob-num problems)))

(defn get-tests [problems]
  (:tests (nth problems (dec (prob-num problems)))))

(defn submit [problems]  
    (s/replace (get-tests problems)
               "__"
               (get-last-answer problems)))

(defn get-problem [n]
  (nth problems (dec n))) 

(defn answer [n]
  (spit "progress.edn"
        (assoc-in problems [(dec n) :answer]
                  (read-line))))

(defn correct? [results]
    (if (empty? results)
           true
          (if (= false (first results))
                false
               (recur (next results)))))

(defn check-last [problems]
  (correct? (evaluate (read-string (submit problems)))))

(defn next-prob [problems]
  (if (check-last problems)
        (inc (prob-num problems))
        (prob-num problems)))

(defn print-problem [n]
  (println (str "\n#" n ": " (:title (get-problem n))))
  (println (str "\n" (:description (get-problem n)) "\n"))
  (run! println (:tests (get-problem n))))

(defn -main []
  (let [problems (read-string (slurp "progress.edn"))
            n (prob-num problems)]
    (print-problem n)
    (answer n)
    (next-prob (evaluate (read-string (submit problems))))))