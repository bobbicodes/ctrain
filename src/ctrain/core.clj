(ns ctrain.core
  (:require 
   [clojure.string :as str]
   [ctrain.problems :refer [problems]]))

(declare -main)

(defn check [results]
    (when (empty? results)
      (spit "resources/prob-num" (inc (read-string (slurp "resources/prob-num"))))
      (println "\nNICE! Here's the next one:")
      (Thread/sleep 1500)
      (-main))
    (when (= false (first results))
      (println "\nSorry, try again...")
      (Thread/sleep 1500)
      (-main))
    (recur (rest results)))

(defn evaluate [answers]
  (loop [answers answers results []]
    (if (empty? answers)
      (check results)
      (recur
        (rest answers)
        (conj results
          (try
             (eval (read-string (first answers)))
             (catch Exception e
               (println (str "Error evaluating: " (class e) ":" (.getMessage e)))
               (.printStackTrace e)
               false)))))))

(defn submit [n]
  (let [ans (slurp (str "resources/answers/" n))]
    (when (= ans "")
          (-main))
    (loop [tests (:tests (problems (- n 1))) replaced []]
        (if (empty? tests)
          (evaluate replaced)
          (recur (rest tests) (conj replaced (str/replace (first tests) "__" ans)))))))

(defn prompt [n]
  (println (str "\n#" n ": " (:title (nth problems (dec n)))))
  (println (str "\n" (:description (nth problems (dec n))) "\n"))
  (run! println (:tests (nth problems (dec n))))
  (spit (str "resources/answers/" n)(read-line)))

(defn -main []
  (let [n (read-string (slurp "resources/prob-num"))]
    (prompt n)
    (submit n)))

(comment
  (-main))