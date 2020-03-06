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

(defn safe-eval [ans]
  (try (eval (read-string ans))
    (catch Exception e
      (println (str "\nError evaluating: " (class e) ":" (.getMessage e)))
      false)))

(defn submit []
  (let [n (read-string (slurp "resources/prob-num"))
        ans (slurp (str "resources/answers/" n))]
    (loop [tests    (:tests (problems (- n 1)))
           replaced []]
      (if (empty? tests)
        (check (reduce conj [] (map safe-eval replaced)))
        (recur (rest tests) (conj replaced (str/replace (first tests) "__" ans)))))))

(defn -main []
  (prompt)
  (submit))

(comment
)
