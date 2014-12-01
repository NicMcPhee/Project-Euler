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

;;;;;;;;;;;;;;

; Based on http://stackoverflow.com/a/7625207

(defn gen-primes "Generates an infinite, lazy sequence of prime numbers"
  []
  (let [reinsert (fn [table x prime]
                   (update-in table [(+ prime x)] conj prime))]
    (defn primes-step [table d]
      (if-let [factors (get table d)]
        (recur (reduce #(reinsert %1 d %2) (dissoc table d) factors)
               (inc d))
        (lazy-seq (cons d (primes-step (assoc table (* d d) (list d))
                                       (inc d))))))
    (primes-step {} 2)))

(defn factors [n]
  (loop [n n primes (gen-primes) fs #{}]
    (let [[p & ps] primes]
      (cond
       (= n 1) fs
       (= 0 (mod n p)) (recur (/ n p) primes (conj fs p))
       :else (recur n ps fs))
      )
    )
  )

(defn problem-3 [number-to-factor]
  "The prime factors of 13195 are 5, 7, 13 and 29.
   What is the largest prime factor of the number 600851475143 ?"
  (apply max (factors number-to-factor))
  )

(problem-3 600851475143)

;;;;;;;;;;;;;;;;

(defn palindromic? [n]
  (let [ns (str n)]
    (= ns (clojure.string/reverse ns))))

(defn problem-4 [start end]
  "A palindromic number reads the same both ways. The largest palindrome
  made from the product of two 2-digit numbers is 9009 = 91 × 99.

  Find the largest palindrome made from the product of two 3-digit numbers."
  (apply max
         (for [x (range start end)
               y (range x end)
               :when (palindromic? (* x y))]
           (* x y))
         ))

(problem-4 100 1000)

;;;;;;;;;;;;;;;;

(defn gcd [a b]
  (if (= b 0)
    a
    (gcd b (mod a b))))

(defn lcd [a b]
  (/ (* a b) (gcd a b)))

(defn problem-5 [upper-limit]
  "2520 is the smallest number that can be divided by each of the numbers
  from 1 to 10 without any remainder.

  What is the smallest positive number that is evenly divisible by all
  of the numbers from 1 to 20?"
  (reduce lcd (range 1 (inc upper-limit)))
  )

(problem-5 20)

;;;;;;;;;;;;;;;;

(defn problem-6 [upper-limit]
  (let [values (range (inc upper-limit))
        sum (reduce + values)
        sqrs (map #(* % %) values)
        sum-of-sqrs (reduce + sqrs)]
    (- (* sum sum) sum-of-sqrs)))

(problem-6 100)
