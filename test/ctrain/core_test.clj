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

(deftest get-answer-test
  (is (= "4" (get-answer data 2))))

(deftest prob-num-test
  (is (= 2 (prob-num data))))

(deftest get-current-answer-test
  (is (= "4" (get-current-answer data))))