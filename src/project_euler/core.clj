(ns project-euler.core)

(defn problem-1 [upper-limit]
  "If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9.
  The sum of these multiples is 23.
  Find the sum of all the multiples of 3 or 5 below 1000."
  (apply + (filter #(or (= 0 (mod % 3)) (= 0 (mod % 5)))
                 (range 3 upper-limit)))
  )

(problem-1 1000)

;;;;;;;;;;;;;;

(defn fib-seq []
  ((fn rfib [a b]
       (cons a (lazy-seq (rfib b (+ a b)))))
    0 1))

(defn problem-2 [upper-limit]
  "Each new term in the Fibonacci sequence is generated by adding the previous two terms.
  By starting with 1 and 2, the first 10 terms will be:
    1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...
  By considering the terms in the Fibonacci sequence whose values do not exceed four million,
  find the sum of the even-valued terms."
  (apply + (filter even? (take-while #(< % upper-limit) (fib-seq))))
  )

(problem-2 4000000)
