(ns ctrain.core-test
  (:require [clojure.test :refer :all]
            [ctrain.core :refer :all]))

(deftest get-problem-test
  (is (= (get-problem 1)
         {:_id 1, :title "Nothing but the Truth"
          :tests ["(= __ true)"]
          :description "Complete the expression so it will evaluate to true."})))
