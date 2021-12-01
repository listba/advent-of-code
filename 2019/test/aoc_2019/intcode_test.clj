(ns aoc-2019.intcode-test
  (:require 
    [clojure.test :refer :all]
    [aoc-2019.intcode :as ic]))           
            
          
(deftest intcode-fetch
  (testing "fetch on empty mem returns 0 and expands mem by 1"
    (is (= 0 (:val (ic/fetch [] 0)))))

  (testing "fetch on mem returns val at ptr"
    (let [tval 5
          mem [tval]
          result (:val (ic/fetch mem 0))]
      (is (= tval result)))))

(deftest intcode-parse-op
  (testing "1001"
    (let [mem [1001 4 -106 5 106 0] iptr 0 rbase 0
          e_niptr 4 e_op 1 e_params [{:ptr 4 :val 106} {:ptr 2 :val -106} {:ptr 5 :val 0}]
          {r_niptr :niptr r_op :opcode r_params :params r_mem :mem} (ic/parse-opcode iptr rbase mem)]
      (is (= e_niptr r_niptr))
      (is (= e_op r_op))
      (is (= e_params r_params))
      (is (= mem r_mem)))))


(deftest intcode-features 
  (testing "mem copy" 
    (let [mem [109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99]
          result (vec (take (count mem) (ic/Intcode mem {})))] ; need to drop the last element since out intcode outputs a code at the end
      (is (= mem result))))

  (testing "output large"
    (let [mem [104,1125899906842624,99]
          result (ic/Intcode mem {})]
      (is (= (first result) (get mem 1)))))

  (testing "16 digit math"
    (let [mem [1102,34915192,34915192,7,4,7,99,0]
          e_result (* (get mem 1) (get mem 2))
          result (ic/Intcode mem {})]
      (is (= (first result) e_result)))))