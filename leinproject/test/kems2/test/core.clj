(ns kems2.test.core
  (:use [kems2.core])
  (:use [clojure.test]))

;; f-not  
  
(deftest f-not-test-1-applied
  (is (= (f-not [:f [:not :a]]) [:t :a])))
  
(deftest f-not-test-2-not-applied
  (is (= (f-not [:t [:not :a]]) [:t [:not :a]])))
  
(deftest f-not-test-3-applied
  (is (= (f-not [:t [:not [:and :a :b]]]) [:t [:not [:and :a :b]]] )))

(deftest f-not-test-4-applied
  (is (= (f-not [:f [:not [:and :a :b]]]) [:t [:and :a :b]])))

;; t-not  
  
(deftest t-not-test-1-applied
 (is (= (t-not [:t [:and :a :b]]) [:t [:and :a :b]])))
 
(deftest t-not-test-2-applied
 (is (= (t-not [:t [:not :b]]) [:f :b])))

(deftest t-not-test-3-applied
 (is (= (t-not [:t [:not :b]]) [:f :b])))

(deftest t-not-test-4-applied
 (is (= (t-not [:f [:not :b]]) [:f [:not :b]])))
 
;; t-and

(deftest t-and-1-not-applied
  (is (= (t-and [:t [:not [:and :a :b]]])  [:t [:not [:and :a :b]]] )))
                                                                                                  
(deftest t-and-2-applied
 (is (= (t-and [:t [:and :a :b]]) [[:t :a] [:t :b]] )))


(deftest t-and-3-applied 
 (is (= (t-and [:t [:and [:and :a :b] [:not :b]]])
     [[:t [:and :a :b]] [:t [:not :b]]]
 )))

;; f-or 
 
(deftest f-or-1-applied
 (is (= (f-or [:f [:or :a :b]])
      [[:f :a] [:f :b]]
 )))
 
(deftest f-or-2-applied
 (is (= (f-or [:f [:or [:and :a :b] [:not :b]]])
 [[:f [:and :a :b]] [:f [:not :b]]]
 )))

(deftest f-or-3-not-applied
 (is (= (f-or [:t [:or [:and :a :b] [:not :b]]])
 [:t [:or [:and :a :b] [:not :b]]]
 )))

;; f-implies
 
(deftest f-implies-1-applied
  (is (= (f-implies [:f [:implies :a :b]]) [[:t :a] [:f :b]])))
 
(deftest f-implies-2-applied
  (is (=
   (f-implies [:f [:implies [:and :a :b] [:not :b]]])
   [[:t [:and :a :b]] [:f [:not :b]]]
 )))
 
(deftest f-implies-3-not-applied
  (is (=
   (f-implies [:f [:and [:and :a :b] [:not :b]]])
   [:f [:and [:and :a :b] [:not :b]]]
 ))) 
 
 
 
 
;; f-and


;; auxiliary functions

;; left-subformula

(deftest left-subformula-1
  (is (= (left-subformula [:f [:and :a :b]]) :a)))

(deftest left-subformula-2
  (is (= (left-subformula [:t [:or [:and :a :b] :c]]) [:and :a :b])))  

  
;; right-subformula
  
(deftest right-subformula-1
  (is (= (right-subformula [:f [:and :a :b]]) :b)))  
  
;; formula

(deftest formula-1
  (is (= (formula [:t :a]) :a)))

(deftest formula-2
  (is (= (formula [:t [:and :a :b]]) [:and :a :b])))
  

(deftest f-and-left-1-not-applied
 (is (= (f-and-left [:f [:and :a :b]] [:f :a]) [:f [:and :a :b]])))

(deftest f-and-left-2-applied
 (is (= (f-and-left [:f [:and :a :b]] [:t :a]) [:f :b])))

(deftest f-and-right-1-not-applied
 (is (= (f-and-right [:f [:and :a :b]] [:t :a]) [:f [:and :a :b]])))

(deftest f-and-right-2-applied
 (is (= (f-and-right [:f [:and :a :b]] [:t :b]) [:f :a])))

 
(deftest new-f-and-left-1-not-applied
 (is (= (new-f-and-left [:f [:and :a :b]] [:f :a]) [:f [:and :a :b]])))

(deftest new-f-and-left-2-applied
 (is (= (new-f-and-left [:f [:and :a :b]] [:t :a]) [:f :b])))
 
 
(deftest new-f-and-right-1-not-applied
 (is (= (new-f-and-right [:f [:and :a :b]] [:t :a]) [:f [:and :a :b]])))

(deftest new-f-and-right-2-applied
 (is (= (new-f-and-right [:f [:and :a :b]] [:t :b]) [:f :a])))

 
 ;;   
;; (deftest f-and-2-applied
;;   (is (= (f-and [:f [:and :a :b]] [:t :a]) [:f :b])))

;; F A&B + T A = F B
;; F A&B + T B = F A 
 