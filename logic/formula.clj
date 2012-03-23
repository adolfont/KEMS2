;; Formula record and its functions
;; March 23th, 2012
;; Adolfo Neto

(ns formula)

(defrecord Formula [connective subformulas])

(defn print-connective [connective-symbol]
   (cond
     (= connective-symbol :not) "!"
     (= connective-symbol :and) "&"
     (= connective-symbol :or) "|"
     (= connective-symbol :implies) "->"
  )
)

(declare print-formula)

(defn print-unary-formula [formula]
	(str    
		(print-connective (:connective formula))
		(print-formula (first (:subformulas formula)))               
	)
)
   

(defn print-binary-formula [formula]
	(str    "("
		(print-formula (first (:subformulas formula)))
		(print-connective (:connective formula))
		(print-formula (second (:subformulas formula)))
                ")"     	
	)
)


(defn print-formula [formula]
  (cond
     (= (:connective formula) :atom) (first (:subformulas formula))
     (= (:connective formula) :not) (print-unary-formula formula)     
     true (print-binary-formula formula) 	
  )
)







