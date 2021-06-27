(ns keg.core-test
  (:require [keg.core :as sut]
            [clojure.test :as t]))


(t/deftest formatters-test
  (t/is (= {:function-name #'clojure.core/rest :runtime 12345}
           (sut/pour-runtime #'rest 12345 '(2 3 4 5) [1 2 3 4 5])))
  (t/is (= {:function-name #'clojure.core/rest :runtime 12345 :arguments `([1 2 3 4 5])}
           (sut/pour-runtime-and-args #'rest 12345 '(2 3 4 5) [1 2 3 4 5])))
  (t/is (= {:function-name #'clojure.core/rest :runtime 12345 :arguments `([1 2 3 4 5]) :return-value `(2 3 4 5)}
           (sut/pour-runtime-args-and-return #'rest 12345 '(2 3 4 5) [1 2 3 4 5]))))
