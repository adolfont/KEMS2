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
  

;; f-and-left  
  
(deftest f-and-left-1-not-applied
 (is (= (f-and-left [:f [:and :a :b]] [:f :a]) [:f [:and :a :b]])))

(deftest f-and-left-2-applied
 (is (= (f-and-left [:f [:and :a :b]] [:t :a]) [:f :b])))

;; f-and-right 
 
(deftest f-and-right-1-not-applied
 (is (= (f-and-right [:f [:and :a :b]] [:t :a]) [:f [:and :a :b]])))

(deftest f-and-right-2-applied
 (is (= (f-and-right [:f [:and :a :b]] [:t :b]) [:f :a])))

 
;; t-or-left

(deftest t-or-left-1-not-applied
 (is (= (t-or-left [:t [:or :a :b]] [:t :a]) [:t [:or :a :b]])))

(deftest t-or-left-2-applied
 (is (= (t-or-left [:t [:or :a :b]] [:f :a]) [:t :b])))

;; t-or-right
 
(deftest t-or-right-1-not-applied
 (is (= (t-or-right [:t [:or :a :b]] [:t :b]) [:t [:or :a :b]])))

(deftest t-or-right-2-applied
 (is (= (t-or-right [:t [:or :a :b]] [:f :b]) [:t :a])))
 
 
;; t-or-left

(deftest t-implies-left-1-not-applied
 (is (= (t-implies-left [:t [:implies :a :b]] [:f :a]) [:t [:implies :a :b]])))

(deftest t-implies-left-2-applied
 (is (= (t-implies-left [:t [:implies :a :b]] [:t :a]) [:t :b])))

;; t-or-right
 
(deftest t-implies-right-1-not-applied
 (is (= (t-implies-right [:t [:implies :a :b]] [:t :b]) [:t [:implies :a :b]])))

(deftest t-implies-right-2-applied
 (is (= (t-implies-right [:t [:implies :a :b]] [:f :b]) [:f :a]))) 
 
 
;; closing rule auxiliary functions

(deftest conjugate-formulas-1-test
  (is (conjugate-formulas [:t :a] [:f :a]))
)

(deftest conjugate-formulas-2-test
  (is (not (conjugate-formulas [:t :a] [:t :a])))
)

(deftest conjugate-formulas-3-test
   (is (not (conjugate-formulas [:t :a] [:f :b])))
)

(deftest conjugate-formulas-1-test
  (is (conjugate-formulas [:t [:and :a :b]] [:f [:and :a :b]]))
)


;; sign-is 

(deftest sign-is-template-1-test
  (is (sign-is-template :t [:t :a])))

(deftest sign-is-template-2-test
  (is (sign-is-template :f [:f :a])))
  
(deftest sign-is-template-3-test
  (is (not (sign-is-template :f [:t :a]))))

;; (deftest sign-is-true-1-test
;;   (is (sign-is-true [:t :a])))
;;   
;; (deftest sign-is-true-2-test
;;   (is (not (sign-is-true [:f :a]))))
  

(deftest select-formulas-by-sign-test-1
  (is (= (select-formulas-by-sign :t [[:t :a]]) [[:t :a]])))

(deftest select-formulas-by-sign-test-2
  (is (= (select-formulas-by-sign :t [[:f :a]]) [])))

(deftest select-formulas-by-sign-test-3
  (is (= (select-formulas-by-sign :f [[:t :a] [:f :b] [:f [:and :a :c]]]) [[:f :b] [:f [:and :a :c]]])))
  
  
  ;; closing rule

(deftest closing-1-not-applied
  (is (= (closing-rule [[:t :a] [:f :b]]) [[:t :a] [:f :b]])))
  
(deftest closing-2-applied
  (is (= (closing-rule [[:t :a] [:f :a]]) [:closed])))

(deftest closing-3-applied
  (is (= (closing-rule [[:t :a] [:t [:and :a :b]] [:f :b] [:f [:and :a :b]]]) [:closed])))

(deftest closing-4-not-applied
  (is (= (closing-rule [[:t :a] [:t [:and :a :b]] [:f :b] [:t [:and :a :b]]]) [[:t :a] [:t [:and :a :b]] [:f :b] [:t [:and :a :b]]])))
  
  