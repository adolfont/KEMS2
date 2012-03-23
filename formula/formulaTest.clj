;; Tests for the Formula record and its functions
;; March 23th, 2012
;; Adolfo Neto

(ns test
  (:use clojure.test)
  (:use formula)
  (:import [formula Formula]))


(def A (Formula. :atom ["A"]))
(def B (Formula. :atom ["B"]))

(def NOT-A (Formula. :not [A]))

(def A-AND-B (Formula. :and [A B]))

(def NOT-A-AND-B (Formula. :and [NOT-A B]))

(def NOT--A-AND-B (Formula. :not [A-AND-B]))

(deftest print-atomic-formula-test
  (is (= "A" (print-formula A)))
)

(deftest print-unary-formula-test
  (is (= "!A" (print-formula NOT-A)))
)

(deftest print-binary-formula-test
  (is (= "(A&B)" (print-formula A-AND-B)))
)

(deftest print-unary-binary-formula-test
  (is (= "!(A&B)" (print-formula NOT--A-AND-B)))
)

(deftest print-binary-unary-formula-test
  (is (= "(!A&B)" (print-formula NOT-A-AND-B)))
)


(run-tests)
