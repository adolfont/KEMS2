(ns kems2.core)

 ;rules

; auxiliary functions


(defn extract-left [formula]
  (second (second formula))
)

(defn extract-right [formula]
  (nth (second formula) 2)
)

(defn sign [formula]
  (first formula)
)

; a signed-formula is [:sign [:connective rest]]
(defn connective [formula]
    (first (second formula))
)

(defn extract-sign-and-connective [formula]
    (second (second formula))
)

;;; linear rules
;; NOT
(defn t-not [formula]
     (if 
        (and 
        (= (sign formula) :t)
        (= (connective formula) :not)
        )
        [:f (extract-sign-and-connective formula)]
        formula)
)

(defn f-not [formula]
     (if 
        (and 
        (= (sign formula) :f)
        (= (connective formula) :not)
        )
        [:t (extract-sign-and-connective formula)]
        formula)
)


;; Auxiliary function for
;; T-AND
;; F-OR
;; F-IMPLIES
;; and possibly many others

(defn binary-connective-linear [premise-sign rule-connective conclusion-sign-left conclusion-sign-right formula]
     (if 
        (and 
        (= (sign formula) premise-sign)
        (= (connective formula) rule-connective)
        )
        [[conclusion-sign-left (extract-left formula)] [conclusion-sign-right (extract-right formula)]]
        formula)
)


;; AND
(def t-and (partial binary-connective-linear :t :and :t :t))


;; OR
(def f-or (partial binary-connective-linear :f :or :f :f))


;; IMPLIES
(def f-implies (partial binary-connective-linear :f :implies :t :f))


;; Auxiliary functions for
;; F-AND
;; T-OR
;; T-IMPLIES
;; and possibly many others

(defn left-subformula [signed-formula]
  (extract-sign-and-connective signed-formula))

(defn right-subformula [signed-formula]
  (extract-right signed-formula))
  
  
(defn formula [signed-formula]
  (second signed-formula))


(defn binary-connective-two-premises [main-premise-sign main-premise-connective 
	  auxiliary-premise-sign 
	  auxiliary-subformula-function-extractor
	  conclusion-sign
	  conclusion-subformula-extractor
      main-premise auxiliary-premise]
   (if (and 
         (= (sign main-premise) main-premise-sign)
         (= (connective main-premise) main-premise-connective)
         (= (sign auxiliary-premise) auxiliary-premise-sign)
         (= (apply auxiliary-subformula-function-extractor [main-premise]) (formula auxiliary-premise))
        )
        [conclusion-sign (apply conclusion-subformula-extractor [main-premise])]
        main-premise
   )
)

(def f-and-left (partial binary-connective-two-premises :f :and :t left-subformula :f right-subformula))
(def f-and-right (partial binary-connective-two-premises :f :and :t right-subformula :f left-subformula))

;; T-OR

(def t-or-left (partial binary-connective-two-premises :t :or :f left-subformula :t right-subformula))
(def t-or-right (partial binary-connective-two-premises :t :or :f right-subformula :t left-subformula))

;; T-IMPLIES

(def t-implies-left (partial binary-connective-two-premises :t :implies :t left-subformula :t right-subformula))
(def t-implies-right (partial binary-connective-two-premises :t :implies :f right-subformula :f left-subformula))

;; closing rule auxiliary functions

(defn conjugate-formulas [formula-1 formula-2]
  (and 
    (or 
      (and (= (sign formula-1) :t) (= (sign formula-2) :f))
      (and (= (sign formula-1) :f) (= (sign formula-2) :t))
    )
    (= (formula formula-1) (formula formula-2))
  )
)


(defn sign-is-template [sign-symbol formula]
   (= sign-symbol (sign formula)))
   
;; (def sign-is-true (partial sign-is-template :t))


(defn select-formulas-by-sign [sign formulas]
  (filter (partial sign-is-template sign) formulas)
)

;; closing rule 

(defn ordered-intersect [a b] (filter (set b) a))  ;; from https://groups.google.com/forum/#!topic/clojure/uNZWCMbItS8

(defn closing-rule [signed-formula-list] 
  (if (not (empty? (ordered-intersect (map formula (select-formulas-by-sign :t signed-formula-list)) (map formula (select-formulas-by-sign :f signed-formula-list)))))
      [:closed]
      signed-formula-list))
;  (println (ordered-intersect (map formula (select-formulas-by-sign :t [[:t :a] [:f :a]])) (map formula (select-formulas-by-sign :f [[:t :a] [:f :a]]))))
      


;; --- main ---

(defn -main 
  "Hello, KEMS2!"
  [& args]
  (println "Hello, KEMS2!")
)
