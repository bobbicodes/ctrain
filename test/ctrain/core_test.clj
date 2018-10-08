(ns ctrain.core-test
  (:require [clojure.test :refer :all]
            [ctrain.core :refer :all]))

(def data
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
  (is (= "[1 2 3 4]" (get-answer data 2))))

(deftest prob-num-test
  (is (= 2 (prob-num data))))

(deftest get-last-answer-test
  (is (= "[1 2 3 4]" (get-last-answer data))))

(deftest get-tests-test
  (is (= ["(= __ (conj [1 2 3] 4))" "(= __ (conj [1 2] 3 4))"]
             (get-tests data))))

(deftest submit-test
  (is (= "[\"(= [1 2 3 4] (conj [1 2 3] 4))\" \"(= [1 2 3 4] (conj [1 2] 3 4))\"]"
                       (submit data))))

(deftest evaluate-test
  (is (= [true true]
              (evaluate (read-string (submit data))))))