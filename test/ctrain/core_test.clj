(ns ctrain.core-test
  (:require [clojure.test :refer :all]
            [ctrain.core :refer :all]))

(def data
  [{:_id 1, :title "Nothing but the Truth"
                                           :tests ["(= __ true)"]
                                           :description "Complete the expression so it will evaluate to true."
                                           :answer "true"}
                                          {:_id 2, :title "Simple Math"
                                            :tests ["(= (- 10 (* 2 3)) __)"]
                                            :description "Innermost forms are evaluated first."
                                            :answer "4"}
                                          {:_id 3, :title "Strings"
                                            :tests ["(= __ (.toUpperCase \"eat me\"))"]
                                            :description "Clojure strings are Java strings, so you can use Java string methods on them."}])

(def other-data
  [{:_id 6, :title "Vectors"
     :tests ["(= [__] (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c))"]
     :description "Vectors can be constructed several ways.  You can compare them with lists."
      :answer ":a :b :c"}
    {:_id 7, :title "conj on vectors"
      :tests ["(= __ (conj [1 2 3] 4))" "(= __ (conj [1 2] 3 4))"]
      :description "When operating on a Vector, the conj function will return a new vector with one or more items \"added\" to the end."
      :answer "[1 2 3 4]"}
    {:_id 8, :title "Sets"
      :tests ["(= __ (set '(:a :a :b :c :c :c :c :d :d)))"
                     "(= __ (clojure.set/union #{:a :b :c} #{:b :c :d}))"]
      :description "Sets are collections of unique values."}])

(deftest get-answer-test
  (is (= "4" (get-answer data 2))))

(deftest prob-num-test
  (is (= 2 (prob-num data))))

(deftest get-current-answer-test
  (is (= "4" (get-current-answer data))))

(deftest get-tests-test
  (is (= ["(= __ (conj [1 2 3] 4))" "(= __ (conj [1 2] 3 4))"]
             (get-tests other-data))))