(ns ctrain.core
  (:require 
   [clojure.string :as str]
   [clojure.set]
   [ctrain.problems :refer [problems]]))

(declare -main)

(defn prompt []
  (let [n (read-string (slurp "resources/prob-num"))]
    (println (str "\n#" n ": " (:title (nth problems (dec n)))))
    (println (str "\n" (:description (nth problems (dec n))) "\n"))
    (run! println (:tests (nth problems (dec n))))
    (spit (str "resources/answers/" n) (read-line))))

(defn reject []
  (println "\nSorry, try again...")
  (Thread/sleep 1500)
  (-main))

(defn advance-prob-num! []
  (spit "resources/prob-num"
        (inc (read-string (slurp "resources/prob-num")))))

(defn pass []
  (println "\nNICE! Here's the next one:")
  (Thread/sleep 1500)
  (advance-prob-num!)
  (-main))

(defn check [results]
  (cond (empty? results) (pass)
        (= false (first results)) (reject)
        :else (recur (rest results))))

(defn evaluate [answers]
  (loop [answers answers
         results []]
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

(defn submit []
  (let [n (read-string (slurp "resources/prob-num"))
        ans (slurp (str "resources/answers/" n))]
    (loop [tests    (:tests (problems (- n 1)))
           replaced []]
      (if (empty? tests)
        (evaluate replaced)
        (recur (rest tests) (conj replaced (str/replace (first tests) "__" ans)))))))

(defn -main []
  (prompt)
  (submit))

(comment
)