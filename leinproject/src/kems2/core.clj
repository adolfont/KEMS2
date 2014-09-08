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


;; Auxiliary function for
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

(defn f-and-left [main-premise auxiliary-premise]
   (if (and 
         (= (sign main-premise) :f)
         (= (connective main-premise) :and)
         (= (sign auxiliary-premise) :t)
         (= (left-subformula main-premise) (formula auxiliary-premise))
        )
        [(sign main-premise) (right-subformula main-premise)]
        main-premise
   )
)

(defn f-and-right [main-premise auxiliary-premise]
   (if (and 
         (= (sign main-premise) :f)
         (= (connective main-premise) :and)
         (= (sign auxiliary-premise) :t)
         (= (right-subformula main-premise) (formula auxiliary-premise))
        )
        [(sign main-premise) (left-subformula main-premise)]
        main-premise
   )
)


(defn binary-connective-two-premises [main-premise-sign main-premise-connective 
	  auxiliary-premise-sign 
	  auxiliary-subformula-function-extractor
	  conclusion-subformula-extractor
      main-premise auxiliary-premise]
   (if (and 
         (= (sign main-premise) main-premise-sign)
         (= (connective main-premise) main-premise-connective)
         (= (sign auxiliary-premise) auxiliary-premise-sign)
         (= (apply auxiliary-subformula-function-extractor [main-premise]) (formula auxiliary-premise))
        )
        [(sign main-premise) (apply conclusion-subformula-extractor [main-premise])]
        main-premise
   )
)

(def new-f-and-left (partial binary-connective-two-premises :f :and :t left-subformula right-subformula))
(def new-f-and-right (partial binary-connective-two-premises :f :and :t right-subformula left-subformula))

;; continuar fazendo o mesmo para T-OR


(defn -main 
  "Hello, KEMS2!"
  [& args]
  (println "Hello, KEMS2!")
;  (println (f-implies [:f [:implies :a :b]]))
)
