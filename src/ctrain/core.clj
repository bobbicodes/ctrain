(ns ctrain.core
  (:require [clojure.string :as s]
	    [clojure.java.io :as io])
  (:gen-class))

(def problems (read-string (slurp (io/resource "problems"))))

(defn get-input
  "Waits for user to enter text and hit enter, then cleans the input"
  ([] (get-input ""))
  ([default]
     (let [input (s/trim (read-line))]
       (if (empty? input)
         default
         input))))

(defn replacer [n]
  (let [ans (get-input)]
  (str "=> " (eval (read-string (s/replace (first (:tests (nth problems (- n 1)))) "__" ans))))))

(defn problem [n]
 (println (str (:title (nth problems (- n 1)))))
   (println (str (:description (nth problems (- n 1)))))
   (println "")
  (println (first (:tests (nth problems (- n 1)))))
  (println (replacer n))
  (println ""))

(defn prompt-prob
  []
  (loop [prob 1]
    (problem prob)
    (recur (inc prob))))

(defn -main
  [& args]
  (println "")
  (println "Welcome to C-Train,")
  (println "Your very own Clojure Training App.")
  (println "")
  (println "Enter a value to make the expression")
  (println "evaluate as true.")
  (println "")
  (prompt-prob))
